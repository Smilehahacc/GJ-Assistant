import requests
import pymysql
import re
import time
from selenium import webdriver

from bs4 import BeautifulSoup

# server = '192.168.31.118'
server = '172.18.16.1'
user = 'root'
password = 'root'
database = 'assistant'
db = pymysql.connect(server, user, password, database)
cursor = db.cursor()
url = 'https://product.pconline.com.cn/multi/mobile' \
      '/22586_44299_37376_39474_47505_99210_47071_97992_99063_22587_98649_98337_24390_23285_32569_110524_92002/'
page = 5
result_output = ''

# 加入用户信息请求头，模拟用户请求
header = 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) ' \
         'Chrome/103.0.5060.114 Safari/537.36 Edg/103.0.1264.62'
options = webdriver.Edge()
# options.add_argument(header)
driver = webdriver.Edge()


def get_data():
    global result_output
    print('检索出的在售手机机型如下：')
    result_output += '检索出的在售手机机型如下：<br/>'
    for count in range(0, page):
        print('第' + str(count + 1) + '页')
        result_output += '第' + str(count + 1) + '页<br/>'
        html = requests.request('POST', url + str(count * 25) + 's1.shtml')
        ren = re.compile('<a href=".*?" target="_blank" class="item-title-name" title=".*?">(.*?)</a>')
        result = re.findall(ren, html.text)
        for model in result:
            print(model)
            result_output += model + '<br/>'
            insert_phone(model)
        cursor.connection.commit()
        print('第' + str(count + 1) + '页数据存入数据库成功！')
        result_output += '第' + str(count + 1) + '页数据存入数据库成功！<br/>'


# 将查询到的机型信息插入表
def insert_phone(model):
    sql = 'select * from assistant_phone where phone_model = %s'
    cursor.execute(sql, str(model))
    result = cursor.fetchall()
    # 若爬取到的数据为新数据，则插入表
    if len(result) == 0:
        sql = 'insert into assistant_phone(phone_model) VALUES(%s)'
        cursor.execute(sql, str(model))


# 插入机型数据到详细信息表
def insert_model():
    sql = 'select * from assistant_phone order by phone_id asc'
    cursor.execute(sql)
    model_list = cursor.fetchall()
    for count in range(0, len(model_list)):
        print(str(model_list[count][0]) + model_list[count][1] + str(get_date()))
        sql = 'select * from model_infor where phone_model = %s'
        cursor.execute(sql, str(model_list[count][1]))
        result = cursor.fetchone()
        if result is None:
            sql = 'insert into model_infor(phone_id, phone_model, update_date) VALUES(%s, %s, %s)'
            cursor.execute(sql, (model_list[count][0], model_list[count][1], get_date()))
    cursor.connection.commit()


def get_price():
    global result_output
    for count in range(0, page):
        print('开始查找第' + str(count + 1) + '页价格数据和机型图片')
        result_output += '开始查找第' + str(count + 1) + '页价格数据和机型图片<br/>'
        html = url + str(count * 25) + 's1.shtml'
        contents = requests.request('POST', html).text
        soup = BeautifulSoup(contents, "html.parser")
        # 将所有的机型参数标签找出来
        result = soup.find_all('li', class_='item')
        for tag in result:
            model_element = tag.find('a', class_='item-title-name')
            if model_element is not None:
                model_value = model_element.get_text()
                img_url = tag.find('img', class_='pic').get('#src')
                if img_url != '':
                    get_img(img_url, model_value)
                price_div = tag.find('div', class_='price price-now')
                if price_div is not None:
                    output_str = ''
                    try:
                        price_value = price_div.find('a').get_text().replace('￥', '')
                        output_str += model_value + '：￥' + price_value
                        # buy_link = get_buy_link(tag)
                        # sql = 'update model_infor set infor_price=%s, update_date=%s, infor_buy_link=%s ' \
                        #       'where phone_model=%s'
                        # cursor.execute(sql, (price_value, get_date(), buy_link, model_value))
                        sql = 'update model_infor set infor_price=%s, update_date=%s where phone_model=%s'
                        cursor.execute(sql, (price_value, get_date(), model_value))
                        cursor.connection.commit()
                    # except:
                    #     print(model_value + '价格存入数据库出错！')
                    finally:
                        output_str += '--价格存入数据库完成！'
                        print(output_str)
                        result_output += output_str + '<br/>'
        print('第' + str(count + 1) + '页价格及购买链接数据存入完成！')
        result_output += '第' + str(count + 1) + '页价格、购买链接和图片数据存入完成！<br/>'


def get_buy_link(tag):
    buy_link1 = tag.find('a', class_='xs1 buyLink')
    buy_link2 = tag.find('a', class_='xs2 buyLink')
    if buy_link2 is not None:
        return buy_link2.get('href')
    if buy_link1 is not None:
        return buy_link1.get('href')
    return ''


def get_img(img_url, model_value):
    global result_output
    try:
        r = requests.get('http:' + img_url)
        img_name = model_value + '.png'
        filename = '..\\..\\vue-assistant\\src\\assets\\img\\mobile\\' + img_name
        with open(filename, "wb") as f:
            f.write(r.content)
        print(model_value + '--机型图片下载完成！')
        result_output += model_value + '--机型图片下载完成！<br/>'
    except:
        print(model_value + '--机型图片下载失败！')


def get_date():
    t = time.time()
    return int(t)


def main():
    global result_output
    try:
        # 依次执行以下方法
        get_data()
        insert_model()
        get_price()
        print('所有数据更新完毕！')
        result_output += '所有数据更新完毕！<br/>'
        driver.quit()
    finally:
        # db.close()
        print('一次更新完成')
        return result_output


# 包含功能，1.从相关站点获取在售机型 2.存储机型名称至数据库 3.获取机型价格和图片（在主函数中依次执行123步骤）
if __name__ == '__main__':
    main()

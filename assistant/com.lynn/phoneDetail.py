from urllib import request
from bs4 import BeautifulSoup
from pymysql.err import raise_mysql_exception
from selenium import webdriver
from selenium.common.exceptions import NoSuchElementException
from selenium.webdriver.common.keys import Keys
import time
import pymysql
import re
import time

server = '116.62.45.163'
user = 'root'
password = 'root'
database = 'assistant'
db = pymysql.connect(server, user, password, database)
cursor = db.cursor()

# 加入用户信息请求头，模拟用户请求
header = 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.105 ' \
         'Safari/537.36 '
options = webdriver.ChromeOptions()
options.add_argument(header)
driver = webdriver.Chrome(options=options)
# 设定要调用的浏览器驱动
# driver = webdriver.Chrome()
url = 'https://m.baidu.com/sf?pd=product_card&openapi=1&dspName=iphone&from_sf=1&new_need_di=1&resource_id=4530&ie' \
      '=utf-8&group=search_box&dsp=iphone&lid=11065262672842969153&lid=11065262672842969153&frsrcid=47181&frsrcid' \
      '=47181&tab=param&top=%7B%22sfhs%22%3A1%7D&word=%E5%B0%8F%E7%B1%B39&title=%E5%B0%8F%E7%B1%B39&referlid' \
      '=10633732804307241063&ms=1&frorder=2 '

# model_list = ['小米9', '华为Mate30', '苹果iPhone11']
# 对应8项基本参数名
parameter_list = ['价格', 'CPU型号', '主屏尺寸', '主屏分辨率', '主屏材质', '手机重量', '手机尺寸', '电池容量']
# 对应8项基本参数的值
parameter_specific = ['', '', '', '', '', '', '', '']
# 对应10项参数类型的值（重要参数main，基本参数basic，屏幕screen，硬件hardware
# ，网络与连接network, 摄像头camera, 外观appearance, 功能与服务service, 手机附件enclosure, 保修信息guarantee）
parameter_overview = ['', '', '', '', '', '', '', '', '', '']


# 从数据库中获取要爬取的机型
def get_model_list():
    sql = 'select phone_model from assistant_phone order by phone_id asc'
    cursor.execute(sql)
    model_list = cursor.fetchall()
    # 控制爬取与存入数据库的数量
    for count in range(0, 66):
        print('正在更新第' + str(count + 1) + '条机型数据...')
        get_detail(model_list[count][0])
        clean_parameter()


# 参数情况，意外情况不出现重复
def clean_parameter():
    global parameter_specific
    global parameter_overview
    parameter_specific = ['', '', '', '', '', '', '', '']
    parameter_overview = ['', '', '', '', '', '', '', '', '', '']


# 获取参数相关的页面资源
def get_result(contents):
    soup = BeautifulSoup(contents, "html.parser")
    # 将所有的机型参数标签找出来
    result = soup.find_all('span', class_='c-gap-bottom-small c-blocka c-gap-top-large')
    result += soup.find_all('span', class_='c-gap-bottom-small c-blocka c-gap-top')
    return result


# 获取机型参数
def get_parameter(current_url, phone_model):
    print('现在查找的机型是：' + phone_model)
    result = []
    # 将所有的机型参数标签找出来
    result = get_result(request.urlopen(current_url).read())
    if not result:
        result = get_result(get_html(current_url))
    i = 0
    for tag in result:
        p_type = tag.find('div', class_='c-font-big c-color-link c-line-clamp1').get_text()
        p_name = tag.find_all('div', class_='c-span4')
        p_value = tag.find_all('div', class_='c-span8')
        print('========机型参数类型：' + p_type + '========')
        for count in range(0, len(p_name)):
            for c in range(0, len(parameter_list)):
                if parameter_list[c] == p_name[count].get_text():
                    parameter_specific[c] = p_value[count].get_text()
            parameter_overview[i] += p_name[count].get_text() + '：' + p_value[count].get_text() + '<br/>'
            if parameter_overview[i] != "" and len(parameter_overview[i]) > 1200:
                parameter_overview[i] = parameter_overview[i][0:1195] + '...'
        print(parameter_overview[i])
        # 将机型基本参数存入数据库
        try:
            sql = 'update model_infor set infor_main=%s, infor_basic=%s, infor_screen=%s, infor_hardware=%s, ' \
                  'infor_network=%s, infor_camera=%s, infor_appearance=%s, infor_service=%s, infor_enclosure=%s, ' \
                  'infor_guarantee=%s, update_date=%s where phone_model=%s'
            if parameter_overview[0] != '':
                cursor.execute(sql, (parameter_overview[0], parameter_overview[1], parameter_overview[2],
                                     parameter_overview[3], parameter_overview[4], parameter_overview[5],
                                     parameter_overview[6], parameter_overview[7], parameter_overview[8],
                                     parameter_overview[9], get_date(), phone_model))
        finally:
            i += 1
    print('检查存入基本参数')
    for v in parameter_specific:
        print(v)
    # 将机型特定8项参数存入数据库
    try:
        if parameter_specific[2] == '':
            parameter_specific[2] = '0'
        if parameter_specific[5] == '':
            parameter_specific[5] = '0'
        if parameter_specific[7] == '':
            parameter_specific[7] = '0'
        # 匹配中文字符，将其去除
        p = re.compile(r'[\u4e00-\u9fa5]')
        parameter_specific[2] = re.sub(p, '', parameter_specific[2])
        parameter_specific[5] = re.sub(p, '', parameter_specific[5])
        parameter_specific[7] = re.sub(p, '', parameter_specific[7])
        parameter_specific[2] = float(parameter_specific[2].replace('英寸', ''))
        parameter_specific[5] = float(parameter_specific[5].replace('g', ''))
        parameter_specific[7] = int(parameter_specific[7].replace('mAh', ''))
        sql = 'update model_infor set infor_cpu=%s, infor_screen_size=%s, infor_resolving_power=%s, ' \
              'infor_screen_material=%s, infor_weight=%s, infor_size=%s, infor_battery=%s where phone_model=%s'
        if parameter_specific[1] != '':
            cursor.execute(sql,
                           (parameter_specific[1], parameter_specific[2], parameter_specific[3],
                            parameter_specific[4], parameter_specific[5], parameter_specific[6], parameter_specific[7],
                            phone_model))
        cursor.connection.commit()
    except raise_mysql_exception:
        print(phone_model + '--机型数据库存储出错！')
    finally:
        print(phone_model + '--机型参数存入数据库完成！')


# 根据机型信息开始自动打开对应参数网页进行数据爬取与存储
def get_detail(phone_model):
    sql1 = 'select infor_url from model_infor where phone_model = %s'
    cursor.execute(sql1, phone_model)
    result_url = cursor.fetchone()
    if result_url[0] is None:
        print(phone_model + '爬取地址为空，已更新！')
        driver.get(url)
        time.sleep(1)
        driver.find_element_by_class_name("sp-sf-search-input").send_keys(phone_model)
        time.sleep(0.5)
        driver.find_element_by_class_name("sp-sf-search-input").send_keys(Keys.ENTER)
        time.sleep(2)
        try:
            # driver.find_element_by_xpath('//*[@id="super-frame"]/div/b-superframe-body/div/div['
            #                              '1]/div/div/div/article/section/div/div[1]/div/div[1]/ul/div[2]/span').click()
            driver.find_element_by_xpath('//*[@id="super-frame"]/div/b-superframe-body/div/div['
                                         '1]/div/div/div/article/section/div/ul/li[2]/div/div/span').click()
            time.sleep(2)
        except NoSuchElementException:
            print(phone_model + '，该机型无法爬取，跳过！')
            return
        # 插入爬取站点地址
        sql = 'update model_infor set infor_url=%s where phone_model=%s'
        cursor.execute(sql, (driver.current_url, phone_model))
        cursor.connection.commit()
        # 调用函数，开始爬取该机型参数信息，保存至数据库
        get_parameter(driver.current_url, phone_model)
    else:
        print(phone_model + '爬取地址已存在，直接爬取...')
        get_parameter(result_url[0], phone_model)
    print(phone_model + '机型参数获取且保存完毕！开始获取下一机型...')
    time.sleep(2)


# 解析动态加载页面
def get_html(local_url):
    o = webdriver.ChromeOptions()
    o.add_argument('-headless')
    d = webdriver.Chrome(options=options)
    d.get(local_url)
    return d.page_source


# 获取当前时间戳
def get_date():
    t = time.time()
    return int(t)


# 手动模式，对于一些爬取失败的机型手动输入地址进行参数检索
def get_parameter_manual(phone_model):
    current_url = ''
    get_parameter(current_url, phone_model)
    driver.quit()


def main():
    # 尝试获取数据
    try:
        # get_model_list()
        get_parameter_manual('荣耀X10')
    finally:
        print('完成所有机型数据获取与保存！')


# 包含功能，1.从数据库更新机型数据 2.挨个遍历，调用浏览器自动化爬取详细机型参数并存入数据库
if __name__ == '__main__':
    main()

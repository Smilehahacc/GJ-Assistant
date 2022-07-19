from urllib import request
import pymysql
from bs4 import BeautifulSoup
import re
from selenium import webdriver
# from sqlalchemy import null


# server = '192.168.31.118'
server = '172.18.16.1'
user = 'root'
password = 'root'
database = 'assistant'
db = pymysql.connect(server, user, password, database)
cursor = db.cursor()
url = 'http://www.antutu.com'
# 加入用户信息请求头，模拟用户请求
header = 'User-Agent="Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) ' \
         'Chrome/79.0.3945.79 Safari/537.36" '
options = webdriver.Edge()
# options.add_argument(header)
# driver = webdriver.Edge(options=options)
driver = webdriver.Edge()
result_output = ''
page = 5


def get_head_article(home_url):
    global result_output
    head_result = get_head_result(get_html(home_url))
    for tag in head_result:
        try:
            # 提取轮播图文章相关信息
            article_title = tag.find('p').get_text()
            article_content = ''
            article_url = url + tag.find('a').get('href')
            article_img = tag.find('img').get('src')
            article_date = ''
            article_view = 0
            article_comment = 0
            print('文章标题：' + article_title)
            print('文章地址：' + article_url)
            print('图片地址：' + article_img)
            result_output += '文章标题：' + article_title +'<br/>文章地址：' + article_url + '<br/>图片地址：' + article_img
        except Exception as e:
            print(repr(e))
            return
        try:
            sql = 'insert into assistant_article(article_title,article_content,article_url,' \
                  'article_img,article_date,article_view,article_comment) VALUES(%s,%s,%s,%s,%s,%s,%s)'
            cursor.execute(sql, (article_title, article_content, article_url, article_img,
                                 article_date, article_view, article_comment))
        except Exception as e:
            print(repr(e))
        finally:
            cursor.connection.commit()
    print('轮播图文章爬取和保存完成！')
    result_output += '<br/>轮播图文章爬取和保存完成！'


def get_article(now_url):
    global result_output
    result = get_result(get_html(now_url))
    for tag in result:
        try:
            # 提取文章相关信息
            article_div = tag.find('div', class_='c3 col-sm-8')
            article_title = article_div.find('h3').get_text()
            article_content = article_div.find('p').get_text()
            article_url = url + article_div.find('a').get('href')
            img = tag.find('div', class_='bgimg').get('style')
            article_img = re.findall(r"background:url\(\'(.*?)\'\) center no-repeat", img)[0]
            article_bar = article_div.find('div', class_='c4')
            article_date = article_bar.find('p', class_='p3').get_text()
            article_view = int(article_bar.find('span', class_='antutu_news_read_count').get_text())
            article_comment = re.findall(r'\d+', article_bar.find('p', class_='p2').find('span').get_text())[0]
            print('文章标题：' + article_title + '  文章内容：' + article_content)
            print('文章地址：' + article_url)
            print('图片地址：' + article_img)
            print('发布时间：' + article_date + '  阅读量：' + str(article_view) + '  评论数：' + article_comment)
            result_output += '文章标题：' + article_title + '  文章内容：' + article_content + '<br/>文章地址：' \
                             + article_url + '<br/>图片地址：' + article_img + '<br/>发布时间：' + article_date \
                             + '  阅读量：' + str(article_view) + '  评论数：' + article_comment
        except Exception as e:
            print(repr(e))
            return
        try:
            cursor.execute('select * from assistant_article where article_title = %s and article_content !=""',
                           article_title)
            if cursor.fetchall() != ():
                sql = 'update assistant_article set article_content=%s, article_url=%s, article_img=%s, ' \
                      'article_date=%s, article_view=%s, article_comment=%s where article_title=%s'
                cursor.execute(sql, (article_content, article_url, article_img, article_date, article_view,
                                     article_comment, article_title))
            else:
                sql = 'insert into assistant_article(article_title,article_content,article_url,article_img,' \
                      'article_date,article_view,article_comment) VALUES(%s,%s,%s,%s,%s,%s,%s)'
                cursor.execute(sql, (article_title, article_content, article_url, article_img, article_date,
                                     article_view, article_comment))
        except Exception as e:
            print(repr(e))
        finally:
            cursor.connection.commit()
    print('该页文章爬取和保存完成！')
    result_output += '<br/>该页文章爬取和保存完成！'


# 获取轮播图文章
def get_head_result(contents):
    soup = BeautifulSoup(contents, "html.parser")
    # 将所有文章div提取出来
    result = []
    result += soup.find('div', class_='carousel-inner').find_all('div', class_='item')
    return result


# 获取当前页面的所有文章
def get_result(contents):
    soup = BeautifulSoup(contents, "html.parser")
    # 将所有文章div提取出来
    result = []
    result += soup.find_all('div', class_='index-c1 row')
    return result


# 解析动态加载页面
def get_html(local_url):
    # o = webdriver.ChromeOptions()
    # o.add_argument('-headless')
    # d = webdriver.Chrome(options=options)
    driver.get(local_url)
    return driver.page_source


# 清除文章数据库缓存
def clean_article():
    try:
        cursor.execute('truncate table assistant_article  ')
    finally:
        cursor.connection.commit()


def main():
    global result_output
    try:
        clean_article()
        for count in range(0, page):
            print('现在爬取第' + str(count + 1) + '页的文章内容...')
            result_output += '现在爬取第' + str(count + 1) + '页的文章内容...<br/>'
            get_article(url + '/doc/list_' + str(count) + '.htm')
        get_head_article(url)
        print('所有文章爬取完成！')
        result_output += '<br/>所有文章爬取完成！'
        # driver.close()
        driver.quit()
    finally:
        # db.close()
        return result_output


# 包含功能，1.获取安兔兔头部轮播图文章 2.获取安兔兔手机圈文章（直接运行主函数即可）
if __name__ == '__main__':
    main()

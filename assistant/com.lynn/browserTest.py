import os
from urllib import request

from bs4 import BeautifulSoup
from h5py.h5s import NULL
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
import time

phone_list = ['小米9', '华为Mate30', '苹果iPhone11']
# 对应8项基本参数名
parameter_list = ['价格', 'CPU型号', '主屏尺寸', '主屏分辨率', '主屏材质', '手机重量', '手机尺寸', '电池容量']
# 对应8项基本参数的值
parameter_specific = ['', '', '', '', '', '', '', '']
# 对应10项参数类型的值（重要参数main，基本参数basic，屏幕screen，硬件hardware
# ，网络与连接network, 摄像头camera, 外观appearance, 功能与服务service, 手机附件enclosure, 保修信息guarantee）
parameter_overview = ['', '', '', '', '', '', '', '', '', '']
# 加入用户信息请求头，模拟用户请求
header = {'User-Agent: ': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) '
                          'Chrome/79.0.3945.79 Safari/537.36'}
# 设定要调用的浏览器驱动
driver = webdriver.Chrome()
# 外层循环，实现翻页
url = 'https://m.baidu.com/sf?pd=product_card&openapi=1&dspName=iphone&from_sf=1&new_need_di=1&resource_id=4530&ie' \
      '=utf-8&group=search_box&dsp=iphone&lid=11065262672842969153&lid=11065262672842969153&frsrcid=47181&frsrcid' \
      '=47181&tab=param&top=%7B%22sfhs%22%3A1%7D&word=%E5%B0%8F%E7%B1%B39&title=%E5%B0%8F%E7%B1%B39&referlid' \
      '=12231490145617946047&ms=1&frorder=2 '


# 根据机型信息开始自动打开对应参数网页进行数据爬取与存储
def get_detail(phone_model):
    driver.get(url)
    time.sleep(2)
    driver.find_element_by_class_name("sp-sf-search-input").send_keys(phone_model)
    time.sleep(0.5)
    driver.find_element_by_class_name("sp-sf-search-input").send_keys(Keys.ENTER)
    time.sleep(2)
    # tab = driver.find_element_by_class_name("c-color-gray-a li c-navs-scroll-selected "
    #                                         "c-navs-scroll-select-bottom WA_LOG_TAB")
    # if tab != NULL:
    #     tab.click()
    # time.sleep(2)
    # 调用函数，开始爬取该机型参数信息，保存至数据库
    get_parameter_test(driver.current_url, phone_model)
    print(phone_model + '机型参数获取且保存完毕！开始获取下一机型...')
    time.sleep(8)


def get_parameter_test(current_url, phone_model):
    print('现在查找的机型是：' + phone_model)
    page = request.urlopen(current_url)
    contents = page.read()
    soup = BeautifulSoup(contents, "html.parser")
    # 将所有的机型参数标签找出来
    result = soup.find_all('span', class_='c-gap-bottom-small c-blocka c-gap-top-large')
    result += soup.find_all('span', class_='c-gap-bottom-small c-blocka c-gap-top')
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
            parameter_overview[i] += p_name[count].get_text() + '：' + p_value[count].get_text() + '\n'
            if parameter_overview[i] != "" and len(parameter_overview[i]) > 1200:
                parameter_overview[i] = parameter_overview[i][0:1195] + '...'
        print(parameter_overview[i])
        i += 1
    print('检查存入基本参数')
    for v in parameter_specific:
        print(v)


def open_chrome():
    d = webdriver.Chrome()  # 模拟打开浏览器
    d.get("https://www.baidu.com/")  # 打开网址
    # d.quit()


try:
    # for pl in phone_list:
    #     get_detail(pl)
    # get_detail('华为Mate 30 Pro')
    open_chrome()
finally:
    print('完成机型数据爬取！')

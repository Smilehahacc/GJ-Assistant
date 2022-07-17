import requests
from selenium import webdriver

# 加入用户信息请求头，模拟用户请求
header = 'User-Agent="Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) ' \
         'Chrome/79.0.3945.79 Safari/537.36" '
options = webdriver.ChromeOptions()
options.add_argument(header)
driver = webdriver.Chrome(options=options)
u = 'https://priceparity.pconline.com.cn/load.jsp?pmid=bf195d2578acbaf337826ea2d7550454#ad=8714'
u1 = 'https://item.jd.com/100008384332.html?utm_source=kong&utm_medium=zssc&utm_campaign=t_1000027280_107222' \
     '&utm_term=99211e3b-09cd-4361-a545-e27a17edd718-p_1999-pr_2164-at_107222&jd_pop=99211e3b-09cd-4361-a545' \
     '-e27a17edd718&abt=0 '

#
# def getBuyLink():
#     # driver.get(url)
#     # print(driver.current_url)
#     print(get_redirect_url(u))
#
#
# def get_redirect_url(url):
#     # 请求网页
#     response = requests.get(url)
#     print(response.status_code)  # 打印响应的状态码
#     print(response.url)  # 打印重定向后的网址
#     # 返回重定向后的网址
#     return response.url
#
#
# # 包含功能，1.从相关站点获取在售机型 2.存储机型到数据库 3.获取机型价格和图片
# if __name__ == '__main__':
#     getBuyLink()

r = requests.get(u, headers={"Content-Type": "application/json"})
List = r.history  # 可以看出获取的是一个地址序列
print(List)
# print(f'获取重定向最终的url：{List[len(List) - 2].headers["location"]}')

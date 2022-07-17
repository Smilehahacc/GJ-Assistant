import requests
import pymysql
import time
import re
from selenium import webdriver

from bs4 import BeautifulSoup

server = '116.62.45.163'
user = 'root'
password = 'root'
database = 'assistant'
db = pymysql.connect(server, user, password, database)
cursor = db.cursor()
url = 'https://www.dxomark.com/category/smartphone-reviews'
page = 1
result_output = ''

# 加入用户信息请求头，模拟用户请求
header = 'User-Agent="Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) ' \
         'Chrome/79.0.3945.79 Safari/537.36" '
options = webdriver.ChromeOptions()
options.add_argument(header)
driver = webdriver.Chrome(options=options)
result_output = ''


def getDxoMark():
    global result_output
    print('开始获取DxoMark智能手机相机排行...')
    result_output += '开始获取DxoMark智能手机相机排行...'
    contents = requests.request('POST', url).text
    soup = BeautifulSoup(contents, "html.parser")
    # 将所有的设备分数标签找出来
    result = soup.find_all('div', class_='listElement')
    ergodic_element(result)


def ergodic_element(result):
    global result_output
    for tag in result:
        if tag.find('div', class_='deviceName sensor') is not None:
            phone_model = tag.find('div', class_='deviceName sensor').find('a').get_text()
            print('设备名' + phone_model)
            score_camera = getScore(tag.find('div', class_='listElementScoreColumn mobile'))
            score_selfie = getScore(tag.find('div', class_='listElementScoreColumn selfie'))
            score_audio = getScore(tag.find('div', class_='listElementScoreColumn audio'))
            print('相机得分：' + str(score_camera) + ' 自拍得分：' + str(score_selfie) + ' 音频得分：' + str(score_audio))
            result_output += '相机得分：' + str(score_camera) + ' 自拍得分：' + str(score_selfie) + ' 音频得分：' + str(score_audio)
            insert_score(phone_model, score_camera, score_selfie, score_audio)


def getScore(tag):
    score_div = tag.find('div', class_='deviceScore')
    if score_div is not None:
        score_this = re.sub('\r|\n', '', score_div.get_text().replace(' ', ''))
        if len(score_this) == 0:
            return 0
        return int(score_this)
    else:
        return 0


def insert_score(phone_model, score_camera, score_selfie, score_audio):
    global result_output
    try:
        cursor.execute('select * from dxomark_score where phone_model = %s', phone_model)
        if cursor.fetchall() != ():
            sql = 'update dxomark_score set score_camera=%s, score_selfie=%s, ' \
                  'score_audio=%s, update_date=%s where phone_model=%s'
            cursor.execute(sql, (score_camera, score_selfie, score_audio, get_date(), phone_model))
        else:
            sql = 'insert into dxomark_score(phone_model, score_camera, score_selfie, score_audio, update_date) ' \
                  'VALUES(%s, %s, %s, %s, %s)'
            cursor.execute(sql, (phone_model, score_camera, score_selfie, score_audio, get_date()))
    except Exception as e:
        print(repr(e))
    finally:
        print(phone_model + 'DxoMark分数数据更新完成！')
        result_output += phone_model + 'DxoMark分数数据更新完成！'
        cursor.connection.commit()


def get_date():
    t = time.time()
    return int(t)


def main():
    global result_output
    try:
        getDxoMark()
        result_output += 'DXO数据更新完毕！<br/>'
        return result_output
    finally:
        # db.close()
        return result_output


# 包含功能，1.
if __name__ == '__main__':
    main()

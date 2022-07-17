from flask import Flask
from flask import request
from threading import Timer
import phoneModel
import phoneArticle
import dxomark

app = Flask(__name__)


@app.route('/')
def hello_world():
    return '欢迎开启python后端服务!'


@app.route('/updateModel')
def update_model():
    try:
        return phoneModel.main()
    except:
        return 'ERROR'


@app.route('/updateModelAutomatic')
def updateModelAutomatic():
    interval = request.args.get("interval")
    time = int(interval) * 60
    try:
        print('现在开始每' + str(interval) + '分钟更新一次机型信息...')
        t = RepeatingTimer(time, phoneModel.main)
        t.start()
        return 'SUCCESS'
    except Exception as e:
        print(e.args)
        return 'ERROR'


@app.route('/updateArticle')
def updateArticle():
    try:
        return phoneArticle.main()
    except:
        return 'ERROR'


@app.route('/updateArticleAutomatic')
def updateArticleAutomatic():
    interval = request.args.get("interval")
    time = int(interval) * 60
    try:
        print('现在开始每' + str(interval) + '分钟更新一次文章信息...')
        t = RepeatingTimer(time, phoneArticle.main)
        t.start()
        return 'SUCCESS'
    except Exception as e:
        print(e.args)
        return 'ERROR'


@app.route('/updateDxomark')
def updateDxomark():
    try:
        return dxomark.main()
    except:
        return 'ERROR'


@app.route('/updateDxomarkAutomatic')
def updateDxomarkAutomatic():
    interval = request.args.get("interval")
    time = int(interval) * 60
    try:
        print('现在开始每' + str(interval) + '分钟更新一次Dxomark榜单数据...')
        t = RepeatingTimer(time, dxomark.main)
        t.start()
        return 'SUCCESS'
    except Exception as e:
        print(e.args)
        return 'ERROR'


class RepeatingTimer(Timer):
    def run(self):
        while not self.finished.is_set():
            self.function(*self.args, **self.kwargs)
            print(str(self.interval / 60) + '分钟后开始下一次更新...')
            self.finished.wait(self.interval)

    def cancel(self):
        print('结束进程')
        self.finished.set()


if __name__ == '__main__':
    app.run(threaded=True)

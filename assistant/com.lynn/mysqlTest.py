import pymysql
db = pymysql.connect('116.62.132.144', 'root', 'root', 'assistant')
cursor = db.cursor()
cursor.execute("SELECT VERSION()")
data = cursor.fetchone()
print(data)
db.close()




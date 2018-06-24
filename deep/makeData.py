import pymysql

conn = pymysql.connect(host='localhost', user='root', password='1234',
                       db='tryeat', charset='utf8')

curs = conn.cursor()

sql = "SELECT user, restaurant, review FROM tryeat.counting WHERE target=0"
curs.execute(sql)
counts = curs.fetchall()

sql = "SELECT user_id, restaurant_id, rate FROM review"
curs.execute(sql)
rows = curs.fetchall()

conn.close()

f = open(r'''.\deep\data.dat''', 'w')
f.write("%d::%d::%d\n" % (counts[0][0], counts[0][1], counts[0][2]))
for row in rows:
    f.write("%d::%d::%.1f\n" % (row[0], row[1], row[2]))
f.close()

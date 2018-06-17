import numpy as np
import sys
import pymysql

user = int(sys.argv[1])
start =int(sys.argv[2])
end = int(sys.argv[3])

conn = pymysql.connect(host='localhost', user='root', password='1234',
                       db='tryeat', charset='utf8')
curs = conn.cursor()

sql = "SELECT user, restaurant FROM counting WHERE target=0"
curs.execute(sql)
counts = curs.fetchall()

conn.close()

data = open(r'''F:\project\TryEat\deep\u.dat''')
user_data = data.read().split()
user_len = (int)(counts[0][0])
u = np.random.rand(user_len*30)
index = 0
for i in user_data[1:]:
    u[index] = i
    index+=1
u = u.reshape((user_len,30))
data.close()

data = open(r'''F:\project\TryEat\deep\p.dat''')
restaurant_data = data.read().split()
restaurant_len = (int)(counts[0][1])
p = np.random.rand(restaurant_len*30)
index = 0
for i in restaurant_data[1:]:
    p[index] = i
    index+=1
p = p.reshape((30,restaurant_len))
data.close()
data.close()

all = np.matmul(u,p)[user-1]
index = sorted((e,i+1) for i,e in enumerate(all))[::-1]
print(','.join(str(i) for e,i in index[start:start+end]))

 
import numpy as np
import sys

user = int(sys.argv[1])
start = int(sys.argv[2])
end = int(sys.argv[3])

data = open(r'''F:\project\TryEat\deep\u.dat''')
user_data = data.read().split()
user_len = (int)(user_data[0])
u = np.zeros(shape=[user_len*10])
index = 0
for i in user_data[1:]:
    u[index] = i
    index+=1
u = u.reshape((user_len,10))
data.close()

data = open(r'''F:\project\TryEat\deep\p.dat''')
restaurant_data = data.read().split()
restaurant_len = (int)(restaurant_data[0])
p = np.zeros(shape=[restaurant_len*10])
index = 0
for i in restaurant_data[1:]:
    p[index] = i
    index+=1
p = p.reshape((10,restaurant_len))
data.close()
data.close()

all = np.matmul(u,p)[user-1]
index = sorted((e,i+1) for i,e in enumerate(all))[::-1]
print(','.join(str(i) for e,i in index[start:end]))

 
import numpy as np
import sys

user = int(sys.argv[1])
start = int(sys.argv[2])
end = int(sys.argv[3])

data = open(r'''F:\project\TryEat\deep\u.dat''')
u = np.array([float(num) for num in data.read().split()]).reshape((100,10))
data.close()

data = open(r'''F:\project\TryEat\deep\p.dat''')
p = np.array([float(num) for num in data.read().split()]).reshape((10,10))
data.close()

all = np.matmul(u,p)[user-1]
index = sorted((e,i) for i,e in enumerate(all))[::-1]
print(','.join(str(i) for e,i in index[start:end]))

 
import pymysql
import random
restaurant = []
user = []
review = []
bookmark = []

rLen = random.randint(2000,3000)
uLen = random.randint(3000,5000)

for i in range(rLen):
    restaurant.append({})
    restaurant[i]["name"] = "음식점"+str(i+1)
    restaurant[i]["uri"] = str(random.randint(1,110))+".webp"
    restaurant[i]["address"] = "음식점 주소"+str(i+1)
    restaurant[i]["phone"] = "010-0000-{0:04d}".format(i+1)
    restaurant[i]["lat"] = random.random()*random.randrange(33,43)
    restaurant[i]["ion"] = random.random()*random.randint(124,132)
    restaurant[i]["bookmark"] = 0
    restaurant[i]["count"] = 0
    restaurant[i]["rate"] = 0

for i in range(uLen):
    user.append({})
    user[i]["id"] = "sample"+str(i+1)
    user[i]["pwd"] = "sample"+str(i+1)
    user[i]["review"] = random.randint(5,60)
    user[i]["bookmark"] = random.randint(5,50)

restaurantList = [i for i in range(rLen)]

bookmarkIndex = 0
for i in range(uLen):
    targetList = random.sample(restaurantList,user[i]["bookmark"])
    for j in range(user[i]["bookmark"]):
        bookmark.append({})
        bookmark[bookmarkIndex]["user"] = i+1
        bookmark[bookmarkIndex]["restaurant"] = targetList[j]+1
        restaurant[targetList[j]]["bookmark"] += 1
        bookmarkIndex+=1

reviewIndex=0

for i in range(uLen):
    targetList = random.sample(restaurantList,user[i]["review"])
    for j in range(user[i]["review"]):
        review.append({})
        review[reviewIndex]["user"] = i+1
        review[reviewIndex]["uri"] = str(random.randint(1,110))+".webp"
        review[reviewIndex]["text"] = "이것은 {0}번째 리뷰입니다. {1}번째 유저가 쓴 리뷰입니다. {2} 번째 음식점인 이곳은 맛있습니다. 정말 맜있읍니다.".format(reviewIndex+1,i+1,targetList[j]+1)
        review[reviewIndex]["restaurant"] = targetList[j]+1
        rate = random.randint(2,5)
        review[reviewIndex]["rate"] = rate
        restaurant[targetList[j]]["rate"]+=rate
        restaurant[targetList[j]]["count"]+=1
        reviewIndex+=1

conn = pymysql.connect(host='localhost', user='root', password='1234',
                       db='tryeat', charset='utf8')

curs = conn.cursor()
sql = "INSERT INTO counting (target,user,restaurant,review) VALUES (0,{0},{1},{2})".format(uLen,rLen,reviewIndex)
curs.execute(sql)

for i in range(uLen):
    sql = "INSERT INTO user (user_login_id ,user_pwd,review_count,bookmark_count) VALUES ('{0}','{1}',{2},{3})".format(user[i]["id"],user[i]["pwd"],user[i]["review"],user[i]["bookmark"])
    curs.execute(sql)
    conn.commit()
    if i%10==0:
        print("{0}번째 유저 입력 완료".format(i))

for i in range(rLen):
    data = restaurant[i]
    sql = "INSERT INTO restaurant (img_uri,restaurant_name,address,phone,locate_latitude,locate_longitude,review_count,total_rate,total_bookmark) \
    VALUES ('{0}','{1}','{2}','{3}',{4},{5},{6},{7},{8})".format(data["uri"],data["name"],data["address"],data["phone"],data["lat"],data["ion"],data["count"],data["rate"],data["bookmark"])
    curs.execute(sql)
    conn.commit()
    if i%10==0:
        print("{0}번째 음식점 입력 완료".format(i))

for i in range(bookmarkIndex):
    data = bookmark[i]
    sql = "INSERT INTO bookmark (user_id ,restaurant_id) \
    VALUES ({0},{1})".format(data["user"],data["restaurant"])
    curs.execute(sql)
    conn.commit()
    if i%10==0:
        print("{0}번째 북마크 입력 완료".format(i))

for i in range(reviewIndex):
    data = review[i]
    sql = "INSERT INTO review (user_id ,restaurant_id,img_uri,content,rate) \
    VALUES ({0},{1},'{2}','{3}',{4})".format(data["user"],data["restaurant"],data["uri"],data["text"],data["rate"])
    curs.execute(sql)
    conn.commit()
    if i%10==0:
        print("{0}번째 리뷰 입력 완료".format(i))

conn.close()


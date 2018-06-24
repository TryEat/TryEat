import numpy
import tensorflow as tf
import pandas as pd

import makeData

# http://hameddaily.blogspot.com/2016/12/simple-matrix-factorization-with.html 을 이용하여 제작 하였습니다.

df = pd.read_csv(r'''.\deep\data.dat''', sep="::", names=['user', 'item', 'rate'],engine="python")

user_len = df.user.values[0]
item_len = df.item.values[0]

user_indecies = [x-1 for x in df.user.values[1:]]
item_indecies = [x-1 for x in df.item.values[1:]]
rates = df.rate.values[1:]

feature_len = 50
U = tf.Variable(initial_value=tf.truncated_normal([user_len,feature_len]), name='users')
P = tf.Variable(initial_value=tf.truncated_normal([feature_len,item_len]), name='items')
result = tf.matmul(U, P)
result_flatten = tf.reshape(result, [-1])

R = tf.gather(result_flatten, user_indecies * tf.shape(result)[1] + item_indecies, name='extracting_user_rate')

diff_op = tf.subtract(R, rates, name='trainig_diff')
diff_op_squared = tf.abs(diff_op, name="squared_difference")
base_cost = tf.reduce_sum(diff_op_squared, name="sum_squared_error")

lda = tf.constant(.001, name='lambda')
norm_sums = tf.add(tf.reduce_sum(tf.abs(U, name='user_abs'), name='user_norm'),
                   tf.reduce_sum(tf.abs(P, name='item_abs'), name='item_norm'))
regularizer = tf.multiply(norm_sums, lda, 'regularizer')
cost = tf.add(base_cost,regularizer)

lr = tf.constant(.001, name='learning_rate')
global_step = tf.Variable(0, trainable=False)
learning_rate = tf.train.exponential_decay(lr, global_step, 1000, 0.96, staircase=True)
optimizer = tf.train.GradientDescentOptimizer(learning_rate)
training_step = optimizer.minimize(cost, global_step=global_step)

sess = tf.Session()
init = tf.global_variables_initializer()
sess.run(init)

for i in range(500):
    sess.run(training_step)

f = open(r'''.\deep\u.dat''','w')
val = sess.run(U)
f.write("%d\n"%user_len)
for i in range(user_len):
    for j in range(feature_len):
        f.write("%f\n" %val[i][j])
f.close()

f = open(r'''.\deep\p.dat''','w')
val = sess.run(P)
f.write("%d\n"%item_len)
for i in range(feature_len):
    for j in range(item_len):
        f.write("%f\n" %val[i][j])
f.close()

user_indecies_test = [x-1 for x in df.user.values[1:]]
item_indecies_test = [x-1 for x in df.item.values[1:]]
rates_test = df.rate.values[1:]

R_test = tf.gather(result_flatten, user_indecies_test * tf.shape(result)[1] + item_indecies_test, name='extracting_user_rate_test')
diff_op_test = tf.subtract(R_test, rates_test, name='test_diff')
diff_op_squared_test = tf.abs(diff_op_test, name="squared_difference_test")

cost_test = tf.div(tf.reduce_sum(tf.square(diff_op_squared_test, name="squared_difference_test"), name="sum_squared_error_test"), df.shape[0] * 2, name="average_error")
print (sess.run(cost_test))

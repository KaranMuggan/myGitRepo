'''The following code displays a polynomial regression model which takes into
account employees salaries along with the number of years of experience they
have and shows us the relationship
'''

import numpy as np
import matplotlib.pyplot as plt
from sklearn.linear_model import LinearRegression
from sklearn.preprocessing import PolynomialFeatures

#Training set
x_train = [[1], [2], [3], [4], [5], [6], [7], [8], [9], [10]]  #Number Of years
y_train = [[80000], [100000], [125000], [135000], [150000], [175000], [210000], [280000], [350000], [500000]]    #Salary

#Testing set
x_test = [[2], [4], [6], [8] ,[10], [12]]   #Number Of Years
y_test = [[110000], [130000], [160000], [280000], [3750000], [660000]]  #Salary

# Train the Linear Regression model and plot a prediction
regressor = LinearRegression()
regressor.fit(x_train, y_train)
xx = np.linspace(0, 50, 100)
yy = regressor.predict(xx.reshape(xx.shape[0], 1))
plt.plot(xx, yy)

# Set the degree of the Polynomial Regression model
quadratic_featurizer = PolynomialFeatures(degree=2)

# This preprocessor transforms an input data matrix into a new data matrix of a given degree
x_train_quadratic = quadratic_featurizer.fit_transform(x_train)
x_test_quadratic = quadratic_featurizer.transform(x_test)

# Train and test the regressor_quadratic model
regressor_quadratic = LinearRegression()
regressor_quadratic.fit(x_train_quadratic, y_train)
xx_quadratic = quadratic_featurizer.transform(xx.reshape(xx.shape[0], 1))

# Visualizing the Linear Regression results
def viz_linear():
    plt.scatter(x_test, y_test, c='red')
    plt.plot(x_test, regressor.predict(x_test), color='blue')
    plt.axis([0, 15, 0, 1000000])
    plt.title('(Linear Regression)')
    plt.xlabel('Experience level')
    plt.ylabel('Salary')
    plt.show()
    return
viz_linear()

# Plotting the polynomial regression model
plt.plot(xx, regressor_quadratic.predict(xx_quadratic), c='r', linestyle='--')
plt.title('Salary of employees per years of experience - Polynomial regression')
plt.xlabel('Years of experience')
plt.ylabel('Salary In $')
plt.axis([0, 15, 0, 1000000])
plt.grid(True)
plt.scatter(x_test,y_test)
plt.show()
print(x_train)
print(x_train_quadratic)
print(x_test)
print(x_test_quadratic)

#In the below statement we can predict what salary to expect at certain experience
#levels using linear regression
print("Linear regression model prediction: ")
print(regressor.predict([[7.5]]))
#And in the following statement we can do the same and get a more accurate estimate
#of a salary at a certain experience level
print("Polynomial regression model prediction: ")
print(regressor_quadratic.predict(quadratic_featurizer.transform([[7.5]])))

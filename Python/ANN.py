#Description: This program detects/predicts if a person has diabetes (1) or not (0)

#Load libraries
from tensorflow.python.keras.models import Sequential
from tensorflow.python.keras.layers import Dense
import pandas as pd
from sklearn.model_selection import train_test_split
import matplotlib.pyplot as plt
import joblib
from tensorflow.python.keras.models import load_model
from datetime import datetime
import zope.interface
import IClassification

@zope.interface.implementer(IClassification.IClassification)
class ANN():

    def CreatModel(self):
        plt.style.use('fivethirtyeight')

        #Store the data set
        df = pd.read_csv('diabetes.csv')

        #Checking for duplicates and removing them
        df.drop_duplicates(inplace = True)

        #Convert the data into an array
        dataset = df.values

        # Get all of the rows from the first eight columns of the dataset
        X = dataset[:,0:8]
        # Get all of the rows from the last column
        y = dataset[:,8]

        #from sklearn import preprocessing
        #min_max_scaler = preprocessing.MinMaxScaler()
        #X_scale = min_max_scaler.fit_transform(X)

        X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state = 4)

        # Finally we can start building the artificial neural network.
        # The models architecture will contain three layers. The first layer will have 12 neurons and
        # use the ReLu activation function, the second layer will have 15 neurons and use the ReLu activation function,
        # and the third and final layer will use 1 neuron and the sigmoid activation function.

        # Initialising the ANN >> Sequential
        # Adding the input layer and the first hidden layer
        # Adding the second hidden layer
        # Adding the output layer
        model = Sequential([
            Dense(12, activation='relu', input_shape=( 8 ,)),
            Dense(15, activation='relu'),
            Dense(1, activation='sigmoid')
        ])

        # Compiling the ANN
        model.compile(optimizer='sgd',
                      loss='binary_crossentropy',
                      metrics=['accuracy'])

        # Fitting the ANN to the Training set
        hist = model.fit(X_train, y_train, batch_size=57, epochs=1000, validation_split=0.2)

        # Part 3 - Making the predictions and evaluating the model

        # Predicting the Test set results
        y_pred = model.predict(X_test)
        y_pred = (y_pred > 0.5)

        # Making the Confusion Matrix
        from sklearn.metrics import confusion_matrix
        cm = confusion_matrix(y_test, y_pred)

        from sklearn import metrics #Import scikit-learn metrics module for accuracy calculation
        # Model Accuracy, how often is the classifier correct?
        print("Accuracy:", metrics.accuracy_score(y_test, y_pred))

        # save the model to disk
        # save the model to disk
        # creates a HDF5 file
        model.save('ANN_model.h5')


    def RunModel(self,y_test):
        # returns a compiled model
        # identical to the previous one

        model = load_model('ANN_model.h5')
        result = model.evaluate(self.X_test, y_test)


        return result

    def Classify(self, arraySignals):
        return self.RunModel(arraySignals)


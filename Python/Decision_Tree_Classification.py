# Load libraries
# import pandas as pd
from sklearn.tree import DecisionTreeClassifier # Import Decision Tree Classifier
from sklearn import metrics #Import scikit-learn metrics module for accuracy calculation

import base
import joblib
import zope.interface
import IClassification

@zope.interface.implementer(IClassification.IClassification)
class DecisionTree():
    X_train, X_test, y_train, y_test = base.initialization()

    def CreatModel(self):


        # Create Decision Tree classifer object
        clf = DecisionTreeClassifier(criterion="entropy", random_state=0)

        self.X_test = self.X_test.fillna(X_train.mean())

        # Train Decision Tree Classifer
        clf = clf.fit(self.X_train,self.y_train)

        # save the model to disk
        filename = 'DT_model.sav'
        joblib.dump(clf, filename)

        #Predict the response for test dataset
        y_pred = clf.predict(self.X_test)

        # Model Accuracy, how often is the classifier correct?
        print("Accuracy:", metrics.accuracy_score(self.y_test, self.y_pred))

    def RunModel(self,y_test):
        # load the model from disk

        loaded_model = joblib.load(filename)
        result = loaded_model.score(self.X_test, y_test)

        return result

    def Classify(self, arraySignals):
        return self.RunModel(arraySignals)
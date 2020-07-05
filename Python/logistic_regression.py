# Logistic Regression
import joblib
import base
from datetime import datetime

import zope.interface
import IClassification

@zope.interface.implementer(IClassification.IClassification)
class LogisticRegression():
    X_train, X_test, y_train, y_test = base.initialization()
    def CreatModel(self):


        # Feature Scaling

        sc = StandardScaler()
        self.X_train = sc.fit_transform(self.X_train)
        self.X_test = sc.transform(self.X_test)

        # Fitting Logistic Regression to the Training set
        from sklearn.linear_model import LogisticRegression
        classifier = LogisticRegression(random_state = 0)
        classifier.fit(X_train, y_train)



        # save the model to disk
        filename = 'LR_model.sav'
        joblib.dump(classifier, filename)

        # Predicting the Test set results
        y_pred = classifier.predict(self.X_test)

        # Making the Confusion Matrix
        from sklearn.metrics import confusion_matrix
        cm = confusion_matrix(self.y_test, self.y_pred)

        from sklearn import metrics #Import scikit-learn metrics module for accuracy calculation
        # Model Accuracy, how often is the classifier correct?
        print("Accuracy:", metrics.accuracy_score(self.y_test, self.y_pred))

    def RunModel(self,y_test):
        # load the model from disk

        loaded_model = joblib.load(filename)
        result = loaded_model.score(self.X_test, y_test)

        return result

    def Classify(self, arraySignals):
        return self.RunModel(arraySignals)
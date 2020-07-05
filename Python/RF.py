import pandas as pd
# list for column headers
from sklearn.ensemble import RandomForestClassifier
import joblib
import base
import zope.interface
import IClassification

@zope.interface.implementer(IClassification.IClassification)
class RF():
    X_train, X_test, y_train, y_test = base.initialization()

    def CreatModel(self):


        from sklearn import model_selection
        # random forest model creation
        rfc = RandomForestClassifier()
        rfc.fit(self.X_train,self.y_train)

        # save the model to disk
        filename = 'RF_model.sav'
        joblib.dump(rfc, filename)

        # predictions
        rfc_predict = rfc.predict(self.X_test)
        print(rfc_predict)

        from sklearn import metrics #Import scikit-learn metrics module for accuracy calculation
        # Model Accuracy, how often is the classifier correct?
        print("Accuracy:", metrics.accuracy_score(self.y_test, rfc_predict))


    def RunModel(self,y_test):
        # load the model from disk

        loaded_model = joblib.load(filename)
        result = loaded_model.score(self.X_test, y_test)

        return result

    def Classify(self, arraySignals):
        return self.RunModel(arraySignals)


# Kernel SVM

# Importing the libraries
from sklearn import metrics #Import scikit-learn metrics module for accuracy calculation
import joblib
import base
from sklearn.svm import SVC
from sklearn.preprocessing import StandardScaler
import zope.interface
import IClassification

@zope.interface.implementer(IClassification.IClassification)
class K_SVM():
    X_train, X_test, y_train, y_test = base.initialization()

    def CreatModel(self):


        # Feature Scaling

        sc = StandardScaler()
        self.X_train = sc.fit_transform(self.X_train)
        self.X_test = sc.transform(self.X_test)

        # Fitting Kernel SVM to the Training set

        classifier = SVC(kernel = 'rbf', random_state = 0)
        classifier.fit(self.X_train, self.y_train)


        # save the model to disk
        filename = 'KSVM_model.sav'
        joblib.dump(classifier, filename)

        # Predicting the Test set results
        y_pred = classifier.predict(X_test)

        # Making the Confusion Matrix
        from sklearn.metrics import confusion_matrix
        cm = confusion_matrix(self.y_test, self.y_pred)

        # Model Accuracy, how often is the classifier correct?
        print("Accuracy:", metrics.accuracy_score(self.y_test, self.y_pred))


    def RunModel(self, y_test):
        # load the model from disk
        loaded_model = joblib.load(filename)
        result = loaded_model.score(self.X_test, y_test)
        return result

    def Classify(self, arraySignals):
        return self.RunModel(arraySignals)
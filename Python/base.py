import pandas as pd
from sklearn.model_selection import train_test_split

def initialization():

    column_names = [
      'handRightX',
      'handRightY',
      'handRightZ',
      'ElbowRightX',
      'ElbowRightY',
      'ElbowRightZ',
      'ShoulderRightX',
      'ShoulderRightY',
      'ShoulderRightZ',
      'HipLeftX',
      'HipLeftY',
      'HipLeftZ',
      'HipRightX',
      'HipRightY',
      'HipRightZ',
      'label'
    ]
    # load dataset
    df = pd.read_csv("dataset.csv", header=None, names=column_names)
    df.head()

    #split dataset in features and target variable
    feature_cols = [
      'handRightX', 'handRightY', 'handRightZ',
      'ElbowRightX', 'ElbowRightY', 'ElbowRightZ',
      'ShoulderRightX', 'ShoulderRightY', 'ShoulderRightZ',
      'HipLeftX','HipLeftY', 'HipLeftZ',
      'HipRightX', 'HipRightY', 'HipRightZ'
      ]
    X = df[feature_cols] # Features
    y = df.label # Target variable

    # Split dataset into training set and test set
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.3, random_state=1) # 70% training and 30% test
    return X_train, X_test, y_train, y_test

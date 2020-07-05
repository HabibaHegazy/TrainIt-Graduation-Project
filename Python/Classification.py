import numpy as np
import matplotlib.pyplot as plt
from fastdtw import fastdtw
import statistics
from random import randint
import csv
from scipy.spatial.distance import euclidean
from scipy.optimize import curve_fit
from dtw import dtw
from datetime import datetime
from pykalman import KalmanFilter
import IRCameraSignals
import WearableSignals
import Session
import copy

class Classification:

    app_wave = []
    result = ""
    shortest_path = ""
    app_wave2 = []
    StrokeToClassify=[]
    player_id=0
    noCorrect=0
    noWrong=0





    def ReadingDataset(self):
        f = open("TrainingDataPath.txt", "r")
        TrainingFilePath = []
        for x in f:
            line = x.split()
            for i in line:
                TrainingFilePath.append(line)




    def StartClassification(self,ArrayToClassify):


        result = 0.0
        shortest_path = ""

        # looping on every stroke in dataset and add in array for classification
        for i in TrainingFilePath:

          file = i.split()
          dataset_wave = []

          f = open(str(file), "r")

          # adding every frame data in arr2 then add all frames in dataset_wave
          for x in f:
            line = x.split()
            arr2 = []
            for l in range(15):
                arr2.append(float(line[i]))

                dataset_wave.append(arr2)


            dataset_wavee = np.array(dataset_wave)
            app_wavee = np.array(ArrayToClassify)
            distance=0.0

            distance,path = fastdtw(dataset_wavee, app_wavee, dist=euclidean)



            if (result == 0.0):

                result = distance
                shortest_path = file
            elif (result > distance):
                result = distance
                shortest_path = file


        string = shortest_path.split('/')
        isMistake=False
        StrokeType=""
        ErrorType=""
        if(string[1]=="Wrong"):
            self.noWrong+=1
            isMistake=True
            ErrorType=string[3]
        else:self.noCorrect+=1

        StrokeType = string[2]

        Session.GetSessionInfo(ArrayToClassify)
        Session.InsertClassificationResult(ErrorType,isMistake,StrokeType)

    def sendSession(self,SessionEnded):
        if(SessionEnded):
            Session.GetfromClassification()





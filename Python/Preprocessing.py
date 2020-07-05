import numpy as np
import matplotlib.pyplot as plt
from mpl_toolkits import mplot3d
from fastdtw import fastdtw
from scipy.spatial import distance
from scipy.optimize import curve_fit
import WearableSignals
import IRCameraSignals
import copy
from filterpy.kalman import KalmanFilter
import statistics

class CurveFitting:

#Joints array
    arrJ1 = []
    arrJ2 = []
    arrJ3 = []
    arrJ4 = []
    arrJ5 = []
    arrD =[]


    arrT = []#Timestamp of Kinect
    arrT2 = []#Timestamp of Accelerometer
    arrT3 = []#Timestamp of Gyroscope
    arrT4 = []#time of stroke start from 0 to end of kinect
    arrT5 = []#time of stroke start from 0 to end of Accelerometer
    arrT6 = []#time of stroke start from 0 to end of Gyroscope
    arrT7 = []

    SensorStroke =[]
    KinectStroke = []

    acc =[]

    gyro = []



    def FilteringData(self,DataArray):
        #First construct the object with the required dimensionality.
        f = KalmanFilter(dim_x=len(DataArray), dim_z=3)
        f.x = np.array(DataArray)# Real data
        f.F = np.array([[1., 1.],[0., 1.]])#Define the state transition matrix
        f.H = np.array([[1., 0.]])#Define the measurement function
        f.P = np.array([[1000., 0.],[0., 1000.]])#Define the covariance matrix
        f.R = 5 #Assign the measurement noise.
        from filterpy.common import Q_discrete_white_noise
        f.Q = Q_discrete_white_noise(dim=len(DataArray), dt=0.1, var=0.13) #Assign the process noise
        for i in range(100): #IterationNum
            f.predict()
            f.update()

        return f.x


    def SignalsIntake(self,S_Stroke,K_Stroke):
        self.SensorStroke = S_Stroke
        self.KinectStroke = K_Stroke
        self.player_id=player_id

        for line in range(len(self.KinectStroke)):
                self.arrJ1.append(float(line[0]))
                self.arrJ1.append(float(line[1]))
                self.arrJ1.append(float(line[2]))
                self.arrJ2.append(float(line[3]))
                self.arrJ2.append(float(line[4]))
                self.arrJ2.append(float(line[5]))
                self.arrJ3.append(float(line[6]))
                self.arrJ3.append(float(line[7]))
                self.arrJ3.append(float(line[8]))
                self.arrJ4.append(float(line[9]))
                self.arrJ4.append(float(line[10]))
                self.arrJ4.append(float(line[11]))
                self.arrJ5.append(float(line[12]))
                self.arrJ5.append(float(line[13]))
                self.arrJ5.append(float(line[14]))
                self.arrT.append(float(line[15]))


        for y in range(len(self.SensorStroke)):
            for j in range(5):
                if j[0] == "acc":
                    self.acc.append(float(y[1]))
                    self.acc.append(float(y[2]))
                    self.acc.append(float(y[3]))
                    self.arrT2.append(float(y[4]))

                elif j[0] =="gyro":
                    self.gyro.append(float(y[1]))
                    self.gyro.append(float(y[2]))
                    self.gyro.append(float(y[3]))
                    self.arrT3.append(float(y[4]))

        arrT4.insert(0, 0)
        arrT5.insert(0, 0)
        arrT6.insert(0, 0)

        # Time from 0 to end of stroke
        for t in range(arrT.__len__() - 1):
            timeS = (arrT[t + 1] - arrT[0]) / 1000
            arrT4.insert(t + 1, timeS)

        for t2 in range(arrT2.__len__() - 1):
            timeS2 = (arrT2[t2 + 1] - arrT2[0]) / 1000
            arrT5.insert(t2 + 1, timeS2)

        for t3 in range(arrT3.__len__() - 1):
            timeS3 = (arrT3[t2 + 1] - arrT3[0]) / 1000
            arrT6.insert(t2 + 1, timeS3)



# The magnitude of each joint x,y,z
    arrM1 = []
    arrM2 = []
    arrM3 = []
    arrM4 = []
    arrM5 = []
    arrM6 = []
    arrM7 = []

    # put every joint/acc/gyro data magnitude in array
    def GetMagnitude(self):

        for a in range(0,len(self.arrJ1),3):#Mag for kinect

            b = np.array([self.arrJ1[a],self.arrJ1[a+1],self.arrJ1[a+2]], dtype=float)
            self.arrM1.append(np.linalg.norm(b))
            b3 = np.array([self.arrJ2[a],self.arrJ2[a+1],self.arrJ2[a+2]], dtype=float)
            self.arrM2.append(np.linalg.norm(b3))
            b5 = np.array([self.arrJ3[a],self.arrJ3[a+1],self.arrJ3[a+2]], dtype=float)
            self.arrM3.append(np.linalg.norm(b5))
            b6 = np.array([self.arrJ4[a],self.arrJ4[a+1],self.arrJ4[a+2]], dtype=float)
            self.arrM4.append(np.linalg.norm(b6))
            b7 = np.array([self.arrJ5[a],self.arrJ5[a+1],self.arrJ5[a+2]], dtype=float)
            self.arrM5.append(np.linalg.norm(b7))


        for a2 in range(0,len(self.acc),3):#Mag for acc
            b1 = np.array([self.acc[a2],self.acc[a2+1],self.acc[a2+2]], dtype=float)
            self.arrM6.append(np.linalg.norm(b1))


        for a3 in range(0,len(self.gyro),3):#Mag for gyro
            b2 = np.array([self.gyro[a2],self.gyro[a2+1],self.gyro[a2+2]], dtype=float)
            self.arrM6.append(np.linalg.norm(b2))




    #Fitting function
    def func(self,x, a, b):
        return a*np.exp(b*x)

    def func2(self,x, a, b, c):
        return a * np.exp(-b * x) + c

    def func3(self,x, a, b):
        return a*x+b



    #Initial guess for the pramameters
    initialGuess = [1.0,1.0]

    #Curve fitting for acc/gyro/kinect joint

    def StartCurveFit(self,xData1,yData1,xData2,yData2,xData3,yData3):

        xData = np.array(xData1)
        yData = np.array(yData1)
        #Perform the curve fit
        popt1, pcov1 = curve_fit(func2, xData, yData)

        xData = np.array(xData2)
        yData = np.array(yData2)
        # Perform the curve fit
        popt2, pcov2 = curve_fit(func2, xData, yData)

        xData = np.array(xData3)
        yData = np.array(yData3)
        # Perform the curve fit
        popt3, pcov3 = curve_fit(func2, xData, yData)

        Merg =[]
        for i in range(0,len(popt1),int(len(popt1)/len(popt3))):
            Merg.append(statistics.mean([popt1[i],popt2[i%len(popt2)],popt3[i%len(popt3)]]))

        return  Merg




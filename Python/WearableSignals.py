import socket
import sys
import numpy as np
import IRCameraSignals
import Preprocessing
import Session
import Classification

class WearableSignals:

    HOST = "192.168.1.2"
    PORT = 8090

    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    print('socket created')

    try:
        s.bind((HOST, PORT))
    except socket.error as err:
        print('Bind Failed Error Code: ' + str(err[0] + ', Message: ' + err[1]))
        sys.exit()

    print("socket Bind Successfully")

    s.listen(10)
    print('socket is now listening')

    MultiFrames = []
    FullStrokes = []
    start = False
    MaxDistance = 0
    Forward = False
    KinectFlag = False
    CutStroke = False
    playerID =0
    SessionStart=0
    SessionEnd=0
    KinectStroke=[]

    def SensorSignals(self):

        SingleFrame = []
        while 1:
            conn, addr = self.s.accept()
            buf = conn.recv(1024)
            x = buf.decode("ascii").split("@")
            if x[0] == 'true': #start getting data from sensor
                self.start=True
                self.playerID=int(x[1])#playerID from android
                self.SessionStart=int(x[2])
                self.KinectFlag=True #flag sent to kinect server to start getting data
                continue
            if self.start==True:

                #Flag from Kinect to cut and flag for stroke is going forward
                self.CutStroke,self.Forward=IRCameraSignals.SensorCutStroke()

                sensor = buf.decode("ascii").split("@")

                if self.Forward is True:
                    for i in range(5):
                        SingleFrame.append(sensor[i])
                    self.MultiFrames.append(SingleFrame)
                    SingleFrame = []

                if self.CutStroke is True:
                            self.FullStrokes.append(self.MultiFrames)
                            Preprocessing.SignalsIntake(self.MultiFrames,self.KinectStroke,self.playerID)
                            self.MultiFrames = []
                            self.Forward = False

            if x[0]=='false':
                self.start = False
                self.SessionEnd=int(x[1])
                self.KinectFlag=False
                Session.GetInfoFromSensors(self.SessionStart,self.SessionEnd,self.playerID)
                Classification.sendSession(True)

        s.close()

    def GetKStroke(self,K_Stroke):
        self.KinectStroke=K_Stroke


    def StartKinect(self):
        return self.KinectFlag

    def SendplayerIDandTime(self):
        return self.playerID,self.SessionStart



ob=WearableSignals()
ob.SensorSignals()

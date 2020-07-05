
import socket
import numpy as np
import WearableSignals
from scipy.spatial import distance




class KinectServer:

    s = socket.socket()
    print("Socket successfully created")

    # reserve a port on your computer in our
    # case it is 12345 but it can be anything
    port = 20201

    # Next bind to the port
    # we have not typed any ip in the ip field
    # instead we have inputted an empty string
    # this makes the server listen to requests
    # coming from other computers on the network
    s.bind(('', port))
    print("socket binded to %s" % (port))

    # put the socket into listening mode
    s.listen(5)
    print("socket is listening")

    # a forever loop until we interrupt it or
    # an error occurs
    c, addr = s.accept()
    print('Got connection from', addr)

    MultiFrames =[]
    FullStrokes = []
    Points = []
    Distances = []
    MaxDistance = 0
    Forward = False
    KinectStart = False
    CutStrokeFlag = False

    def KinectSignals(self):

        SingleFrame =[]
        while 1:
            self.KinectStart= WearableSignals.StartKinect()
            if (self.KinectStart):

                dataKinect = c.recv(1024)
                resultKinect = dataKinect.decode("ascii").split("@")

                Point = [resultKinect[0], resultKinect[1], resultKinect[2]]
                self.Points.append(Point)

                if len(self.Points)> 1:
                    a = np.array(self.Points[0],dtype=float)
                    b = np.array(self.Points[self.count+1],dtype=float)
                    Distance = np.linalg.norm(a-b)
                    self.Distances.append(Distance)

                if len(self.Distances)== 1:
                    self.MaxDistance = self.Distances[0]

                elif len(self.Distances) > 1:

                    if self.Distances[self.count-1] > self.MaxDistance:
                        self.Forward = True
                        self.CutStrokeFlag=False
                        self.MaxDistance = self.Distances[self.count-1]

                    else:

                        self.MaxDistance = self.Distances[self.count - 1]
                        if self.Forward is True:
                            self.CutStrokeFlag=True
                            WearableSignals.GetKStroke(self.MultiFrames)
                            self.FullStrokes.append(self.MultiFrames)
                            self.MultiFrames = []
                            self.Forward = False

                if self.Forward is True:
                    for i in range(16):
                        SingleFrame.append(resultKinect[i])

                    self.MultiFrames.append(SingleFrame)
                    SingleFrame = []




        c.close()

    def SendStroke(self):
        return self.FullStrokes

    def SensorCutStroke(self):
        return  self.CutStrokeFlag,self.Forward







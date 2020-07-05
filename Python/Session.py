import requests
import json

class Session:

    SessionData=[]
    SessionSTime=0
    SessionETime=0
    PlayerID=0
    noCorrect = 0
    noWrong = 0
    noTotal = 0
    PercentageRatio = 0
    ErrorRatio = 0
    SessionID=0


    def GetSessionInfo(self,array):
        self.SessionData=array

    def GetInfoFromSensors(self,SST,SET,PID):
        self.SessionETime=SET
        self.SessionSTime=SST
        self.PlayerID=PID

    def GetfromClassification(self,no_correct,no_wrong):
        self.noCorrect=no_correct
        self.noWrong=no_wrong
        self.noTotal=no_wrong+no_correct
        self.PercentageRatio=no_correct / self.noTotal
        self.ErrorRatio=1-self.PercentageRatio

    def InsertSession(self):


        new_dict = dict(playerID = self.PlayerID , startSessionTime= self.SessionSTime , endSessionTime= self.SessionETime,numberOfCorrectStrokes= self.noCorrect , numberOfWrongStrokes= self.noWrong,totalNumberOfStrokes = self.noTotal , errorRatio= self.ErrorRatio , percentageRate= self.PercentageRatio)

        tableName = 'tb_Session'

        encoded = json.dumps(new_dict)
        keys = {"table": tableName, "data": encoded}

        r = requests.post("https://traintit-ipingpong.000webhostapp.com/insert.php", data=keys)


    def InsertClassificationResult(self,error,mistake,stroke):

        new_dict = dict(date=self.SessionSTime,errorType=error, isMistake=mistake, strokeType=stroke,
                        sessionDataID=self.SessionID,playerID = self.PlayerID)

        tableName = 'tb_ClassificationResult'

        encoded = json.dumps(new_dict)
        keys = {"table": tableName, "data": encoded}

        r = requests.post("https://traintit-ipingpong.000webhostapp.com/insert.php", data=keys)


    def InsertSessionData(self):

        new_dict = dict(self.SessionData)
        str1 = str(new_dict)
        a_string = str1.replace("'", '"')
        print(a_string)

        new_dict2 = dict(StrokeDataJson=a_string)
        tableName = 'tb_SessionData'

        encoded = json.dumps(new_dict2)
        keys = {"table": tableName, "data": encoded}

        r = requests.post("https://traintit-ipingpong.000webhostapp.com/insert.php", data=keys)
        x = json.loads(r.text)
        self.SessionID = x["id"]










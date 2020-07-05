import zope.interface


class IClassification(zope.interface.Interface):

    def Classify(self, arraySignals):
        pass




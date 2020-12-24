
#Check student ID
def validID(stuID):
    stuIDLst = []
    Flag =  False
    with open ("Student.txt","r") as name:
        for line in name:
            nameIDLst = line.strip("\n").split("|")
            stuIDLst.append(nameIDLst[0])
        if stuID in stuIDLst:
            Flag = True
    return Flag

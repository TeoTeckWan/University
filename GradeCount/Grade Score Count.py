gradeList = ["F","C","C+","B-","B","B+","A-","A","A+"]
uplist = [49,54,59,64,69,74,79,89,100]
downlist = [0,50,55,60,65,70,75,80,90]
scoreList = []
countList = [0,0,0,0,0,0,0,0,0] #Grade Score Count (F,C,C+,B-,B,B+,A-,A,A+)

with open("StudentsMarks.txt","r") as f: #Open file and Read
    for line in f:
        data = line.strip("\n").split(",") #List form
        score = data[1]

        scoreList.append(score)

count = 0
limit = 100

for i in range(len(scoreList)): #For each of element in list (If only 1 element/input then no need)
    while count < limit:
        if int(downlist[count])<=int(scoreList[i])<=int(uplist[count]):
            countList[count]+=1
            count = limit

        else:
            count+=1
    count = 0

print("Grade\t\tCount")
print("-----\t\t-----")
print("A+\t\t","%4d"%countList[8])
print("A\t\t","%4d"%countList[7])
print("A-\t\t","%4d"%countList[6])
print("B+\t\t","%4d"%countList[5])
print("B\t\t","%4d"%countList[4])
print("B-\t\t","%4d"%countList[3])
print("C+\t\t","%4d"%countList[2])
print("C\t\t","%4d"%countList[1])
print("F\t\t","%4d"%countList[0])

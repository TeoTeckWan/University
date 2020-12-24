import os

#Screen display
def recRead(stuID):
    print("\n\n\n      ","-"*95)

    #Transfer students' ID and name into list
    with open ("Student.txt","r") as name:
        stuIDLst = []
        stuName = []
        for line in name:
            nameIDLst = line.strip("\n").split("|")
            stuIDLst.append(nameIDLst[0])
            stuName.append(nameIDLst[1])

        #Find student who entered the student ID
        if stuID in stuIDLst:
            positionID = stuIDLst.index(stuID)
            print("\t%s"%stuIDLst[positionID],"(%s)"%stuName[positionID])
            
    print("      ","-"*95)
    print("        UTAR CFS Students' CGPA Calculator")
    print("      ","-"*95)

    #Transfer course/code into list
    with open ("Courses.txt","r") as crsFile:
        courseCode = []
        courseName = []
        for line in crsFile:
            courseLst = line.strip("\n").split("|")
            courseCode.append(courseLst[0])
            courseName.append(courseLst[1])

    #Display the upper part of student profile (Student ID -> Course/Grade)    
    with open ("StudentRecord.txt","r") as stuRec:
        c = 1
        for line in stuRec:
            lineLst = line.strip("\n").split("|")
            studentID = lineLst[0]
            courseData = lineLst[1:]

            if stuID == studentID:
                if courseData[0] in courseCode:
                    positionCrs = courseCode.index(courseData[0])
                    nameCourse = courseName[positionCrs]
                    lineStr = "\t%d\t%s    %-45s\t%-3s\t%.3f"%(c,lineLst[1],nameCourse,lineLst[2],float(lineLst[3]))
                    c += 1
                    print(lineStr)

    #Display the bottom part of student profile (TotCr/CGPA/...)
    with open ("StudentRecord.txt","r") as file:
        countCrs = []
        
        crhrLst = []
        
        gradeLst = ["A+","A","A-","B+","B","B-","C+","C","F"]
        gpoints = [4.0,4.0,3.667,3.333,3.0,2.667,2.333,2.0,0]
        addGrade = []
        gpLst = []

        stuIDList = []

        for line in file:
            profile = line.strip("\n").split("|")
            
            studentID = profile[0]
            stuIDList.append(studentID)
            
            course = profile[1]
            crhr = course[-1]

            grade = profile[2]
            if stuID in stuIDList:
                if stuID == studentID:
                
                    countCrs.append(c-1)
                    lastCount = countCrs[-1]
                    intlastCount = int(lastCount)

                    crhrLst.append(crhr)
                    intCrhr = [int(x) for x in crhrLst]
                    totalCrhr = sum(intCrhr)

                    addGrade.append(grade)
                    positionGP = [gpoints[gradeLst.index(i)] for i in addGrade]
                    gpLst = [intCrhr[z] * positionGP[z] for z in range(len(intCrhr))]
                    totalGP = sum(gpLst)

                    cgpa = totalGP/totalCrhr
            else:
                intlastCount = 0
                totalCrhr = 0
                totalGP = 0
                cgpa = 0

    print("\n\n\t","%.2d"%intlastCount,"\tTotCH:","%2d"%totalCrhr,"    TotGP: %.3f"%totalGP,"\t\t\t\t\tCGPA:%8.3f"%cgpa, sep= "")
    print("      ","-"*95)
    print("\t\tOptions > (A)dd   (U)pdate   (D)elete   (R)emove Account   (Q)uit")

#Function Add
def addRec(stuID):
    gradeLst = ["A+","A","A-","B+","B","B-","C+","C","F"]
    gpoints = [4.0,4.0,3.667,3.333,3.0,2.667,2.333,2.0,0]

    flag = True
    while flag:
        crsCode = input("\t\tEnter the course code\t\t\t > ")
        upperCourse = crsCode.upper()

        #Transfer course/code into list
        with open ("Courses.txt","r") as crsFile:
            courseCode = []
            courseName = []
        
            for line in crsFile:
                courseLst = line.strip("\n").split("|")
                courseCode.append(courseLst[0])
                courseName.append(courseLst[1])

            #Check course is available or not
            if upperCourse in courseCode:
                positionCrs = courseCode.index(upperCourse)
                nameCrs = courseName[positionCrs]
                flag = False
                print("\t\t>>",nameCrs)
                
            else:
                print("\t\tError course entered...")

    flag = True
    while flag:
        grade = input("\t\tEnter the grade (A+/A/A-/B+/B/B-/C+/C/F) > ")
        upperGrade = grade.upper()
        
        #Check grade is it in the list
        if upperGrade in gradeLst:
            GP = gpoints[gradeLst.index(upperGrade)]
            strGP = str(GP)
            flag = False
        else:
            print("\t\tError grade entered...")

    #Input for Confirm / Rest / Quit
    print("      ","-"*95)
    confirm = input("\t\t(C)onfirm / (R)eset / (A)bort or Quit\t > ")
    
    #Check input
    if confirm.upper() == "C":
        #Transfer course/code into list
        with open ("StudentRecord.txt","r") as stuRec:
            stuCrs = []
            
            for line in stuRec:
                lineLst = line.strip("\n").split("|")
                studentID = lineLst[0]
                courseData = lineLst[1:]

                if stuID == studentID:
                    if courseData[0] in courseCode:
                        stuCrs.append(courseData[0])
            
            #Check course is in list or not
            if upperCourse in stuCrs:
                enter = input("\t\tCourse already taken...\n\t\tPress Enter to continue")
                os.system("cls")
                recRead(stuID)

            else:
                #Add file
                with open ("StudentRecord.txt","a+") as addfile2:
                    wStr = stuID+"|"+upperCourse+"|"+upperGrade+"|"+strGP+"\n"
                    addfile2.write(wStr)

                os.system("cls")
                recRead(stuID)
        
    elif confirm.upper() == "R":
        os.system("cls")
        recRead(stuID)
        addRec(stuID)
        
    elif confirm.upper() == "A":
        os.system("cls")
        recRead(stuID)
        
    else:
        enter = input("\t\tError command entered...\n\t\tPress enter to back the view...")
        os.system("cls")
        recRead(stuID)

#Function Update
def updRec(stuID):
    gradeLst = ["A+","A","A-","B+","B","B-","C+","C","F"]
    gpoints = [4.0,4.0,3.667,3.333,3.0,2.667,2.333,2.0,0]

    flag = True
    while flag:
        crsCode = input("\t\tEnter the course code\t\t\t > ")
        upperCourse = crsCode.upper()

        #Transfer course/code into list
        with open ("Courses.txt","r") as crsFile:
            courseCode = []
            courseName = []
            
            for line in crsFile:
                courseLst = line.strip("\n").split("|")
                courseCode.append(courseLst[0])
                courseName.append(courseLst[1])

            #Check course is available or not
            if upperCourse in courseCode:
                positionCrs = courseCode.index(upperCourse)
                nameCrs = courseName[positionCrs]
                print("\t\t>>",nameCrs)
                flag = False

            else:
                print("\t\tError course entered...")
                        
    flag = True
    while flag:
        #Input
        grade = input("\t\tEnter the grade (A+/A/A-/B+/B/B-/C+/C/F) > ")
        upperGrade = grade.upper()

        #Check grade is it in the list
        if upperGrade in gradeLst:
            GP = gpoints[gradeLst.index(upperGrade)]
            strGP = str(GP)
            flag = False
        else:
            print("\t\tError grade entered...")

    #Input for Confirm / Rest / Quit
    print("      ","-"*95)
    confirm = input("\t\t(C)onfirm / (R)eset / (A)bort or Quit\t > ")

    #Check input    
    if confirm.upper() == "C":
        #Transfer course/code into list
        with open ("StudentRecord.txt","r") as stuRec:
            stuCrs = []
            
            for line in stuRec:
                lineLst = line.strip("\n").split("|")
                studentID = lineLst[0]
                courseData = lineLst[1:]

                if stuID == studentID:
                    if courseData[0] in courseCode:
                        stuCrs.append(courseData[0])
            
            #Check course is in list or not
            if upperCourse not in stuCrs:
                enter = input("\t\tCourse not found...\n\t\tPress Enter to continue")
                os.system("cls")
                recRead(stuID)

            else:       
                #Update file
                with open ("StudentRecord.txt","r+") as file3:
                    fileLines = file3.readlines()
                    file3.seek(0)
    
                    for line in fileLines:
                        if stuID not in line:
                            file3.write(line)
                
                        elif upperCourse not in line:
                            file3.write(line)
                
                        else:
                            wStr = stuID+"|"+upperCourse+"|"+upperGrade+"|"+strGP+"\n"
                            file3.write(wStr)
                            
                    file3.truncate()
            
                os.system("cls")
                recRead(stuID)
        
    elif confirm.upper() == "R":
        os.system("cls")
        recRead()
        updRec(stuID)
        
    elif confirm.upper() == "A":
        os.system("cls")
        recRead(stuID)
        
    else:
        enter = input("\t\tError command entered...\n\t\tPress enter to back the view...")
        os.system("cls")
        recRead(stuID)

#Function delete
def delRec(stuID):
    flag = True
    while flag:
        crsCode = input("\t\tEnter the course code\t\t\t > ")
        upperCourse = crsCode.upper()

        #Transfer course/code into list
        with open ("Courses.txt","r") as crsFile:
            courseCode = []
            courseName = []
            
            for line in crsFile:
                courseLst = line.strip("\n").split("|")
                courseCode.append(courseLst[0])
                courseName.append(courseLst[1])
                
            #Check course is available or not
            if upperCourse in courseCode:
                positionCrs = courseCode.index(upperCourse)
                nameCrs = courseName[positionCrs]
                print("\t\t>>",nameCrs)
                flag = False

            else:
                print("\t\tError course entered...")

    #Input for Confirm / Rest / Quit
    print("      ","-"*95)
    confirm = input("\t\t(C)onfirm / (R)eset / (A)bort or Quit\t > ")

    #Check input    
    if confirm.upper() == "C":
        #Transfer course/code into list
        with open ("StudentRecord.txt","r") as stuRec:
            stuCrs = []
            
            for line in stuRec:
                lineLst = line.strip("\n").split("|")
                studentID = lineLst[0]
                courseData = lineLst[1:]

                if stuID == studentID:
                    if courseData[0] in courseCode:
                        stuCrs.append(courseData[0])
            
            #Check course is in list or not
            if upperCourse not in stuCrs:
                enter = input("\t\tCourse not found...\n\t\tPress Enter to continue")
                os.system("cls")
                recRead(stuID)

            else:
                #Delete file
                with open ("StudentRecord.txt","r+") as file4:
                    fileLines = file4.readlines()
                    file4.seek(0)

                    for line in fileLines:
                        if stuID not in line:
                            file4.write(line)
            
                        elif upperCourse not in line:
                            file4.write(line)
                        
                    file4.truncate()

                os.system("cls")
                recRead(stuID)
        
    elif confirm.upper() == "R":
        os.system("cls")
        recRead(stuID)
        delRec(stuID)
    elif confirm.upper() == "A":
        os.system("cls")
        recRead(stuID)
        
    else:
        enter = input("\t\tError command entered...\n\t\tPress enter to back the view...")
        os.system("cls")
        recRead(stuID)
        
def remRec(stuID):
    flags = True
    while flags:
        confirm = input("\t\tAre you confirm want to remove your account? (Y)es / (N)o > ")

        if confirm.upper() == "N":
            os.system("cls")
            flags = False
            
        elif confirm.upper() == "Y":            
            with open ("StudentRecord.txt","r+") as file5:
                fileLines = file5.readlines()
                file5.seek(0)

                for line in fileLines:
                    if stuID not in line:
                        file5.write(line)
                        
                file5.truncate()

            with open ("Student.txt","r+") as file6:
                fileLines = file6.readlines()
                file6.seek(0)

                for line in fileLines:
                    if stuID not in line:
                        file6.write(line)
                        
                file6.truncate()
            os.system("cls")
            flags = False

        else:
            error = input("\t\tInvalid command\n\t\tPress enter to continue...")
            os.system("cls")
            recRead(stuID)

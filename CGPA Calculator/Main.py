import os
import StudentID
import CRUD

flag = True
loop = True

while flag:
    print("\n\n\n\n\n\t\t\t\t\t\t\t\t  University Tunku Abdul Rahman (UTAR)")
    print("\t\t\t\t\t\t\t\t\t  Foundation in Science / Arts")
    print("      ","-"*95)
    print("\n\t\tFirst time here?")
    print("\t\tPress (S) for sign up free for now!")
    print("\n      ","-"*95)
    
    ID = input("\t\tStudent's ID (xxxxxxx) (Q)uit to stop > ")

    if ID.upper() == "Q":
        flag = False

    elif ID.upper() == "S":
        os.system("cls")
        print("\n\n\n\n\n\t\t\t\t\t\t\t\t  University Tunku Abdul Rahman (UTAR)")
        print("\t\t\t\t\t\t\t\t\t  Foundation in Science / Arts")
        print("      ","-"*95)
        
        newname = input("\t\tEnter your name\t      > ")
        newstudentID = input("\t\tEnter your student ID > ")

        #Write data to file
        with open("Student.txt","a+") as file:
            data = newstudentID+"|"+newname+"\n"
            file.write(data)
            
        enter = input("\n\t\tNew Account is succssfully sign up!\n\t\tYou are able to log in now.\n\t\tPlease enter to continue...")
        os.system("cls")
        
    #Validation of student ID
    elif StudentID.validID(ID):
        os.system("cls")
        CRUD.recRead(ID)
        loop = True
        
        while loop:
            opt = input("\t\tOption >> ")

        #Functions execution (Add / Update / Delete / Quit)
            if opt.upper() == "A":
                CRUD.addRec(ID)

            elif opt.upper() == "U":
                CRUD.updRec(ID)

            elif opt.upper() == "D":
                CRUD.delRec(ID)

            elif opt.upper() == "R":
                CRUD.remRec(ID)

                with open("Student.txt","r") as file2:
                    stuIDLst = []
                    for line in file2:
                        nameIDLst = line.strip("\n").split("|")
                        stuIDLst.append(nameIDLst[0])
                #Check ID still there or not        
                if ID in stuIDLst:
                    CRUD.recRead(ID)
                    
                elif ID not in stuIDLst:
                    loop = False

            elif opt.upper() == "Q":
                os.system("cls")
                loop = False

            else:
                error = input("\t\tError command entered...\n\t\tPress Enter to continue...")
                os.system("cls")
                CRUD.recRead(ID)

    else:
        print("\n\n\n      ","-"*95)
        error = input("\t\tStudent not found...\n\t\tPress enter to continue...")
        os.system("cls")

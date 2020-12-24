import datetime

nric = "1"
mthLst = ["","January","February","March","April","May","June","July","August","September","October","November","December"]
yearLst = [2000,1900,1900,1900,1900]
date = datetime.datetime.now() #Date now
month = mthLst[date.month]

while nric != "0":
    nric = input("Enter NRIC Number (YYMMDD) / Enter 0 to Stop >> ")
    if nric.isdigit(): #Check digit
        if nric != "0": #check if you are want get out of the loop
            if len(nric) == 6 and 1<=int(nric[2:4])<=12 and 1<=int(nric[4:])<=31: #Validation of date
                dd = nric[4:]
                mm = nric[2:4]
                yy = nric[:2]

                if int(dd) in [1,21,31] or date.day in [1,21,31]:
                    postfix = "ST"
                elif int(dd) in [2,22] or date.day in [2,22]:
                    postfix = "ND"
                elif int(dd) in [3,23] or date.day in [3,23]:
                    postfix = "RD"
                else:
                    postfix = "TH"
    
                datenow = "%d%s"%(date.day,postfix)
        
                dayDesc = "%d%s"%(int(dd),postfix)
                mthDesc = mthLst[int(mm)]
                yearDesc = yearLst[(int(yy)//21)] + int(yy)

                age = date.year - yearDesc
            
                print("\nToday's Date  = ",datenow,month,date.year)
                print("Your Birthday = ",dayDesc,mthDesc,yearDesc)
                print("-"*37)
                print("Age =",age)
                if yearDesc%4 == 0 and yearDesc != 100 or yearDesc%400 == 0: #Leap year check
                    print("Your Birthday is a leap year!\n")
                else:
                    print("Your Birthday is not a leap year.\n")
            else:
                print("Invalid IC number...\n")
    else:
        print("Error number entered...\n")
                

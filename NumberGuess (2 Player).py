from random import *

name1 = input("Enter Name of player 1\t\t: ")
name2 = input("Enter Name of player 2\t\t: ")
point = [0,0] #Player 1,Player 2

loop = True
while loop:
    print("\nPoints accumulated for player 1 :",point[0])
    print("Points accumulated for player 2 :",point[1])
    print("-"*50)
    
    play = input("Play a game? (Y-yes, N-to stop) : ")
    if play.upper() == "Y":
        number = randint(1,250)
        player = randint(1,2) #Random choose player to start
        count = 0

        while count < 20: #20 Rounds (Each 10 Rounds)
            guess = input("Player %d : "%player)
            if guess.isdigit():
                if int(guess) > number:
                    print("Number is smaller than",guess)
                    count += 1
                    
                elif int(guess) < number:
                    print("Number is larger than",guess)
                    count += 1
                    
                elif int(guess) == number:
                    print("You've got a match, player",player,"wins")
                    count += 1
                    points = 20-count #Calculate points
                    point[player-1] += points #First player or Second player in list to add points
                    count = 21 #Exit while loop
                    
                elif count == 20: #No chance left
                    print("You have exhausted all chances")
                    
            else:
                print("Error in number given")
                
            if player == 1: #Change player
                player = 2
                
            else:
                player = 1
                
    elif play.upper() == "N":
        print("\n\nPoints:  Player 1->","%02d"%point[0],"  Player 2->","%02d"%point[1])
        loop = False

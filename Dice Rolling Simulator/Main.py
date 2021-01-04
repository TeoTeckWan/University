import pygame
import random

pygame.init()

logo = pygame.image.load("Logo.jpg")
pygame.display.set_icon(logo)
pygame.display.set_caption("Dice Rolling Simulator")

screen = pygame.display.set_mode((1200,650))

pic = pygame.image.load("DicePicture.png")
pic = pygame.transform.scale(pic,(480,70))

font0 = pygame.font.SysFont("ink free",55,False,False)
text0 = font0.render("Dice Rolling Simulator",1,(0,0,0))

class dice(object): #Dice button
    def __init__(self,color,x,y,width,height,text = "",name = ""): #Follow the pygame.draw.rect format
        self.color = color
        self.x = x
        self.y = y
        self.width = width
        self.height = height
        self.text = text
        self.name = name

    def draw(self,screen,outline = None): #Draw where, outline(bracket rectangle)
        if outline: #Bracket rectangle
            pygame.draw.rect(screen,outline,(self.x-5,self.y-5,self.width+10,self.height+10),0)

        #Format pygame.draw.rect(surface,color,[details],number>decide fill in color in rect or not)
        pygame.draw.rect(screen,self.color,(self.x,self.y,self.width,self.height),0)

        if self.text != "": #(Faces / Remove)
            font = pygame.font.SysFont("banhnschrift light semicondensed",28,False,False)
            text = font.render(self.text,1,(0,0,0))
            screen.blit(text,(self.x + self.width/4.1, self.y + self.height/2))

        if self.name == "Tetrahedron":
            font1 = pygame.font.SysFont("comicsans",30,False,False)
            text1 = font1.render(self.name,1,(0,0,0))
            screen.blit(text1,(self.x+self.width/5.5, self.y+self.height/4))

        if self.name == "Cube":
            font2 = pygame.font.SysFont("comicsans",30,False,False)
            text2 = font2.render(self.name,1,(0,0,0))
            screen.blit(text2,(self.x+self.width/2.7, self.y+self.height/4))

        if self.name == "Octahedron":
            font3 = pygame.font.SysFont("comicsans",30,False,False)
            text3 = font3.render(self.name,1,(0,0,0))
            screen.blit(text3,(self.x+self.width/5.35, self.y+self.height/4))

        if self.name == "Pentagonal Trapezohedron":
            font4 = pygame.font.SysFont("comicsans",20,False,False)
            text4 = font4.render(self.name,1,(0,0,0))
            screen.blit(text4,(self.x+self.width/30, self.y+self.height/4))

        if self.name == "Dodecahedron":
            font5 = pygame.font.SysFont("comicsans",30,False,False)
            text5 = font5.render(self.name,1,(0,0,0))
            screen.blit(text5,(self.x+self.width/9, self.y+self.height/4))

        if self.name == "Reset Button":
            font6 = pygame.font.SysFont("comicsans",28,False,False)
            text6 = font6.render(self.name,1,(0,0,0))
            screen.blit(text6,(self.x+self.width/7.3, self.y+self.height/6.5))

    def isOver(self,pos): #Position mouse
        if pos[0] > self.x and pos[0] < self.x + self.width: #Make sure inside the box
            if pos[1] > self.y and pos[1] < self.y + self.height:
                return True

        return False
    
            
def reDrawScreen():
    screen.fill((255,255,255))
    screen.blit(pic,(360,70))
    screen.blit(text0,(335,0))
    
    fourDice.draw(screen,(0,0,0)) #Draw on screen, Outline (Color)
    sixDice.draw(screen,(0,0,0))
    eightDice.draw(screen,(0,0,0))
    tenDice.draw(screen,(0,0,0))
    twelveDice.draw(screen,(0,0,0))
    
    clearButton.draw(screen,(0,0,0))

    if draw == True:
        font7 = pygame.font.SysFont("comicsans",30,False,False)
        text7 = font7.render("The number that you rolled is ",1,(0,0,0))
        screen.blit(text7,(440,420))
        
        pygame.draw.rect(screen,(0,0,0),(484,150,230,230),4) #Square

        if ranNum == 1:
            pygame.draw.circle(screen,(0,0,0),(599,265),15) #Circle inside the square
            
            font8 = pygame.font.SysFont("sans serif",30,True,False)
            text8 = font8.render(str(ranNum),1,(247, 37, 37))
            screen.blit(text8,(736,420))
            
        elif ranNum == 2:
            pygame.draw.circle(screen,(0,0,0),(561,210),15)
            pygame.draw.circle(screen,(0,0,0),(637,320),15)
            
            font9 = pygame.font.SysFont("sans serif",30,True,False)
            text9 = font9.render(str(ranNum),1,(247, 37, 37))
            screen.blit(text9,(736,420))
            
        elif ranNum == 3:
            pygame.draw.circle(screen,(0,0,0),(561,210),15)
            pygame.draw.circle(screen,(0,0,0),(637,320),15)
            pygame.draw.circle(screen,(0,0,0),(599,265),15)
            
            font10 = pygame.font.SysFont("sans serif",30,True,False)
            text10 = font10.render(str(ranNum),1,(247, 37, 37))
            screen.blit(text10,(736,420))
            
        elif ranNum == 4:
            pygame.draw.circle(screen,(0,0,0),(561,230),15)
            pygame.draw.circle(screen,(0,0,0),(637,300),15)
            pygame.draw.circle(screen,(0,0,0),(637,230),15)
            pygame.draw.circle(screen,(0,0,0),(561,300),15)

            font11 = pygame.font.SysFont("sans serif",30,True,False)
            text11 = font11.render(str(ranNum),1,(247, 37, 37))
            screen.blit(text11,(736,420))
            
        elif ranNum == 5:
            pygame.draw.circle(screen,(0,0,0),(561,210),15)
            pygame.draw.circle(screen,(0,0,0),(637,320),15)
            pygame.draw.circle(screen,(0,0,0),(599,265),15)
            pygame.draw.circle(screen,(0,0,0),(637,210),15)
            pygame.draw.circle(screen,(0,0,0),(561,320),15)

            font12 = pygame.font.SysFont("sans serif",30,True,False)
            text12 = font12.render(str(ranNum),1,(247, 37, 37))
            screen.blit(text12,(736,420))
            
        elif ranNum == 6:
            pygame.draw.circle(screen,(0,0,0),(561,210),15)
            pygame.draw.circle(screen,(0,0,0),(637,320),15)
            pygame.draw.circle(screen,(0,0,0),(561,265),15)
            pygame.draw.circle(screen,(0,0,0),(637,210),15)
            pygame.draw.circle(screen,(0,0,0),(561,320),15)
            pygame.draw.circle(screen,(0,0,0),(637,265),15)

            font13 = pygame.font.SysFont("sans serif",30,True,False)
            text13 = font13.render(str(ranNum),1,(247, 37, 37))
            screen.blit(text13,(736,420))
            
        elif ranNum == 7:
            pygame.draw.circle(screen,(0,0,0),(541,200),15)
            pygame.draw.circle(screen,(0,0,0),(599,200),15)
            pygame.draw.circle(screen,(0,0,0),(541,265),15)
            pygame.draw.circle(screen,(0,0,0),(657,200),15)
            pygame.draw.circle(screen,(0,0,0),(541,330),15)
            pygame.draw.circle(screen,(0,0,0),(599,265),15)
            pygame.draw.circle(screen,(0,0,0),(657,330),15)

            font14 = pygame.font.SysFont("sans serif",30,True,False)
            text14 = font14.render(str(ranNum),1,(247, 37, 37))
            screen.blit(text14,(736,420))
            
        elif ranNum == 8:
            pygame.draw.circle(screen,(0,0,0),(541,200),15)
            pygame.draw.circle(screen,(0,0,0),(599,200),15)
            pygame.draw.circle(screen,(0,0,0),(541,265),15)
            pygame.draw.circle(screen,(0,0,0),(657,200),15)
            pygame.draw.circle(screen,(0,0,0),(541,330),15)
            pygame.draw.circle(screen,(0,0,0),(657,265),15)
            pygame.draw.circle(screen,(0,0,0),(657,330),15)
            pygame.draw.circle(screen,(0,0,0),(599,330),15)

            font15 = pygame.font.SysFont("sans serif",30,True,False)
            text15 = font15.render(str(ranNum),1,(247, 37, 37))
            screen.blit(text15,(736,420))
            
        elif ranNum == 9:
            pygame.draw.circle(screen,(0,0,0),(541,200),15)
            pygame.draw.circle(screen,(0,0,0),(599,200),15)
            pygame.draw.circle(screen,(0,0,0),(541,265),15)
            pygame.draw.circle(screen,(0,0,0),(657,200),15)
            pygame.draw.circle(screen,(0,0,0),(541,330),15)
            pygame.draw.circle(screen,(0,0,0),(657,265),15)
            pygame.draw.circle(screen,(0,0,0),(657,330),15)
            pygame.draw.circle(screen,(0,0,0),(599,330),15)
            pygame.draw.circle(screen,(0,0,0),(599,265),15)

            font16 = pygame.font.SysFont("sans serif",30,True,False)
            text16 = font16.render(str(ranNum),1,(247, 37, 37))
            screen.blit(text16,(736,420))
            
        elif ranNum == 10:
            pygame.draw.circle(screen,(0,0,0),(570,190),15)
            pygame.draw.circle(screen,(0,0,0),(628,190),15)
            pygame.draw.circle(screen,(0,0,0),(531,233),15)
            pygame.draw.circle(screen,(0,0,0),(667,233),15)
            pygame.draw.circle(screen,(0,0,0),(531,298),15)
            pygame.draw.circle(screen,(0,0,0),(667,298),15)
            pygame.draw.circle(screen,(0,0,0),(628,340),15)
            pygame.draw.circle(screen,(0,0,0),(570,340),15)
            pygame.draw.circle(screen,(0,0,0),(570,233),15)
            pygame.draw.circle(screen,(0,0,0),(628,298),15)

            font17 = pygame.font.SysFont("sans serif",30,True,False)
            text17 = font17.render(str(ranNum),1,(247, 37, 37))
            screen.blit(text17,(736,420))
            
        elif ranNum == 11:
            pygame.draw.circle(screen,(0,0,0),(570,190),15)
            pygame.draw.circle(screen,(0,0,0),(628,190),15)
            pygame.draw.circle(screen,(0,0,0),(531,233),15)
            pygame.draw.circle(screen,(0,0,0),(667,233),15)
            pygame.draw.circle(screen,(0,0,0),(531,298),15)
            pygame.draw.circle(screen,(0,0,0),(667,298),15)
            pygame.draw.circle(screen,(0,0,0),(628,340),15)
            pygame.draw.circle(screen,(0,0,0),(570,340),15)
            pygame.draw.circle(screen,(0,0,0),(570,233),15)
            pygame.draw.circle(screen,(0,0,0),(628,298),15)
            pygame.draw.circle(screen,(0,0,0),(628,233),15)

            font18 = pygame.font.SysFont("sans serif",30,True,False)
            text18 = font18.render(str(ranNum),1,(247, 37, 37))
            screen.blit(text18,(736,420))
            
        elif ranNum == 12:
            pygame.draw.circle(screen,(0,0,0),(570,190),15)
            pygame.draw.circle(screen,(0,0,0),(628,190),15)
            pygame.draw.circle(screen,(0,0,0),(531,233),15)
            pygame.draw.circle(screen,(0,0,0),(667,233),15)
            pygame.draw.circle(screen,(0,0,0),(531,298),15)
            pygame.draw.circle(screen,(0,0,0),(667,298),15)
            pygame.draw.circle(screen,(0,0,0),(628,340),15)
            pygame.draw.circle(screen,(0,0,0),(570,340),15)
            pygame.draw.circle(screen,(0,0,0),(570,233),15)
            pygame.draw.circle(screen,(0,0,0),(628,298),15)
            pygame.draw.circle(screen,(0,0,0),(628,233),15)
            pygame.draw.circle(screen,(0,0,0),(570,298),15)

            font19 = pygame.font.SysFont("sans serif",30,True,False)
            text19 = font19.render(str(ranNum),1,(247, 37, 37))
            screen.blit(text19,(736,420))

    else:
        font20 = pygame.font.SysFont("sans serif",30,False,False)
        text20 = font20.render("Please select a dice to roll...",1,(36,36,36))
        screen.blit(text20,(461,420))

fourDice = dice((255, 0, 247),110,480,180,80," (4 Faces)","Tetrahedron") #Class(color,x,y,width,height,text,name)
sixDice = dice((0, 153, 255),310,480,180,80," (6 Faces)","Cube")
eightDice = dice((13, 255, 255),510,480,180,80," (8 Faces)","Octahedron")
tenDice = dice((60, 255, 54),710,480,180,80,"(10 Faces)","Pentagonal Trapezohedron")
twelveDice = dice((255, 255, 0),910,480,180,80,"(12 Faces)","Dodecahedron")
clearButton = dice((255,255,255),515,580,170,50,"(Remove)","Reset Button")

run = True
draw = False
while run:
    reDrawScreen()
    pygame.display.update() #Update the screen
    
    for event in pygame.event.get():
        pos = pygame.mouse.get_pos() #Mouse position
        
        if event.type == pygame.QUIT:
            run = False
            pygame.quit()
        
        if event.type == pygame.MOUSEBUTTONDOWN: #Click button
            if fourDice.isOver(pos):
                draw = True
                ranNum = random.randint(1,4)

            elif sixDice.isOver(pos):
                draw = True
                ranNum = random.randint(1,6)

            elif eightDice.isOver(pos):
                draw = True
                ranNum = random.randint(1,8)

            elif tenDice.isOver(pos):
                draw = True
                ranNum = random.randint(1,10)

            elif twelveDice.isOver(pos):
                draw = True
                ranNum = random.randint(1,12)

            elif clearButton.isOver(pos):
                draw = False

        if event.type == pygame.MOUSEMOTION: #Mouse move to the position
            if fourDice.isOver(pos): #Change color
                fourDice.color = (149, 12, 235)
                
            elif sixDice.isOver(pos):
                sixDice.color = (0, 191, 255)

            elif eightDice.isOver(pos):
                eightDice.color = (0, 255, 132)

            elif tenDice.isOver(pos):
                tenDice.color = (183, 255, 66)

            elif twelveDice.isOver(pos):
                twelveDice.color = (255, 201, 84)

            elif clearButton.isOver(pos):
                clearButton.color = (255,0,0)
                
            else:
                fourDice.color = (255, 0, 247)
                sixDice.color = (0, 153, 255)
                eightDice.color = (13, 255, 255)
                tenDice.color = (60, 255, 54)
                twelveDice.color = (255, 255, 0)
                clearButton.color = (255,255,255)

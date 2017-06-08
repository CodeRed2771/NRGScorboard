import random

teams = []

num = int(input("Number of Teams ? "))
for i in range(num):
    teams.append(str(input("Team name " + str(i + 1) + " ? ")))

print("Generating Match Schedule...")

# match, 0 = red or 1 = blue, teams
matches = []

usedteams = []
usedmatchteams = []
def getteam():
    global usedteams
    global usedmatchteams
    choices = []
    for team in teams:
        if team not in usedteams and team not in usedmatchteams:
            choices.append(team)
    choice = random.choice(choices)
    usedteams.append(choice)
    usedmatchteams.append(choice)
    if len(usedteams) is num:
        usedteams = []
    if len(usedmatchteams) is 4:
        usedmatchteams = []
    return choice

for i in range(num):
    red = []
    blue = []
    red1 = getteam()
    red2 = getteam()
    blue1 = getteam()
    blue2 = getteam()
    red = [red1, red2]
    blue = [blue1, blue2]
    match = [red, blue]
    matches.append(match)

# file = open('' + str(random.randint(0, 1000000)) + ".csv")

thefile = open('Schedule.csv', 'w')

for index, match in enumerate(matches):
    # print("Match " + str(index + 1) + ": ", end='')
    # for pos, team in enumerate(match[0]):
    #     print("Red " + str(pos + 1) + ": " + team, end = ' ')
    # for pos, team in enumerate(match[1]):
    #     print("Blue " + str(pos + 1) + ": " + team, end = ' ')
    # print()
   thefile.write(match[0][0]+ ","+match[0][1]+ ","+match[1][0]+ ","+match[1][1] + "\n")

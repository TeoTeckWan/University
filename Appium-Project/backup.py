import json

# Load JSON data from a file
with open('database.json', 'r') as file:
    data = json.load(file)

    for user in data["users"]:
        if user["personName"] == "TeckWan":
            print(f'{user["number"]},{user["code"]}')
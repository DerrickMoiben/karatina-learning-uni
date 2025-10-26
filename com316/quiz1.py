import random

def rational_agent():

    soil_moisture = random.randint(0, 100)
    print("The soil moisture is this", soil_moisture)

    if soil_moisture < 40:
        print("Action: Water plants")
    else:
        print("Action: Wait")

rational_agent()

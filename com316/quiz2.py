def traffic_light_agent(traffic_input):
    print("Perception:", traffic_input)
    
    if traffic_input == "low traffic":
        decision = "Green light time: short"
    elif traffic_input == "medium traffic":
        decision = "Green light: normal"
    elif traffic_input == "high traffic":
        decision = "Green light: long"
    else:
        decision = "Ivalid input"
        
    print("Action:", decision)
    print("Decision Time: Instant (Reflex Agent)")
    
    
traffic_light_agent("high traffic")
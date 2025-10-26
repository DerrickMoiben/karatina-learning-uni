def chatbot():
    print("How can i help  you")
    user_input =  input("")
    
    if "hello" in user_input or "hi" in user_input:
        print("Hello! How can I help you today ?")
    elif "weather" in user_input:
        print("The weather is great! Perfect for Coding")
    else:
        print("I'm not sure how to answer that")

chatbot()
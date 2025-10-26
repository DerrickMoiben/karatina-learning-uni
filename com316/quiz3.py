import string

def text_processing():
  i = "AI is the future of innovation in the world."
  clean_words = ['and', 'the', 'is', 'in', 'of']
  
  lowercasei = i.lower()
  print(lowercasei)
  
  translator = str.maketrans('', '', string.punctuation)
  cleaned_lowercase1 = lowercasei.translate(translator)
  
  print(cleaned_lowercase1)
  
  cleaned_words = [word for word in cleaned_lowercase1.split() if word not in clean_words]
  print(cleaned_words)
  print("Count: ", len(cleaned_words))
text_processing()
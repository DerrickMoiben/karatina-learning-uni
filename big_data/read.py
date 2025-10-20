import pandas as pd


mydataset = {
    'cars': ["BMW", "VOLVO", "FORD"],
    'passings': [3, 7, 2]
}

data = pd.DataFrame(mydataset)

print(data)

#series in pandas is one dimensional array holding data of any type
print("Break the line here starts the series")
a = [1, 7, 2]

data1 = pd.Series(a, index = ["x", "y", "z"])


print(data1)

print('printing element in index zero')
print(data1["x"])


#using key/value object like a  dictionary

calories = {"day1":420, "day2":380, "day3":390}

data2 =  pd.Series(calories)

print(data2)
print(data2["day3"])



#DataFrame

#Dataframe are usually multi-dimensional tables

information = {
    "calories": [420, 380, 399],
    "duration": [50, 40, 45]
}

df =  pd.DataFrame(information)

df =  pd.DataFrame(information, index = ["day1", "day2", "day3"])

print(df)

print(df.loc[["day1", "day2"]])


# fro named index in the loc 


#load a file of csv as a dataframe

df = pd.read_csv('cinema (1).csv')

pd.options.display.max_rows = 9999




#pandas - Analyzing DataFrames

#Head methiod retuen the header  and a specified number of rows staturing from the top

print(df.head(10))
print(df.tail(10))

print(df.info())

#data cleaning 

#data cleaning means fixing bad data in your  data set

#returning  a new dataframe with no empty cells

df.dropna(inplace= True)

print(df.to_string())


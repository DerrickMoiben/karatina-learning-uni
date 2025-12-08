# #pandas
# import pandas as pd

# #kijana rows  ni across na coloms ni up and down usikue dwanzi
# #use skiprows=[0, 2, 3] this function will skip the passed rows
# df = pd.read_csv('Airbnb_Open_Data.csv', low_memory=False)

# # print(df.head(10))


# #handling missing values

# # df = pd.isna(df)

# # print(df)

# #handling missing data 
# # NAN not a number
# #NA not available 

# #method used when cleanig data in pandas
# # Dropna -> Filter axis labels based on whether vues for each label have missign dat varyih threshold show much missign data to tolarate
# #filna -> Fill in missing data with sime value using an interpolation such as "ffill" or "bfill"
# #isna -> return boolen values indicating which vaues ar missing na
# #not na -> negation isna returs True fro non -na value and false for na oppositr for isna

# # df = df.dropna()
# # df.head(50)
# # print(df)
# # 
# import numpy as np
# data =  pd.Series([1, np.nan, 3.5, np.nan, 7])

# #by  default dropna drops any row containig missing value 
# print(data.dropna())

# #data transfromation



# #using apply 
# import pandas as pd


# data = {'A': [10, 20, 30], 'B': [15, 25, 35], 'C': [5, 10, 15]}
# df = pd.DataFrame(data)

# print(df)

# def totals(series):
#     return series.sum()

# column_totals = df.apply(totals)

# print(column_totals)

# #names the column name
# column_totals.name =  "Column_Totals"

# #concatinate the the column totalsto the orginal dataframe 
# df_with_totals =  pd.concat([df, pd.DataFrame([column_totals])])


# print(df_with_totals)



# #usign lambda with pandas
import pandas as pd
df = pd.DataFrame({'Name': ['alice', 'bob', 'charlie'], 
                   'Score': [85, 92, 78]})

#lambda on series increase every score by 5 points

df['new_score'] = df['Score'].apply(lambda x: x + 5)

print(df)
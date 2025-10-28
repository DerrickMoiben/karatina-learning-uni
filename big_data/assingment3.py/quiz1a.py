import pandas as pd
# 
# 
# Question 1: (30 Marks)
# a) Consider the Stores.csv data-set.
# i. Read this data into Python Pandas as a DataFrame.
# (2 marks)
# # 
# # df =  pd.read_csv('Stores.csv')
# # 
# # print(df.tail())
# # 
# ii. Using apply, write a function to determine the totals for the values in each column and save
# them in a new row called “Column_Totals”.
# (3 marks)
# # def calculate_colomn_totals(column):
    # return column.sum()
# 
# column_totals = df.apply(calculate_colomn_totals)
# column_totals.name = "Column_Totals"
# 
# df_with_totals = pd.concat([df, pd.DataFrame([column_totals])]) 
# 
# 
# print(df_with_totals.tail())
# 
# iii. Using lambda with apply, write a function to determine the totals for the values in each
# row and save them in a new column called “Row_Totals”.
# (2 marks)
# # 
# 
# 
# df["Row_Totals"] = df.apply(lambda row: row.sum(), axis=1)
# 
# print(df)
# 
# 
# Consider the Airbnb_Open_Data.csv data-set.

df = pd.read_csv("Airbnb_Open_Data.csv", low_memory=False)

print(df)

# # i. Scrutinize the country column for NaN values
# # (2 marks)
# countryna = df['country'].isna()

# print(countryna)

# # ii. If the country column has NaN values, delete from the data-set all records with these values.
# # (3 marks)
df = df.dropna(subset=['country'])
print(df)
# iii. Examine this data-set for records that are a replica of each other by selecting duplicate rows 
# except the first one.
# (3 marks)
duplicated_records =  df.duplicated(keep="first")

print(duplicated_records)
# iv. If duplicate values exist, prove that indeed at-least one record has been duplicated.
# (2 marks)
number_of_duplicates = df.duplicated(keep="first").sum()

if number_of_duplicates > 0:
    print(f"Proof of Duplication: {number_of_duplicates} records were found to be duplicated")
else:
    print("No duplicatess were found")
# v. If duplicate values exist, remove them. Justify your answer.
# (3 marks)
df = df.drop_duplicates(keep='first')
# vi. Generate a data-frame showing the number of listings in each neighborhood group.
# (3 marks)
neighbourhood_counts = df['neighbourhood group'].value_counts()
print(neighbourhood_counts)
# vii.Using seaborn, visualize the number of listings in the neighborhoods.
# (7 marks)
import seaborn as sns
import matplotlib.pyplot as plt



plt.figure(figsize=(10, 6)) # Set the size of the plot


sns.barplot(
    x=neighbourhood_counts.index,  
    y=neighbourhood_counts.values, 
    palette='viridis'              
)


plt.title('Number of Listings by Neighbourhood Group', fontsize=16)
plt.xlabel('Neighbourhood Group', fontsize=12)
plt.ylabel('Total Number of Listings', fontsize=12)


plt.xticks(rotation=45, ha='right')


plt.grid(axis='y', linestyle='--', alpha=0.7)


plt.tight_layout()
plt.show()

# Question 2: (15 Marks)
# Consider the Politics_Tweets.csv data-set.
# The media column of the this data-set has data that has the following format for photos:
# [{"url":"https://pbs.twimg.com/media/
# FUBWWSOWAAIzEJ6.jpg","type":"photo","media_key":"3_1531318814155079682"},
# {"url":"https://pbs.twimg.com/media/
# FUBWWd1XoAMVYmd.jpg","type":"photo","media_key":"3_1531318817271554051"}]
# The most important part of this string that is required for the current project is the url of the image i.e., 
# https://pbs.twimg.com/media/FUBWWd1XoAMVYmd.jpg. 
# Automate the extraction of media urls from these data strings for all records. It is expected 
# that the column of media should only retain the urls.
# (15 marks)
import pandas as pd
import re
import numpy as np 


df = pd.read_csv('Politics_Tweets.csv')

regex_pattern = r'url":"(.*?)"'


df['media'] = df['media'].astype(str).str.extract(regex_pattern, expand=False)

print("DataFrame head after URL extraction:")
print(df.head())
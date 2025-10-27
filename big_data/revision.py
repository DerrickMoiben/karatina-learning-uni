#pandas
import pandas as pd

#kijana rows  ni across na coloms ni up and down usikue dwanzi
#use skiprows=[0, 2, 3] this function will skip the passed rows
df = pd.read_csv('Airbnb_Open_Data.csv', low_memory=False)

# print(df.head(10))


#handling missing values
df = pd.isna(df)

print(df)
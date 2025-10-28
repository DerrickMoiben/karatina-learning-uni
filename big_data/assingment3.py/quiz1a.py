import pandas as pd


# Question 1: (30 Marks)
# a) Consider the Stores.csv data-set.
# i. Read this data into Python Pandas as a DataFrame.
# (2 marks)

df =  pd.read_csv('Stores.csv')

print(df.tail())

# ii. Using apply, write a function to determine the totals for the values in each column and save
# them in a new row called “Column_Totals”.
# (3 marks)
def calculate_colomn_totals(column):
    return column.sum()

column_totals = df.apply(calculate_colomn_totals)
column_totals.name = "Column_Totals"

df_with_totals = pd.concat([df, pd.DataFrame([column_totals])]) 


print(df_with_totals.tail())
# iii. Using lambda with apply, write a function to determine the totals for the values in each
# row and save them in a new column called “Row_Totals”.
# (2 marks)
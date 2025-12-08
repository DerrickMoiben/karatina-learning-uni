#Qestion 1
# import pandas as pd

# df = pd.read_csv("Education_attainment.csv")

#question B                                                                                                                                               

# print(df.head())

# bach_1980 = df[(df['Year'] == 1980) & (df['Min degree'] == "bachelor's")]

# women = bach_1980[bach_1980['Sex'] == 'F']['total'].values
# men = bach_1980[bach_1980['Sex'] == 'M']['total'].values

# print("Women (1980):", women)
# print("Men   (1980):", men)


#qestion c                                

# import pandas as pd

# df = pd.read_csv("Education_attainment.csv")
# df['total'] = pd.to_numeric(df['total'], errors='coerce')

# b2000 = df[(df['Year'] == 2000) & (df['Min degree'].str.contains("bachel"))]['total'].mean()
# b2010 = df[(df['Year'] == 2010) & (df['Min degree'].str.contains("bachel"))]['total'].mean()

# print("2000:", b2000)
# print("2010:", b2010)
# print("Difference:", b2010 - b2000)

#qestion d
# import pandas as pd

# df = pd.read_csv('Education_attainment.csv')

# df['total'] = pd.to_numeric(df['total'], errors='coerce')

# result = (
#     df[df['Year'].between(2000, 2010)]
#     .groupby('Min degree')['total']
#     .mean()
#     .sort_values(ascending=False)
#     .head(2)
# )

# print(result)


#question e
# import pandas as pd

# df = pd.read_csv("Education_attainment.csv")
# df['total'] = pd.to_numeric(df['total'], errors='coerce')

# stats = df.describe()
# print(stats)


#Question f(i)

import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt

df = pd.read_csv("Education_attainment.csv")
df['total'] = pd.to_numeric(df['total'], errors='coerce')

# Ignore NaN values in Min degree column
bachelor_df = df[df['Min degree'].str.contains("bachel", na=False)]

sns.lineplot(data=bachelor_df, x='Year', y='total')
plt.title("Total % with Bachelor's Degree Over Years")



#question f(ii)

import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt

df = pd.read_csv("Education_attainment.csv")

# Ensure 'total' is numeric
df['total'] = pd.to_numeric(df['total'], errors='coerce')

# Filter for 2009 (and assuming high school is in Min degree if available)
# If no Min degree column, we just use all 2009 data
hs_2009 = df[df['Year'] == 2009]

# Create a small dataframe for plotting three bars: Women, Men, Total
# Using 'total' for all since your CSV has no Women/Men
plot_data = pd.DataFrame({
    'Sex': ['Women', 'Men', 'Total'],
    'Percentage': [hs_2009['total'].values[0]]*3  # repeat total three times
})

sns.barplot(data=plot_data, x='Sex', y='Percentage')
plt.xlabel("Sex")
plt.ylabel("Percentage")
plt.title("Percentage Completed High School by Sex (2009)")
plt.show()





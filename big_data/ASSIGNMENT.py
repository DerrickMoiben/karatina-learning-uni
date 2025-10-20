import pandas as pd
import matplotlib.pyplot as plt

df = pd.read_csv('cinema (1).csv')

df.info()


df['budget'] = df['budget'] / 1000000
df['Gross'] = df['Gross'] / 1000000



df['profit'] = df['gross(Million $)'] - df['budget(Million $)']

df_sorted = df.sort_values('profit', ascending=False)

top10 = df_sorted.head(10)


plt.figure(figsize=(12, 8))
plt.scatter(df['budget(Million $)'], df['profit'], alpha=0.7, s=60, color='blue')
plt.xlabel('Budget (Million $)')
plt.ylabel('Profit (Million $)')
plt.title('Budget vs Profit Analysis')
plt.grid(True, alpha=0.3)
plt.axhline(y=0, color='red', linestyle='--', label='Break-even')
plt.legend()
plt.tight_layout()
plt.savefig('budget_vs_profit.png', dpi=150)


plt.figure(figsize=(12, 8))
content_rating_profit = df.groupby('content_rating')['profit'].mean().sort_values(ascending=False)
content_rating_profit.plot(kind='bar', color='orange', alpha=0.7, edgecolor='black')
plt.title('Average Profit by Content Rating', fontweight='bold')
plt.xlabel('Content Rating', fontweight='bold')
plt.ylabel('Average Profit (Million $)', fontweight='bold')
plt.xticks(rotation=45)
plt.grid(axis='y', alpha=0.3)
plt.tight_layout()
plt.show()

plt.figure(figsize=(12, 8))
plt.hist(df['budget(Million $)'].dropna(), bins=30, alpha=0.7, color='skyblue', edgecolor='black')
plt.xlabel('Budget (Million $)', fontweight='bold')
plt.ylabel('Number of Movies', fontweight='bold')
plt.title('Distribution of Movie Budgets', fontweight='bold')
plt.grid(alpha=0.3)
plt.show()

print(df)
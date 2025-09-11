from collections import Counter

with open ("data/sample.txt", "r") as f:
    text = f.read().lower()

words = text.split()
freq = Counter(words)
print(words)
# print(freq)
print(freq.most_common(10))

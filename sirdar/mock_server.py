import pandas as pd

# import requests

# server_url = "127.0.0.1:5000"

# request = requests.post("http://127.0.0.1:5000/savedata", json={
#     "name" : "Sibusiso", 
#     "surname" : "Nkabinde",
#     "age" : "23"
# })

# print(request.content)
df = pd.read_csv("credentials/credentials.csv")


print(type(df.loc[0, "password"]))
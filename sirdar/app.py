import pandas as pd
import string
from flask import Flask, request
import os
import random

app = Flask(__name__)

@app.post("/log-in")
def login():
    data_received = request.get_json()
    email = data_received['email']
    password = data_received['password']

    exists, df = username_password_exists(email, password)
    if exists:
        company_name = df.loc[0, 'company name']
        return {"status" : "200", "company-name" : company_name}
    else:
        return { "status": "404" }

    


@app.post("/create-password")
def create_passwords():
    data_received = request.get_json()
    create_credentials_file()

    company_name = data_received["company-name"]
    count = 0
    response = { "company name": company_name }

    while ( key_defined(data_received, str(count)) ) :
        email = data_received[str(count)]
        password = generate_random_password()

        data = {
            "email": email,
            "password": password,
        }

        with open("credentials/credentials.csv", "a") as f:
            f.write(f"{email},{company_name},{password}\n")
        
        response[str(count)] = data
        count += 1

    print(data_received)
    
    return response


def username_password_exists(email: str, password: str) -> pd:
    if os.path.exists("credentials/credentials.csv"):
        df = pd.read_csv("credentials/credentials.csv")
        df = df[df["email"] == email]
        df = df[df["password"] == password]

        return [df.size > 0, df]

    return [False, None]


def create_credentials_file():
    if not os.path.exists("credentials"):
        os.mkdir("credentials")
    
    if not os.path.exists("credentials/credentials.csv"):
        with open("credentials/credentials.csv", "w") as f:
            f.write("email,company name,password\n")


def key_defined(json: dict, key: str) -> bool:
    try:
        json[key]
        return True
    except KeyError:
        return False
    

def generate_random_password():
    password_length = random.randint(12, 18)
    all_characters = string.ascii_letters + string.digits + string.punctuation.replace(',', '')
    password = ''.join(random.choice(all_characters) for i in range(password_length))
    return password
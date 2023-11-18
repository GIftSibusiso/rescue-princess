import streamlit as st
import requests

def login_form():
    # Create a login form
    form = st.form("Login Form")
    username = form.text_input("Username:")
    password = form.text_input("Password:", type="password")
    submit_button = form.form_submit_button()

    # Check if the user has submitted the form
    if submit_button:
        request = requests.post("http://127.0.0.1:5000/log-in", json={  
            "email": username, "password" : password
        })

        if request.status_code==200 and request.json()['status'] == "200":
            # If the credentials are correct, log the user in
            st.session_state['page'] = "form1"
            st.session_state["logged_in"] = True
        else:
            # If the credentials are incorrect, display an error message
            st.error("Incorrect username or password")

    # Check if the user is logged in
    if st.session_state.get("logged_in", False):
        # If the user is logged in, display the application content
        st.session_state['page'] = "form1"
        
def form1() :
    st.write("Welcome, admin!")

current_page = st.session_state.get("page", "login")

if current_page == "login":
    login_form()
elif current_page == "form1":
    form1()


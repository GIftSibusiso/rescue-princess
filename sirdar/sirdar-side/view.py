import streamlit as st
import requests
from app import key_defined


def render_email_inputs():
    email_inputs = []

    # Get the number of email fields from the user input
    number = st.session_state.get("number", 1)

    # Create the email text fields
    for i in range(number):
        input_name = f"email{i + 1}"
        email_inputs.append(st.text_input(f"Enter your {i + 1}st email address:", key=input_name))

    return email_inputs


def emails_empty(emails: list) -> bool:
    for email in emails:
        if len(email) == 0:
            return True
    return False


def get_board_emails():
    global board_credentials
    st.title("Dynamic Email Form")

    company_name = st.text_input(label="Company name:", key="username")

    # Create a form
    with st.form("user_input"):
        number = st.number_input("Enter a number:", min_value=1, max_value=100)
        st.session_state["number"] = number

        # Render the email inputs based on the number
        email_inputs = render_email_inputs()

        # Submit button
        submitted = st.form_submit_button("Submit")

    if submitted:
        
        if not emails_empty(email_inputs) and len(company_name) != 0:
            board_credentials = {}
            json_data = {
                "company-name" : company_name
            }
            
            for i, email_input in enumerate(email_inputs):
                json_data[i] = email_input
            
            request = requests.post("http://127.0.0.1:5000/create-password", json=json_data)
            if request.status_code==200:
                board_credentials = request.json()
            st.session_state["cred"] = board_credentials

            st.session_state["page"] = "credentials"



def display_emails_passwords():
    st.title("Board login credentials")

    number = st.session_state.get("cred", "empty")
    count = 0

    while (key_defined(number, str(count))):
        data = number[str(count)]
        email = data['email']
        password = data['password']

        st.write("Email: " + email)
        st.write("Password: " + password)
        st.write("")

        count += 1


current_page = st.session_state.get("page", "main")

if current_page == "main":
    get_board_emails()
elif current_page == "credentials":
    display_emails_passwords()

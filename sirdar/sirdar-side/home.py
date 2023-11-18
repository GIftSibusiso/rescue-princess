import streamlit as st
import view

def home():
    st.title("Welcome to Sirdar page")

    st.write("AHhh geez")

selected_page = st.sidebar.selectbox("Navigator", ["home", "add board members"])

if selected_page == "home":
    home()
elif selected_page == "add board members":
    view.get_board_emails()
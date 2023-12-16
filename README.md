# RESCUE THE PRINCESS

This is a text based multiple player game where players can connect to the server and play with other connected players simultaneously. The game objective is to simply navigate through a maze until you find the lost princess while avoiding getting shot by other players.

## Features

- Multi-player: Multiple players can connect to the server and play simultaneously.
- Socket communication: Server and clients communicate using Java sockets for real-time interaction.
- Text-based gameplay: The game interface is text-based, allowing for flexibility and diverse game mechanics.
- Graphic interphase is provided making it easy to navigate around the maze

## Requirements

1. Java Development Kit (JDK) 11+
2. IDE (e.g., Eclipse, IntelliJ IDEA, VScode)
3. Basic understanding of Java networking, socket programming and JSON strings
4. Maven

## Run
### Server:

1. Clone the project repository.
2. A script for compiling and running server already exists on your terminal run `bash rescue_princess.sh server` (make sure port 2000 is available if not you'll have to change port number on the client and server sides)

*Whoever runs the server controls the game*

### Player:

1. Clone the project repository.
2. A script for compiling and running server already exists on your terminal run `bash rescue_princess.sh`

*The game is unable to play without an active server*

### Gameplay

Once player connect to the server, they can interact and play the game according to the specific game rules and mechanics.
Server and client logic has been implemented for handling player actions, game state updates, and sending/receiving messages.


## Contribution

- Feel free to fork this template and customize it for your specific game concept.
- Contribution to the codebase is welcome through pull requests and bug reports.

## Contact
If you have any questions or feedback about the project, please feel free to contact the author at nkabinde17sibusiso@gmail.com

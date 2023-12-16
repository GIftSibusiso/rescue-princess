#!/bin/bash

if [ "$1" == "server" ]; then
    # Compile and run the server
    mvn compile exec:java -Dexec.mainClass="rescue.game.App"
else
    mvn compile exec:java -Dexec.mainClass="rescue.game.client.ClientServerConnection"
fi
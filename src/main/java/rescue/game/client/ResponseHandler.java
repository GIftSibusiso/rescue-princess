package rescue.game.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.fasterxml.jackson.databind.JsonNode;

import rescue.game.Additions;

public class ResponseHandler implements Runnable {
    private Socket socket;
    public boolean running = false;

    public ResponseHandler(Socket socket) {
        this.socket = socket;
        running = true;
    }
    
    @Override
    public void run() {
        try {
            while (running) {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                JsonNode node = Additions.getMapper().readTree(in.readLine());
                System.out.println(node.toPrettyString());
            }
        } catch (IOException ignore) {
            running = false;
        }
        
    }
}

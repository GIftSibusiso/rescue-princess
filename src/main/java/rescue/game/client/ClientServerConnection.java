package rescue.game.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import rescue.game.Additions;
import rescue.game.turtle.Turtle;

public class ClientServerConnection {
    private ResponseHandler responseHandler;
    private ObjectMapper mapper = new ObjectMapper();
    private Socket socket;
    private JSONObject request = new JSONObject();
    private PrintWriter out;
    private String name;
    private BufferedReader in;
    private Turtle player;

    public ClientServerConnection( int port, String ip ) {
        
        try {
            socket = new Socket(ip, port);
            out = new PrintWriter(socket.getOutputStream());
            makeRequest();
        } catch (UnknownHostException e) {
            System.out.println(ip + " cannot be found... :(");
        } catch (ConnectException e) {
            System.out.println( "   => Connection refused, Server not running" );
        } catch (IOException ignore) { }
    }

    public ClientServerConnection( int port ) {
        
        try {
            socket = new Socket("localhost", port);
            out = new PrintWriter(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            makeRequest();
        } catch (ConnectException e) {
            System.out.println( "   => Connection refused, Server not running" );
        } catch (IOException ignore) { }
    }

    private void makeRequest() throws IOException {
        launchPlayer();
        String playerCMD;

        while ( responseHandler.running ) {
            playerCMD = Additions.getInput(name + " command");
            sendRequest(playerCMD);
        }

    }

    private void launchPlayer() throws JsonMappingException, JsonProcessingException, IOException {
        name = Additions.getInput("Player name");
        String make = getMake() + "";

        request.put("player", "placeholder");
        request.put("command", "launch");
        request.put("arguments",  new String[] {make, name});

        System.out.println(request);
        out.println(request);
        out.flush();

        JsonNode node = mapper.readTree(in.readLine());

        if ( node.get("result").asText().equals("ERROR") ) {
            System.out.println(node.toPrettyString());
            launchPlayer();
        } else {
            showWorld(node);
            System.out.println(node.toPrettyString());
            responseHandler = new ResponseHandler(socket, player);
            Thread thread = new Thread(responseHandler);
            thread.start();
        }
    }

    private String getMake() {
        String make = Additions.getInput(
            "There 3 weapon makes, choose one between:\n" + 
            "  1. Shot gun\n  2. Sniper\n  3. Bazooka\nYour choice"
        ); 

        if ( 
            !Additions.isInt(make) || 
            !(Integer.parseInt(make)>0 && Integer.parseInt(make)<4)
        ) {
            return getMake();
        }

        return make;
    }

    private void sendRequest( String cmd ) {
        String[] command = cmd.split(" ");

        request.put("player", name);
        request.put("command", command[0]);
        request.put("arguments", getArgs(command));

        out.println(request);
        out.flush();
    }

    private String[] getArgs( String[] command) {
        String[] args = new String[command.length-1];

        for ( int i=1; i<command.length; i++ ) {
            args[i-1] = command[i];
        }

        return args;
    }

    private void showWorld(JsonNode node) {
        int width = node.get("world").get("width").asInt(),
            height = node.get("world").get("height").asInt();
        player = new Turtle(-200, -200);
        player.hide();
        player.speed(0);

        player.forward(width);
        player.left(90);
        player.forward(height);
        player.left(90);
        player.forward(width);
        player.left(90);
        player.forward(height);

        player.up();
        player.setPosition(
            node.get("data").get("position").get(0).asInt() - 200,
            node.get("data").get("position").get(1).asInt() - 200
        );
        player.setDirection(directionMapper(node.get("data").get("direction").asText()));
        player.shape("triangle");
        player.shapeSize(5, 5);
        player.show();
    }

    public static int directionMapper( String direction ) {
        switch (direction) {
            case "NORTH":
                return 90;
            case "WEST":
                return 180;
            case "SOUTH":
                return 270;
            default:
                return 0;
        }
    }

    public static void main(String[] args) throws UnknownHostException, IOException
    {
        new ClientServerConnection(2000);
    }
}

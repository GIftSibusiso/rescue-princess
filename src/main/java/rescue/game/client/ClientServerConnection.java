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
        welcomeMessage();
        launchPlayer();
        String playerCMD;

        while ( responseHandler.running ) {
            playerCMD = Additions.getInput(name + " command");
            if (playerCMD.equalsIgnoreCase("help")) {
                validCommands();
                continue;
            }
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
            responseHandler = new ResponseHandler(socket, player, name);
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

    private void welcomeMessage() {
        System.out.println(
            "_-".repeat(25) + "* RESCUE PRINCESS *" + "-_".repeat(25) + "\n" +
            "\nWelcome to the exciting adventure of \"Rescue Princess\"!\n\n" +
            
            "In this exciting game, you'll navigate through a maze using a graphic interface " +
            "to find the princess. The maze is initially empty, but as you encounter obstacles " +
            "or other players, you can use the 'look' command to see them. Once you've seen " +
            "an obstacle it won't disappear, so you can use your observations to find your way around.\n\n" +

            "You start the game with 100% health. If you encounter another player and get shot " +
            "your health drops, if it reaches zero you'll be reincarnated at a different location in the maze.\n\n" +

            "To help you find the princess, you can use the 'state' command to get your displacement " +
            "form the princess. Displacement is a measure of how far you are from something. The " +
            "lower your displacement, the closer you are to the princess.\n\n" +

            "The first player to reach the princess with a displacement of less than 10 wins the game!\n"
        );

        validCommands();
    }

    private void validCommands() {
        System.out.println(
            "Commands:\n" +
            "  -> forward <steps> = move your player the specified number of steps\n" +
            "  -> back <steps> = move your player back the specified number of steps\n" +
            "  -> turn right = turn your robot 90 degrees clockwise\n" +
            "  -> turn left = turn your robot 90 degrees counter clockwise\n" +
            "  -> state = to see the condition of your player\n" +
            "  -> look = to see obstacles and players in your line of sight\n" +
            "  -> fire = To shoot whoever is in your line of sight\n" +
            "  -> reload = increment your armor to the initial value" +
            "  -> repair = increment your health by 10%\n" +
            "  -> help = to see all valid commands"

        );
    }

    public static void main(String[] args) throws UnknownHostException, IOException
    {
        new ClientServerConnection(2000);
    }
}

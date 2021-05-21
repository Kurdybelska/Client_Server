import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.*;

public class Server implements Runnable {

    private ServerSocket serverSocket;
    private Integer port;
    private Thread serverThread;
    private final static Logger logger = Logger.getLogger(Server.class.getName());


    public Server(Integer port) {
        this.port = port;
    }

    public void startServer()
    {
        logger.info("Server started.");
        try
        {
            serverSocket = new ServerSocket( port );
            serverThread = new Thread(this);
            serverThread.start();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void stopServer()
    {
        logger.info("Server stopped");
        serverThread.interrupt();
        System.exit(0);
    }

    @Override
    public void run() {

        while (!Thread.interrupted()) {
            try {
                logger.info("Listening for connection...");
                Socket socket = serverSocket.accept();
                RequestHandler requestHandler = new RequestHandler( socket );
                new Thread(requestHandler).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args)
    {
        Server server = new Server(80);
        server.startServer();

        Scanner scan = new Scanner(System.in);
        String line = scan.nextLine();
        if(line.equals("stop")) {
            server.stopServer();
        }
    }
}

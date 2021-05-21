import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

public class Client {

    private final static Logger logger = Logger.getLogger(Server.class.getName());

    public static void main(String[] args)
    {
        logger.info("How many messages do you wanna send??");
        Scanner scan = new Scanner(System.in);
        Integer n = scan.nextInt();
        String server = "localhost";

        try {
            Socket socket = new Socket(server,80);

            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream output = new ObjectOutputStream(outputStream);

            InputStream inputStream = socket.getInputStream();
            ObjectInputStream input = new ObjectInputStream(inputStream);

            String line = String.valueOf(input.readObject());

            if (line.equals("Ready"))
            {
                output.writeObject( n );
                line = String.valueOf(input.readObject());

                if (line.equals("Ready for messages"))
                {
                    logger.info("Sending messages...");
                    for (int k = 1; k<=n; k++)
                    {
                        //try {
                            //Thread.sleep( 6000 );
                        //} catch( Exception e ) {
                            //e.printStackTrace();
                        //}

                        Message mess = new Message(k,"That's a message.");
                        output.writeObject(mess);
                    }
                    logger.info("All messages sent.");
                }
            }

            input.close();
            output.close();
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}

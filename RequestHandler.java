import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

public class RequestHandler implements Runnable {

    private Socket socket;
    private final static Logger logger = Logger.getLogger(Server.class.getName());

    RequestHandler( Socket socket )
    {
        this.socket = socket;
    }

    @Override
    public void run() {

        try {
            logger.info("Connected.");

            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream output = new ObjectOutputStream(outputStream);

            InputStream inputStream = socket.getInputStream();
            ObjectInputStream input = new ObjectInputStream(inputStream);

            output.writeObject( "Ready" );
            output.flush();
            Object line = input.readObject();

            if (line != null)
            {
                Integer n = Integer.valueOf(String.valueOf(line));
                output.writeObject( "Ready for messages" );

                for (int k = 1; k<=n; k++)
                {
                    Message mess = (Message) input.readObject();
                    mess.showMessage();
                }
            }
            output.writeObject( "Finished" );

            input.close();
            output.close();
            socket.close();
            logger.info("Communication finished.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

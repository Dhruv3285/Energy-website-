import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class BinaryServer {

    private final int port;

    /**
     * Initialise a new server. To run the server, call run().
     * @param port the port number on which the server will listen for connections
     */
    public BinaryServer(int port) {
        this.port = port;
    }

    /**
     * Runs the server.
     * @param connections the number of connections to accept
     * @throws IOException
     */
    public void run(int connections) throws IOException, InterruptedException {
        if (connections < 1) {
            System.out.println("Needs at least one client");
            return;
        }

        // Open the server socket
        ServerSocket serverSocket = new ServerSocket(port);

        System.out.println("BinaryServer listening on port " + port);
        System.out.println("BinaryServer expecting " + connections + " clients");


        // Data structures to hold connections
        Socket clients[] = new Socket[connections];
        InputStream inputs[] = new InputStream[connections];
        OutputStream outputs[] = new OutputStream[connections];


        // Accept the given number of clients
        for (int i = 0; i < clients.length; i++) {

            System.out.println("BinaryServer waiting for client " + i);

            clients[i] = serverSocket.accept();
            inputs[i] = clients[i].getInputStream();
            outputs[i] = clients[i].getOutputStream();

            System.out.println("Client " + i + " is " + clients[i].getInetAddress() +
                    ":" + clients[i].getPort());
        }


        // Run the communication protocol
        int currentClient = 0;
        while (true) {
            System.out.println("Checking client " + currentClient + " for input");

            if (clients[currentClient].isInputShutdown()) {
                System.out.println("Client " + currentClient + " input has been shut down");
                return;
            }

            // See if there is any input from the client
            int amountAvailableToRead = inputs[currentClient].available();

            if (amountAvailableToRead > 0) {
                // If there is, read it in

                byte packet[] = new byte[amountAvailableToRead];

                int amountRead = inputs[currentClient].read(packet, 0, packet.length);

                System.out.println("Client " + currentClient + " read " + amountRead + " bytes");

                // Say it to everyone else
                for (int i = 0; i < outputs.length; i++) {
                    if (clients[i].isOutputShutdown()) {
                        System.out.println("Client " + currentClient + " output has been shut down");
                        return;
                    }

                    if (i != currentClient) {
                        try {
                            outputs[i].write(packet);
                            // this flush() is necessary, otherwise ouput is buffered locally and
                            // won't be sent to the client until it is too late
                            outputs[i].flush();
                        } catch (Exception e) {
                            System.out.println("Client " + i + " disconnect notice on write");
                            return;
                        }
                    }
                }
            }

            // The next client gets a go
            currentClient = (currentClient + 1) % clients.length;

            // To avoid using all of the CPU on checking...
            Thread.sleep(1000);
        }
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        String usage = "Usage: java BinaryServer [<connections>] [<port-number>] ";
        if (args.length > 2) {
            throw new Error(usage);
        }

        int connections = 1;
        try {
            if (args.length > 0) {
                connections = Integer.parseInt(args[0]);
            }
        } catch (NumberFormatException e) {
            throw new Error(usage + "\n" + "<connections> must be an integer");
        }


        int port = 0x4942;
        try {
            if (args.length > 1) {
                port = Integer.parseInt(args[1]);
            }
        } catch (NumberFormatException e) {
            throw new Error(usage + "\n" + "<port-number> must be an integer");
        }

        try {
            InetAddress ip = InetAddress.getLocalHost();
            System.out.println("BinaryServer host: " + ip.getHostAddress() + " (" + ip.getHostName() + ")");
        } catch (IOException e) {
            System.err.println("could not determine local host name");
        }

        BinaryServer server = new BinaryServer(port);
        server.run(connections);

        System.err.println("BinaryServer loop terminated!");
    }
}

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class BinaryClient {

    private final int port;

    /**
     * Initialise a new client. To run the client, call run().
     * @param port the port number to connect to
     */
    public BinaryClient(int port) {
        this.port = port;
    }

    void printHexLine(byte packet[]) {
        // Implement this!
        for (int i = 0; i < packet.length; ++i) {
            System.out.print("??");
            System.out.print(" ");
            if (i % 8 == 7) {
                System.out.print(" ");
            }
        }
        System.out.println("");
    }

    /**
     * Runs the client.
     * @throws IOException
     */
    public void run() throws IOException, InterruptedException {
        InetAddress host = InetAddress.getLocalHost();  // IP addresses will be explained...

        System.out.println("BinaryClient connecting to 127.0.0.1:" + port);
        Socket clientSocket = new Socket(host, port);

        // Use the input and output stream directly
        InputStream input = clientSocket.getInputStream();
        OutputStream output = clientSocket.getOutputStream();

        while (true) {  // The client loops until interrupted

            // If there is not a line's worth of data...
            while (input.available() < 16) {
                Thread.sleep(1000);  // wait 1 second
            }

            // An array of byte to hold the packet
            byte packet[] = new byte[16];

            // Read it in
            input.read(packet, 0, packet.length);

            // Print it out in hex
            printHexLine(packet);
        }

        // Close down the connection
        //clientSocket.close();
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        String usage = "Usage: java BinaryClient [<port-number>] ";
        if (args.length > 1) {
            throw new Error(usage);
        }

        int port = 0x4942;
        try {
            if (args.length > 0) {
                port = Integer.parseInt(args[0]);
            }
        } catch (NumberFormatException e) {
            throw new Error(usage + "\n" + "<port-number> must be an integer");
        }

        BinaryClient client = new BinaryClient(port);
        client.run();
    }
}

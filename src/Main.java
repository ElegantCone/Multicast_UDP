import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main (String[] args) throws IOException, InterruptedException {
        InetAddress group = InetAddress.getByName(args[0]);
        int port = Integer.parseInt(args[1]);
        PCSocket socket = new PCSocket();
        try{
            socket.run(group, port);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}

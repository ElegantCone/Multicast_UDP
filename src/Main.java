import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Main {
    public static void main (String[] args) throws IOException {
        InetAddress group = InetAddress.getByName(args[0]);
        int port = Integer.parseInt(args[1]);

        MulticastSocket socket = new MulticastSocket(port);
        socket.joinGroup(group);
        Sender send = new Sender(socket, group, port);
        Receiver recv = new Receiver(socket);
        send.start();
        recv.start();
    }
}

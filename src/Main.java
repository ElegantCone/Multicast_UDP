import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Main {
    public static void main (String[] args) throws IOException {
        InetAddress group = InetAddress.getByName(args[0]);
        int port = Integer.parseInt(args[1]);

        AddressSet activeSockets = new AddressSet();
        Sender send = new Sender(group, port, activeSockets);
        send.start();
        Receiver recv = new Receiver(group, port, activeSockets);
        recv.start();
        Checker check = new Checker(activeSockets);
        check.start();
    }
}

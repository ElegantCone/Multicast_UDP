import java.io.IOException;
import java.net.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Receiver extends Thread {
    MulticastSocket socket;
    AddressSet activeSockets;
    InetAddress group;
    int port;

    Receiver(InetAddress addr, int port, AddressSet addressSet) throws IOException {
        this.activeSockets = addressSet;
        this.group = addr;
        this.port = port;

        socket = new MulticastSocket(port);
        socket.joinGroup(new InetSocketAddress(group, port), NetworkInterface.getByInetAddress(group));
        //socket.setSoTimeout(10000);
    }

    @Override
    public void run() {
        int count = 0;
        while (true) {
            byte[] buf = new byte[1000];
            DatagramPacket recv = new DatagramPacket(buf, buf.length, group, port);
            try {
                socket.receive(recv);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String recvMsg = new String(recv.getData(), 0, recv.getLength());
            SocketAddress recvIP = recv.getSocketAddress();
            activeSockets.add(recvIP, System.currentTimeMillis(), recvMsg);
            count++;
            if (count == 3) count = 0;
        }
    }
}

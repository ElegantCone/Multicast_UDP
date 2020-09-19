import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Receiver extends Thread {
    MulticastSocket socket;
    HashMap<SocketAddress, Integer> activeSockets;

    Receiver (MulticastSocket socket){
        this.socket = socket;
        this.activeSockets = new HashMap<>();
    }
    @Override
    public void run() {
        int count = 0;
        boolean isClosed;
        while (true) {
            if (count == 3) {
                count = 0;
                isClosed = false;
                Iterator<Map.Entry<SocketAddress, Integer>> iterator = this.activeSockets.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<SocketAddress, Integer> entry = iterator.next();
                    if (entry.getValue() == 0) {
                        System.out.println("User " + entry.getKey() + " disconnected\n");
                        iterator.remove();
                        isClosed = true;
                    } else
                        activeSockets.replace(entry.getKey(), 0);
                }
                if (isClosed) {
                    System.out.println("Active users:\n");
                    for (SocketAddress key : activeSockets.keySet()) {
                        System.out.println(key + "\n");
                    }
                }
            }
            byte[] buf = new byte[10000];
            DatagramPacket recv = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(recv);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String recvMsg = new String(recv.getData(), 0, recv.getLength());
            SocketAddress recvIP = recv.getSocketAddress();
            //System.out.println(recvIP);
            if (activeSockets.containsKey(recvIP))
                activeSockets.replace(recvIP, activeSockets.get(recvIP) + 1);
            else {
                System.out.println("User " + recvIP + " connected! Text: " + recvMsg + "\n");
                activeSockets.put(recvIP, 1);
            }
            count++;
        }
    }
}

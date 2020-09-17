import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketAddress;
import java.util.HashMap;

public class PCSocket {
    MulticastSocket socket;
    HashMap<String, Integer> activeSockets;

    void run(InetAddress group, int port) throws InterruptedException, IOException {
        this.socket = new MulticastSocket(port);
        socket.joinGroup(group);
        SocketAddress locAddr = socket.getLocalSocketAddress();
        //String msg = "New connection! It's user num "+ args[2] + " " + String.valueOf(addr) + "\n";
        String msg = String.valueOf(locAddr);
        this.activeSockets = new HashMap<>();
        DatagramPacket ip = new DatagramPacket(msg.getBytes(), msg.length(), group, port);
        int count = 0;
        boolean isClosed = false;
        while (true){
            if (count == 3){
                count = 0;
                isClosed = false;
                for (String key : activeSockets.keySet()){
                    if (activeSockets.get(key) == 0){
                        System.out.println("User " + key + " disconnected\n");
                        activeSockets.remove(key);
                        isClosed = true;
                    }
                }
                if (isClosed) {
                    System.out.println("Active users:\n");
                    for (String key : activeSockets.keySet()){
                        activeSockets.replace(key, 0);
                        System.out.println(key + "\n");
                    }
                }
            }
            socket.send(ip);
            //getting responses
            byte[] buf = new byte[1000];
            DatagramPacket recv = new DatagramPacket(buf, buf.length);
            socket.receive(recv);
            String recvMsg = new String(recv.getData(), 0, recv.getLength());
            //System.out.println(recvMsg);
            if (activeSockets.containsKey(recvMsg))
                activeSockets.replace(recvMsg, activeSockets.get(recvMsg) + 1);
            else {
                System.out.println("User " + recvMsg + " connected!\n");
                activeSockets.put(recvMsg, 1);
            }
            count++;
            Thread.sleep(1000);
        }
    }


}

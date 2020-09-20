import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AddressSet {
    HashMap<SocketAddress, Long> activeSockets;

    AddressSet() {
        activeSockets = new HashMap<>();
    }

    void add(SocketAddress addr, long time, String recvMsg) {
        if (activeSockets.containsKey(addr)) {
            activeSockets.replace(addr, time);
        } else {
            activeSockets.put(addr, time);
            System.out.println("User " + addr + " connected! Text: " + recvMsg + "\n");
        }
    }

    void check() {
        int count = 0;
        boolean isClosed = false;
        Iterator<Map.Entry<SocketAddress, Long>> iterator = this.activeSockets.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<SocketAddress, Long> entry = iterator.next();
            if (System.currentTimeMillis() - entry.getValue() > 3000) {
                System.out.println("User " + entry.getKey() + " disconnected\n");
                iterator.remove();
                isClosed = true;
            } /*else
                activeSockets.replace(entry.getKey(), );*/
        }
        if (isClosed) {
            System.out.println("Active users:\n");
            for (SocketAddress key : activeSockets.keySet()) {
                System.out.println(key + "\n");
            }
        }
    }
}

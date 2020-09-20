import java.net.SocketAddress;
import java.util.HashMap;

public class Checker extends Thread {

    AddressSet activeSockets;

    Checker(AddressSet addressSet) {
        activeSockets = addressSet;
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            activeSockets.check();
        }
    }
}

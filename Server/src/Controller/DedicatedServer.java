package src.Controller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

public class DedicatedServer extends Thread {
    private final ArrayList<DedicatedServer> dedicatedServers;
    private final Socket socket;
    private ObjectInputStream oimput;
    private DataOutputStream doutput;

    public DedicatedServer (Socket socket, ArrayList<DedicatedServer> dedicatedServers) {

        this.socket = socket;
        this.dedicatedServers = dedicatedServers;

    }

    @Override
    public void run () {
        try {

            oimput = new ObjectInputStream(socket.getInputStream());
            doutput = new DataOutputStream(socket.getOutputStream());

            while (true) {

                //ObjectInputStream
                //DataOutputStream

            }
        } catch (IOException e) {
            dedicatedServers.remove(this);
        } finally {
            try {
                oimput.close();
            } catch (IOException e) {}
            try {
                doutput.close();
            } catch (IOException e) {}
            try {
                socket.close();
            } catch (IOException e) {}
        }


    }
}

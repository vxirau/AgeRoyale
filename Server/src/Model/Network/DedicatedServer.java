package src.Model.Network;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;

public class DedicatedServer {
    private boolean isOn;
    private Socket sClient;
    private DataInputStream dataInput;
    private ObjectOutputStream objectOut;
    private LinkedList<DedicatedServer> clients;

    //private MainView vista;
    //private Server server;

    public DedicatedServer( Socket sClient /*MainView vista*/, LinkedList<DedicatedServer> clients /*Server server*/) {
        this.isOn = false;
        this.sClient = sClient;
        //this.vista = vista;
        this.clients = clients;
        //this.server = server;
    }

    public void startDedicatedServer() {
        // iniciem el servidor dedicat
        isOn = true;
        //this.start();
    }

    public void stopDedicatedServer() {
        // aturem el servidor dedicat
        this.isOn = false;
        //this.interrupt();
    }

    public void run() {
        String in;
        String[] aux;
        try {
            // creem els canals de comunicacio
            dataInput = new DataInputStream(sClient.getInputStream());
            objectOut = new ObjectOutputStream(sClient.getOutputStream());
            // enviem estat de la grid al client
            // NO ENVIEM EL MODEL, SINO EL SEU ESTAT, ALLO QUE ES RELLEVANT
            // PELS CLIENTS (OBSERVAR Grid != GridState)
            //objectOut.writeObject(new GridState(model.getMatrix()));
            while (isOn) {

                in = dataInput.readUTF();

            }
        } catch (IOException e1) {
            // en cas derror aturem el servidor dedicat
            stopDedicatedServer();
            // eliminem el servidor dedicat del conjunt de servidors dedicats
            clients.remove(this);
            // invoquem el metode del servidor que mostra els servidors dedicats actuals
            //server.showClients();
        }
    }
}

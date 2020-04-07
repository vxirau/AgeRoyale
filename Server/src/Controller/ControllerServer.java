package src.Controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ControllerServer {

  private ServerSocket serverSocket;
  private boolean isRunning;
  private final ArrayList<DedicatedServer> dedicatedServers;

  public ControllerServer(){
    this.serverSocket = null;
    dedicatedServers = new ArrayList<>();
  }

  public void startServer(){
    try {

        this.serverSocket = new ServerSocket(NetworkConfiguration.staticPort);
        this.isRunning = true;

        while (this.isRunning) {
            System.out.println("Waiting for a client...");
            Socket socket = this.serverSocket.accept();
            System.out.println("Client connected");
            DedicatedServer dServer = new DedicatedServer(socket, dedicatedServers);
            dedicatedServers.add(dServer);
            dServer.start();
        }
    } catch (IOException e) {
        System.err.println(e.getMessage());
    } finally {
        if (this.serverSocket != null && !this.serverSocket.isClosed()) {
            try {
                this.serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
  }

  public void stopServer(){
    isRunning = false;
  }
}

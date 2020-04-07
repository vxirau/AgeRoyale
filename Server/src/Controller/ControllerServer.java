package src.Controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ControllerServer {

  private ServerSocket serverSocket;
  private boolean isRunning;

  public ControllerServer(){
    this.serverSocket = null;
  }

  public void startServer(){
    try {
        this.serverSocket = new ServerSocket(NetworkConfiguration.staticPort);
        this.isRunning = true;

        while (this.isRunning) {
            System.out.println("Waiting for a client...");
            Socket socket = this.serverSocket.accept();
            System.out.println("Client connected");
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

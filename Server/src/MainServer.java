package src;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import src.Controller.ControllerServer;
import src.Controller.NetworkConfiguration;
import src.Model.Network.Server;
import src.View.ViewServer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainServer {
    public static void main (String[] args) {

        Path current = Paths.get("./Server/resources/config.json");
        String arxiu = current.toAbsolutePath().toString();

        try(BufferedReader reader = new BufferedReader(new FileReader(arxiu))) {

            Gson gson = new GsonBuilder().create();
            NetworkConfiguration config = gson.fromJson(reader, NetworkConfiguration.class);
            config.ompleStatic();
            System.out.println(NetworkConfiguration.staticIP);
            System.out.println("SERVER");


            ViewServer vServer = new ViewServer();
            Server serv = new Server(vServer);
            ControllerServer cServer = new ControllerServer(serv,vServer);
            vServer.serverController(cServer);
            vServer.getTabbedPane().setSelectedIndex(0);
            vServer.setVisible(true);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

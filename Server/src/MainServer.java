package src;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import src.Controller.NetworkConfiguartion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainServer {
    public static void main (String[] args) {

        Path current = Paths.get("");
        String arxiu = current.toAbsolutePath().toString();

        try(BufferedReader reader = new BufferedReader(new FileReader(arxiu))) {

            Gson gson = new GsonBuilder().create();
            NetworkConfiguartion config = gson.fromJson(reader, NetworkConfiguartion.class);



        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}

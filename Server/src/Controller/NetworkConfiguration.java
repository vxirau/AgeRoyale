package src.Controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NetworkConfiguration {

		private String ip;
		private int port;

		public static String staticIP;
		public static int staticPort;

		public NetworkConfiguration(){

		}

		public NetworkConfiguration(String ip, int port){
			this.ip = ip;
			this.port = port;
		}

		public void ompleStatic(){
			staticIP = this.ip;
			staticPort = this.port;
		}

		public void setIP(String ip){
			this.ip = ip;
		}

		public String getIP(){
			return this.ip;
		}

		public void setPort(int port){
			this.port = port;
		}

		public int getPort(){
			return this.port;
		}


}

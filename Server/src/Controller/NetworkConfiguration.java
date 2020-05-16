package src.Controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NetworkConfiguration {

		private String ip;
		private int serverPort;
		private String DBip;
		private String DBUser;
		private String DBPass;


		public static boolean Setup=false;
		public static String staticIP;
		public static int staticPort;
		public static String staticDPip;
		public static String staticDBUser;
		public static String staticDBPass;

		public NetworkConfiguration(){

		}

		public NetworkConfiguration(String ip, int port){
			this.ip = ip;
			this.serverPort = port;
		}

		public void ompleStatic(){
			staticIP = this.ip;
			staticPort = serverPort;
			staticDPip = DBip;
			staticDBUser = DBUser;
			staticDBPass=DBPass;
		}

		public void setIP(String ip){
			this.ip = ip;
		}

		public String getIP(){
			return this.ip;
		}

		public void setPort(int port){
			this.serverPort = port;
		}

		public int getPort(){
			return this.serverPort;
		}


}

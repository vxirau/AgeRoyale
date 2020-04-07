package src.Controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NetworkConfiguartion {

		private static final String ip;
		private static final int port;

		public NetworkConfiguartion(){
		}

		public NetworkConfiguartion(String ip, int port){
			this.ip = ip;
			this.port = port;
		}

		public void setIP(String ip){
			this.ip = ip;
		}

		public String getIP(){
			return this.ip;
		}

		public void setPort(String port){
			this.port = port;
		}

		public int getPort(){
			return this.port;
		}


}

package src;

/**
 * Classe que guarda la informació per a connectarse a la dada de bases
 */
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

		/**
		 * Constructor de la classe
		 */
		public NetworkConfiguration(){

		}

		/**
		 * Constructor secundari de la classe
		 * @param ip indica la direcció ip
		 * @param port indica el port
		 */
		public NetworkConfiguration(String ip, int port){
			this.ip = ip;
			this.serverPort = port;
		}

		/**
		 * Omple les variables amb la informació corresponent
		 */
		public void ompleStatic(){
			staticIP = this.ip;
			staticPort = serverPort;
			staticDPip = DBip;
			staticDBUser = DBUser;
			staticDBPass=DBPass;
		}

		/**
		 * Assigna la ip
		 * @param ip indica la direcció ip
		 */
		public void setIP(String ip){
			this.ip = ip;
		}

		/**
		 * Retorna la ip
		 * @return ip indica la direcció ip
		 */
		public String getIP(){
			return this.ip;
		}

		/**
		 * Assigna el port
		 * @param port indica el port
		 */
		public void setPort(int port){
			this.serverPort = port;
		}

		/**
		 * Retorna el port
		 * @return port indica el port
		 */
		public int getPort(){
			return this.serverPort;
		}


}

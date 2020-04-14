package src;

import src.View.ViewClient;

import javax.swing.text.View;

public class MainClient{

	private static boolean login_registre = true;

    public static void main(String[] args) {

        System.out.println("prova");
        new ViewClient().setVisible(true);

				if(login_registre){
					//obre finestra registre
				}else{
					//obre finestra login
				}


    }
}

package src.Controller;

import src.Message;
import src.Model.Network.UserService;
import src.Usuari;
import src.View.LoginView;
import src.View.MenuView;
import src.View.ViewRegistre;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


/**
* Controlador destinat a la finestra de login. Implementa ActionListener per poder fer ús d'aquesta quan es prem un JButton en aquest cas.
* */
public class LoginViewController implements ActionListener {
    private LoginView view;
    private UserService uService;
    private Usuari user;


    /**
    * Constructor de la classe.
     * @param view vista gràfica del login
     * @param userService variable que permet la connexió del client amb el servidor
    * */
    public LoginViewController(LoginView view, UserService userService) {
        this.view = view;
        this.uService = userService;
        if(!userService.serviceStarted()){
            userService.startServerComunication();
        }
    }


    /**
    * Funció que s'invoca desde el propi userService que notifica al client que el login ha estat satisfactori
     * @param usr objecte usuari que retorna el servidor si s'ha pogut fer login
    * */
    public void loginSuccessful(Usuari usr){
        this.user = usr;
        Message m = new Message(usr, "getRequests");
        uService.sendObject(m);
    }


    /**
    * Funció encarregada de avisar al usuari que esta bannejat.
     * @param s objecte emprat pel servidor per retornar la informació del temps restant de banneig
    * */
    public void userIsBanned(Usuari s){
        JOptionPane.showMessageDialog(view, "Estas bannejat!\nEt queden: " + s.getNickName()+ " dies per poder entrar");
    }


    /**
    * Quan es reben les solicituds del servidor, que s'han demant a la loginSuccessful, inicia les vistes pertinents
     * @param requests llista de usuaris que han solicitat amistat al client.
    * */
    public void onRequestsRecieved(ArrayList<Usuari> requests){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MenuView rView = new MenuView();
                view.setVisible(false);
                rView.setVisible(true);
                try {
                    ArrayList<Usuari> r = separateRequests(requests, 0);
                    ArrayList<Usuari> rq = separateRequests(requests, 1);
                    checkForAcceptedRequests(rq);
                    MenuController controlador = new MenuController(rView, uService, LoginViewController.this.user, r, rq);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
    * Comprova si algun client ha acceptat una solicitud que el client hagués enviat en una sessió prèvia
     * @param rq llista de usuaris que l'usuari ha solicitat amistat
    * */
    private void checkForAcceptedRequests(ArrayList<Usuari> rq){
        ArrayList<Usuari> r = new ArrayList<>();
        for(Usuari y : rq){
            if(y.isAccepted()!=null){
                r.add(y);
                if(y.isAccepted()){
                    JOptionPane.showMessageDialog(view, "Una solicitud teva ha estat acceptada!\nConsulta els teus amics.");
                }
            }
        }
        for(Usuari e : r){
            ArrayList<Usuari> h = new ArrayList<>();
            h.add(e);
            h.add(this.user);
            Message m = new Message(h, "removeRequest");
            uService.sendLogin(m, this);
        }
    }


    /**
    * El servidor envia una resposta de tots els usuaris que el nostre client ha solicitat amistat, i de tots els que l'han solicitat a ell.
     * Aquesta funció te la finalitat de separar aquesta resposta en dos arrays per separat, els que han solicitat, i els que ell ha solicitat
     * @param requests array de usuaris amb els que ha solicitat i els que li han solicitat
     * @param criteri criteri que decideix si volem la llista de solicitats o dels que soliciten
     * @return users llista resultant de la separació. Conté un dels dos tipus de solicituds
    * */
    public ArrayList<Usuari> separateRequests(ArrayList<Usuari> requests, int criteri){
        ArrayList<Usuari> users = new ArrayList<>();
        int i=0;
        boolean ok = true;
        if(criteri==0){
            for(i=0; ok; i++){
                if(requests.get(i).getIdUsuari()==-20 && requests.get(i).getNickName().equals("SEPARATOR")){
                    ok = false;
                }else{
                    users.add(requests.get(i));
                }
            }
        }else if(criteri==1){
            for(i=0; ok; i++){
                if(requests.get(i).getIdUsuari()==-20 && requests.get(i).getNickName().equals("SEPARATOR")){
                    ok = false;
                }
            }
            for(int j=i; j<requests.size() ;j++){
                users.add(requests.get(j));
            }
        }
        return users;
    }


    /**
    * En cas que el login no es pugui realitzar s'instancia aquesta funció. Encarregada de mostrar un missatge al client
    * */
    public void loginNotSuccessful(){
        //System.out.println("No existeix. Registra't!");
        JOptionPane.showMessageDialog(view, "No existeix. Registra't!");
    }


    /**
    * Com que la classe implement actionListener, ha de implementar la funció actionPerformed. Aquesta detectarà quan l'usuari ha interaccionat amb algun element de UI que tingui aquest controlador assignat
     * Encarregada de enviar la informació al servidor de que es vol fer login.
     * @param e variable que conté la informació dels elements emprats
    * */
    @Override
    public void actionPerformed(ActionEvent e) {
        String boto = ((JButton) e.getSource()).getText();

        if(boto.equals("INICIAR SESSIÓ")){
            Message message = new Message(new Usuari(view.getUsuari(), view.getPassword()), "Login");
            uService.sendLogin(message, this);

        } else if(boto.equals("Registra't ara")){
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    ViewRegistre rView = new ViewRegistre();
                    RegisterViewController controlador = new RegisterViewController(rView, uService);
                    rView.registerController(controlador);
                    view.setVisible(false);
                    rView.setVisible(true);
                }
            });
        }
    }
}

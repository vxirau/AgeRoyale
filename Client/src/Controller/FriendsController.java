package src.Controller;

import src.Message;
import src.Model.Network.UserService;
import src.Usuari;
import src.View.FriendRequestView;
import src.View.FriendView;
import src.View.MenuView;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;


/**
* Controlador assignat a la vista dels amics. Implementa un ActionListener.
* */
public class FriendsController implements ActionListener{


    //Declaració de variables.
    private FriendView friendView;
    private FriendRequestView friendRequestView;

    private MenuController menuController;

    private Usuari usuari;
    private UserService uService;
    private ArrayList<Usuari> requests;
    private ArrayList<Usuari> requested;

    private String cerca;


    /**
    * Constuctor de un Mouse listener assignat al botó de cerca. Encarregat de duu a terme les accións de cerca.
    * */
    public MouseListener listenerCercaAmic = new MouseAdapter(){
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            Message mes = new Message(friendView.getJtfSearchAmic().getText(), "FindFriend");
            uService.sendFriendSearch(mes, FriendsController.this, false);
        }
    };


    /**
    * Constructor de un KeyListener, destinat a detectar canvis en el text introduit i canviar la vista per adaptar-se als valors cercats.
    * */
    public KeyListener listenerDelTextField = new KeyAdapter() {
        @Override
        public void keyReleased(KeyEvent e) {
            cerca = FriendsController.this.friendView.getJtfSearchAmic().getText();
            if (cerca.equals("")){
                resetMessage();
            }
        }
    };



    /**
    * Constuctor del controlador.
     * @param usr usuari que esta emprant la aplicació
     * @param userService variable mitjançant la que accedim a la classe que permet comunicació amb el servidor desde el cleint
     * @param menuCtrl controlador del menú. El necessitarem en aquesta classe per poder realitzar canvis en la vista del menú.
     * @param requests Array de usuaris que han solicitad amistat al client que ha iniciat sessió
     * @param requested Array de usuaris als que el client ha solicitat amistad. (Es fa servir per evitar que envii dues solicituds)
    * */
    public FriendsController(Usuari usr, UserService userService, MenuController menuCtrl, ArrayList<Usuari> requests, ArrayList<Usuari> requested) {
        this.usuari = usr;
        this.uService = userService;
        this.menuController = menuCtrl;
        this.requests = requests;
        this.requested = requested;
    }


    /**
    * Encarregada de asssignar les solicituds de amistad. Es fa servir per actualitzar aquestes.
     * @param request array de usuaris que han solicitad amistat al client.
    * */
    public void setRequests (ArrayList<Usuari> request){
        request.removeIf(usr -> usr.getIdUsuari() == -20);
        this.requests = request;
        friendRequestView.setRequests(requests);
    }


    /**
    * Encarregat de eliminar amic de la llista de amics. Es crida quan el client prem un usuari de la seva llista de amics.
     * @param user usuari que ha seleccionat de la seva llista d'amics.
    * */
    public void removeFriend(Usuari user){
        boolean eliminar=false, ok=true;
        ArrayList<Usuari> parella = new ArrayList<>();
        parella.add(usuari);
        parella.add(user);
        if(this.usuari.getAmics().contains(user)){
            int a= JOptionPane.showConfirmDialog(friendView, "Vols eliminar aquest amic?");
            if(a==JOptionPane.YES_OPTION){
                eliminar=true;
            }else if(a==JOptionPane.NO_OPTION){
                eliminar=false;
            }else{
                ok=false;
            }
            if(ok && eliminar){
                Message mes = new Message(parella, "removeFriend");
                uService.sendGetFriends(mes, this, false);
            }
        }else{
            if(requestHasUser(user)){
                int a= JOptionPane.showConfirmDialog(friendView, "Vols enviar una solicitud a: " + user.getNickName());
                if(a==JOptionPane.YES_OPTION){
                    eliminar=true;
                }else if(a==JOptionPane.NO_OPTION){
                    eliminar=false;
                }else{
                    ok=false;
                }
                if(ok && eliminar){
                    Message mes = new Message(parella, "sendRequest");
                    requested.add(user);
                    uService.sendGetFriends(mes, this, false);
                }
            }else{
                JOptionPane.showMessageDialog(friendView, "Ja has enviat una solicitud a aquest usuari!");
            }
        }
    }


    /**
    * Encarregada de controlar si al usuari al que vol enviar la solicitud es troba entre els usuaris ja solicitats
     * @param u usuari que ha seleccionat el client
     * @return boolean que ens indicarà si ja l'ha solicitat o no
    * */
    private boolean requestHasUser(Usuari u){
        boolean has = true;
        for(Usuari user: requested) {
            if(user.getIdUsuari() == u.getIdUsuari()){
                has = false;
            }
        }
        return has;
    }


    /**
    * Vista encarregada de assignar les dues vistes d'aquesta secció: soliticuts i llistat d'amics
     * @param friendView vista de llistat d'amics
     * @param friendRequestView vista de llistat de solicituds
    */
    public void setFriendView(FriendView friendView, FriendRequestView friendRequestView) {
        this.friendView = friendView;
        this.friendRequestView = friendRequestView;
        friendView.setControllers(listenerDelTextField, listenerCercaAmic, this);
    }


    /**
    * Assigna l'usuari que li passa al usuari de la classe. Es fa servir per actualitzar la informació de usuari.
     * @param usuari usuari que li passa el servidor per actualitzar la informació
    * */
    public void setUsuari(Usuari usuari) {
        this.usuari = usuari;
    }


    /**
    * Envia el missatge al servidor de que es torni a demanar la llista de amics del client
     *
    * */
    public synchronized void resetMessage() {
        Message m = new Message(usuari, "Friends");
        uService.sendGetFriends(m, this, true);
    }


    /**
    * Assigna la llista de amics a la nova llista de amics que reb. Es fa per actualitzar la llista de usuaris que es motra a la vista.
     * @param amics llista de usuaris que li retorna el servidor per mostrar al client.
    * */
     public void setFriends(ArrayList<Usuari> amics) {
        if(friendView.getTextField().getText().toString().length()>0){
            clearFriendList(amics);
        }
        friendView.setAmics(amics);
        menuController.updateViews();
        friendView.setControllers(listenerDelTextField, listenerCercaAmic, this);
        friendView.getJtfSearchAmic().setText(cerca);
     }


     /**
     * Encarregada de netejar la llista que retorna el servidor. A vegades retorna algun usuari repetit, o ens retorna al propi client.
      * Bàsicament, recorre aquesta llista eliminant els usuaris no desitjats
      * @param amics llista de amics que ha rebut del servidor.
     * */
     public void clearFriendList(ArrayList<Usuari> amics){
        for(int i=0; i<amics.size() ;i++){
            if(amics.get(i).getIdUsuari() == this.usuari.getIdUsuari()){
                amics.remove(i);
            }
            if(this.usuari.getAmics().contains(amics.get(i))){
                amics.remove(i);
            }
        }
     }

     /**
     * Assigna els amics del usuari a una llista que reb del servidor. Es fa servir per actualitzar la llista d'amics del client
      * @param amicsUsuari llista de usuaris que reb del servidor
     * */
    public void setAmicsUsuari(ArrayList<Usuari> amicsUsuari) {
        usuari.setAmics(amicsUsuari);
        friendView.setAmicsUsuari(amicsUsuari);
        friendView.getJtfSearchAmic().setText(cerca);
    }


    /**
    * Encarregada de confirmar o denegar solicituds i enviar aquesta informació al servidor.
     * @param u usuari que ha premut el client.
    * */
    public void requestFriend(Usuari u){
        ArrayList<Usuari> users = new ArrayList<>();
        users.add(this.usuari);
        users.add(u);

        int a= JOptionPane.showConfirmDialog(friendView, "Vols acceptar aquesta solicitud?");
        if(a==JOptionPane.YES_OPTION){
            Message m = new Message(users, "acceptRequest");
            uService.sendFriendSearch(m, this, true);
            requests.remove(u);
        }else if(a==JOptionPane.NO_OPTION){
            Message m = new Message(users, "denyRequest");
            uService.sendFriendSearch(m, this, true);
            requests.remove(u);
        }
        friendRequestView.setRequests(requests);
    }

    /**
    * Funcio que retorna les solicituds que té el client
     * @return requests llista de usuaris
    * */
    public ArrayList<Usuari> getRequests() {
        return requests;
    }



    /**
    * Funció propia de la interficie que implementa la classe, actionListener. Encarregada de detectar interaccións del client amb la finestra grafica
     * @param e variable de tipus ActionEvent que conté la informació de quin element s'ha premut.
    * */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        if (button.getText().equals("Friend Request")){
            menuController.getView().invokeAdjustViews(MenuView.AMICSREQUEST);
        } else {
            menuController.getView().invokeAdjustViews(MenuView.AMICS);
        }
    }
}

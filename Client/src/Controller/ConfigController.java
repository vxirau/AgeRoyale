package src.Controller;

import src.Message;
import src.Model.Network.UserService;
import src.Usuari;
import src.View.ConfigView;

import javax.swing.*;

public class ConfigController {

    private ConfigView configView;
    private Usuari usuari;
    private UserService uService;

    //Variables auxiliars
    private static int NO_CANVI = 0;
    private static int INTENT_CANVI_FALLIT = -1;
    private static int CANVI = 1;

    private int nickname = ConfigController.NO_CANVI;
    private int password = ConfigController.NO_CANVI;
    private boolean flag = false;

    public ConfigController(Usuari usr, UserService userService) {
        this.usuari = usr;
        this.uService = userService;
    }

    public void setConfigView(ConfigView configView) {
        this.configView = configView;
        initInfo();
    }

    private void initInfo() {
        configView.getJtfConfigNickname().setText(usuari.getNickName());
        configView.getJtfConfigCorreu().setText(usuari.getEmail());
        configView.getJtfConfigContrasenya().setText(usuari.getPassword());
    }

    public void saveBtnClicked() {
        JButton jButtonSave = configView.getJbConfigSave();

        if (usuari.getNickName() != null && usuari.getEmail() != null && usuari.getPassword() != null) {
            if (jButtonSave.getName().equals("SAVE")) {
                if (!usuari.getPassword().equals(configView.getJtfConfigContrasenya().getText())) {
                    System.out.println("La contrasenya es diferent");
                    String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}";
                    if(configView.getJtfConfigContrasenya().getText().matches(pattern)){
                        System.out.println("Contrasenya modificada");
                        usuari.setPassword(configView.getJtfConfigContrasenya().getText());
                        Message m = new Message(usuari, "PasswordUpdate");
                        uService.sendPassUpdate(m);
                        password = ConfigController.CANVI;
                    } else {
                        System.out.println("La contrasnya no compleix els estandards");
                        password = ConfigController.INTENT_CANVI_FALLIT;
                        configView.getJtfConfigContrasenya().setText(usuari.getPassword());
                    }
                }
                if (!usuari.getNickName().equals(configView.getJtfConfigNickname().getText()) || !usuari.getEmail().equals(configView.getJtfConfigCorreu().getText())) {
                    System.out.println("El nickname o l'email són diferents");
                    flag = true;
                    Usuari usr = new Usuari(usuari.getIdUsuari(), configView.getJtfConfigNickname().getText(), configView.getJtfConfigCorreu().getText(), usuari.getPassword(), null, null, null);
                    Message m = new Message(usr, "UserPKUpdates");
                    uService.sendUserPKUpdate(m, this);
                }

                if (password == ConfigController.NO_CANVI && !flag && (usuari.getNickName().equals(configView.getJtfConfigNickname().getText()) || usuari.getEmail().equals(configView.getJtfConfigCorreu().getText()) || usuari.getPassword().equals(configView.getJtfConfigContrasenya().getText()))){
                    JOptionPane.showMessageDialog(new JFrame(), "Tot esta al dia, no s'ha modificat res.");
                }

                if (password != ConfigController.NO_CANVI && !flag){
                    if (password == ConfigController.CANVI){
                        JOptionPane.showMessageDialog(new JFrame(), "La contrasenya s'ha actualitzat correctament");
                    } else {
                        JOptionPane.showMessageDialog(new JFrame(), "La contrasenya NO s'ha actualitzat.\nLa contrasenya no és vàlida. Ha tenir almenys:\n - Una majúscula\n - Una minúscula\n - 8 caràcters\n - 1 valor numèric","Males noticies", JOptionPane.ERROR_MESSAGE);
                    }
                    password = ConfigController.NO_CANVI;
                    resetFlags();
                }
            }
        }
    }

    public void canviSuccessful(){
        this.usuari.setNickName(configView.getJtfConfigNickname().getText());
        this.usuari.setEmail(configView.getJtfConfigCorreu().getText());
        if (password == ConfigController.NO_CANVI){
            JOptionPane.showMessageDialog(new JFrame(), "FANTASTIC\n El nom d'usuari i el correu s'han actualitzat satisfactoriament");
        }
        if (password == ConfigController.CANVI){
            JOptionPane.showMessageDialog(new JFrame(), "FANTASTIC\n El nom d'usuari, el correu i la contrasenya s'han actualitzat satisfactoriament");
        }
        if (password == ConfigController.INTENT_CANVI_FALLIT){
            JOptionPane.showMessageDialog(new JFrame(), "NO TAN FANTASTIC COM ESPERAVEM\n El nom d'usuari i el correu s'han actualitzat satisfactoriament. Pero la contrasenya no s'ha pogut actualitzar.\nLa contrasenya no és vàlida. Ha tenir almenys:\n - Una majúscula\n - Una minúscula\n - 8 caràcters\n - 1 valor numèric","Bones i males noticies", JOptionPane.WARNING_MESSAGE);
            configView.getJtfConfigContrasenya().setText(usuari.getPassword());
        }
        password = ConfigController.NO_CANVI;
    }

    public void canviNotSuccessful(){
        configView.getJtfConfigNickname().setText(usuari.getNickName());
        configView.getJtfConfigCorreu().setText(usuari.getEmail());

        if (password == ConfigController.NO_CANVI){
            JOptionPane.showMessageDialog(new JFrame(), "MAL\n El nom d'usuari i el correu NO s'han actualitzat. Aixo es deu a que algun altre usuari ja fa us d'aquest nom d'usuari o d'aquest correu.","Males noticies", JOptionPane.ERROR_MESSAGE);
        }
        if (password == ConfigController.CANVI){
            JOptionPane.showMessageDialog(new JFrame(), "NO TAN FANTASTIC COM ESPERAVEM\n El nom d'usuari i el correu NO s'han actualitzat. Aixo es deu a que algun altre usuari ja fa us d'aquest nom d'usuari o d'aquest correu.\nPer altre banda la contrasenya s'ha actualitzat correctament.","Bones i males noticies", JOptionPane.WARNING_MESSAGE);
        }
        if (password == ConfigController.INTENT_CANVI_FALLIT){
            JOptionPane.showMessageDialog(new JFrame(), "FATAL\n El nom d'usuari i el correu NO s'han actualitzat. Aixo es deu a que algun altre usuari ja fa us d'aquest nom d'usuari o d'aquest correu.\nLa contrasenya no és vàlida. Ha tenir almenys:\n - Una majúscula\n - Una minúscula\n - 8 caràcters\n - 1 valor numèric","Males i molt males noticies", JOptionPane.ERROR_MESSAGE);
            configView.getJtfConfigContrasenya().setText(usuari.getPassword());
        }
        password = ConfigController.NO_CANVI;
    }

    private void resetFlags(){
        nickname = ConfigController.NO_CANVI;
        password = ConfigController.NO_CANVI;
        flag = false;
    }

    public void setUsuari(Usuari usuari) {
        this.usuari = usuari;

    }
}

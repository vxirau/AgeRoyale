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
                if (!usuari.getNickName().equals(configView.getJtfConfigNickname().getText()) || !usuari.getEmail().equals(configView.getJtfConfigCorreu().getText())) {
                    System.out.println("El nickname o l'email són diferents");
                    //do check si es pot modificar
                    Message m = new Message(null, "getAllGames");
                    //uService.sendGetPartides(m, this);
                    //if (check is correct) {
                    //    update user
                    //}
                } else {
                    if (!usuari.getPassword().equals(configView.getJtfConfigContrasenya().getText())) {
                        System.out.println("La contrasenya es diferent");
                        //update usr
                    }
                }
            }
        }
    }
}

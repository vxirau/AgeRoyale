package src;

import java.io.Serializable;

/**
 * Classe que guarda la informació de les invitacions
 */
public class Invite implements Serializable {
    private Usuari origen;
    private Usuari desti;
    private Partida partida;

    /**
     * Constructor de la classe
     * @param origen indica l'usuari origen
     * @param desti indica l'usuari destí
     * @param partida indica la partida a la que s'està invitant
     */
    public Invite(Usuari origen, Usuari desti, Partida partida) {
        this.origen = origen;
        this.desti = desti;
        this.partida = partida;
    }

    /**
     * retorna l'usuari origen
     * @return origen indica l'usuari origen
     */
    public Usuari getOrigen() {
        return origen;
    }

    /**
     * Assigna l'usuari origen
     * @param origen indica l'usuari origen
     */
    public void setOrigen(Usuari origen) {
        this.origen = origen;
    }

    /**
     * Retorna l'usuari destí
     * @return desti indica l'usuari destí
     */
    public Usuari getDesti() {
        return desti;
    }

    /**
     * Assigna l'usuari destí
     * @param desti indica l'usuari destí
     */
    public void setDesti(Usuari desti) {
        this.desti = desti;
    }

    /**
     * Retorna la partida
     * @return partida indica la partida a la que s'invita
     */
    public Partida getPartida() {
        return partida;
    }

    /**
     * Assigna la partida
     * @param partida indica la partida a la que s'invita
     */
    public void setPartida(Partida partida) {
        this.partida = partida;
    }
}

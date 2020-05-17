package src;

public class Invite {
    private Usuari origen;
    private Usuari desti;
    private Partida partida;

    public Invite(Usuari origen, Usuari desti, Partida partida) {
        this.origen = origen;
        this.desti = desti;
        this.partida = partida;
    }

    public Usuari getOrigen() {
        return origen;
    }

    public void setOrigen(Usuari origen) {
        this.origen = origen;
    }

    public Usuari getDesti() {
        return desti;
    }

    public void setDesti(Usuari desti) {
        this.desti = desti;
    }

    public Partida getPartida() {
        return partida;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }
}

package src;

import java.io.Serializable;

public class GameMetadata implements Serializable {

    private Integer usuariDesti;
    private Tropa tropa;
    private Integer idPartida;

    public GameMetadata(){

    }
    public GameMetadata(Integer usuariDesti, Tropa tropa, Integer idPartida) {
        this.usuariDesti = usuariDesti;
        this.tropa = tropa;
        this.idPartida = idPartida;
    }

    public Integer getUsuariDesti() {
        return usuariDesti;
    }

    public void setUsuariDesti(Integer usuariDesti) {
        this.usuariDesti = usuariDesti;
    }

    public Tropa getTropa() {
        return tropa;
    }

    public void setTropa(Tropa tropa) {
        this.tropa = tropa;
    }

    public Integer getPartida() {
        return idPartida;
    }

    public void setPartida(Integer idPartida) {
        this.idPartida = idPartida;
    }
}

package entities;

import java.sql.SQLException;

public class Magazzino {
    private int idMagazzino;
    private String comune;
    private int cap;
    private String indirizzo;
    private int pacchiInGiacenza;
    private int capienzaPacchi;

    public Magazzino(int idMagazzino, String comune, int cap,String indirizzo, int pacchiInGiacenza, int capienzaPacchi) {
        this.idMagazzino = idMagazzino;
        this.comune = comune;
        this.cap = cap;
        this.indirizzo = indirizzo;
        this.pacchiInGiacenza = pacchiInGiacenza;
        this.capienzaPacchi = capienzaPacchi;
    }

    public Magazzino() {

    }


    // Metodi getter e setter
    public int getIdMagazzino() {
        return idMagazzino;
    }

    public void setIdMagazzino(int idMagazzino) {
        this.idMagazzino = idMagazzino;
    }

    public String getComune() {
        return comune;
    }

    public void setComune(String comune) {
        this.comune = comune;
    }

    public int getCap() {
        return cap;
    }

    public void setCap(int cap) {
        this.cap = cap;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public int getPacchiInGiacenza() {
        return pacchiInGiacenza;
    }

    public void setPacchiInGiacenza(int pacchiInGiacenza) {
        this.pacchiInGiacenza = pacchiInGiacenza;
    }

    public int getCapienzaPacchi() {
        return capienzaPacchi;
    }

    public void setCapienzaPacchi(int capienzaPacchi) {
        this.pacchiInGiacenza = capienzaPacchi;
    }


    public void updateGiacenza(String nuovoStato) throws SQLException {
        if (nuovoStato.equalsIgnoreCase("In deposito")) {
            this.pacchiInGiacenza++;
        }
        if (nuovoStato.equalsIgnoreCase("In Consegna")) {
            this.pacchiInGiacenza--;
        }
        MagazzinoDAO dao = new MagazzinoDAO();
        dao.update(this);
    }

}
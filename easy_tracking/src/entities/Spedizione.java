package entities;

import encrypt.StringEncryption;
import java.sql.SQLException;
import java.util.Date;


public class Spedizione {
    // Attributi comuni a tutte le spedizioni
    protected int trackingCode; //id
    protected String codiceQR; //per semplicità implementativa il codice qr sarà simulato da una stringa crittografata con AES data dalla concatenazione di CF di mittente e destinatario, data spedizione e stato
    public Cliente mittente;
    public Cliente destinatario;
    public Magazzino deposito;
    public Corriere corriere;

    public void setPesoPacco(double pesoPacco) {
        this.pesoPacco = pesoPacco;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    protected double pesoPacco;
    protected double prezzo;

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getTipoSpedizione() {
        return tipoSpedizione;
    }

    public void setTipoSpedizione(String tipoSpedizione) {
        this.tipoSpedizione = tipoSpedizione;
    }

    protected String tipoSpedizione;
    protected String stato;
    protected Date dataSpedizione;
    protected Date dataConsegna;

    public String getStato() {
        return this.stato;
    }


    public Spedizione creaSpedizione(Integer trackingCode, Cliente mittente, Cliente destinatario, double peso, String tipoSpedizione, Date dataSpedizione) throws Exception {

        // Utilizza il Factory Method per creare l'oggetto entities.Spedizione in base al tipo di spedizione

        SpedizioneFactory spedizioneFactory = new SpedizioneFactory();
        StrategiaSpedizione spedizione =  spedizioneFactory.createSpedizione(tipoSpedizione);

        // Imposta le informazioni della spedizione
        this.stato = "Nuovo";
        this.setTrackingCode(trackingCode);
        this.setMittente(mittente);
        this.setDestinatario(destinatario);
        this.setDeposito();
        this.setCorriere();
        this.setPeso(peso);
        this.setDataSpedizione(dataSpedizione);
        this.setPrezzo(spedizione);
        this.setDataConsegna(spedizione);
        this.updateCodiceQR();


        return this;
    }

    public int getTrackingCode() {
        return this.trackingCode;
    }

    public void setTrackingCode(int trackingCodev) {
        this.trackingCode = trackingCode;
    }

    protected void setCorriere() throws SQLException {
        CorriereDAO dao = new CorriereDAO();
        this.corriere = dao.getById(dao.getIdCorriereFromCap(destinatario.getCap()));
    }

    public java.util.Date getDataSpedizione() {
        return this.dataSpedizione;
    }

    void setDataSpedizione(Date dataSpedizione) {
        this.dataSpedizione = dataSpedizione;
    }

    private void setPeso(double peso) {
        this.pesoPacco = peso;
    }

    protected void setDestinatario(Cliente destinatario) {
        this.destinatario = destinatario;
    }


    public void setMittente(Cliente mittente) {
        this.mittente = mittente;
    }

    public Magazzino getDeposito() {
        return this.deposito;
    }

    public void setDeposito() throws SQLException {
        MagazzinoDAO dao = new MagazzinoDAO();
        this.deposito = dao.getById(dao.getIdMagazzinoFromCap(destinatario.getCap()));
    }

    public double getPrezzo() {
        return this.prezzo;
    }

    public void setPrezzo(StrategiaSpedizione startegiaspedizione) {
        this.prezzo = startegiaspedizione.calcolaPrezzo(this);
    }

    public java.util.Date getDataConsegna() {
        return this.dataConsegna;
    }

    public void setDataConsegna(StrategiaSpedizione startegiaspedizione) {
        this.dataConsegna = startegiaspedizione.calcolaDataConsegna(this);
    }

    public void setDataConsegnaStatic(Date dataConsegna) {
        this.dataConsegna = dataConsegna;
    }

    public void updateCodiceQR() throws Exception {
        String concat_string = this.mittente.getCodiceFiscale() + this.destinatario.getCodiceFiscale() + this.dataSpedizione + this.stato;
        this.codiceQR = StringEncryption.encrypt(concat_string);
    }

    public void updateStato(String nuovoStato) throws Exception {
        this.stato = nuovoStato;
        if (nuovoStato.equalsIgnoreCase("In deposito") || nuovoStato.equalsIgnoreCase("In Consegna")) {
            deposito.updateGiacenza(nuovoStato);
        }
        updateCodiceQR();
        SpedizioneDAO dao = new SpedizioneDAO();
        dao.update(this);
    }

    public String getCodiceQR() {
        return this.codiceQR;
    }


    public void setCodiceQR(String codiceQR) {
        this.codiceQR = codiceQR;
    }

    public double getPesoPacco() {
        return this.pesoPacco;
    }
}


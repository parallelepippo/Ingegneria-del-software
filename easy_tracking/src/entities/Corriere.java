package entities;

import encrypt.StringEncryption;
import entities.Spedizione;


public class Corriere {
    private String nome;
    private String cognome;
    private int id;
    private int cap;


    public Corriere(int id, String nome, String cognome, int cap) {
        this.nome = nome;
        this.cognome = cognome;
        this.id = id;
        this.cap = cap;
    }

    public Corriere() {

    }


    // Metodi getter e setter

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCap() {
        return cap;
    }

    public void setCap(int cap) {
        this.cap = cap;
    }


    //update stato
    public void update_stato(Spedizione spedizione) throws Exception {
        String qrCode = spedizione.getCodiceQR();
        //con la substring rimuovo i codici fiscali (16 caratteri e la data 10 caratteri)
        String stato_spedizione = StringEncryption.decrypt(qrCode).substring(16+16+10);
        System.out.print(stato_spedizione);
        if (stato_spedizione.equalsIgnoreCase("Consegnato")) {
            System.out.println("Ordine consegnato");
            return;
        }

        String nuovo_stato = "";

        if (stato_spedizione.equalsIgnoreCase("Nuovo")) {
            nuovo_stato = "Spedito";
        }
        if (stato_spedizione.equalsIgnoreCase("Spedito")) {
            nuovo_stato = "In deposito";
        }
        if (stato_spedizione.equalsIgnoreCase("In deposito")) {
            nuovo_stato = "In Consegna";
        }
        if (stato_spedizione.equalsIgnoreCase("In Consegna")) {
            nuovo_stato = "Consegnato";
        }

        spedizione.updateStato(nuovo_stato);
    }

}

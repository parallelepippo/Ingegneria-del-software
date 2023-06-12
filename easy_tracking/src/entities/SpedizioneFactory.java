package entities;

public class SpedizioneFactory {
    public StrategiaSpedizione createSpedizione(String tipoSpedizione) {

        if (tipoSpedizione.equalsIgnoreCase("Veloce")) {
           return new SpedizioneVeloce();
        } else if (tipoSpedizione.equalsIgnoreCase("Normale")) {
            return new SpedizioneNormale();
        }
        return null;
    }
}

package entities;

import java.util.Date;

public interface StrategiaSpedizione {
    double calcolaPrezzo(Spedizione spedizione);

    Date calcolaDataConsegna(Spedizione spedizione);
}

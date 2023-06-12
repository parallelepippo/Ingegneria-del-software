package entities;

import entities.StrategiaSpedizione;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

import static java.lang.Math.abs;

public class SpedizioneNormale implements StrategiaSpedizione {



    public double calcolaPrezzo(Spedizione spedizione) {
        return spedizione.pesoPacco*0.3;
    }

    @Override
    public Date calcolaDataConsegna(Spedizione spedizione) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(spedizione.dataSpedizione);

        int daysToAdd = Math.abs(spedizione.mittente.getCap() - spedizione.deposito.getCap()) + 5;
        calendar.add(Calendar.DAY_OF_MONTH, daysToAdd);

        Date dataConsegna = new Date(calendar.getTimeInMillis());

        return dataConsegna;
    }
}

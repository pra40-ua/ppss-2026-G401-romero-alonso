package ppss;

import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;

public class CalendarioStub extends Calendario {
    private Map<LocalDate, Boolean> festivos = new HashMap<>();
    private Map<LocalDate, Exception> excepciones = new HashMap<>();

    public void setFestivos(LocalDate fecha, boolean festivo) {
        festivos.put(fecha, festivo);
    }

    public void setException(LocalDate fecha, Exception exception) {
        excepciones.put(fecha, exception);
    }

    @Override
    public boolean es_festivo(LocalDate fecha) throws CalendarioException {
        Exception ex = excepciones.get(fecha);
        Boolean festivo = festivos.get(fecha);
        if (ex != null) {
            throw (CalendarioException) ex;
        } else if (festivo != null && festivo) {
            return true;
        } else {
            return false;
        }
    }
}

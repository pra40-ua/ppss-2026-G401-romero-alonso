package llamadas;

public class CalendarioStub extends Calendario {
    private int hora;

    public CalendarioStub(int hora) {
        this.hora = hora;
    }

    @Override
    public int getHoraActual() {
        return hora;
    }
}

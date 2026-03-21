package llamadas;

public class GestorLlamadasTestable extends GestorLlamadas {
    private CalendarioStub calendarioStub;

    public GestorLlamadasTestable(CalendarioStub calendarioStub) {
        this.calendarioStub = calendarioStub;
    }

    @Override
    public Calendario getCalendario() {
        return calendarioStub;
    }
}

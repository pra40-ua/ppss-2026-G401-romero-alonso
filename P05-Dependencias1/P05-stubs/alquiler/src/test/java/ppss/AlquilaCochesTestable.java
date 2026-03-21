package ppss;

public class AlquilaCochesTestable extends AlquilaCoches {
    private CalendarioStub calendarioStub;
    private ServicioStub servicioStub;

    public AlquilaCochesTestable(CalendarioStub calendarioStub, ServicioStub servicioStub) {
        this.calendarioStub = calendarioStub;
        this.servicioStub = servicioStub;
        this.calendario = calendarioStub;
    }

    @Override
    public IService getServicio(){
        return servicioStub;
    }
}

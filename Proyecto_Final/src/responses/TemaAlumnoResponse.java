package responses;

import models.AsignacionLista;
import models.ItemListTema;

import java.util.List;

public class TemaAlumnoResponse {

    private ItemListTema tema;
    private List<AsignacionLista> asignaciones;
    private List<ItemListTema> subtemas;

    public TemaAlumnoResponse(ItemListTema tema, List<AsignacionLista> asignaciones, List<ItemListTema> subtemas) {
        this.tema = tema;
        this.asignaciones = asignaciones;
        this.subtemas = subtemas;
    }

    public ItemListTema getTema() {
        return tema;
    }

    public void setTema(ItemListTema tema) {
        this.tema = tema;
    }

    public List<AsignacionLista> getAsignaciones() {
        return asignaciones;
    }

    public void setAsignaciones(List<AsignacionLista> asignaciones) {
        this.asignaciones = asignaciones;
    }

    public List<ItemListTema> getSubtemas() {
        return subtemas;
    }

    public void setSubtemas(List<ItemListTema> subtemas) {
        this.subtemas = subtemas;
    }
}

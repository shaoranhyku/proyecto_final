package responses;

import models.AsignacionAlumnoLista;
import models.ItemListTema;

import java.util.List;

public class TemaAlumnoResponse {

    private ItemListTema tema;
    private List<AsignacionAlumnoLista> asignaciones;
    private List<ItemListTema> subtemas;

    public TemaAlumnoResponse(ItemListTema tema, List<AsignacionAlumnoLista> asignaciones, List<ItemListTema> subtemas) {
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

    public List<AsignacionAlumnoLista> getAsignaciones() {
        return asignaciones;
    }

    public void setAsignaciones(List<AsignacionAlumnoLista> asignaciones) {
        this.asignaciones = asignaciones;
    }

    public List<ItemListTema> getSubtemas() {
        return subtemas;
    }

    public void setSubtemas(List<ItemListTema> subtemas) {
        this.subtemas = subtemas;
    }
}

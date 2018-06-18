package models;

public class ItemListEnlace {

    private String codigoEnlace;
    private String url;
    private String descripcion;

    public ItemListEnlace(String codigoEnlace, String url, String descripcion) {
        this.codigoEnlace = codigoEnlace;
        this.url = url;
        this.descripcion = descripcion;
    }

    public String getCodigoEnlace() {
        return codigoEnlace;
    }

    public void setCodigoEnlace(String codigoEnlace) {
        this.codigoEnlace = codigoEnlace;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}

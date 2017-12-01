
package model;

public class TaskPojo {
    
    private String id;
    private String titulo;
    private String fecha;
    private String encargado;
    private String coments;
    private String prioridad;
    private String contenido;
    private String estado;
    private String visible;
    
    
    public TaskPojo() {
    }

	
    public TaskPojo(String id, String titulo, String fecha, String encargado, String coments, String prioridad,
                    String contenido, String estado, String visible) {
            super();
            this.id = id;
            this.titulo = titulo;
            this.fecha = fecha;
            this.encargado = encargado;
            this.coments = coments;
            this.prioridad = prioridad;
            this.contenido = contenido;
            this.estado = estado;
            this.visible = visible;
    }

    
    
    public String getId() {
            return id;
    }

    public String getTitulo() {
            return titulo;
    }

    public String getFecha() {
            return fecha;
    }

    public String getEncargado() {
            return encargado;
    }

    public String getComents() {
            return coments;
    }

    public void setComents(String coments) {
            this.coments = coments;
    }

    public String getPrioridad() {
            return prioridad;
    }

    public String getContenido() {
            return contenido;
    }

    public boolean getEstado() {
            if (estado.equals("1")) {
                    return true;
            }else {
                    return false;
            }
    }

    public void setEstado(String estado) {
            this.estado = estado;
    }

    public boolean getVisible() {
            if (visible.equals("1")) {
                    return true;
            }else {
                    return false;
            }
    }

    public void setVisible(String visible) {
            this.visible = visible;
    }
    
}

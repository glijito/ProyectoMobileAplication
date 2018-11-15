package desarrollojhlibreros.com.proyectomobileaplication;

public class Contacto {

    private String nombre;
    private String telefono;
    private String imagen;

    public Contacto(String nombre, String telefono, String imagen) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.imagen = imagen;
    }

    public Contacto() {
        this("","","");
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}

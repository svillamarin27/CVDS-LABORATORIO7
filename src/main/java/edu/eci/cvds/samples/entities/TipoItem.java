
package edu.eci.cvds.samples.entities;

import java.io.Serializable;

public class TipoItem implements Serializable{
    private int id;
    private String descripcion;

    public TipoItem(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public TipoItem() {
    }

    public int getID() {
        return id;
    }

    public void setID(int ID) {
        this.id = ID;
    }

    

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.descripcion = Descripcion;
    }

    @Override
    public String toString() {
        return "TipoItem{" + "id=" + id + ", descripcion=" + descripcion + '}';
    }
    
    
}

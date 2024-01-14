/*
 * Nombre: Pedro
 * Apellidos: Osorio Lopez
 * Correo electrónico: posorio22@alumno.uned.es
 */

package Proyecto.comunes.barcos;

import Proyecto.comunes.barcos.utilidades.*;
import Proyecto.comunes.tablero.Posicion;
import Proyecto.comunes.tablero.Tablero;

public class Barcos {
    private final String name;
    private final int length;
    private Posicion position;
    private Direccion direction;

    public Barcos(String name, int length){
        this.name = name;
        this.length = length;
        this.position = null;
        this.direction = null;
    }

    public Barcos(String name, int length, Posicion position, Direccion direction){
        this.name = name;
        this.length = length;
        this.position = position;
        this.direction = direction;
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public Posicion getPosition() {
        return position;
    }

    public void setPosition(Posicion position) {
        this.position = position;
    }

    public Direccion getDirection() {
        return direction;
    }
    
    public void setDirection(Direccion direction) {
        this.direction = direction;
    }

    public String toGraphicLength() {
        return ("" + Tablero.SHIP).repeat(length);
    }

    public String toString(){
        return name + ";" + length;
    }
}

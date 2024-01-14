/*
 * Nombre: Pedro
 * Apellidos: Osorio Lopez
 * Correo electrónico: posorio22@alumno.uned.es
 */

package Proyecto.comunes.barcos.utilidades;

import Proyecto.comunes.barcos.utilidades.excepciones.ExcepcionDireccion;

public enum Direccion {
    HORIZONTAL,
    VERTICAL;

    public static Direccion decode(char c) throws ExcepcionDireccion {
        if (c == 'h' || c == 'H') return HORIZONTAL;
        else if (c == 'v' || c == 'V') return VERTICAL;
        else throw new ExcepcionDireccion("Il carattere '"+c+"' non può essere convertito in una direzione");
    }

    public static Direccion decode(String str) throws ExcepcionDireccion {
        if (str.toLowerCase().equals("horizontal")) return HORIZONTAL;
        else if (str.toLowerCase().equals("vertical")) return VERTICAL;
        else throw new ExcepcionDireccion("La stringa '"+str+"' non può essere convertita in una direzione");
    }
}

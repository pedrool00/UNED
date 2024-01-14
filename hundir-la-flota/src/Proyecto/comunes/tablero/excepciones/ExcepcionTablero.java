/*
 * Nombre: Pedro
 * Apellidos: Osorio Lopez
 * Correo electrónico: posorio22@alumno.uned.es
 */

package Proyecto.comunes.tablero.excepciones;

public class ExcepcionTablero extends Exception{
    String msg;
    public ExcepcionTablero(String msg){
        super(msg);
        this.msg = msg;
    }

    @Override
    public String toString() {
        return msg;
    }
}
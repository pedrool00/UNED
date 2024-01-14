/*
 * Nombre: Pedro
 * Apellidos: Osorio Lopez
 * Correo electrónico: posorio22@alumno.uned.es
 */

package Proyecto.comunes.tablero;

import Proyecto.comunes.tablero.excepciones.ExcepcionPosicion;

public class Posicion {
    private final int row;
    private final int column;

    public Posicion(int row, int column) throws ExcepcionPosicion {
        if (row < 0 || column < 0)
            throw new ExcepcionPosicion("Valores permitidos mayores que 1");
        this.row = row;
        this.column = column;
    }

    public Posicion(char row, int column) throws ExcepcionPosicion {
        if (row < 'a' || column < 0)
            throw new ExcepcionPosicion("Valores permitidos mayores que A1");
        this.row = decode(row);
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public static int decode(char row) {
        return row - 'a';
    }

    public static char encode(int row) {
        return (char)('a' + row);
    }

    public String toStringEncode(Posicion position) {
        return "(" + (char)('a' + position.getRow()) + "," + (position.getColumn() + 1) + ")";
    }

    public static boolean isInRange(char row, int column, Tablero board) throws ExcepcionPosicion {
        int decodeRow = decode(row);
        if (decodeRow >= board.getLength() || column > board.getLength() || decodeRow < 0 || column < 0)
            throw new ExcepcionPosicion("Error, valores permitidos entre A1 y " + Posicion.encode(board.getLength() - 1) + board.getLength());
        else return true;
    }

    @Override
    public String toString (){
        return "(" + row + "," + column + ")";
    }

}

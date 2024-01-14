/*
 * Nombre: Pedro
 * Apellidos: Osorio Lopez
 * Correo electrónico: posorio22@alumno.uned.es
 */

package Proyecto.comunes.jugador;
import java.util.InputMismatchException;
import java.util.Scanner;

import Proyecto.comunes.Display;
import Proyecto.comunes.barcos.utilidades.Direccion;
import Proyecto.comunes.barcos.utilidades.excepciones.ExcepcionDireccion;
import Proyecto.comunes.tablero.Posicion;
import Proyecto.comunes.tablero.Tablero;
import Proyecto.comunes.tablero.excepciones.ExcepcionPosicion;

public class Input {

    public static Posicion readPosition(Scanner sc, Tablero board, String message){
        try {
            System.out.print(message);
            String s = sc.nextLine().toLowerCase();
            char row = s.charAt(0);
            int column = Integer.parseInt(s.substring(1));
            Posicion.isInRange(row, column, board);
            return new Posicion(row, column - 1);
        } catch (ExcepcionPosicion | NumberFormatException | StringIndexOutOfBoundsException e){
            Display.printError("Error, valores permitidos entre A1 y " + Posicion.encode(board.getLength() - 1) + board.getLength());
            return readPosition(sc, board, message);
        }

    }

    public static Direccion readDirection(Scanner sc, String message){
        try {
            System.out.print(message);
            String s = sc.nextLine();
            return Direccion.decode(s.charAt(0));
        } catch (ExcepcionDireccion | StringIndexOutOfBoundsException e){
            Display.printError("Error, los valores permitidos para la dirección son 'h' o 'v'");
            return readDirection(sc, message);
        }
    }

    public static int readOption(Scanner sc, String message){
        try {
            System.out.print(message);
            return Integer.parseInt(sc.next());
        } catch (NumberFormatException | InputMismatchException e){
            Display.printError("Error, inserte un número para continuar");
            return readOption(sc, message);
        }
    }
}

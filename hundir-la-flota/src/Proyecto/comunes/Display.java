/*
 * Nombre: Pedro
 * Apellidos: Osorio Lopez
 * Correo electrónico: posorio22@alumno.uned.es
 */

package Proyecto.comunes;

import java.util.Scanner;

import Proyecto.comunes.barcos.Barcos;
import Proyecto.comunes.barcos.utilidades.TiposDeBarcos;
import Proyecto.comunes.jugador.Input;
import Proyecto.comunes.jugador.Player;
import Proyecto.comunes.tablero.Posicion;
import Proyecto.comunes.tablero.Tablero;
import Proyecto.comunes.tablero.excepciones.ExcepcionPosicion;
import Proyecto.comunes.utilidades.Colores;

@SuppressWarnings("resource")
public class Display {

    public static void printTitle(){
        System.out.println("" +
                "\n" +
                "██   ██ ██   ██ ████  ██ ██████ ██████ ██████    ██     ███████    ██████ ██     ███████ ██████ ███████   \n" +
                "██   ██ ██   ██ ██ █  ██ ██   ██  ██   ██  ██    ██     ██   ██    ██     ██     ██   ██   ██   ██   ██   \n" +
                "███████ ██   ██ ██ ██ ██ ██    ██ ██   ██████    ██     ███████    ██████ ██     ██   ██   ██   ███████   \n" +
                "██   ██ ██   ██ ██  █ ██ ██   ██  ██   ██ ██     ██     ██   ██    ██     ██     ██   ██   ██   ██   ██   \n" +
                "██   ██ ███████ ██  ████ ██████ ██████ ██  ██    ██████ ██   ██    ██     ██████ ███████   ██   ██   ██   \n");
    }

    public static int printMenuInicial(){
        printTitle();
        System.out.println("\nBienvenido a Hundir la Flota! ");
        System.out.println("\n(1) - Registrar a un nuevo jugador");
        System.out.println("(2) - Hacer login");
        System.out.println("(3) - Jugar Offline");
        System.out.println("(4) - Salir del Juego\n");
        return Input.readOption(new Scanner(System.in),"Tu Respuesta: ");
    }

    public static int printMenuOnline(){
        System.out.println("\nEscoge una opcion...");
        System.out.println("\n(1) -  Información del jugador (consultar puntuación histórica).");
        System.out.println("(2) -  Iniciar una partida");
        System.out.println("(3) -  Listar partidas iniciadas a la espera de contrincante.");
        System.out.println("(4) -  Unirse a una partida ya iniciada.");
        System.out.println("(5) -  Salir (Logout)\n");
        return Input.readOption(new Scanner(System.in),"Tu Respuesta: ");
    }
    public static int printMenuOffline(){
        System.out.println("\nEscoge un modo de juego...");
        System.out.println("\n(1) - Inicia una partida (PvE)");
        System.out.println("(2) - Inicia una partida (PvP)");
        System.out.println("(3) - Simula una partida (EvE)");
        System.out.println("(4) - Reglas y Leyenda");
        System.out.println("(5) - Volver al menu inicial\n");
        return Input.readOption(new Scanner(System.in),"Tu Respuesta: ");
    }

    public static int printMenuServidor(){
        System.out.println("\nBienvenido a la interfaz del Servidor!");
        System.out.println("\n(1) - Ver Informacion del Servidor");
        System.out.println("(2) - Ver el Estado de las Partidas que se estan jugando");
        System.out.println("(3) - Salir");
        return Input.readOption(new Scanner(System.in),"Tu Respuesta: ");
    }

    public static int printMenuBaseDeDatos(){
        System.out.println("\nBienvenido a la interfaz de la Base de Datos!");
        System.out.println("\n(1) - Ver Informacion de la Base de Datos");
        System.out.println("(2) - Listar jugadores registrados y puntuaciones");
        System.out.println("(3) - Salir");
        return Input.readOption(new Scanner(System.in),"Tu Respuesta: ");
    }

    public static void printRules(){
        System.out.println(Colores.ANSI_YELLOW + "\nComo ganar:" + Colores.ANSI_RESET);
        System.out.println(Colores.ANSI_WHITE +
                "- Cada jugador tiene un campo de batalla representado por una cuadrícula de 10x10 (predeterminada) en la que coloca "+ TiposDeBarcos.sizeAllShips() +" barcos, ocultos para su oponente.\n" +
                "- ¡El objetivo del juego es hundir todos los barcos del oponente! Un barco se hunde cuando se le golpea una vez por cada espacio que ocupa.\n" +
                "- En otras palabras, un "+TiposDeBarcos.toSpanishNameShip(TiposDeBarcos.values()[0])+", que ocupa "+TiposDeBarcos.values()[0].getShipLength()+" espacios, se hunde después de ser golpeado 2 veces.\n" +
                "- Los "+ TiposDeBarcos.sizeAllShips() +" barcos ocupan un total de "+TiposDeBarcos.lengthAllShips()+" espacios, por lo que el primer jugador en registrar 25 golpes gana!" +
                Colores.ANSI_RESET);

        System.out.println(Colores.ANSI_YELLOW + "\nComo jugar:" + Colores.ANSI_RESET);
        System.out.println(Colores.ANSI_WHITE +
                "- Para jugar, sigue las instrucciones para configurar tus "+ TiposDeBarcos.sizeAllShips() +" barcos en cualquier patrón que desees (no se permiten colocaciones diagonales o adyacentes a otros barcos).\n" +
                "- Para colocar un barco, debes ingresar una coordenada de inicio (A1-J10 para el tablero 10x10 predeterminado) y una dirección (vertical u horizontal).\n" +
                "- Por ejemplo: A1 o B7. Los barcos no pueden superponerse o estar adyacentes (tocarse) y debes permanecer dentro de los límites del borde.\n" +
                "- Una vez que ambos jugadores hayan configurado sus barcos, ¡la batalla puede comenzar!\n" +
                "- Lanza torpedos contra los barcos de tu oponente adivinando las coordenadas.\n" +
                "- Las filas están representadas por las letras A-J y las columnas con los números 1-10 (tablero 10x10).\n" +
                "- Las coordenadas válidas incluyen una fila seguida de una columna, por ejemplo, A1, B7, J10, etc.\n" +
                "- Se te informará si has golpeado o fallado en un barco.\n" +
                "- ¡Hunde los 8 barcos de la computadora para ganar!" +
                Colores.ANSI_RESET);
        System.out.println(Colores.ANSI_YELLOW + "\nLeyenda:" + Colores.ANSI_RESET);
        for (TiposDeBarcos type: TiposDeBarcos.values()){
            System.out.println(Colores.ANSI_WHITE +
                    "- (" + Colores.ANSI_YELLOW + Tablero.SHIP + Colores.ANSI_WHITE+ "x"+type.getShipLength() + ")\t: " + TiposDeBarcos.toSpanishNameShip(type) +
                    Colores.ANSI_RESET);
        }
        System.out.println(
                "- (" + Colores.ANSI_BLUE + Tablero.WATER + Colores.ANSI_WHITE + ")\t: Agua\n" +
                "- (" + Colores.ANSI_YELLOW + Tablero.SHIP + Colores.ANSI_WHITE + ")\t: Barco\n" +
                "- (" + Colores.ANSI_RED + Tablero.HIT + Colores.ANSI_WHITE + ")\t: Barco impactado\n" +
                "- (" + Colores.ANSI_WHITE + Tablero.MISS + Colores.ANSI_WHITE + ")\t: Disparo fallado\n");

        System.out.print("\nPresiona Enter para continuar...");
        new Scanner(System.in).nextLine();
    }

    public static void printCredits(){
        System.out.println("\nGracias por jugar!");
        System.out.println("\nPedro Osorio Lopez\nCopyright: 2023-2024.");
    }

    public static void printError(String message){
        System.out.println(Colores.ANSI_RED + message + Colores.ANSI_RESET);
    }

    public static void printShot(Player player, Posicion position, boolean isHit){
        System.out.println("- " + player.getName() + " ha disparado a " + position.toStringEncode(position) + ": " +
                (isHit ? Colores.ANSI_BLUE + "¡Impactado!" + Colores.ANSI_RESET :
                        Colores.ANSI_RED + "¡Fallado!" + Colores.ANSI_RESET));
    }

    public static void printWinner(Player player){
        System.out.println(Colores.ANSI_BLUE + "\nEl jugador " + player.getName() + " ha ganado!" + Colores.ANSI_RESET + "\n");
        System.out.print("\nPresiona una tecla para continuar...");
        new Scanner(System.in).nextLine();
    }
    
    public static void printCurrentShip(Barcos ship, int numShipLeft){
        System.out.println("☛ " + ship.getName() + " (" +
                Colores.ANSI_YELLOW + ship.toGraphicLength() + Colores.ANSI_RESET +
                ") x" + numShipLeft);
    }

    public static void printAdjacentBoard(Player pOne, Player pTwo) throws ExcepcionPosicion {
        System.out.println(toStringAdjacentBoard(pOne, pTwo));
    }

    public static String toStringAdjacentBoard(Player pOne, Player pTwo) throws ExcepcionPosicion {
        Tablero firstBoard = pOne.getBoard();
        Tablero secondBoard = pTwo.getBoard().getBoardHideShips();
        String numbers  = "1234567890";
        String letters = "ABCDEFGHIJ";
        String s = "\n------------------------------------------------------------------------------\n";
        s += "\n   ";

        for (int i = 0; i < firstBoard.getLength(); i++) s += " " + numbers.charAt(i) + " ";
        s += "         ";
        for (int i = 0; i < secondBoard.getLength(); i++) s += " " + numbers.charAt(i) + " ";


        s += "\n\n";
        for (int i = 0; i < firstBoard.getLength(); i++){
            s += Colores.ANSI_WHITE;
            s += letters.charAt(i) + "   ";
            s += Colores.ANSI_RESET;

            for (int j = 0; j < firstBoard.getLength(); j++){
                if (firstBoard.getBoard()[i][j] == Tablero.WATER) s += Colores.ANSI_BLUE + Tablero.WATER + "  " + Colores.ANSI_RESET;
                else if (firstBoard.getBoard()[i][j] == Tablero.HIT) s += Colores.ANSI_RED +  Tablero.HIT + "  " + Colores.ANSI_RESET;
                else if (firstBoard.getBoard()[i][j] == Tablero.MISS) s += Tablero.MISS + "  " + Colores.ANSI_RESET;
                else s += Colores.ANSI_YELLOW + firstBoard.getBoard()[i][j] + "  " + Colores.ANSI_RESET;
            }

            s += "     ";

            s += Colores.ANSI_WHITE;
            s += letters.charAt(i) + "   ";
            s += Colores.ANSI_RESET;

            for (int j = 0; j < secondBoard.getLength(); j++){
                if (secondBoard.getBoard()[i][j] == Tablero.WATER) s += Colores.ANSI_BLUE + Tablero.WATER + "  " + Colores.ANSI_RESET;
                else if (secondBoard.getBoard()[i][j] == Tablero.HIT) s += Colores.ANSI_RED +  Tablero.HIT + "  " + Colores.ANSI_RESET;
                else if (secondBoard.getBoard()[i][j] == Tablero.MISS) s += Tablero.MISS + "  " + Colores.ANSI_RESET;
                else s += Colores.ANSI_YELLOW + secondBoard.getBoard()[i][j] + "  " + Colores.ANSI_RESET;
            }

            s += "\n";
        }
        s += "\n------------------------------------------------------------------------------\n";
        return s;
    }

    public static void printBoard(Tablero board){
        System.out.println(toStringBoard(board));
    }

    public static String toStringBoard(Tablero board){
        String numbers  = "1234567890";
        String letters = "ABCDEFGHIJ";
        String s = "\n   ";
        for (int i = 0; i < board.getLength(); i++) s += " " + numbers.charAt(i) + " ";
        s += "\n\n";
        for (int i = 0; i < board.getLength(); i++){
            s += Colores.ANSI_WHITE;
            s += letters.charAt(i) + "   ";

            for (int j = 0; j < board.getLength(); j++){
                if (board.getBoard()[i][j] == Tablero.WATER) s += Colores.ANSI_BLUE + Tablero.WATER + "  " + Colores.ANSI_RESET;
                else if (board.getBoard()[i][j] == Tablero.HIT) s += Colores.ANSI_RED +  Tablero.HIT + "  " + Colores.ANSI_RESET;
                else if (board.getBoard()[i][j] == Tablero.MISS) s += Tablero.MISS + "  " + Colores.ANSI_RESET;
                else s += Colores.ANSI_YELLOW + board.getBoard()[i][j] + "  " + Colores.ANSI_RESET;
            }
            s += "\n";
        }
        return s;
    }
}

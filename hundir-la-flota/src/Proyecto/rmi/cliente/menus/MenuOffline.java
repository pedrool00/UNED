/*
 * Nombre: Pedro
 * Apellidos: Osorio Lopez
 * Correo electrónico: posorio22@alumno.uned.es
 */

package Proyecto.rmi.cliente.menus;

import java.util.InputMismatchException;
import java.util.Scanner;

import Proyecto.comunes.Display;
import Proyecto.comunes.tablero.excepciones.ExcepcionPosicion;
import Proyecto.juegoOffline.JuegoOffline;

public class MenuOffline {
    public static void main(String[] args) throws ExcepcionPosicion {
        @SuppressWarnings("resource")
        Scanner sc = new Scanner(System.in);
        JuegoOffline game;
        String name = "", nameOne = "", nameTwo = "";
        int opt;
        boolean hasName = false, hasNameOne = false, hasNameTwo = false;

        try {
            do {
                opt = Display.printMenuOffline();

                switch (opt) {
                    case 1 -> {
                        if (!hasName) {
                            System.out.print("\nEscoge tu nombre: ");
                            name = sc.next();
                            hasName = true;
                        }
                        game = new JuegoOffline(name);
                        game.run();
                    }
                    case 2 -> {
                        if (!hasNameOne || !hasNameTwo)
                        {
                            System.out.print("\nJugador 1 - Escoge tu nombre: ");
                            nameOne = sc.next();
                            hasNameOne = true;
                            System.out.print("\nJugador 2 - Escoge tu nombre:\n");
                            nameTwo = sc.next();
                            hasNameTwo = true;
                        }
                        game = new JuegoOffline(nameOne, nameTwo);
                        game.run();
                    }
                    case 3 -> {
                        game = new JuegoOffline();
                        game.run();
                    }
                    case 4 -> {
                        Display.printRules();
                    }
                }
            } while (opt != 5);
        } catch (InputMismatchException e) { }
    }
}

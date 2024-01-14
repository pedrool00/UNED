/*
 * Nombre: Pedro
 * Apellidos: Osorio Lopez
 * Correo electrónico: posorio22@alumno.uned.es
 */

package Proyecto.rmi.cliente.menus;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

import Proyecto.comunes.Display;
import Proyecto.comunes.tablero.excepciones.ExcepcionPosicion;
import Proyecto.rmi.cliente.Jugador;
import Proyecto.rmi.cliente.CallbackJugadorImpl;
import Proyecto.rmi.servidor.ServicioAutenticacionInterface;
import Proyecto.rmi.servidor.ServicioGestorInterface;

public class MenuOnline {
    @SuppressWarnings("resource")
    public static void main(String[] args) throws ExcepcionPosicion {
        Jugador jugador = new Jugador();
        CallbackJugadorImpl game;
        int opt;

        try {
            do {
                opt = Display.printMenuOnline();

                switch (opt) {
                    case 1 -> {
                        System.out.print("\nAqui se podra ver la informacion del Jugador: ");
                        String username1 = jugador.getMyUser();
                        System.out.println("\nTu nombre de Usuario: " + username1);
                        System.out.print("\nPresiona Enter para continuar...");
                        new Scanner(System.in).nextLine();
                        //Todo: Anadir puntuaciones tras implementar juego
                    }
                    case 2 -> {
                        try {
                            // Iniciar una partida
                            Registry reg = LocateRegistry.getRegistry("localhost", 1099);
                            ServicioGestorInterface intobj = (ServicioGestorInterface) reg.lookup("rmi://localhost/servicio_gestor");
                            String username1 = jugador.getMyUser();
                            String username2 = null;
                            String response = intobj.iniciarPartida(username1);
                            System.out.println(response);

                            if (response.equals("Has iniciado una Partida")) {
                                System.out.print("\nEsperando a que se una otro jugador...");
                                username2 = intobj.buscarUsuariosEnEspera(username2);
                            }
                            if (username2 == null) {
                                do {
                                    System.out.print("\nNo hay jugadores disponibles en espera");
                                    System.out.print("\nPresiona 1 para intentar de nuevo");
                                    System.out.print("\nPresiona 2 para volver al menu");
                                    switch (opt) {
                                        case 1 -> {
                                            username2 = intobj.buscarUsuariosEnEspera(username2);
                                            if (username2 != null) {
                                                intobj.anadirPartidaEnCurso(username1, username2);
                                                System.out.print("\nSe ha encontrado un contrincante.");
                                                System.out.print("\nComenzando el juego...\n");
                                                //Todo: Arreglar el metodo para comenzar el juego
                                                //Esto es temporal hasta que pueda pasar el juego desde
                                                //el ServidorPor ahora se quedan esperando mutuamente
                                                //tras la seleccion de barcos
                                                game = new CallbackJugadorImpl(username1, username2);
                                                game.runPlayerOneGame();
                                                intobj.eliminarPartidaEnCurso(username1, username2);
                                            }
                                        }
                                        case 2 -> {
                                            response = intobj.cancelarPartida(username2);
                                        }
                                    } 
                                } while (opt != 2);
                            }
                            else {
                                intobj.anadirPartidaEnCurso(username1, username2);
                                System.out.print("\nSe ha encontrado un contrincante.");
                                System.out.print("\nComenzando el juego...\n");
                                //Todo: Arreglar el metodo para comenzar el juego
                                game = new CallbackJugadorImpl(username1, username2);
                                game.runPlayerOneGame();
                                intobj.eliminarPartidaEnCurso(username1, username2);
                            }

                        } catch (RemoteException | NotBoundException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                    case 3 -> {
                        try {
                            // Ver partidas iniciadas y en espera
                            Registry reg = LocateRegistry.getRegistry("localhost", 1099);
                            ServicioGestorInterface intobj = (ServicioGestorInterface) reg.lookup("rmi://localhost/servicio_gestor");
                            System.out.print("\nUsuarios que han iniciado partidas y estan a la espera: ");
                            Set<String> PartidasIniciadas = intobj.listaPartidasIniciadas();
                            for (String usuario : PartidasIniciadas) {
                                System.out.println(usuario);
                            }

                        } catch (RemoteException | NotBoundException ex) {
                            System.out.println(ex.getMessage());
                        }
                        System.out.print("\nPresiona Enter para continuar...");
                        new Scanner(System.in).nextLine();
                    }
                    case 4 -> {
                        try {
                            // Unirse a una partida iniciada
                            Registry reg = LocateRegistry.getRegistry("localhost", 1099);
                            ServicioGestorInterface intobj = (ServicioGestorInterface) reg.lookup("rmi://localhost/servicio_gestor");
                            String username1 = null;
                            String username2 = jugador.getMyUser();
                            String response = intobj.unirseListaEspera(username2);
                            System.out.println(response);

                            if (response.equals("Te has unido a la lista de espera")) {
                                System.out.print("\nBuscando una partida iniciada...");
                                username1 = intobj.buscarPartidasIniciadas(username1); 
                            }
                            if (username1 == null) {
                                do {
                                    System.out.print("\nNo hay partidas iniciadas");
                                    System.out.print("\nPresiona 1 para intentar de nuevo");
                                    System.out.print("\nPresiona 2 para volver al menu");
                                    switch (opt) {
                                        case 1 -> {
                                            username1 = intobj.buscarPartidasIniciadas(username1);
                                            if (username1 != null) {
                                                System.out.print("\nSe ha encontrado una partida.");
                                                System.out.print("\nComenzando el juego...\n");
                                                //Todo: Arreglar el metodo para comenzar el juego
                                                game = new CallbackJugadorImpl(username1, username2);
                                                game.runPlayerTwoGame();
                                            }
                                        }
                                        case 2 -> {
                                            response = intobj.salirseListaEspera(username2);
                                        }
                                    } 
                                } while (opt != 2);
                            }
                            else {
                                System.out.print("\nSe ha encontrado una partida.");
                                System.out.print("\nComenzando el juego...\n");
                                //Todo: Arreglar el metodo para comenzar el juego
                                game = new CallbackJugadorImpl(username1, username2);
                                game.runPlayerTwoGame();
                            }

                        } catch (RemoteException | NotBoundException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                    case 5 -> {
                        try {
                            // Llamar al método logout en el servidor, obteniendo el nombre
                            // de usuario desde el cliente.
                            Registry reg = LocateRegistry.getRegistry("localhost", 1099);
                            ServicioAutenticacionInterface intobj = (ServicioAutenticacionInterface) reg.lookup("rmi://localhost/servicio_autenticacion");
                            String username = jugador.getMyUser();
                            String response = intobj.logout(username);
                            System.out.println(response);

                            if (response.equals("LOGOUT_SUCCESSFUL")) {
                                System.out.print("\nSesion finalizada con exito.");
                            }
                            else {
                                System.out.print("\nNo se ha podido cerrar la sesion.");
                            }

                        } catch (RemoteException | NotBoundException ex) {
                            System.out.println(ex.getMessage());
                        }
                        System.out.print("\nPresiona Enter para volver al menu de inicio");
                        new Scanner(System.in).nextLine();
                    }
                }
            } while (opt != 5);
        } catch (InputMismatchException e) { }
    }
}

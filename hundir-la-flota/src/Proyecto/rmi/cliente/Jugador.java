/*
 * Nombre: Pedro
 * Apellidos: Osorio Lopez
 * Correo electrónico: posorio22@alumno.uned.es
 */

package Proyecto.rmi.cliente;

import java.util.InputMismatchException;
import java.util.Scanner;

import Proyecto.comunes.Display;
import Proyecto.comunes.tablero.excepciones.ExcepcionPosicion;
import Proyecto.rmi.cliente.menus.MenuOffline;
import Proyecto.rmi.cliente.menus.MenuOnline;
import Proyecto.rmi.servidor.ServicioAutenticacionInterface;

import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

@SuppressWarnings("resource")
public class Jugador {
    private static String myUser;

    public String getMyUser() {
        return myUser;
    }

    public static void main(String[] args) throws RemoteException, NotBoundException, ExcepcionPosicion {
        //CallbackJugadorImpl receiver = new CallbackJugadorImpl();

        Scanner sc = new Scanner(System.in);
        int opt;

        try {
            do {
                opt = Display.printMenuInicial();

                switch (opt) {
                    case 1 -> {
                        try {
                            Registry reg = LocateRegistry.getRegistry("localhost", Registry.REGISTRY_PORT);
                            ServicioAutenticacionInterface intobj = (ServicioAutenticacionInterface) reg.lookup("rmi://localhost/servicio_autenticacion");

                            System.out.println("Nuevo nombre de usuario: ");
                            String username = sc.nextLine();
                            System.out.println("Escoge tu contrasena: ");
                            String password = sc.nextLine();

                            String response = intobj.register(username, password);

                            System.out.println("Response from server : " + response);
                            if (response.equals("REGISTRATION_SUCCESSFUL")) {
                                System.out.print("\nTe has registrado con exito.");
                                System.out.print("\nInicia sesion para acceder al menu del juego.");
                                System.out.print("\nPresiona Enter para continuar...");
                                new Scanner(System.in).nextLine();
                            } else {
                                System.out.print("\nEl usuario ya existe.");
                                System.out.print("\nPor favor, inicia sesion o crea un nuevo usuario.");
                                System.out.print("\nPresiona Enter para continuar...");
                                new Scanner(System.in).nextLine();
                            }
                        } catch (RemoteException | NotBoundException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                    case 2 -> {
                        try {
                            Registry reg = LocateRegistry.getRegistry("localhost", Registry.REGISTRY_PORT);
                            ServicioAutenticacionInterface intobj = (ServicioAutenticacionInterface) reg.lookup("rmi://localhost/servicio_autenticacion");

                            System.out.println("Escribe tu nombre de usuario: ");
                            String username = sc.nextLine();
                            System.out.println("Escribe tu contrasena: ");
                            String password = sc.nextLine();

                            String response = intobj.login(username, password);

                            System.out.println("Response from server : " + response);
                            if (response.equals("LOGIN_SUCCESFUL")) {
                                myUser = username;
                                MenuOnline.main(args);
                            } else {
                                System.out.print("\nNo has iniciado sesion correctamente.");
                                System.out.print("\nPor favor, vuelva a intentarlo o cree un nuevo usuario.");
                                System.out.print("\nPresiona Enter para continuar...");
                                new Scanner(System.in).nextLine();
                            }
                        } catch (RemoteException | NotBoundException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                    case 3 -> {
                        MenuOffline.main(args);
                    }
                }
            } while (opt != 4);
        } catch (InputMismatchException e) {
        }
        Display.printCredits();
        sc.close();
    }
}

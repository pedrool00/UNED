/*
 * Nombre: Pedro
 * Apellidos: Osorio Lopez
 * Correo electrónico: posorio22@alumno.uned.es
 */

package Proyecto.rmi.basededatos;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;
import java.util.Map;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import Proyecto.comunes.Display;
import Proyecto.comunes.tablero.excepciones.ExcepcionPosicion;
import Proyecto.rmi.servidor.ServicioAutenticacionInterface;

@SuppressWarnings("resource")
public class BaseDeDatos {

    public static void main(String[] args) throws ExcepcionPosicion {
        Scanner sc = new Scanner(System.in);
        int opt;

        try {
            do {
                opt = Display.printMenuBaseDeDatos();

                switch (opt) {
                    case 1 -> {
                        try {
                            // Por ahora, la opcion 1 de la base de datos
                            // se usa para ver la lista de usuarios conectados.
                            Registry reg = LocateRegistry.getRegistry("localhost", Registry.REGISTRY_PORT);
                            ServicioAutenticacionInterface intobj = (ServicioAutenticacionInterface) reg.lookup("rmi://localhost/servicio_autenticacion");
                        
                            System.out.print("\nLista de usuarios conectados: \n");
                            Set<String> usuariosConectados = intobj.listaUsuariosConectados();
                        
                            for (String usuario : usuariosConectados) {
                                System.out.println(usuario);
                            }
                        } catch (RemoteException | NotBoundException ex) {
                            System.out.println(ex.getMessage());
                        }
                        System.out.print("\nPresiona Enter para continuar...");
                        new Scanner(System.in).nextLine();
                    }
                    case 2 -> {
                            // Por ahora, la opcion 2 de la base de datos
                            // se usa para ver la lista de usuarios registrados
                            System.out.print("\nAqui se pueden ver los usuarios registrados");
                            System.out.print("\nLista de usuarios registrados: \n");

                            Map<String, String> usuariosRegistrados = ServicioDatosImpl.leerUsuariosRegistrados();
                            for (Map.Entry<String, String> entry : usuariosRegistrados.entrySet()) {
                                String usuario = entry.getKey();
                                System.out.println(usuario);
                            }
                            System.out.print("\nPresiona Enter para continuar...");
                            new Scanner(System.in).nextLine();
                    }
                }
            } while (opt != 3);
        } catch (InputMismatchException e) {
        }
        sc.close();
    }
}

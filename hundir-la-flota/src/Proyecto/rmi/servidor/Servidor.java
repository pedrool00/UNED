/*
 * Nombre: Pedro
 * Apellidos: Osorio Lopez
 * Correo electrónico: posorio22@alumno.uned.es
 */

package Proyecto.rmi.servidor;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;
import java.util.Map;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import Proyecto.comunes.Display;


public class Servidor {
    public static void main(String[] args) throws RemoteException {

        //Metodo para iniciar el servidor
        try {
            Registry reg = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);

            ServicioAutenticacionImpl obj = new ServicioAutenticacionImpl();

            ServicioGestorImpl obj2 = new ServicioGestorImpl();

            reg.rebind("rmi://localhost/servicio_autenticacion", obj);

            reg.rebind("rmi://localhost/servicio_gestor", obj2);
    
            System.out.println("Server Running...");
        } catch (RemoteException ex) {
            System.out.println(ex.getMessage());
        }
    

        Scanner sc = new Scanner(System.in);
        int opt;

        try {
            do {
                opt = Display.printMenuServidor();

                switch (opt) {
                    case 1 -> {
                        System.out.print("\nAqui se puede ver la informacion del Servidor: ");
                        try {
                            // Ver usuarios conectados y descripcion del servidor
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
                        System.out.print("\nAqui se puede ver el estado de las partidas en juego: ");
                        try {
                            // Ver partidas en juego
                            Registry reg = LocateRegistry.getRegistry("localhost", Registry.REGISTRY_PORT);
                            ServicioGestorInterface intobj = (ServicioGestorInterface) reg.lookup("rmi://localhost/servicio_gestor");
                        
                            System.out.print("\nPartidas en Juego: \n");
                            Map<String, String> PartidasEnCurso = intobj.listaPartidasEnCurso();
                        
                            for (Map.Entry<String, String> entry : PartidasEnCurso.entrySet()) {
                                String usuario1 = entry.getKey();
                                String usuario2 = entry.getValue();
                                System.out.println(usuario1 + "vs " + usuario2);
                            }
                        } catch (RemoteException | NotBoundException ex) {
                            System.out.println(ex.getMessage());
                        }
                        System.out.print("\nPresiona Enter para continuar...");
                        new Scanner(System.in).nextLine();
                    }
                }
            } while (opt != 3);
        } catch (InputMismatchException e) { }
        sc.close();
        System.exit(0);
    }
}

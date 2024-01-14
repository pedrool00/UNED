/*
 * Nombre: Pedro
 * Apellidos: Osorio Lopez
 * Correo electrónico: posorio22@alumno.uned.es
 */

package Proyecto.rmi.servidor;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.Set;

public interface ServicioAutenticacionInterface extends Remote {

    public String register(String username, String password) throws RemoteException;

    public String login(String username, String password) throws RemoteException;

    public String logout(String username) throws RemoteException;

    public Map<String, String> listaUsuariosRegistrados() throws RemoteException;
    
    public Set<String> listaUsuariosConectados() throws RemoteException;
}

/*
 * Nombre: Pedro
 * Apellidos: Osorio Lopez
 * Correo electrónico: posorio22@alumno.uned.es
 */

package Proyecto.rmi.servidor;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;
import java.util.Map;

//import Proyecto.rmi.cliente.CallbackJugadorInterface;

public interface ServicioGestorInterface extends Remote {

    public String iniciarPartida(String username) throws RemoteException;

    public String cancelarPartida(String username) throws RemoteException;

    public String unirseListaEspera(String username) throws RemoteException;

    public String salirseListaEspera(String username) throws RemoteException;

    public Set<String> listaPartidasIniciadas() throws RemoteException;

    public Set<String> listaJugadoresEnEspera() throws RemoteException;

    public Map<String, String> listaPartidasEnCurso() throws RemoteException;

    public String buscarPartidasIniciadas(String username) throws RemoteException;

    public String buscarUsuariosEnEspera(String username) throws RemoteException;

    public void anadirPartidaEnCurso(String username1, String username2) throws RemoteException;

    //Todo: Por ahora faltan por ser utilizados los metodos debajo de este
    //mensaje y estan sujetos a cambios.

    public void eliminarPartidaEnCurso(String username1, String username2) throws RemoteException;

    public void comenzarJuegoUsuario1(String username1, String username2) throws RemoteException;

    public void comenzarJuegoUsuario2(String username1, String username2) throws RemoteException;

}

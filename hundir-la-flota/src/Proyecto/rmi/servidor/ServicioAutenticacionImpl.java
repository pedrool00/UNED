/*
 * Nombre: Pedro
 * Apellidos: Osorio Lopez
 * Correo electrónico: posorio22@alumno.uned.es
 */

package Proyecto.rmi.servidor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Proyecto.rmi.basededatos.ServicioDatosImpl;

public class ServicioAutenticacionImpl extends UnicastRemoteObject implements ServicioAutenticacionInterface {
    private Map<String, String> usuariosRegistrados = new HashMap<>();
    private Set<String> usuariosConectados = new HashSet<>();

    public ServicioAutenticacionImpl() throws RemoteException {
        super();
    }

    // Método para registrar un usuario
    public String register(String username, String password) throws RemoteException {
        init();
        String response = searchRegistration(username, password);
        return response;
    }

    // Método para iniciar sesión de un usuario
    public String login(String username, String password) throws RemoteException {
        init();
        String response = searchLogin(username, password);
        return response;
    }

    public String logout(String username) throws RemoteException {
        String response = "";
        if (usuariosConectados.contains(username)) {
            usuariosConectados.remove(username);
            response = "LOGOUT_SUCCESSFUL";
        }
        else {
            response = "USER_NOT_CONNECTED";
        }
        return response;
    }

    // Método para obtener la lista de jugadores registrados
    public Map<String, String> listaUsuariosRegistrados() {
        init();
        return usuariosRegistrados;
    }
    
    // Método para obtener la lista de usuarios conectados
    public Set<String> listaUsuariosConectados() {
        return usuariosConectados;
    }

    // Método para inicializar la lista de clientes con usuarios predefinidos
    private void init() {
        usuariosRegistrados = ServicioDatosImpl.leerUsuariosRegistrados();
    }

    // Método para buscar un usuario en la lista de clientes durante el registro
    // Si el registro es exitoso, se agrega el usuario a la lista de usuarios registrados.
    private String searchRegistration(String username, String password) {
        String response = "";

        if (usuariosRegistrados.containsKey(username)) {
            response = "USER_ALREADY_EXISTS";
        } else {
            response = "REGISTRATION_SUCCESSFUL";
            usuariosRegistrados.put(username, password);
            ServicioDatosImpl.escribirUsuariosRegistrados(usuariosRegistrados);
        }

        return response;
    }

    // Método para buscar un usuario en la lista de clientes durante el inicio de sesión.
    // Si el login es exitoso, se agrega el usuario a la lista de usuarios conectados.
    private String searchLogin(String username, String password) {
        String response = "";

        List<Map.Entry<String, String>> entries = new ArrayList<Map.Entry<String, String>>(usuariosRegistrados.entrySet());
        Iterator<Map.Entry<String, String>> itr = entries.iterator();

        boolean flag = false;

        while (itr.hasNext()) {
            response = "";
            Map.Entry<String, String> entry = itr.next();
            String user = entry.getKey();
            String pass = entry.getValue();

            if (username.equals(user)) {
                flag = true;
                if (password.equals(pass)) {
                    response = "LOGIN_SUCCESFUL";
                    usuariosConectados.add(username);

                } else {
                    response = "PASSWORD_INCORRECT";
                }
                break;
            }
        }

        if (!flag) {
            response = "USER_NOT_EXISTS";
        }

        return response;
    }
}


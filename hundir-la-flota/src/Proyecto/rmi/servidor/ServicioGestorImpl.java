/*
 * Nombre: Pedro
 * Apellidos: Osorio Lopez
 * Correo electrónico: posorio22@alumno.uned.es
 */

package Proyecto.rmi.servidor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import Proyecto.comunes.tablero.excepciones.ExcepcionPosicion;
import Proyecto.rmi.cliente.CallbackJugadorImpl;
//import Proyecto.rmi.basededatos.ServicioDatosImpl;

public class ServicioGestorImpl extends UnicastRemoteObject implements ServicioGestorInterface {
    private Set<String> PartidasIniciadas = new HashSet<>();
	private Set<String> JugadoresEnEspera = new HashSet<>();
	private Map<String, String> PartidasEnCurso = new HashMap<>();

	//Todo: Implementar lista de puntuaciones
	private Map<String, Integer> Puntuaciones = new HashMap<>();

    public ServicioGestorImpl() throws RemoteException {
        super();
    }

	// Método para iniciar una partida
	public String iniciarPartida(String username) throws RemoteException {
        String response = "";
		if (username != null) {
			PartidasIniciadas.add(username);
			response = "Has iniciado una Partida";
		}
		else {
			response = "Necesitas un nombre de usuario";
		}
		return response;
	}

	// Método para cancelar una partida
	public String cancelarPartida(String username) throws RemoteException {
        String response = "";
        if (PartidasIniciadas.contains(username)) {
            PartidasIniciadas.remove(username);
            response = "Has cancelado una Partida";
        }
        else {
            response = "No tienes partidas iniciadas";
        }
        return response;
    }

    // Método para unirse a la lista de espera
    public String unirseListaEspera(String username) throws RemoteException {
		String response = "";
		if (username != null) {
			JugadoresEnEspera.add(username);
			response = "Te has unido a la lista de espera";
		}
		else {
			response = "Necesitas un nombre de usuario";
		}
		return response;
	}

	// Método para salirse de la lista de espera
	public String salirseListaEspera(String username) throws RemoteException {
        String response = "";
        if (JugadoresEnEspera.contains(username)) {
			JugadoresEnEspera.remove(username);
            response = "Has salido de la lista de espera";
        }
        else {
            response = "No estas en la lista de espera";
        }
        return response;
	}

	//Metodo para obtener la lista de Partidas Iniciadas
	public Set<String> listaPartidasIniciadas() {
        return PartidasIniciadas;
    }

    // Método para obtener la lista de jugadores a la espera
    public Set<String> listaJugadoresEnEspera() {
        return JugadoresEnEspera;
    }
    
    // Método para obtener la lista de partidas en curso
    public Map<String, String> listaPartidasEnCurso() {
        return PartidasEnCurso;
    }

	// Método para obtener la lista de puntuaciones
	//Todo: Implementar lista de puntuaciones
	public Map<String, Integer> listaPuntuaciones() {
		return Puntuaciones;
	}

	public String buscarPartidasIniciadas(String username) {
		int intervaloTiempo = 1000;
		int tiempoMaximo = 60000;
		int tiempoTranscurrido = 0;
	
		while (tiempoTranscurrido <= tiempoMaximo && username == null) {
			// Buscar un usuario de la lista de UsuariosEnEspera
				for (String usuario : PartidasIniciadas) {
					username = usuario;
					break;
				}
			// Verificar si se encontró un usuario
			if (username != null) {
				try {
					Thread.sleep(intervaloTiempo);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				PartidasIniciadas.remove(username);
				break;
			}
			// Esperar el intervalo de tiempo antes de volver a buscar
			try {
				Thread.sleep(intervaloTiempo);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			tiempoTranscurrido += intervaloTiempo;
		}
		return username;
	}

	public String buscarUsuariosEnEspera(String username) {
		int intervaloTiempo = 1000;
		int tiempoMaximo = 60000;
		int tiempoTranscurrido = 0;
	
		while (tiempoTranscurrido <= tiempoMaximo && username == null) {
			// Buscar un usuario de la lista de UsuariosEnEspera
				for (String usuario : JugadoresEnEspera) {
					username = usuario;
					break;
				}
			// Verificar si se encontró un usuario
			if (username != null) {
				try {
					Thread.sleep(intervaloTiempo);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				JugadoresEnEspera.remove(username);
				break;
			}
			// Esperar el intervalo de tiempo antes de volver a buscar
			try {
				Thread.sleep(intervaloTiempo);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			tiempoTranscurrido += intervaloTiempo;
		}
		return username;
	}

	// Método para anadir partidas en curso
    public void anadirPartidaEnCurso(String username1, String username2) throws RemoteException {
        PartidasEnCurso.put(username1, username2);
    }
	
	//Metodo para eliminar partidas en curso
	public void eliminarPartidaEnCurso(String username1, String username2) throws RemoteException {
	    PartidasEnCurso.remove(username1, username2);
	}

	//Todo: Cambiar para que los juegos no se inicien en el servidor
	// Metodo para comenzar el juego del usuario1
	public void comenzarJuegoUsuario1(String username1, String username2) {
		try {
			CallbackJugadorImpl gameUsername1;
        	gameUsername1 = new CallbackJugadorImpl(username1, username2);
			gameUsername1.runPlayerOneGame();
		} catch (ExcepcionPosicion e) { }	
    }

	// Metodo para comenzar el juego del usuario2
	public void comenzarJuegoUsuario2(String username1, String username2) {
		try {
			CallbackJugadorImpl gameUsername2;
        	gameUsername2 = new CallbackJugadorImpl(username1, username2);
			gameUsername2.runPlayerTwoGame();
		} catch (ExcepcionPosicion e) { }	
    }

	//Todo: Utilizar los metodos para anadir y guardar puntuaciones
	// Método para añadir puntuaciones
	public void anadirPuntuaciones(String username, int puntuacion) {
		if (Puntuaciones.containsKey(username)) {
			int puntuacionActual = Puntuaciones.get(username);
			puntuacionActual += 1;
			Puntuaciones.put(username, puntuacionActual);
		} else {
			Puntuaciones.put(username, 1);
		}
	}

	public void guardarPuntuaciones(String username, int puntuacion) {
		Puntuaciones.put(username, puntuacion);
	}
}




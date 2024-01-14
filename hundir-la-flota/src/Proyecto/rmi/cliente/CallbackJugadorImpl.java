/*
 * Nombre: Pedro
 * Apellidos: Osorio Lopez
 * Correo electrónico: posorio22@alumno.uned.es
 */

package Proyecto.rmi.cliente;

import Proyecto.comunes.Display;
import Proyecto.comunes.jugador.Player;
import Proyecto.comunes.tablero.Posicion;
import Proyecto.comunes.tablero.excepciones.ExcepcionPosicion;
import Proyecto.comunes.tablero.excepciones.ExcepcionTablero;

public class CallbackJugadorImpl {
    private final Player pOne;
    private final Player pTwo;
    private boolean playerOneShipsPlaced = false;
    private boolean playerTwoShipsPlaced = false;
    private boolean isPlayerOneTurn = true;
    
    // Constructor que recibe los nombres de ambos jugadores y crea una instancia del juego con dos jugadores humanos
    public CallbackJugadorImpl(String username1, String username2){
        pOne = new Player(username1);
        pTwo = new Player(username2);
    }

    // Método privado que representa un turno de juego. Recibe al jugador que ataca y al jugador que defiende. Devuelve un booleano que indica si el juego debe continuar.
    private boolean turn(Player attack, Player defend) throws ExcepcionPosicion {
        Posicion shoot = null;
        boolean isHit, isAddHit;
        if (attack.hasShipsLive()){
            do {
                try {
                    // El jugador atacante realiza un disparo en la posición seleccionada por el jugador defensor
                    shoot = attack.shoot(defend.getBoard().getBoardHideShips());
                    // El jugador defensor registra el disparo realizado por el jugador atacante
                    isAddHit = defend.addShoot(shoot);
                } catch (ExcepcionTablero e) {
                    // Si ocurre un error, como disparar en una posición ya atacada, se muestra un mensaje de error
                    Display.printError("¡Error, ya has disparado en esta posición!");
                    isAddHit = false;
                }
            } while (!isAddHit);
            // Se verifica si el disparo realizado por el jugador atacante impactó en algún barco del jugador defensor
            isHit = defend.getBoard().thereIsHit(shoot);
            // Si hay impacto, se registra el disparo en el jugador atacante
            if (isHit) attack.registerShoot(shoot);
            //Todo: Anadir puntuaciones
            // Se muestra en pantalla el resultado del disparo
            Display.printShot(attack, shoot, isHit);

            // Se muestra en pantalla los tableros adyacentes de los jugadores
            // Todo: Optimizar como se muestra el tablero
            if (!attack.isAI()) Display.printAdjacentBoard(attack, defend);
            else if (!defend.isAI()) Display.printAdjacentBoard(defend, attack);

            try { Thread.sleep(500); } catch (InterruptedException e) { }
            return true;
        } else return false;
    }

    // Método privado que agrega todos los barcos a los jugadores
    //Todo: Hacer pruebas con este nuevo enfoque usando hilos.
    //Seguramente el uso de countdownlatch sea mas eficiente, pero 
    //esta implementacion es mas ilustrativa de la logica que he pensado.
    private void addAllShips() {
        Thread playerOneThread = new Thread(() -> {
            pOne.addAllShips();
            playerOneShipsPlaced = true;
            waitForOponentShips();
        });

        Thread playerTwoThread = new Thread(() -> {
            pTwo.addAllShips();
            playerTwoShipsPlaced = true;
            waitForOponentShips();
        });

        playerOneThread.start();
        playerTwoThread.start();
    }

    // Método privado que imprime el resultado del juego
    private void printResultGame(){
        // Se verifica qué jugador tiene más barcos restantes y se muestra como el ganador
        if (pOne.shipsLeft() > pTwo.shipsLeft()) Display.printWinner(pOne);
        else Display.printWinner(pTwo);
        // Todo: Agregar puntuaciones y escribirlas en la base de datos
    }

    //Todo: Seguramente la solucion correcta sea iniciar el juego completo
    //desde el servidor y dar acceso a cada usuario a su parte correspondiente
    //en funcion de que se cumpla la condicion de cambio de turno. Sin embargo,
    //no me ha dado tiempo a terminar de probarlo.

    // Método público que inicia y ejecuta el juego
    public void runGame() {
        // Se agregan los barcos a los jugadores
        addAllShips();

        // Esperar a que el oponente coloque sus barcos
        waitForOponentShips();
    
        // Mientras ambos jugadores tengan barcos vivos, se continúa el juego
        while (pOne.hasShipsLive() && pTwo.hasShipsLive()) {
            try {
                // Se verifica de quién es el turno y se llama al método turn() correspondiente
                if (isPlayerOneTurn == true) {
                    turn(pOne, pTwo);
                    isPlayerOneTurn = false;
                    waitPlayerOne();
                } else {
                    turn(pTwo, pOne);
                    isPlayerOneTurn = true;
                    waitPlayerTwo();
                }
            } catch (ExcepcionPosicion e) {
                // Si ocurre un error al realizar un disparo, se muestra un mensaje de error y se finaliza el juego
                Display.printError("¡Error al realizar el disparo!");
                break;
            }
        }
        // Se muestra el resultado final del juego
        printResultGame();
    }

    //Todo: Intento de separar el juego en dos metodos
    //de manera que cada jugador inicie una "mitad" y vayan alternandose
    //a medida que se cumpla la condicion de cambio de turno. Seguramente 
    //esto sea eliminado en la version final, pero lo uso para hacer pruebas.

    // Función de run para el jugador uno
    public void runPlayerOneGame() throws ExcepcionPosicion {
        // Se agregan los barcos al jugador uno
        pOne.addAllShips();
        playerOneShipsPlaced = true;

        // Esperar a que el oponente coloque sus barcos
        waitForOponentShips();
    
        // Bucle principal del juego
        while (pOne.hasShipsLive() && pTwo.hasShipsLive()) {
            // Comprobar si es el turno del jugador uno
            if (isPlayerOneTurn) {
                // Turno del jugador uno
                turn(pOne, pTwo);
                isPlayerOneTurn = false;
            } else {
                // Poner al jugador uno en espera
                waitPlayerOne();
            }
        }
        // Se imprime el resultado final del juego
        printResultGame();
    }
    
    // Función de run para el jugador dos
    public void runPlayerTwoGame() throws ExcepcionPosicion {
        // Se agregan los barcos al jugador dos
        pTwo.addAllShips();
        playerTwoShipsPlaced = true;

        // Esperar a que el oponente coloque sus barcos
        waitForOponentShips();
    
        // Bucle principal del juego
        while (pOne.hasShipsLive() && pTwo.hasShipsLive()) {
            // Comprobar si es el turno del jugador dos
            if (!isPlayerOneTurn) {
                // Turno del jugador dos
                turn(pTwo, pOne);
                isPlayerOneTurn = true;
            } else {
                // Poner al jugador dos en espera
                waitPlayerTwo();
            }
        }
        // Se imprime el resultado final del juego
        printResultGame();
    }

    // Función para esperar a que el jugador dos coloque sus barcos
    private void waitForOponentShips() {
        printWaitMessage();
        while (!playerOneShipsPlaced || !playerTwoShipsPlaced) {
            try {
                // Pausa el bucle durante x milisegundos
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Función para poner al jugador uno en espera
    private void waitPlayerOne() {
        printWaitMessage();
        while (!isPlayerOneTurn) {
            try {
                // Pausa el bucle durante x milisegundos
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Función para poner al jugador dos en espera
    private void waitPlayerTwo() {
        printWaitMessage();
        while (isPlayerOneTurn) {
            try {
                // Pausa el bucle durante x milisegundos
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Función para imprimir el mensaje de espera
    private void printWaitMessage() {
        System.out.println("Esperando por el oponente...");
    }
}

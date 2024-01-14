/*
 * Nombre: Pedro
 * Apellidos: Osorio Lopez
 * Correo electrónico: posorio22@alumno.uned.es
 */

package Proyecto.juegoOffline;
import Proyecto.comunes.Display;
import Proyecto.comunes.jugador.Player;
import Proyecto.comunes.tablero.Posicion;
import Proyecto.comunes.tablero.excepciones.ExcepcionPosicion;
import Proyecto.comunes.tablero.excepciones.ExcepcionTablero;

public class JuegoOffline {
    private final Player pOne;
    private final Player pTwo;
    private final String COMPUTER = "AI";
    
    // Constructor que recibe los nombres de ambos jugadores y crea una instancia del juego con dos jugadores humanos
    public JuegoOffline(String nameOne, String nameTwo){
        pOne = new Player(nameOne);
        pTwo = new Player(nameTwo);
    }

    // Constructor que recibe el nombre del jugador humano y crea una instancia del juego con un jugador humano y un jugador computadora
    public JuegoOffline(String name){
        pOne = new Player(name);
        pTwo = new Player(COMPUTER, true);
    }

    // Constructor que crea una instancia del juego con dos jugadores computadora
    public JuegoOffline(){
        pOne = new Player(COMPUTER+"1",true);
        pTwo = new Player(COMPUTER+"2", true);
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
                    if (!attack.isAI()) Display.printError("¡Error, ya has disparado en esta posición!");
                    isAddHit = false;
                }
            } while (!isAddHit);
            // Se verifica si el disparo realizado por el jugador atacante impactó en algún barco del jugador defensor
            isHit = defend.getBoard().thereIsHit(shoot);
            // Si hay impacto, se registra el disparo en el jugador atacante
            if (isHit) attack.registerShoot(shoot);
            // Se muestra en pantalla el resultado del disparo
            Display.printShot(attack, shoot, isHit);

            // Se muestra en pantalla los tableros adyacentes de los jugadores
            if (attack.isAI() && defend.isAI()) Display.printAdjacentBoard(attack, defend);
            else if (!attack.isAI()) Display.printAdjacentBoard(attack, defend);
            else if (!defend.isAI()) Display.printAdjacentBoard(defend, attack);

            // Si no son jugadores computadora, se espera un segundo antes de continuar al siguiente turno
            if (!attack.isAI() && !defend.isAI()) try { Thread.sleep(1000); } catch (InterruptedException e) { }
            return true;
        } else return false;
    }

    // Método privado que agrega todos los barcos a los jugadores
    private void addAllShips(){
        pOne.addAllShips();
        pTwo.addAllShips();
    }

    // Método privado que imprime el resultado del juego
    private void printResultGame(){
        // Se verifica qué jugador tiene más barcos restantes y se muestra como el ganador
        if (pOne.shipsLeft() > pTwo.shipsLeft()) Display.printWinner(pOne);
        else Display.printWinner(pTwo);
    }

    // Método público que inicia y ejecuta el juego
    public void run() throws ExcepcionPosicion {
        // Se agregan los barcos a los jugadores
        addAllShips();
        while (turn(pOne, pTwo) && turn(pTwo, pOne)) { }
        // Se imprime el resultado final del juego
        printResultGame();
    }
}

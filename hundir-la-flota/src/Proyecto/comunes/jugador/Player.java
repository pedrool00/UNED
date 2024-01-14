/*
 * Nombre: Pedro
 * Apellidos: Osorio Lopez
 * Correo electrónico: posorio22@alumno.uned.es
 */

package Proyecto.comunes.jugador;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import Proyecto.comunes.Display;
import Proyecto.comunes.barcos.Barcos;
import Proyecto.comunes.barcos.utilidades.*;
import Proyecto.comunes.tablero.*;
import Proyecto.comunes.tablero.excepciones.ExcepcionPosicion;
import Proyecto.comunes.tablero.excepciones.ExcepcionTablero;

public class Player {
    private String name;
    private final Tablero board = new Tablero(10);
    private final ArrayList<Posicion> shoots = new ArrayList<>();
    private ArrayList<Posicion> nextTargets = new ArrayList<>();
    private boolean isAI;

    public Player(){
        name = randName();
        isAI = true;
    }

    public Player(String name){
        this.name = name;
        isAI = false;
    }

    public Player(String name, boolean isAI){
        this.name = name;
        this.isAI = isAI;
    }

    public Player(boolean isAI){
        this.name = randName();
        this.isAI = isAI;
    }

    private ArrayList<Barcos> initShips(){
        ArrayList<Barcos> list = new ArrayList<>();
        for (TiposDeBarcos type: TiposDeBarcos.values()){
            for (int i = 0; i < type.getNumShips(); i++){
                list.add(new Barcos(TiposDeBarcos.toSpanishNameShip(type), type.getShipLength()));
            }
        }
        return list;
    }

    private String randName(){
        Random rand = new Random();
        String letters = "abcdefghiljkmnopqrstuvwxyz";
        String name = "";
        int maxLung = 10, minLung = 3;
        int l = rand.nextInt(maxLung - minLung) + minLung;
        for (int i=0; i<l; i++){
            name += letters.charAt(rand.nextInt(letters.length()));
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Tablero getBoard() {
        return board;
    }

    public ArrayList<Posicion> getShoots() {
        return shoots;
    }

    public void setAI(boolean AI) {
        isAI = AI;
    }

    public boolean isAI(){
        return isAI;
    }

    public void addAllShips(){
        if (!isAI) {
            boolean isAdded;
            Posicion position;
            Direccion direction;
            String messageInputPosition = "- Inserte la coordenada (ej. A1): ";
            String messageInputDirection = "- Escoja la dirección (h/v): ";
            Scanner sc = new Scanner(System.in);
            ArrayList<Barcos> list = initShips();
            for (int i = 0; i < list.size(); i++) {
                Barcos ship = list.get(i);
                do {
                    Display.printBoard(board);
                    Display.printCurrentShip(ship, countShip(list, ship.getLength()));

                    position = Input.readPosition(sc, board, messageInputPosition);
                    direction = Input.readDirection(sc, messageInputDirection);
                    ship.setPosition(position);
                    ship.setDirection(direction);

                    try {
                        isAdded = board.addShip(ship);
                    } catch (ExcepcionTablero | ExcepcionPosicion e) {
                        Display.printError(e.toString());
                        isAdded = false;
                    }
                } while (!isAdded);
                list.remove(i);
                i--;
            }
            Display.printBoard(board);
        }else randAddAllShips();
    }

    private void randAddAllShips(){
        Random random = new Random();
        ArrayList<Barcos> list = initShips();

        boolean isAdded;
        Posicion position;
        Direccion direction;
        int deadlock = 0, limit = 1000;

        for (int i = 0; i < list.size(); i++){
            Barcos ship = list.get(i);
            deadlock = 0;
            do {
                try {
                    position = new Posicion(random.nextInt(board.getLength()), random.nextInt(board.getLength()));
                    direction = random.nextBoolean() ? Direccion.VERTICAL : Direccion.HORIZONTAL;
                    ship.setPosition(position);
                    ship.setDirection(direction);
                    isAdded = board.addShip(ship);
                } catch (ExcepcionTablero | ExcepcionPosicion e){ isAdded = false; }
                if (!isAdded) deadlock++;
                if (deadlock > limit) {
                    reset();
                    i = -1;
                    break;
                }
            } while (!isAdded);
        }
    }

    public boolean hasShipsLive(){
        return board.getNumShips() > 0;
    }

    private int countShip(ArrayList<Barcos> ships, int length){
        int count = 0;
        for (Barcos ship: ships){
            if (ship.getLength() == length) count++;
        }
        return count;
    }

    public int shipsLeft(){
        return board.getNumShips();
    }

    private Posicion randPosition() throws ExcepcionPosicion {
        Random random = new Random();
        int x = random.nextInt(board.getLength());
        int y = random.nextInt(board.getLength());
        return new Posicion(x, y);
    }

    public boolean addShoot(Posicion pos) throws ExcepcionTablero {
        return board.addHit(pos);
    }

    public Posicion shootAI(Tablero boardEnemy) throws ExcepcionPosicion {
        Posicion lastPos, nextPos;
        if (shoots.isEmpty()) return randPosition();
        else {
            lastPos = getLastShoot(); //last hit
            nextTargets.addAll(boardEnemy.getPossibleTarget(lastPos));
            if (nextTargets.isEmpty()) return randPosition();
            nextPos = nextTargets.get(0);
            nextTargets.remove(0);
            return nextPos;
        }
    }

    public Posicion shoot(Tablero boardEnemy) throws ExcepcionPosicion {
        if (isAI) return shootAI(boardEnemy);
        else {
            Scanner sc = new Scanner(System.in);
            return Input.readPosition(sc, board,  "- " + name + ", ¿Dónde quieres disparar? ");
        }
    }

    public void registerShoot(Posicion position){
        shoots.add(position);
    }

    public Posicion getLastShoot(){
        if (shoots.isEmpty()) return null;
        return shoots.get(shoots.size() - 1);
    }

    private void reset(){
        board.reset();
    }


}

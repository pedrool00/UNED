/*
 * Nombre: Pedro
 * Apellidos: Osorio Lopez
 * Correo electrónico: posorio22@alumno.uned.es
 */

package Proyecto.comunes.tablero;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import Proyecto.comunes.barcos.Barcos;
import Proyecto.comunes.barcos.utilidades.Direccion;
import Proyecto.comunes.tablero.excepciones.ExcepcionPosicion;
import Proyecto.comunes.tablero.excepciones.ExcepcionTablero;

public class Tablero {
    private final int length; //one variable for rows = columns = 10 [10x10 matrix]
    private char[][] board;
    private int numShips = 0;
    public static final char HIT = 'O';
    public static final char MISS = 'X';
    public static final char SHIP = '#';
    public static final char WATER = '~';

    public Tablero(int length){
        this.length = length;
        board = initBoard();
    }

    public Tablero(char[][] matrix){
        this.length = matrix.length;
        board = matrix;
    }

    private char[][] initBoard(){
        char[][] matrix = new char[length][length];
        for (char[] row: matrix){
            Arrays.fill(row, WATER);
        }
        return matrix;
    }

    public int getLength() {
        return length;
    }

    public int getNumShips() {
        return numShips;
    }

    public char[][] getBoard() {
        return board;
    }

    public char at(Posicion position) {
        return board[position.getRow()][position.getColumn()];
    }

    public boolean set(char status, Posicion position) {
        board[position.getRow()][position.getColumn()] = status;
        return true;
    }

    public boolean thereIsShip(Posicion position) {
        return at(position) == SHIP;
    }

    public boolean thereIsWater(Posicion position) {
        return at(position) == WATER;
    }

    public boolean thereIsMiss(Posicion position){
        return at(position) == MISS;
    }

    public boolean thereIsHit(Posicion position){
        return at(position) == HIT;
    }

    public boolean thereIsSpace(Barcos ship) {
        int l = ship.getLength();
        int x = ship.getPosition().getRow();
        int y = ship.getPosition().getColumn();
        if (ship.getDirection() == Direccion.HORIZONTAL) return (length - y + 1) > l;
        else return (length - x + 1) > l;
    }

    public boolean isNearShip(Barcos ship) throws ExcepcionPosicion {
        int k, row, column;
        row = ship.getPosition().getRow();
        column = ship.getPosition().getColumn();

        if (ship.getDirection() == Direccion.HORIZONTAL) k = column;
        else k = row;

        for (int i = 0; i < ship.getLength() && k + i < length - 1; i++) {
            if (isShipAround(row, column)) return true;

            if (ship.getDirection() == Direccion.HORIZONTAL) column++;
            else if (ship.getDirection() == Direccion.VERTICAL) row++;
        }
        return false;
    }

    private boolean isShipAround(int row, int column) throws ExcepcionPosicion {
        ArrayList<Posicion> list = getAllNearPositions(row, column);
        for (Posicion position: list){
            if (at(position) == SHIP) return true;
        }
        return false;
    }

    public ArrayList<Posicion> getPossibleTarget(Posicion position) throws ExcepcionPosicion {
        int row = position.getRow(), column = position.getColumn();
        ArrayList<Posicion> list = new ArrayList<>();
        //norte
        if (row - 1 >= 0 && !thereIsMiss(new Posicion(row - 1, column)) && !thereIsHit(new Posicion(row - 1, column)))
            list.add(new Posicion(row - 1, column));
        //oeste
        if (column - 1 >= 0 && !thereIsMiss(new Posicion(row, column - 1)) && !thereIsHit(new Posicion(row, column - 1)))
            list.add(new Posicion(row, column - 1));
        //sur
        if (row + 1 < length && !thereIsMiss(new Posicion(row + 1, column)) && !thereIsHit(new Posicion(row + 1, column)))
            list.add(new Posicion(row + 1, column));
        //este
        if (column + 1 < length && !thereIsMiss(new Posicion(row, column + 1)) && !thereIsHit(new Posicion(row, column + 1)))
            list.add(new Posicion(row, column + 1));
        return list;
    }

    public ArrayList<Posicion> getAllNearPositions(int row, int column) throws ExcepcionPosicion {
        ArrayList<Posicion> list = new ArrayList<>();
        //norte
        if (row - 1 >= 0) list.add(new Posicion(row - 1, column));
        //sur
        if (row + 1 < length) list.add(new Posicion(row + 1, column));
        //este
        if (column + 1 < length) list.add(new Posicion(row, column + 1));
        //oeste
        if (column - 1 >= 0) list.add(new Posicion(row, column - 1));
        //nor-este
        if (row - 1 >= 0 && column + 1 < length) list.add(new Posicion(row - 1, column + 1));
        //nor-oeste
        if (row - 1 >= 0 && column - 1 >= 0) list.add(new Posicion(row - 1, column - 1));
        //sur-este
        if (row + 1 < length && column + 1 < length) list.add(new Posicion(row + 1, column + 1));
        //sud-oeste
        if (row + 1 < length && column - 1 >= 0) list.add(new Posicion(row + 1, column - 1));
        return list;
    }

    public boolean addShip(Barcos ship) throws ExcepcionTablero, ExcepcionPosicion {
        int k = 0, row, column;
        if (!thereIsShip(ship.getPosition())){
            if (thereIsSpace(ship)){
                if (!isNearShip(ship)){
                    row = ship.getPosition().getRow();
                    column = ship.getPosition().getColumn();
                    for (int i = 0; i < ship.getLength() && k + i < length; i++) {
                        if (ship.getDirection() == Direccion.HORIZONTAL) {
                            if (i == 0) k = column;
                            board[row][column + i] = SHIP;
                        }
                        else if (ship.getDirection() == Direccion.VERTICAL) {
                            if (i == 0) k = row;
                            board[row + i][column] = SHIP;
                        }
                        numShips++;
                    }
                    return true;
                }
                else throw new ExcepcionTablero("Error, otro barco se encuentra cerca");
            }
            else throw new ExcepcionTablero("Error, no hay espacio para el barco en esa dirección");
        }
        else throw new ExcepcionTablero("Error, ya hay un barco en esa posición");
    }

    public boolean addHit(Posicion position) throws ExcepcionTablero {
        if (thereIsShip(position)) {
            numShips--;
            return set(HIT, position);
        }
        else if (thereIsWater(position)) return set(MISS, position);
        else throw new ExcepcionTablero("Error, ya has disparado en esta posición");
    }

    public Posicion randPositionFromList(ArrayList<Posicion> list){
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }

    public Tablero getBoardHideShips() throws ExcepcionPosicion{
        char[][] matrix = new char[length][length];
        for (int i = 0; i < length; i++){
            for (int j = 0; j < length; j++){
                if (!thereIsShip(new Posicion(i,j))){
                    matrix[i][j] = at(new Posicion(i,j));
                }
                else matrix[i][j] = WATER;
            }
        }
        return new Tablero(matrix);
    }

    public void reset(){
        numShips = 0;
        board = initBoard();
    }

}

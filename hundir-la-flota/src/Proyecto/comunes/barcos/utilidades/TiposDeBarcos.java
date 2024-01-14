/*
 * Nombre: Pedro
 * Apellidos: Osorio Lopez
 * Correo electrónico: posorio22@alumno.uned.es
 */

package Proyecto.comunes.barcos.utilidades;

public enum TiposDeBarcos {
    SUBMARINE(3, 2),
    CRUISER(2, 3),
    DESTROYER(2, 4),
    BATTLESHIP(1, 5);

    private final int numShips;
    private final int shipLength;

    TiposDeBarcos (int numShips, int shipLength) {
        this.numShips = numShips;
        this.shipLength = shipLength;
    }

    public int getShipLength() {
        return shipLength;
    }
    public int getNumShips() {
        return numShips;
    }

    public static int lengthAllShips(){
        int sum = 0;
        for (TiposDeBarcos type: TiposDeBarcos.values()) sum += type.shipLength * type.numShips;
        return sum;
    }
    public static int sizeAllShips(){
        int sum = 0;
        for (TiposDeBarcos type: TiposDeBarcos.values()) sum += type.numShips;
        return sum;
    }
    public static String toSpanishNameShip(TiposDeBarcos type){
        return switch (type) {
            case BATTLESHIP -> "Acorazado";
            case DESTROYER -> "Destructor";
            case CRUISER -> "Crucero";
            case SUBMARINE -> "Submarino";
        };
    }
}

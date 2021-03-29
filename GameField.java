package battleship;

import java.util.Scanner;

public class GameField {
    private char[][] map;
    private char[][] shipMap;
    private Ship[] ships;

    public GameField() {
        this.map = createMap();
        this.shipMap = createMap();
        this.ships = new Ship[5];
    }

    private char[][] createMap() {
        char[][] map = new char[11][11];
        for (int row = 0; row < map.length; row++) {
            for (int column = 0; column < map[row].length; column++) {
                if (row == 0 && column == 0) {
                    map[row][column] = ' ';
                    continue;
                }
                if (row == 0) {
                    map[row][column] = (char) ('0' + column);

                    continue;
                }
                if (column == 0) {
                    map[row][column] = (char) (64 + row);
                    continue;
                }
                map[row][column] = '~';
            }
        }
        return map;
    }

    public void printMap() {
        for (int row = 0; row < this.map.length; row++) {
            if (row > 0) {
                System.out.println();
            }
            for (int column = 0; column < this.map[row].length; column++) {
                if (this.map[row][column] == ':') {
                    System.out.print("10");
                    continue;
                }
                System.out.print(this.map[row][column] + " ");
            }
        }
        System.out.println();
        System.out.println();
    }

    public void printFogMap() {
        for (int row = 0; row < this.map.length; row++) {
            System.out.println();
            for (int column = 0; column < this.map[row].length; column++) {
                if (this.map[row][column] == ':') {
                    System.out.print("10");
                    continue;
                }
                if (this.map[row][column] == 'O'){
                    System.out.print("~ ");
                } else {
                    System.out.print(this.map[row][column] + " ");
                }

            }
        }
        System.out.println();
    }

    public void addShip(char identifier, int size, Scanner scanner) throws Exception {
        System.out.println();
        String start = scanner.next();
        String end = scanner.next();
        if (start.length() < 2  || end.length() < 2  || start.length() > 3 || end.length() > 3) {
            throw new Exception("Incorrect input length. Must be __ __");
        }
        char startRow = start.charAt(0);
        char endRow = end.charAt(0);
        int startColumn = Integer.parseInt(start.substring(1));
        int endColumn = Integer.parseInt(end.substring(1));

        if (notRow(startRow) || notRow(endRow) || notColumn(startColumn) || notColumn(endColumn)) {
            throw new Exception("Ship must be on the game board(A-J,1-10)");
        }
        if (startRow != endRow && startColumn != endColumn) {
            throw new Exception("Ship must be horizontal or vertical! ");
        }
        if (startRow == endRow) {
            if (Math.abs(startColumn - endColumn) != size - 1) {
                throw new Exception("Length should be " + size);
            }
        } else {
            if (Math.abs(startRow - endRow) != size - 1) {
                throw new Exception("Length should be " + size + "!");
            }
        }

        if (startRow == endRow) {
            int stern =  startColumn;
            if (startColumn > endColumn) {
                stern = endColumn;
            }
            if (!isClearHorizontal(stern, startRow - 64, size)) {
                throw new Exception("Too close to another ship! ");
            }
            for (int i = 0; i < size; i++) {
                this.map[(startRow - 64)][stern + i] = 'O';
                this.shipMap[(startRow - 64)][stern + i] = identifier;
            }
        } else {
            int stern = startRow - 64;
            if (startRow > endRow) {
                stern = endRow - 64;
            }
            if (!isClearVertical(stern, startColumn, size)) {
                throw new Exception("Too close to another ship! ");
            }
            for (int i = 0; i < size; i++) {
                this.map[stern + i][startColumn] = 'O';
                this.shipMap[stern + i][startColumn] = identifier;
            }
        }
        System.out.println();
        printMap();
    }

    public void addShipToList(int index, Ship ship) {
        ships[index] = ship;
    }

    private void updateShips(int x, int y) {
        for (Ship ship : ships) {
            char id = ship.getIdentifier();
            if (this.shipMap[x][y] == id) {
                ship.hitShip();
                if (ship.getHp() == 0) {
                    System.out.println("You sank a ship");
                } else {
                    System.out.println("You hit a ship");
                }
            }
        }
    }

    public boolean isDefeated() {
        int hitCount = 0;
        for (int row = 0; row < this.map.length; row++) {
            for (int column = 0; column < this.map[row].length; column++) {
                if (this.map[row][column] == 'X') {
                    hitCount++;
                    if (hitCount == 17) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public void fireShot(Scanner scanner) throws Exception {
        System.out.println();
        String coordinates = scanner.next();
        System.out.println();
        char shotRow = coordinates.charAt(0);
        int shotColumn = Integer.parseInt(coordinates.substring(1));
        if (notRow(shotRow) || notColumn(shotColumn)) {
            throw new Exception("You cannot shoot outside the playing field!");
        }
        if (map[shotRow - 64][shotColumn] == '~') {
            map[shotRow - 64][shotColumn] = 'M';
            System.out.println("You missed!");
        } else {
            map[shotRow - 64][shotColumn] = 'X';
            updateShips(shotRow - 64, shotColumn);
        }
    }

    private boolean isClearVertical(int stern, int column, int size) {
        for (int i = 0; i < size; i++) {
            if (this.map[stern + i][column] == 'O') {
                return false;
            }
            if (column != 10) {
                if (this.map[stern + i][column + 1] == 'O') {
                    return false;
                }
            }
            if (column != 1) {
                if (this.map[stern + i][column - 1] == 'O') {
                    return false;
                }
            }
            if (stern + i != 10) {
                if (this.map[stern + i + 1][column] == 'O')  {
                    return false;
                }
            }
            if (stern + i != 1) {
                if (this.map[stern + i - 1][column] == 'O') {
                    return false;
                }
            }

        }
        return true;
    }

    private boolean isClearHorizontal(int stern, int row, int size) {
        for (int i = 0; i < size; i++) {
            if (this.map[row][stern + i] == 'O') {
                return false;
            }
            if (row != 10) {
                if (this.map[row + 1][stern + i] == 'O') {
                    return false;
                }
            }
            if (row != 1) {
                if (this.map[row - 1][stern + i] == 'O') {
                    return false;
                }
            }
            if (stern + i != 10) {
                if (this.map[row][stern + i + 1] == 'O')  {
                    return false;
                }
            }
            if (stern + i != 1) {
                if (this.map[row][stern + i - 1] == 'O') {
                    return false;
                }
            }

        }
        return true;
    }

    private boolean notRow(char character) {
        return character < 'A' || character > 'J';
    }

    private boolean notColumn(int number) {
        return number < 1 || number > 10;
    }

}

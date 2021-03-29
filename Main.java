package battleship;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Write your code here
        Scanner scanner = new Scanner(System.in);
        GameField home = new GameField();
        System.out.println("Player 1, place your ships on the game field");
        System.out.println();
        home.printMap();
        setUp(home, scanner);
        GameField away = new GameField();
        changePlayer();
        System.out.println("Player 2, place your ships on the game field");
        System.out.println();
        away.printMap();
        setUp(away, scanner);
        changePlayer();

        while (true) {
            away.printFogMap();
            System.out.println("---------------------");
            home.printMap();
            System.out.println("Player 1, it's your turn:");
            attemptShot(scanner, away);
            if (away.isDefeated()) {
                break;
            }
            changePlayer();
            home.printFogMap();
            System.out.println("---------------------");
            away.printMap();
            System.out.println("Player 2, it's your turn:");
            attemptShot(scanner, home);
            if (home.isDefeated()) {
                break;
            }
            changePlayer();
        }
        System.out.println("You sank the last ship. You won. Congratulations!");
    }

    public static void changePlayer() {
        System.out.println("Press Enter and pass the move to another player");
        try {
            System.in.read();
            for (int i = 0; i < 24; i++) {
                System.out.println();
            }
            return;
        } catch(IOException e) {
            System.out.println("hello");
        }
    }

    public static void setUp(GameField gameField, Scanner scanner) {
        System.out.println("Enter the coordinates of the Aircraft Carrier (5 cells):");
        attemptAddShip(5,'a',scanner,gameField);
        gameField.addShipToList(0, new Ship('a', 5));
        System.out.println("Enter the coordinates of the Battleship (4 cells):");
        attemptAddShip(4,'b',scanner,gameField);
        gameField.addShipToList(1, new Ship('b', 4));
        System.out.println("Enter the coordinates of the Submarine (3 cells):");
        attemptAddShip(3,'c',scanner,gameField);
        gameField.addShipToList(2, new Ship('c', 3));
        System.out.println("Enter the coordinates of the Cruiser (3 cells):");
        attemptAddShip(3,'d',scanner,gameField);
        gameField.addShipToList(3, new Ship('d', 3));
        System.out.println("Enter the coordinates of the Destroyer (2 cells):");
        attemptAddShip(2,'e',scanner,gameField);
        gameField.addShipToList(4, new Ship('e', 2));

    }
    public static void attemptAddShip(int size, char identifier, Scanner scanner, GameField field){
        while (true) {
            try {
                field.addShip(identifier, size, scanner);
            } catch (Exception e) {
                System.out.println(errorMessage(e.getMessage()));
                continue;
            }
            break;
        }
    }
    public static void attemptShot(Scanner scanner, GameField field) {
        while (true) {
            try {
                field.fireShot(scanner);
                if (field.isDefeated()) {
                    return;
                }
            } catch (Exception e) {
                System.out.println(errorMessage(e.getMessage()));
                continue;
            }
            break;
        }

    }

    public static String errorMessage(String error) {
        return "Error! " + error + " Try again:";
    }
}

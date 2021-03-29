package battleship;

public class Ship {
    private char identifier;
    private int hp;

    public Ship(char identifier, int hp) {
        this.identifier = identifier;
        this.hp = hp;
    }

    public int getHp() {
        return hp;
    }
    public void hitShip() {
        this.hp--;
        if (this.hp == 0) {
            System.out.print(" It sunk!!!");
        }
    }

    public char getIdentifier() {
        return identifier;
    }
}

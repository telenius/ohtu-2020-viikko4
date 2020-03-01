package ohtu;

public class Player {
    
    private int score;
    private String name;

    public Player(String name) {
        this.name = name;
        this.score = score;
    }

    public void wonPoint() {
           score += 1;
    }

    public int score() {
           return score;
    }

    public String name() {
           return name;
    }
    
}
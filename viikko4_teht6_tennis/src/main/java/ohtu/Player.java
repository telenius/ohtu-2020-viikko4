package ohtu;

public class Player {
    
    private int score;
    private String name;

    public Player(String name) {
        this.name = name;
        this.score = score;
    }

    public void addPoint() {
           this.score += 1;
    }

    public int score() {
           return this.score;
    }

    public String name() {
           return this.name;
    }
    
}
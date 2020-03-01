import ohtu.TennisGame;

public class App {

    public static void main(String[] args) {
        
        TennisGame game = new TennisGame("pelaaja1", "pelaaja2");

        System.out.println(game.getScore());

        game.wonPoint("pelaaja1");
        System.out.println(game.getScore());
    
        game.wonPoint("pelaaja1");
        System.out.println(game.getScore());
    
        game.wonPoint("pelaaja2");
        System.out.println(game.getScore());
    
        game.wonPoint("pelaaja1");
        System.out.println(game.getScore());
    
        game.wonPoint("pelaaja1");
        System.out.println(game.getScore());     

    }
}

package ohtu;
import ohtu.Player;
import static java.lang.Math.abs;

public class TennisGame {
      
    private Player player1;
    private Player player2;
    
    private static String[] pointsAsText = { "Love", "Fifteen", "Thirty", "Forty" };
    
    private Boolean pointsAreEven(Player player1, Player player2) {
        return ( player1.score() == player2.score() );
    }

    private Boolean neitherHasMoreThanThreePoints(Player player1, Player player2) {
        return ( (player1.score() < 4) && (player2.score() < 4) );
    }
    
    private Boolean atLeastOnePlayerHasMoreThanThreePoints(Player player1, Player player2) {
        return ( (player1.score() > 3) || (player2.score() > 3) );
    }

    private Boolean weHaveAwinner(Player player1, Player player2) {
        if ( abs( player1.score() - player2.score() ) > 1 )
            return true ;
        else
            return false ;
    }

    public TennisGame(String namePlayer1, String namePlayer2) {
        
        player1 = new Player(namePlayer1);
        player2 = new Player(namePlayer2);
    }

    public void wonPoint(String playerName) {

        if (playerName == player1.name())
            player1.addPoint();
        else
            player2.addPoint();
    }
    
    public String leadingPlayer(Player player1, Player player2){
        
        if (player1.score() > player2.score())
            return player1.name();
        else 
            return player2.name();
        
        
    }

    public String getScore() {
        String score = "";
        int tempScore=0;
        
        int player1Score = player1.score();
        int player2Score = player2.score();
        
        if (pointsAreEven(player1, player2))
        {

            if(neitherHasMoreThanThreePoints(player1, player2))
                score = pointsAsText[player1.score()] + "-All";
            else    
                score = "Deuce";
        }
        else if (atLeastOnePlayerHasMoreThanThreePoints(player1, player2))
        {
            String leadingPlayer=leadingPlayer(player1,player2);
            
            if(weHaveAwinner(player1,player2))
                score="Win for " + leadingPlayer;
            else    
                score="Advantage " + leadingPlayer;
        }
        else
        {
            score = pointsAsText[player1.score()] + "-" + pointsAsText[player2.score()];

        }
        return score;
    }
}

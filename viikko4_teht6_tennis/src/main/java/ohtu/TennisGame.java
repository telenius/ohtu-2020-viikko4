package ohtu;
import ohtu.Player;

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

    public String getScore() {
        String score = "";
        int tempScore=0;
        
        int player1Score = player1.score();
        int player2Score = player2.score();
        
        if (pointsAreEven(player1, player2))
        {
            switch (player1Score)
            {
                case 0:
                        score = "Love-All";
                    break;
                case 1:
                        score = "Fifteen-All";
                    break;
                case 2:
                        score = "Thirty-All";
                    break;
                case 3:
                        score = "Forty-All";
                    break;
                default:
                        score = "Deuce";
                    break;
                
            }
        }
        else if (atLeastOnePlayerHasMoreThanThreePoints(player1, player2))
        {
            int player1ScoreMinusPlayer2Score = player1Score-player2Score;
            if (player1ScoreMinusPlayer2Score==1) score ="Advantage player1";
            else if (player1ScoreMinusPlayer2Score ==-1) score ="Advantage player2";
            else if (player1ScoreMinusPlayer2Score>=2) score = "Win for player1";
            else score ="Win for player2";
        }
        else
        {
            for (int i=1; i<3; i++)
            {
                if (i==1) tempScore = player1Score;
                else { score+="-"; tempScore = player2Score;}
                switch(tempScore)
                {
                    case 0:
                        score+="Love";
                        break;
                    case 1:
                        score+="Fifteen";
                        break;
                    case 2:
                        score+="Thirty";
                        break;
                    case 3:
                        score+="Forty";
                        break;
                }
            }
        }
        return score;
    }
}

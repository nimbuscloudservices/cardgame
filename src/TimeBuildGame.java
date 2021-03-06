/**
 * Class TimeBuildGame creates Model, View, Timer, and Controller objects. Sets View object visible.
 */
public class TimeBuildGame
{

   public static void main(String[] args)
   {
      int numCardsPerHand = 7;
      int numPlayers = 2;
      View theView = new View ("Time Build Game", numCardsPerHand, numPlayers);
      Model theModel = new Model();
      Timer theTimer = new Timer();

      Controller theController = new Controller (theModel, theView);

      theView.setVisible(true);
   }

}

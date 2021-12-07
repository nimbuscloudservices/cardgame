import javax.swing.Icon;

public class Model
{
   static int MAX_CARDS_PER_HAND = 56;
   static int MAX_PLAYERS = 2;
   
   static int computerWinningsCounter = 0;
   static int humanWinningsCounter = 0;
   
   private int numCardsPerHand = 7;
   private int numPlayers = 2 ;
   
   Hand[] winnings = new Hand[numPlayers];
   
   public Model()
   {      
      GUICard.loadCardIcons();
      for (int k = 0; k < 2; k++)
      {
         winnings[k] = new Hand();
      }
   }
   
   public int getnumCardsPerHand()
   {
      return this.numCardsPerHand;
   }
   
   public int getnumPlayers()
   {
      return this.numPlayers;
   }
   
   public String playGame(Card humanCard,
         Card computerHand)
   {
         if (humanCard.getSuit() == computerHand.getSuit())
         {
            winnings[0].takeCard(humanCard);
            winnings[0].takeCard(computerHand);
            computerWinningsCounter += 2;
            return "Computer";
         }
         else
         {
            winnings[1].takeCard(humanCard);
            winnings[1].takeCard(computerHand);
            humanWinningsCounter += 2;
            return "Human";
         }
         
   }
   public Icon getHumanCard(Card humanCard)
   {
      return GUICard.getIcon(humanCard);
   }
   public Icon getComputerCard(Card computerCard)
   {
      return GUICard.getIcon(computerCard);
   }
 
   public Icon initializeHumanCards(Card humanCard)
   {
      return GUICard.getIcon(humanCard);
   }
   public Icon initializeComputerCards()
   {
      return GUICard.getBackCardIcon();  
   }
   public Icon updateHand(Card card)
   {
      return GUICard.getIcon(card);
   }
   public String endGame()
   {
      //Determine who collected the most cards
      if (humanWinningsCounter > computerWinningsCounter)
      {
         return "Human";
      }
      else
      {
         return "Computer";
      }
   }
}
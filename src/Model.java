import javax.swing.Icon;

public class Model
{
   private CardGameOutline SuitMatchGame;

   private int numPacksPerDeck = 1;
   private int numJokersPerPack = 2;
   private int numUnusedCardsPerPack = 0;
   private Card[] unusedCardsPerPack = null;

   static int MAX_CARDS_PER_HAND = 56;
   static int MAX_PLAYERS = 2;

   static int computerWinningsCounter = 0;
   static int humanWinningsCounter = 0;

   private int numCardsPerHand = 7;
   private int numPlayers = 2 ;

   private int humanCP = 0;
   private int computerCP = 0;

   public int humanRounds = 1, computerRounds = 1;
   public int  bothCP = 0;

   Hand[] winnings = new Hand[numPlayers];
   Hand[] threeCardStack = new Hand[3];

   public Model()
   {
      GUICard.loadCardIcons();

      SuitMatchGame = new CardGameOutline(numPacksPerDeck, numJokersPerPack,
            numUnusedCardsPerPack, unusedCardsPerPack, 2, 7);

      SuitMatchGame.deal();

      for (int k = 0; k < 2; k++)
      {
         winnings[k] = new Hand();
      }
      for (int k = 0; k < 3; k++)
      {
         threeCardStack[k] = new Hand();
      }

      for(int i=0; i < 3; i++)
      {
         addCardsInTheStacks(getCardFromDeck(), i);
      }
   }
   public void addCardsInTheStacks(Card card, int indexOf)
   {
      threeCardStack[indexOf].takeCard(card);
   }
   public Card getCardFromStack(int indexOf)
   {
      return threeCardStack[indexOf].playCard();
   }
   public Icon getIconCardFromStack(int indexOf)
   {
      return GUICard.getIcon(threeCardStack[indexOf].playCard());
   }
   public int getnumCardsPerHand()
   {
      return this.numCardsPerHand;
   }
   public int getnumPlayers()
   {
      return this.numPlayers;
   }
   public String playGame(Card card)
   {
      if(Card.cardValue(threeCardStack[0].playCard()) - Card.cardValue(card)  == -1 || Card.cardValue(threeCardStack[0].playCard()) - Card.cardValue(card)  == 1)
      {
         return "1";
      }
      if(Card.cardValue(threeCardStack[1].playCard())- Card.cardValue(card) == -1 || Card.cardValue(threeCardStack[1].playCard()) - Card.cardValue(card)  == 1)
      {
         return "2";
      }
      if(Card.cardValue(threeCardStack[2].playCard()) - Card.cardValue(card)  == -1 || Card.cardValue(threeCardStack[2].playCard()) - Card.cardValue(card)  == 1)
      {
         return "3";
      }
      else
      {
         return "Cannot play";
      }
   }
   public int getNumberOfCardsInDeck()
   {
      return SuitMatchGame.getNumCardsRemainingInDeck();
   }
   public Card getCardFromDeck()
   {
      return SuitMatchGame.getCardFromDeck();
   }
   public Icon getCard(Card card)
   {
      return GUICard.getIcon(card);
   }
   public Card getCard(int player, int indexOf)
   {
      return SuitMatchGame.playCard(player, indexOf);
   }
   public Icon getIconCardFromDeck()
   {
      return GUICard.getIcon(SuitMatchGame.getCardFromDeck());
   }
   public Icon getHumanIconCard(int indexOf)
   {
      return GUICard.getIcon(SuitMatchGame.getHand(1).inspectCard(indexOf));
   }
   public void addCardToHand(int player)
   {
      SuitMatchGame.getHand(player).takeCard(getCardFromDeck());
   }
   public void playCard(int player, int indexOf)
   {
      SuitMatchGame.getHand(player).playCard(indexOf);
   }
   public Icon initializeComputerCards()
   {
      return GUICard.getBackCardIcon();
   }
   public int cannotPlayCounter(int player)
   {
      if(player == 0)
      {
         return ++computerCP;
      }
      else
      {
         return ++humanCP;
      }
   }
   public String endGame()
   {
      //Determine who collected the most cards
      if (humanCP < computerCP)
      {
         return "Human";
      }
      else
      {
         return "Computer";
      }
   }
}

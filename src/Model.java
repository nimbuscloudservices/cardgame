import javax.swing.Icon;

/**
 *  Class Model hold the data for the game (# rounds per player, score, hards in the 3 stacks, player's cards in hand)
 *  and implements game play.
 */
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

   private int humanCP = 0;
   private int computerCP = 0;

   public int humanRounds = 1, computerRounds = 1;
   public int  bothCP = 0;

   Hand[] winnings = new Hand[2];
   Hand[] threeCardStack = new Hand[3];

   /**
    * Model constructor calls GUICard.loadCardIcons to generate card images, creates SuitMatchGame object,
    * initializes winnings and threeCardStack array, and adds 1 card in each stack.
    */
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
   /**
    * addCardsInTheStacks adds a card to the 3 stacks.
    *
    * @param card
    * @param indexOf
    */
   public void addCardsInTheStacks(Card card, int indexOf)
   {
      threeCardStack[indexOf].takeCard(card);
   }
   /**
    * getCardFromStack returns a card from one of the three stacks.
    *
    * @param indexOf
    * @return Card
    */
   public Card getCardFromStack(int indexOf)
   {
      return threeCardStack[indexOf].inspectCard(threeCardStack[indexOf].numCards-1);
   }
   /**
    * getIconCardFromStack returns the image icon of the card selected from one of the three stacks.
    *
    * @param indexOf
    * @return Icon
    */
   public Icon getIconCardFromStack(int indexOf)
   {
      return GUICard.getIcon(threeCardStack[indexOf].inspectCard(threeCardStack[indexOf].numCards-1));
   }
   /**
    * playGame uses if and else statements to determine if a card can be placed in one of the three decks.
    *
    * @param card
    * @return string
    */
   public String playGame(Card card)
   {
      if(Card.cardValue(threeCardStack[0].inspectCard(threeCardStack[0].numCards-1)) - Card.cardValue(card)  == -1 || Card.cardValue(threeCardStack[0].inspectCard(threeCardStack[0].numCards-1)) - Card.cardValue(card)  == 1)
      {
         return "1";
      }
      if(Card.cardValue(threeCardStack[1].inspectCard(threeCardStack[1].numCards-1))- Card.cardValue(card) == -1 || Card.cardValue(threeCardStack[1].inspectCard(threeCardStack[1].numCards-1)) - Card.cardValue(card)  == 1)
      {
         return "2";
      }
      if(Card.cardValue(threeCardStack[2].inspectCard(threeCardStack[2].numCards-1)) - Card.cardValue(card)  == -1 || Card.cardValue(threeCardStack[2].inspectCard(threeCardStack[2].numCards-1)) - Card.cardValue(card)  == 1)
      {
         return "3";
      }
      else
      {
         return "Cannot play";
      }
   }
   /**
    * getNumberOfCardsInDeck returns the number of cards remaining in the deck.
    *
    * @return int
    */
   public int getNumberOfCardsInDeck()
   {
      return SuitMatchGame.getNumCardsRemainingInDeck();
   }
   /**
    * getCardFromDeck returns a card from the deck.
    *
    * @return Card
    */
   public Card getCardFromDeck()
   {
      return SuitMatchGame.getCardFromDeck();
   }
   /**
    * getCard returns a the Icon image of the card.
    *
    * @param card
    * @return Icon
    */
   public Icon getCard(Card card)
   {
      return GUICard.getIcon(card);
   }
   /**
    * getCard returns the card the player was to play
    *
    * @param player
    * @param indexOf
    * @return Card
    */
   public Card getCard(int player, int indexOf)
   {
      return SuitMatchGame.playCard(player, indexOf);
   }
   /**
    * getIconCardFromDeck returns the Icon image of a card that was grabbed from the deck.
    *
    * @return Icon
    */
   public Icon getIconCardFromDeck()
   {
      return GUICard.getIcon(SuitMatchGame.getCardFromDeck());
   }
   /**
    * getHumanIconCard returns the Icon image of a card from the human hand.
    *
    * @param indexOf
    * @return Icon
    */
   public Icon getHumanIconCard(int indexOf)
   {
      return GUICard.getIcon(SuitMatchGame.getHand(1).inspectCard(indexOf));
   }
   /**
    * addCardToHand adds a card to the human hand
    *
    * @param player
    */
   public void addCardToHand(int player)
   {
      SuitMatchGame.getHand(player).takeCard(getCardFromDeck());
   }
   /**
    * initializeComputerCards returns the back of a card icon image
    *
    * @return Icon
    */
   public Icon initializeComputerCards()
   {
      return GUICard.getBackCardIcon();
   }
   /**
    * cannotPlayCounter increments the player's "I cannot play" count.
    *
    * @param player
    * @return int
    */
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
   /**
    * endGame returns the player who used the most "I cannot play" pass.
    *
    * @return String
    */
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

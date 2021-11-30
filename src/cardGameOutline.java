//class CardGameOutline
class CardGameOutline
{
   private static final int MAX_PLAYERS = 50;

   private int numPlayers;
   private int numPacks;            // # standard 52-card packs per deck
   // ignoring jokers or unused cards
   private int numJokersPerPack;    // if 2 per pack & 3 packs per deck, get 6
   private int numUnusedCardsPerPack;  // # cards removed from each pack
   private int numCardsPerHand;        // # cards to deal each player
   private Deck deck;               // holds the initial full deck and gets
   // smaller (usually) during play
   private Hand[] hand;             // one Hand for each player
   private Card[] unusedCardsPerPack;   // an array holding the cards not used
   // in the game.  e.g. pinochle does not
   // use cards 2-8 of any suit

   public CardGameOutline( int numPacks, int numJokersPerPack,
                           int numUnusedCardsPerPack,  Card[] unusedCardsPerPack,
                           int numPlayers, int numCardsPerHand)
   {
      int k;

      // filter bad values
      if (numPacks < 1 || numPacks > 6)
         numPacks = 1;
      if (numJokersPerPack < 0 || numJokersPerPack > 4)
         numJokersPerPack = 0;
      if (numUnusedCardsPerPack < 0 || numUnusedCardsPerPack > 50) //  > 1 card
         numUnusedCardsPerPack = 0;
      if (numPlayers < 1 || numPlayers > MAX_PLAYERS)
         numPlayers = 4;
      // one of many ways to assure at least one full deal to all players
      if  (numCardsPerHand < 1 ||
              numCardsPerHand >  numPacks * (52 - numUnusedCardsPerPack)
                      / numPlayers )
         numCardsPerHand = numPacks * (52 - numUnusedCardsPerPack) / numPlayers;

      // allocate
      this.unusedCardsPerPack = new Card[numUnusedCardsPerPack];
      this.hand = new Hand[numPlayers];
      for (k = 0; k < numPlayers; k++)
         this.hand[k] = new Hand();
      deck = new Deck(numPacks);

      // assign to members
      this.numPacks = numPacks;
      this.numJokersPerPack = numJokersPerPack;
      this.numUnusedCardsPerPack = numUnusedCardsPerPack;
      this.numPlayers = numPlayers;
      this.numCardsPerHand = numCardsPerHand;
      for (k = 0; k < numUnusedCardsPerPack; k++)
         this.unusedCardsPerPack[k] = unusedCardsPerPack[k];

      // prepare deck and shuffle
      newGame();
   }

   // constructor overload/default for game like bridge
   public CardGameOutline()
   {
      this(1, 0, 0, null, 4, 13);
   }

   public Hand getHand(int k)
   {
      // hands start from 0 like arrays

      // on error return automatic empty hand
      if (k < 0 || k >= numPlayers)
         return new Hand();

      return hand[k];
   }

   public Card getCardFromDeck() { return deck.dealCard(); }

   public int getNumCardsRemainingInDeck() { return deck.getNumCards(); }

   public void newGame()
   {
      int k, j;

      // clear the hands
      for (k = 0; k < numPlayers; k++)
         hand[k].resetHand();

      // restock the deck
      deck.init(numPacks);

      // remove unused cards
      for (k = 0; k < numUnusedCardsPerPack; k++)
         deck.removeCard( unusedCardsPerPack[k] );

      // add jokers
      for (k = 0; k < numPacks; k++)
         for ( j = 0; j < numJokersPerPack; j++)
            deck.addCard( new Card('X', Card.Suit.values()[j]) );

      // shuffle the cards
      deck.shuffle();
   }

   public boolean deal()
   {
      // returns false if not enough cards, but deals what it can
      int k, j;
      boolean enoughCards;

      // clear all hands
      for (j = 0; j < numPlayers; j++)
         hand[j].resetHand();

      enoughCards = true;
      for (k = 0; k < numCardsPerHand && enoughCards ; k++)
      {
         for (j = 0; j < numPlayers; j++)
            if (deck.getNumCards() > 0)
               hand[j].takeCard( deck.dealCard() );
            else
            {
               enoughCards = false;
               break;
            }
      }

      return enoughCards;
   }

   void sortHands()
   {
      int k;

      for (k = 0; k < numPlayers; k++)
         hand[k].sort();
   }

   Card playCard(int playerIndex, int cardIndex)
   {
      // returns bad card if either argument is bad
      if (playerIndex < 0 ||  playerIndex > numPlayers - 1 ||
              cardIndex < 0 || cardIndex > numCardsPerHand - 1)
      {
         //Creates a card that does not work
         return new Card('M', Card.Suit.spades);
      }

      // return the card played
      return hand[playerIndex].playCard(cardIndex);

   }


   boolean takeCard(int playerIndex)
   {
      // returns false if either argument is bad
      if (playerIndex < 0 || playerIndex > numPlayers - 1)
         return false;

      // Are there enough Cards?
      if (deck.getNumCards() <= 0)
         return false;

      return hand[playerIndex].takeCard(deck.dealCard());
   }

   static int NUM_CARDS_PER_HAND = 7;
   static int  NUM_PLAYERS = 2;
   static JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND];
   static JLabel[] humanLabels = new JLabel[NUM_CARDS_PER_HAND];
   static JLabel[] playedCardLabels  = new JLabel[NUM_PLAYERS];
   static JLabel[] playLabelText  = new JLabel[NUM_PLAYERS];

   public static void main(String[] args)
   {
      int k;
      Icon tempIcon;
      GUICard.loadCardIcons();

      int numPacksPerDeck = 1;
      int numJokersPerPack = 2;
      int numUnusedCardsPerPack = 0;
      Card[] unusedCardsPerPack = null;

      CardGameOutline SuitMatchGame = new CardGameOutline(
            numPacksPerDeck, numJokersPerPack,
            numUnusedCardsPerPack, unusedCardsPerPack,
            NUM_PLAYERS, NUM_CARDS_PER_HAND);

      SuitMatchGame.deal();

      // establish main frame in which program will run
      CardTable myCardTable
         = new CardTable("CardTable", NUM_CARDS_PER_HAND, NUM_PLAYERS);
      myCardTable.setSize(800, 600);
      myCardTable.setLocationRelativeTo(null);
      myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // show everything to the user
      myCardTable.setVisible(true);

      // CREATE LABELS
      Deck deck = new Deck();

      for (k = 0; k < NUM_CARDS_PER_HAND; k++)
      {
         computerLabels[k] = new JLabel(GUICard.getBackCardIcon());
         humanLabels[k] = new JLabel(GUICard.getIcon(deck.inspectCard(k)));
      }

      // ADD LABELS TO PANELS
      JPanel humanHand = new JPanel();
      for (k = 0; k < NUM_CARDS_PER_HAND; k++)
      {
         humanHand.add(humanLabels[k]);
      }
      myCardTable.pnlHumanHand.add(humanHand);

      JPanel computerHand = new JPanel();
      for (k = 0; k < NUM_CARDS_PER_HAND; k++)
      {
         computerHand.add(computerLabels[k]);
      }

      myCardTable.pnlComputerHand.add(computerHand);

      // and two random cards in the play region (simulating a computer/hum ply)
      for (k = 0; k < NUM_PLAYERS; k++)
      {
         playedCardLabels[k] = new JLabel(GUICard.getIcon(randomCardGenerator()));
      }

      playLabelText[0] = new JLabel("Computer", JLabel.CENTER);
      playLabelText[1] = new JLabel("You", JLabel.CENTER);

      JPanel playHand = new JPanel();
      playHand.setLayout(new GridLayout(2,2));

      for(k = 0; k < NUM_PLAYERS; k++)
      {
         playHand.add(playedCardLabels[k]);
      }

      playHand.add(playLabelText[0]);
      playHand.add(playLabelText[1]);

      myCardTable.pnlPlayArea.add(playHand, BorderLayout.CENTER);

      // show everything to the user
      myCardTable.setVisible(true);

   }

   public static Card randomCardGenerator()
   {
      Deck deck = new Deck();
      Random randomCard = new Random();
      return deck.inspectCard(randomCard.nextInt(deck.getTopCard()));
   }

}
class playGame implements ActionListener
{

   @Override
   public void actionPerformed(ActionEvent e)
   {


   }

}

class resetGame implements ActionListener
{

   @Override
   public void actionPerformed(ActionEvent e)
   {

   }

}

class endGame implements ActionListener
{

   @Override
   public void actionPerformed(ActionEvent e)
   {
      System.exit(0);
   }

}

class selectCard implements ActionListener
{

   @Override
   public void actionPerformed(ActionEvent e)
   {

   }

}

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class SuitMatchGame
{
   public static void main(String[] args)
   {

   }
}
//class CardGameOutline
class CardGameOutline
{
   private static final int MAX_PLAYERS = 50;
   private int numPlayers, numPacks, numJokersPerPack,
         numUnusedCardsPerPack, numCardsPerHand;
   private Deck deck;
   private Hand[] hand;
   private Card[] unusedCardsPerPack;

   /**
    * Constructor for CardGameOutline
    *
    * @param numPacks
    * @param numJokersPerPack
    * @param numUnusedCardsPerPack
    * @param unusedCardsPerPack
    * @param numPlayers
    * @param numCardsPerHand
    */
   public CardGameOutline(int numPacks, int numJokersPerPack,
         int numUnusedCardsPerPack, Card[] unusedCardsPerPack, int numPlayers,
         int numCardsPerHand)
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
      if (numCardsPerHand < 1 || numCardsPerHand > numPacks * (52 - numUnusedCardsPerPack) / numPlayers)
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

   /**
    * Accessor for NumCardsPerHands
    * @return value of numCardsPerHands
    */
   public int getNumCardsPerHand()
   {
      return numCardsPerHand;
   }

   /**
    * Gets card from deck
    * @return a card from deck
    */
   public Card getCardFromDeck()
   {
      return deck.dealCard();
   }

   /**
    * gets number of cards remaining in the Deck
    * @return number of cards remaining in deck
    */
   public int getNumCardsRemainingInDeck()
   {
      return deck.getNumCards();
   }

   /**
    * Starts a new game
    */
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
         deck.removeCard(unusedCardsPerPack[k]);

      // add jokers
      for (k = 0; k < numPacks; k++)
         for (j = 0; j < numJokersPerPack; j++)
            deck.addCard(new Card('X', Card.Suit.values()[j]));

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
      for (k = 0; k < numCardsPerHand && enoughCards; k++)
      {
         for (j = 0; j < numPlayers; j++)
            if (deck.getNumCards() > 0)
               hand[j].takeCard(deck.dealCard());
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
      if (playerIndex < 0 || playerIndex > numPlayers - 1 || cardIndex < 0 || cardIndex > numCardsPerHand - 1)
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
}

/**
 * CardTable class extends JFrame This class controls the positioning of the
 * panels and cards of the GUI
 */
class GameTable extends JFrame
{
   private static String[] pnlTitles = {
         "Suit Match", "Computer Hand", "Player Hand", "Playing Area"};
   private JButton playBtn, resetBtn, endBtn;

   static int MAX_CARDS_PER_HAND = 56;
   static int MAX_PLAYERS = 2;
   static int NUM_CARDS_PER_HAND = 7;
   static int NUM_PLAYERS = 2;
   static JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND];
   static JLabel[] humanLabels = new JLabel[NUM_CARDS_PER_HAND];
   static JLabel[] playedCardLabels = new JLabel[NUM_PLAYERS];
   static JLabel[] playLabelText = new JLabel[NUM_PLAYERS];
   public JPanel pnlComputerHand, pnlHumanHand, pnlPlayArea;
   private int numCardsPerHand, numPlayers;

   /**
    * filters input, adds panels to the JFrame establishes layouts according to
    * the general description
    *
    * @param title
    * @param numCardsPerHand
    * @param numPlayers
    */
   public GameTable(String title, int numCardsPerHand, int numPlayers)
   {
      super(title);
      Border border = BorderFactory.createLineBorder(Color.black);
      setLayout(new BorderLayout());

      pnlComputerHand = new JPanel();
      pnlComputerHand.setBorder(border);
      add(pnlComputerHand, BorderLayout.NORTH);
      JLabel computerHand = new JLabel("Computer Hand", SwingConstants.CENTER);

      pnlComputerHand.setLayout(new BorderLayout());
      pnlComputerHand.add(computerHand, BorderLayout.NORTH);
      pnlComputerHand.setBorder(border);

      pnlPlayArea = new JPanel();
      pnlPlayArea.setBorder(border);
      add(pnlPlayArea, BorderLayout.CENTER);
      JLabel playingArea = new JLabel("Playing Area", SwingConstants.CENTER);

      pnlPlayArea.setLayout(new BorderLayout());
      pnlPlayArea.add(playingArea, BorderLayout.NORTH);

      pnlHumanHand = new JPanel();
      pnlHumanHand.setBorder(border);
      add(pnlHumanHand, BorderLayout.SOUTH);
      JLabel yourHand = new JLabel("Your Hand", SwingConstants.CENTER);

      pnlHumanHand.setLayout(new BorderLayout());
      pnlHumanHand.add(yourHand, BorderLayout.NORTH);

      if (numCardsPerHand > GameTable.MAX_CARDS_PER_HAND && numPlayers > CardTable.MAX_PLAYERS)
      {
         this.numCardsPerHand = GameTable.MAX_CARDS_PER_HAND;
         this.numPlayers = GameTable.MAX_PLAYERS;
      }
      else if (numCardsPerHand > GameTable.MAX_CARDS_PER_HAND && numPlayers <= CardTable.MAX_PLAYERS)
      {
         this.numCardsPerHand = GameTable.MAX_CARDS_PER_HAND;
         this.numPlayers = numPlayers;
      }
      else if (numCardsPerHand <= GameTable.MAX_CARDS_PER_HAND && numPlayers > CardTable.MAX_PLAYERS)
      {
         this.numCardsPerHand = numCardsPerHand;
         this.numPlayers = GameTable.MAX_PLAYERS;
      }
      else
      {
         this.numCardsPerHand = numCardsPerHand;
         this.numPlayers = numPlayers;
      }
   }

   public static void main(String[] args)
   {
      GUICard.loadCardIcons();

      int numPacksPerDeck = 1;
      int numJokersPerPack = 2;
      int numUnusedCardsPerPack = 0;
      Card[] unusedCardsPerPack = null;

      CardGameOutline SuitMatchGame = new CardGameOutline(numPacksPerDeck,
            numJokersPerPack, numUnusedCardsPerPack, unusedCardsPerPack,
            NUM_PLAYERS, NUM_CARDS_PER_HAND);

      SuitMatchGame.deal();

      // establish main frame in which program will run
      GameTable myCardTable = new GameTable("GameTable", NUM_CARDS_PER_HAND,
            NUM_PLAYERS);
      myCardTable.setSize(800, 600);
      myCardTable.setLocationRelativeTo(null);
      myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // show everything to the user
      myCardTable.setVisible(true);

      // CREATE LABELS
      Deck deck = new Deck();

      for (int k = 0; k < NUM_CARDS_PER_HAND; k++)
      {
         computerLabels[k] = new JLabel(GUICardTable.getBackCardIcon());
         humanLabels[k] = new JLabel(GUICardTable.getIcon(deck.inspectCard(k)));
      }

      // ADD LABELS TO PANELS
      JPanel humanHand = new JPanel();
      humanHand.setLayout(new GridLayout(1, 7));

      cardGame gamePlay = new cardGame();

      //First Card
      JButton firstCard = new JButton();
      firstCard.add(humanLabels[0]);
      firstCard.setActionCommand("First Card");
      firstCard.addActionListener(gamePlay);
      humanHand.add(firstCard);

      //Second Card
      JButton secondCard = new JButton();
      secondCard.add(humanLabels[1]);
      secondCard.setActionCommand("Second Card");
      secondCard.addActionListener(gamePlay);
      humanHand.add(secondCard);

      //Third Card
      JButton thirdCard = new JButton();
      thirdCard.add(humanLabels[2]);
      thirdCard.setActionCommand("Third Card");
      thirdCard.addActionListener(gamePlay);
      humanHand.add(thirdCard);

      //Fourth Card
      JButton fourthCard = new JButton();
      fourthCard.add(humanLabels[3]);
      fourthCard.setActionCommand("Fourth Card");
      fourthCard.addActionListener(gamePlay);
      humanHand.add(fourthCard);

      //Fifth Card
      JButton fifthCard = new JButton();
      fifthCard.add(humanLabels[4]);
      fifthCard.setActionCommand("Fifth Card");
      fifthCard.addActionListener(gamePlay);
      humanHand.add(fifthCard);

      //Sixth Card
      JButton sixthCard = new JButton();
      sixthCard.add(humanLabels[5]);
      sixthCard.setActionCommand("Sixth Card");
      sixthCard.addActionListener(gamePlay);
      humanHand.add(sixthCard);

      //Seventh Card
      JButton seventhCard = new JButton();
      seventhCard.add(humanLabels[6]);
      seventhCard.setActionCommand("Seventh Card");
      seventhCard.addActionListener(gamePlay);
      humanHand.add(seventhCard);

      myCardTable.pnlHumanHand.add(humanHand);

      JPanel computerHand = new JPanel();
      for (int k = 0; k < NUM_CARDS_PER_HAND; k++)
      {
         computerHand.add(computerLabels[k]);
      }

      myCardTable.pnlComputerHand.add(computerHand);

      // and two random cards in the play region (simulating a computer/hum ply)
      for (int k = 0; k < NUM_PLAYERS; k++)
      {
         playedCardLabels[k] = new JLabel(
               GUICardTable.getIcon(randomCardGenerator()));
      }

      playLabelText[0] = new JLabel("Computer", JLabel.CENTER);
      playLabelText[1] = new JLabel("You", JLabel.CENTER);

      JPanel playHand = new JPanel();
      playHand.setLayout(new GridLayout(2, 2));

      for (int k = 0; k < NUM_PLAYERS; k++)
      {
         playHand.add(playedCardLabels[k]);
      }

      playHand.add(playLabelText[0]);
      playHand.add(playLabelText[1]);

      myCardTable.pnlPlayArea.add(playHand, BorderLayout.CENTER);

      //Creating JButtons

      //play button
      JButton playBtn = new JButton("Play Game");

      playBtn.setActionCommand("Play Game");
      playBtn.addActionListener(gamePlay);
      myCardTable.pnlPlayArea.add(playBtn, BorderLayout.WEST);

      //reset  & end button
      JPanel leftOptions = new JPanel();
      leftOptions.setLayout(new GridLayout(2, 1));

      JButton resetBtn = new JButton("Reset Game");
      resetBtn.setActionCommand("Reset Game");
      resetBtn.addActionListener(gamePlay);

      JButton endBtn = new JButton("End Game");
      endBtn.setActionCommand("End Game");
      endBtn.addActionListener(gamePlay);

      leftOptions.add(resetBtn);
      leftOptions.add(endBtn);

      myCardTable.pnlPlayArea.add(leftOptions, BorderLayout.EAST);

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

/**
 * GUICard class Manages the reading and building of the card image Icons This
 * class is the benefactor of most of the GUI machinery we tested in Phase 1
 * Reads image files and stores them in a static Icon array
 */
class GUICardTable
{
   static final int NUM_CARD_IMAGES = 57;
   static boolean iconsLoaded = false;
   // 14 = A thru K + joker
   private static Icon[][] iconCards = new ImageIcon[14][4];
   private static Icon iconBack;

   /**
    * creates a string for the file name of each card
    *
    * @return Icons in a 2-D array
    */
   public static void loadCardIcons()
   {
      String dirPrefix = "images/";
      String ext = ".gif";
      for (int val = 0; val < 14; val++)
      {
         for (int suit = 0; suit < 4; suit++)
         {
            String cardVal = intToCardValue(val);
            String cardSuit = intToCardSuit(suit);
            String fileName = dirPrefix + cardVal + cardSuit + ext;
            iconCards[val][suit] = new ImageIcon(fileName);

         }
      }
      iconBack = new ImageIcon("images/BK.gif");
      iconsLoaded = true;
   }

   /**
    * helper method that returns corresponding card value
    *
    * @param k index of card value in array
    * @return card value
    */
   static String intToCardValue(int k)
   {
      char[] cardVal = { 'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J',
            'Q', 'K' };

      if (k < 0 || k > cardVal.length - 1)
      {
         return "invalid value";
      }
      else
      {
         return Character.toString(cardVal[k]);
      }

   }

   /**
    * helper method that returns corresponding card suit
    *
    * @param k index of suit in array
    * @return suit value
    */
   static String intToCardSuit(int k)
   {
      char[] suitVal = { 'C', 'D', 'H', 'S' };

      if (k < 0 || k > suitVal.length)
      {
         return "invalid value";
      }
      else
      {
         return Character.toString(suitVal[k]);
      }
   }

   /**
    * helper method to convert value to int
    *
    * @param card that is being used
    * @return value of this card as int
    */
   private static int valueAsInt(Card card)
   {
      char cardsValue = card.getValue();

      if (cardsValue == 'A')
         return 0;
      if (cardsValue == 'X')
         return 13;
      for (int k = 0; k <= 11; k++)
      {
         if (Card.valueRanks[k] == cardsValue)
            return k + 1;
      }
      return 0;
   }

   /**
    * helper method to convert suit to int
    *
    * @param card that is being used
    * @return suit of this card as int
    */
   private static int suitAsInt(Card card)
   {
      Card.Suit suit = card.getSuit();
      return switch (suit)
            {
               case clubs -> 0;
               case diamonds -> 1;
               case hearts -> 2;
               case spades -> 3;
            };
   }

   /**
    * gets a 2D array of card value and suit as an int
    *
    * @return value and suit
    */
   public static Icon getIcon(Card card)
   {
      if (!iconsLoaded)
      {
         loadCardIcons();
      }
      return iconCards[valueAsInt(card)][suitAsInt(card)];
   }

   /**
    * @return back of card Icon
    */
   public static Icon getBackCardIcon()
   {
      return iconBack;
   }
}

/**
 * create action listeners for suit match game you and the computer each play a
 * card if the 2nd player can match the suit, they take both cards else 1st
 * player gets the cards
 *
 * @return cards in a winnings[] array, not hand
 */
class cardGame implements ActionListener
{
   @Override public void actionPerformed(ActionEvent e)
   {
      String actionCommand = e.getActionCommand();

      if (actionCommand.equals("End Game"))
      {
         System.exit(0);
      }

   }

}


/**
 * Class that models game structure
 */
class GameStructure
{
   private CardGameOutline suitMatch;
   private int cpuPlayer, humanPlayer;
   private int cpuScore, humanScore;
   private String cpuName, humanName;
   private int playerOne;
   private int cardsLeft;
   private Card cpuCardPlayed, humanCardPlayed;

   public GameStructure()
   {
      this.suitMatch = null;
      cpuName = "";
      cpuPlayer = 0;
      cpuScore = 0;
      humanName = "";
      humanPlayer = 0;
      humanScore = 0;
      cardsLeft = 0;
   }
   public GameStructure(CardGameOutline suitMatch, String cpuName,
         String humanName)
   {
      this.suitMatch = suitMatch;
      this.cpuName = cpuName;
      cpuPlayer = 0;
      cpuScore = 0;
      this.humanName = humanName;
      humanPlayer = 1;
      humanScore = 0;
      cardsLeft = suitMatch.getNumCardsPerHand();
   }

   /**
    * resets game
    */
   public void resetGame()
   {
      suitMatch.newGame();
      cpuScore = 0;
      humanScore = 0;
      cardsLeft = suitMatch.getNumCardsPerHand();
   }

   /**
    * deals out cards
    */
   public void dealCards()
   {
      suitMatch.deal();
      suitMatch.sortHands();
   }

   /**
    * gets value of cards remaining
    * @return value of cardsLefts
    */
   public int getCardsLeft()
   {
      return cardsLeft;
   }
   /**
    *
    */
   public boolean setCardsLeft()
   {
      if(cardsLeft - 1 < 0)
      {
         return false;
      }
      cardsLeft--;
      return true;
   }
   public Hand getCpuHand()
   {
      return suitMatch.getHand(cpuPlayer);
   }
   public int getCpuScore()
   {
      return cpuScore;
   }
   public boolean setCpuScore()
   {
      cpuScore++;
      return true;
   }
   public Hand getHumanHand()
   {
      return suitMatch.getHand(humanPlayer);
   }
   public boolean setHumanScore()
   {
      humanScore++;
      return true;
   }
   public boolean setPlayerOne(int playerIndex)
   {
      playerOne = playerIndex;
      return true;
   }

   /**
    *
    * @return
    */
   public int getPlayerOne()
   {
      return playerOne;
   }

   /**
    * gets cpu name
    * @return cpu name
    */
   public String getCpuName()
   {
      return cpuName;
   }

   /**
    * gets human player name
    * @return human player name
    */
   public String getHumanName()
   {
      return humanName;
   }

   /**
    * card played by human
    * @return card played by human
    */
   public Card getHumanCardPlayed()
   {
      return humanCardPlayed;
   }

   /**
    * sets card that was played by human
    * @param card played
    * @return false if null, true if proper card
    */
   public boolean setHumanCardPlayed(Card card)
   {
      if(card == null)
         return false;
      humanCardPlayed = new Card(card.getValue(), card.getSuit());
      return true;
   }

   /**
    * gets card that was played by cpu
    * @return card played by  cpu
    */
   public Card getCpuCardPlayed()
   {
      return cpuCardPlayed;
   }

   /**
    * sets cpu card that was played
    * @param card that is played by cpu
    * @return true if proper card, false if null
    */
   public boolean setCpuCardPlayed(Card card)
   {
      if(card == null)
      {
         return false;
      }
      cpuCardPlayed =  new Card(card.getValue(), card.getSuit());
      return true;
   }


}
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
   /**
    *
    */
   private static final long serialVersionUID = 1L;
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
   public JPanel pnlComputerHand, pnlHumanHand, pnlPlayArea, leftOptions = new JPanel(), addCardButtons = new JPanel();
   private int numCardsPerHand, numPlayers;

   public JPanel computerPlayCard = new JPanel(), humanPlayCard = new JPanel();
   public JLabel pickCardMessage= new JLabel("Pick a card from your hand!", SwingConstants.CENTER);
   public JLabel computerWins= new JLabel("Computer Wins!", SwingConstants.CENTER);
   public JLabel humanWins= new JLabel("Human Wins!", SwingConstants.CENTER);

   SuitMatchGame actionListener = new SuitMatchGame();

   CardGameOutline SuitMatchGame;

   static JButton button1 = new JButton();
   static JButton button2 = new JButton();
   static JButton button3 = new JButton();
   static JButton button4 = new JButton();
   static JButton button5 = new JButton();
   static JButton button6 = new JButton();
   static JButton button7 = new JButton();

   static JButton resetGame = new JButton("Reset");
   static JButton exitGame = new JButton("Exit");


   Border border = BorderFactory.createLineBorder(Color.black);

   int numPacksPerDeck = 1;
   int numJokersPerPack = 2;
   int numUnusedCardsPerPack = 0;
   Card[] unusedCardsPerPack = null;

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

      SuitMatchGame = new CardGameOutline(numPacksPerDeck,
            numJokersPerPack, numUnusedCardsPerPack, unusedCardsPerPack,
            NUM_PLAYERS, NUM_CARDS_PER_HAND);

      SuitMatchGame.deal();
      setLayout(new BorderLayout());

      createLabels();


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
   /*
    * Method sets buttons on JFrame
    */
   public void setButtons()
   {
      //Setting up buttons for JLabels humanLabels
      button1.add(humanLabels[0]);
      button1.setActionCommand("1");
      button1.addActionListener(actionListener);
      addCardButtons.add(button1);

      button2.add(humanLabels[1]);
      button2.setActionCommand("2");
      button2.addActionListener(actionListener);
      addCardButtons.add(button2);

      button3.add(humanLabels[2]);
      button3.setActionCommand("3");
      button3.addActionListener(actionListener);
      addCardButtons.add(button3);

      button4.add(humanLabels[3]);
      button4.setActionCommand("4");
      button4.addActionListener(actionListener);
      addCardButtons.add(button4);

      button5.add(humanLabels[4]);
      button5.setActionCommand("5");
      button5.addActionListener(actionListener);
      addCardButtons.add(button5);

      button6.add(humanLabels[5]);
      button6.setActionCommand("6");
      button6.addActionListener(actionListener);
      addCardButtons.add(button6);

      button7.add(humanLabels[6]);
      button7.setActionCommand("7");
      button7.addActionListener(actionListener);
      addCardButtons.add(button7);

      resetGame.setActionCommand("Reset");
      resetGame.addActionListener(actionListener);
      leftOptions.add(resetGame);

      exitGame.setActionCommand("Exit");
      exitGame.addActionListener(actionListener);
      leftOptions.add(exitGame);

   }

   public void setPanels()
   {

      pnlComputerHand = new JPanel();
      pnlComputerHand.setBorder(border);
      add(pnlComputerHand, BorderLayout.NORTH);

      //Adding label on top panel
      JLabel computerHand = new JLabel("Computer Hand", SwingConstants.CENTER);
      pnlComputerHand.setLayout(new BorderLayout());
      pnlComputerHand.add(computerHand, BorderLayout.NORTH);
      pnlComputerHand.setBorder(border);

      pnlPlayArea = new JPanel();
      pnlPlayArea.setBorder(border);
      add(pnlPlayArea, BorderLayout.CENTER);

      //Adding label on middle panel
      JLabel playingArea = new JLabel("Playing Area", SwingConstants.CENTER);
      pnlPlayArea.setLayout(new BorderLayout());
      pnlPlayArea.add(playingArea, BorderLayout.NORTH);

      pnlHumanHand = new JPanel();
      pnlHumanHand.setBorder(border);
      add(pnlHumanHand, BorderLayout.SOUTH);

      //Adding label on bottom panel
      JLabel yourHand = new JLabel("Your Hand", SwingConstants.CENTER);
      pnlHumanHand.setLayout(new BorderLayout());
      pnlHumanHand.add(yourHand, BorderLayout.NORTH);


      leftOptions.setLayout(new GridLayout(2,1));
      add(leftOptions,BorderLayout.EAST);

      addCardButtons.setLayout(new GridLayout(1,7));
      pnlHumanHand.add(addCardButtons, BorderLayout.SOUTH);

      //Message to pick a card
      pnlPlayArea.add(pickCardMessage, BorderLayout.CENTER);


   }
   public void createLabels()
   {
      setPanels();

      // CREATE LABELS
      Deck deck = new Deck();
      for (int k = 0; k < NUM_CARDS_PER_HAND; k++)
      {
         computerLabels[k] = new JLabel(GUICard.getBackCardIcon());
         humanLabels[k] = new JLabel(GUICard.getIcon(deck.inspectCard(k)));
      }

      JPanel computerHand = new JPanel();
      for (int k = 0; k < NUM_CARDS_PER_HAND; k++)
      {
         computerHand.add(computerLabels[k]);
      }

      pnlComputerHand.add(computerHand);

      setButtons();
   }
   public void callCardtoPlayArea(Card humanCard, Card computerHand)
   {
      JPanel playHand = new JPanel();
      playHand.setLayout(new GridLayout(2,2));


      playedCardLabels[0] = new JLabel(GUICard.getIcon(computerHand));
      computerPlayCard.add(playedCardLabels[0]);

      playedCardLabels[1] = new JLabel(GUICard.getIcon(humanCard));
      humanPlayCard.add(playedCardLabels[1]);


      playLabelText[0] = new JLabel("Computer", JLabel.CENTER);
      playLabelText[1] = new JLabel("You", JLabel.CENTER);


      playHand.add(computerPlayCard);
      playHand.add(humanPlayCard);

      playHand.add(playLabelText[0]);
      playHand.add(playLabelText[1]);

      pnlPlayArea.remove(pickCardMessage);
      pnlPlayArea.add(playHand, BorderLayout.CENTER);

      System.out.println("Computer: " + computerHand.getSuit());
      System.out.println("Human: " + humanCard.getSuit());

      System.out.println("Computer: " + computerHand.getValue());
      System.out.println("Human: " + humanCard.getValue());

      if(humanCard.getSuit() == computerHand.getSuit())
      {
         pnlPlayArea.add(computerWins, BorderLayout.SOUTH);
         pnlPlayArea.revalidate();
         pnlPlayArea.repaint();

      }
      else
      {
         pnlPlayArea.add(humanWins, BorderLayout.SOUTH);
         pnlPlayArea.revalidate();
         pnlPlayArea.repaint();

      }
   }

   public static void main(String[] args)
   {
      GUICard.loadCardIcons();

      // establish main frame in which program will run
      GameTable myCardTable
         = new GameTable("CardTable", NUM_CARDS_PER_HAND, NUM_PLAYERS);
      myCardTable.setSize(800, 600);
      myCardTable.setLocationRelativeTo(null);
      myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // show everything to the user
      myCardTable.setVisible(true);
   }

   public class SuitMatchGame implements ActionListener
   {
      @Override
      public void actionPerformed(ActionEvent e)
      {
         String actionCommand = e.getActionCommand();

         if(actionCommand.equals("1"))
         {
            Random ran = new Random();
            callCardtoPlayArea(SuitMatchGame.playCard(1, 0), SuitMatchGame.playCard(0, ran.nextInt(NUM_CARDS_PER_HAND)));

            humanPlayCard.removeAll();
            humanPlayCard.add(humanLabels[0]);
            humanPlayCard.revalidate();
            humanPlayCard.repaint();

            if(SuitMatchGame.getNumCardsRemainingInDeck() != 0)
            {
               //Checks to see if there are still cards on the deck.
               humanLabels[0] = new JLabel(GUICard.getIcon(SuitMatchGame.getCardFromDeck()));
               button1.add(humanLabels[0]);
               button1.revalidate();
               button1.repaint();
            }
            else
            {
               //game should end winner should be displayed.
            }


         }
         else if(actionCommand.equals("2"))
         {
            humanPlayCard.removeAll();
            humanPlayCard.add(humanLabels[1]);
            humanPlayCard.revalidate();
            humanPlayCard.repaint();

            if(SuitMatchGame.getNumCardsRemainingInDeck() != 0)
            {
               //Checks to see if there are still cards on the deck.
               humanLabels[1] = new JLabel(GUICard.getIcon(SuitMatchGame.getCardFromDeck()));
               button2.add(humanLabels[1]);
               button2.revalidate();
               button2.repaint();
            }
            else
            {
               //game should end winner should be displayed.
            }

         }
         else if(actionCommand.equals("3"))
         {
            humanPlayCard.removeAll();
            humanPlayCard.add(humanLabels[2]);
            humanPlayCard.revalidate();
            humanPlayCard.repaint();

            if(SuitMatchGame.getNumCardsRemainingInDeck() != 0)
            {
               //Checks to see if there are still cards on the deck.
               humanLabels[2] = new JLabel(GUICard.getIcon(SuitMatchGame.getCardFromDeck()));
               button3.add(humanLabels[2]);
               button3.revalidate();
               button3.repaint();
            }
            else
            {
               //game should end winner should be displayed.
            }

         }
         else if(actionCommand.equals("4"))
         {
            humanPlayCard.removeAll();
            humanPlayCard.add(humanLabels[3]);
            humanPlayCard.revalidate();
            humanPlayCard.repaint();

            if(SuitMatchGame.getNumCardsRemainingInDeck() != 0)
            {
               //Checks to see if there are still cards on the deck.
               humanLabels[3] = new JLabel(GUICard.getIcon(SuitMatchGame.getCardFromDeck()));
               button4.add(humanLabels[3]);
               button4.revalidate();
               button4.repaint();
            }
            else
            {
               //game should end winner should be displayed.
            }

         }
         else if(actionCommand.equals("5"))
         {
            humanPlayCard.removeAll();
            humanPlayCard.add(humanLabels[4]);
            humanPlayCard.revalidate();
            humanPlayCard.repaint();

            if(SuitMatchGame.getNumCardsRemainingInDeck() != 0)
            {
               //Checks to see if there are still cards on the deck.
               humanLabels[4] = new JLabel(GUICard.getIcon(SuitMatchGame.getCardFromDeck()));
               button5.add(humanLabels[4]);
               button5.revalidate();
               button5.repaint();
            }
            else
            {
               //game should end winner should be displayed.
            }

         }
         else if(actionCommand.equals("6"))
         {
            humanPlayCard.removeAll();
            humanPlayCard.add(humanLabels[5]);
            humanPlayCard.revalidate();
            humanPlayCard.repaint();

            if(SuitMatchGame.getNumCardsRemainingInDeck() != 0)
            {
               //Checks to see if there are still cards on the deck.
               humanLabels[5] = new JLabel(GUICard.getIcon(SuitMatchGame.getCardFromDeck()));
               button6.add(humanLabels[5]);
               button6.revalidate();
               button6.repaint();
            }
            else
            {
               //game should end winner should be displayed.
            }

         }
         else if(actionCommand.equals("7"))
         {
            humanPlayCard.removeAll();
            humanPlayCard.add(humanLabels[6]);
            humanPlayCard.revalidate();
            humanPlayCard.repaint();

            if(SuitMatchGame.getNumCardsRemainingInDeck() != 0)
            {
               //Checks to see if there are still cards on the deck.
               humanLabels[6] = new JLabel(GUICard.getIcon(SuitMatchGame.getCardFromDeck()));
               button7.add(humanLabels[6]);
               button7.revalidate();
               button7.repaint();
            }
            else
            {
               //game should end winner should be displayed.
            }

         }
         else if(actionCommand.equals("Reset"))
         {

         }
         else if(actionCommand.equals("Exit"))
         {
            System.exit(0);
         }

      }
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

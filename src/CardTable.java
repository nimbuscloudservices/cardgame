import java.awt.*;
import java.util.Locale;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.*;

/**
 * CardTable class extends JFrame
 * This class controls the positioning of the panels and cards of the GUI
 */
public class CardTable extends JFrame
{
   static int MAX_CARDS_PER_HAND = 56;
   static int MAX_PLAYERS = 2;
   private int numCardsPerHand;
   private int numPlayers;
   public JPanel pnlComputerHand = new JPanel();
   public JPanel pnlHumanHand = new JPanel();
   public JPanel pnlPlayArea = new JPanel();


   /**
    * filters input, adds panels to the JFrame
    * establishes layouts according to the general description
    * @param title
    * @param numCardsPerHand
    * @param numPlayers
    */
   public CardTable(String title, int numCardsPerHand, int numPlayers)
   {
     super(title);
     Border border = BorderFactory.createLineBorder(Color.black);
     setLayout(new BorderLayout());

     pnlComputerHand = new JPanel();
     pnlComputerHand.setBorder(border);
     add(pnlComputerHand, BorderLayout.NORTH);
     JLabel computerHand = new JLabel( "Computer Hand",
           SwingConstants.CENTER);
     computerHand.setVerticalTextPosition(JLabel.TOP);
     computerHand.setHorizontalTextPosition(JLabel.LEFT);
     pnlComputerHand.setLayout(new BorderLayout());
     pnlComputerHand.add(computerHand, BorderLayout.NORTH);
     pnlComputerHand.setBorder(border);

     pnlPlayArea = new JPanel();
     pnlPlayArea.setBorder(border);
     add(pnlPlayArea, BorderLayout.CENTER);
     JLabel playingArea = new JLabel( "Playing Area", SwingConstants.CENTER);
     playingArea.setVerticalTextPosition(JLabel.TOP);
     playingArea.setHorizontalTextPosition(JLabel.LEFT);
     pnlPlayArea.setLayout(new BorderLayout());
     pnlPlayArea.add(playingArea, BorderLayout.NORTH);


     pnlHumanHand = new JPanel();
     pnlHumanHand.setBorder(border);
     add(pnlHumanHand, BorderLayout.SOUTH);
     JLabel yourHand = new JLabel( "Your Hand", SwingConstants.CENTER);
     yourHand.setVerticalTextPosition(JLabel.TOP);
     yourHand.setHorizontalTextPosition(JLabel.LEFT);
     pnlHumanHand.setLayout(new BorderLayout());
     pnlHumanHand.add(yourHand, BorderLayout.NORTH);

      if(numCardsPerHand > CardTable.MAX_CARDS_PER_HAND && numPlayers > CardTable.MAX_PLAYERS)
      {
         this.numCardsPerHand = CardTable.MAX_CARDS_PER_HAND;
         this.numPlayers = CardTable.MAX_PLAYERS;
      }
      else if(numCardsPerHand > CardTable.MAX_CARDS_PER_HAND && numPlayers <= CardTable.MAX_PLAYERS)
      {
         this.numCardsPerHand = CardTable.MAX_CARDS_PER_HAND;
         this.numPlayers = numPlayers;
      }
      else if(numCardsPerHand <= CardTable.MAX_CARDS_PER_HAND && numPlayers > CardTable.MAX_PLAYERS)
      {
         this.numCardsPerHand = numCardsPerHand;
         this.numPlayers = CardTable.MAX_PLAYERS;
      }
      else
      {
         this.numCardsPerHand = numCardsPerHand;
         this.numPlayers = numPlayers;
      }
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

      // establish main frame in which program will run
      CardTable myCardTable
         = new CardTable("CardTable", NUM_CARDS_PER_HAND, NUM_PLAYERS);
      myCardTable.setSize(800, 600);
      myCardTable.setLocationRelativeTo(null);
      myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // show everything to the user
      myCardTable.setVisible(true);

      // CREATE LABELS
      for (k = 0; k < NUM_CARDS_PER_HAND; k++)
      {
         computerLabels[k] = new JLabel(GUICard.getBackCardIcon());
         humanLabels[k] = new JLabel(GUICard.getIcon(randomCardGenerator()));
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

/**
 * GUICard class
 * Manages the reading and building of the card image Icons
 * This class is the benefactor of most of the GUI machinery we tested in Phase 1
 * Reads image files and stores them in a static Icon array
 */
class GUICard
{
   // 14 = A thru K + joker
   private static Icon[][] iconCards = new ImageIcon[14][4];
   static final int NUM_CARD_IMAGES = 57;
   private static Icon iconBack;
   static boolean iconsLoaded = false;

   /**
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

   static String intToCardValue(int k)
   {
      char[] cardVal = { 'A', '2', '3', '4', '5', '6', '7', '8',
              '9', 'T', 'J', 'Q', 'K'};

      if (k < 0 || k > cardVal.length - 1)
      {
         return "invalid value";
      }
      else
      {
         return Character.toString(cardVal[k]);
      }

   }

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

   private static int valueAsInt(Card card)
   {
      char cardsValue = card.getValue();

      if(cardsValue == 'A')
         return 0;
      if(cardsValue == 'X')
         return 13;
      for(int k = 0; k <= 11; k++)
      {
         if(Card.valueRanks[k] == cardsValue)
            return k + 1;
      }
      return 0;
   }
   /**
    * helper method to convert suit to int
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
    * @param card
    * @return Icon
    */
   public static Icon getIcon(Card card)
   {
      if(!iconsLoaded)
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

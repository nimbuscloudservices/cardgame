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
   public JPanel pnlComputerHand, pnlHumanHand, pnlPlayArea;

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
      setLayout(new BorderLayout(1, 3));
      JLabel pnlComputerHand = new JLabel( "Computer Hand");
      add(pnlComputerHand, BorderLayout.NORTH);

      JLabel pnlPlayArea = new JLabel( "Playing Area");
      add(pnlPlayArea, BorderLayout.CENTER);

      JLabel pnlHumanHand = new JLabel( "Your Hand");
      add(pnlHumanHand, BorderLayout.SOUTH);

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
         tempIcon = GUICard.getIcon(generateRandomCard());
         humanLabels[k] = new JLabel(tempIcon);
      }

      // ADD LABELS TO PANELS
      for (k = 0; k < NUM_CARDS_PER_HAND; k++)
      {
         myCardTable.pnlComputerHand.add(computerLabels[k]);
         myCardTable.pnlComputerHand.add(humanLabels[k]);
      }

      // and two random cards in the play region (simulating a computer/hum ply)
      for (k = 0; k < NUM_PLAYERS; k++)
      {
         tempIcon = GUICard.getIcon(generateRandomCard());
         playedCardLabels[k] = new JLabel(tempIcon);
         myCardTable.pnlPlayArea.add(playedCardLabels[k]);
      }

      // show everything to the user
      myCardTable.setVisible(true);
   }

   public static Card generateRandomCard()
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
   private static Icon iconBack;
   static boolean iconsLoaded = false;

   /**
    * @return Icons in a 2-D array
    */
   public static void loadCardIcons()
   {
      if (iconsLoaded)
         return;
      for(int value = 0; value < iconCards.length; value++)
      {
         for(int suit = 0; suit < iconCards[value].length; suit++)
         {
            String filename = numCard(value) + numSuit(suit) + ".gif";
            ImageIcon cardImage = new ImageIcon("images/" + filename);
            iconCards[value][suit] = cardImage;
         }
      }

      iconBack = new ImageIcon("/images/BK.gif");
      iconsLoaded = true;
   }

   static String numCard(int cardNum)
   {
      String[] values = {"A", "2", "3", "4", "5", "6",
              "7", "8", "9", "T", "J", "Q", "K", "X"};
      return values[cardNum];
   }

   static String numSuit(int suitNum)
   {
      if(suitNum < 0 || suitNum > 3)
         return "Invalid";
      return Card.Suit.values()[suitNum].toString().toUpperCase().substring(0,1);
   }

   private static int valueAsInt(Card card)
   {
      return Card.cardValue(card);
   }

   private static int suitAsInt(Card card)
   {
      Card.Suit cardSuit = card.getSuit();

      switch (cardSuit)
      {
         case spades -> {
            return 0;
         }
         case hearts -> {
            return 1;
         }
         case diamonds -> {
            return 2;
         }
         case clubs -> {
            return 3;
         }
         default -> {
            return - 1;
         }
      }
   }

   /**
    * @param card
    * @return Icon
    */
   public static Icon getIcon(Card card)
   {
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


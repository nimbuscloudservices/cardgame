package view;

import javax.swing.*;
import javax.swing.border.Border;

import controller.AppController;
import model.*;

import java.awt.*;
import java.util.Random;

public class CardTable extends JFrame {

   private CardTablePanel basePanel;

   public CardTable(AppController baseController)
   {
      basePanel = new CardTablePanel(baseController);
      setupFrame();
   }

   private void setupFrame()
   {
      this.setContentPane(basePanel);
      this.setSize(800,600);
      this.setVisible(true);
   }

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
      setSize(1150, 650);
      setLocationRelativeTo(null);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      Border border = BorderFactory.createLineBorder(Color.black);
      setLayout(new BorderLayout());

      pnlComputerHand = new JPanel();
      pnlComputerHand.setBorder(border);
      add(pnlComputerHand, BorderLayout.NORTH);
      JLabel computerHand = new JLabel( "Computer Hand",
              SwingConstants.CENTER);

      pnlComputerHand.setLayout(new BorderLayout());
      pnlComputerHand.add(computerHand, BorderLayout.NORTH);
      pnlComputerHand.setBorder(border);

      pnlPlayArea = new JPanel();
      pnlPlayArea.setBorder(border);
      add(pnlPlayArea, BorderLayout.CENTER);
      JLabel playingArea = new JLabel( "Playing Area", SwingConstants.CENTER);

      pnlPlayArea.setLayout(new BorderLayout());
      pnlPlayArea.add(playingArea, BorderLayout.NORTH);


      pnlHumanHand = new JPanel();
      pnlHumanHand.setBorder(border);
      add(pnlHumanHand, BorderLayout.SOUTH);
      JLabel yourHand = new JLabel( "Your Hand", SwingConstants.CENTER);

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

   /**
    * Accessor for numCardsPerHand
    */
   public int getnumCardsPerHand()
   {
      return this.numCardsPerHand;
   }

   /**
    * Accessor for numPlayers
    */
   public int getnumPlayers()
   {
      return this.numPlayers;
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


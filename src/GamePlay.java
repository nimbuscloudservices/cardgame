import javax.swing.*;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

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




class GamePlay extends CardTable implements ActionListener
{
   static int NUM_CARDS_PER_HAND = 7;
   static int  NUM_PLAYERS = 2;
   static JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND];
   static JLabel[] humanLabels = new JLabel[NUM_CARDS_PER_HAND];
   static JLabel[] playedCardLabels  = new JLabel[NUM_PLAYERS];
   static JLabel[] playLabelText  = new JLabel[NUM_PLAYERS];

   //Keep track of how many cards are left in each hand
   int cardsInComputerHand, cardsInHumanHand;

   //Store cards that are won after each round
   Hand[] computerWinnings = new Hand[57];
   Hand[] humanWinnings = new Hand[57];
   static int computerWinningsCounter = 0;
   static int humanWinningsCounter = 0;

   //JButtons
   private JButton button1 = new JButton();
   private JButton button2 = new JButton();
   private JButton button3 = new JButton();
   private JButton button4 = new JButton();
   private JButton button5 = new JButton();
   private JButton button6 = new JButton();
   private JButton button7 = new JButton();
   private JButton exitGame = new JButton("Exit");

   public JPanel addCardButtons = new JPanel();
   public JPanel winner = new JPanel();
   public JPanel computerHand = new JPanel();
   public JLabel computerPlayCard = new JLabel();
   public JLabel humanPlayCard = new JLabel();
   public JPanel playHand = new JPanel();
   public JLabel pickCardMessage= new JLabel("Pick a card from your hand!", SwingConstants.CENTER);
   public JLabel computerWins= new JLabel("Computer Wins!", SwingConstants.CENTER);
   public JLabel humanWins= new JLabel("Human Wins!", SwingConstants.CENTER);

   Border border = BorderFactory.createLineBorder(Color.black);

   CardGameOutline SuitMatchGame;

   /*
    * Constructor of GamePlay calls CardTable, sets cardsInComputerHand , cardsInHumanHand, SuitMatchGame
    * and calls createLabels().
    */
   public GamePlay(String title, int numCardsPerHand, int numPlayers, CardGameOutline a)
   {
      super(title, numCardsPerHand, numPlayers);
      cardsInComputerHand = numCardsPerHand;
      cardsInHumanHand = numCardsPerHand;
      SuitMatchGame = a;
      createLabels();
   }
   /*
    * Method setButtons creates the buttons to be added onto the JFrame
    */
   private void setButtons()
   {
      button1.setIcon(GUICard.getIcon(SuitMatchGame.getHand(1).inspectCard(0)));
      button1.setHorizontalAlignment(JButton.CENTER);
      button1.setActionCommand("1");
      button1.addActionListener(this);
      addCardButtons.add(button1);

      button2.setIcon(GUICard.getIcon(SuitMatchGame.getHand(1).inspectCard(1)));
      button2.setHorizontalAlignment(JButton.CENTER);
      button2.setActionCommand("2");
      button2.addActionListener(this);
      addCardButtons.add(button2);

      button3.setIcon(GUICard.getIcon(SuitMatchGame.getHand(1).inspectCard(2)));
      button3.setHorizontalAlignment(JButton.CENTER);
      button3.setActionCommand("3");
      button3.addActionListener(this);
      addCardButtons.add(button3);

      button4.setIcon(GUICard.getIcon(SuitMatchGame.getHand(1).inspectCard(3)));
      button4.setHorizontalAlignment(JButton.CENTER);
      button4.setActionCommand("4");
      button4.addActionListener(this);
      addCardButtons.add(button4);

      button5.setIcon(GUICard.getIcon(SuitMatchGame.getHand(1).inspectCard(4)));
      button5.setHorizontalAlignment(JButton.CENTER);
      button5.setActionCommand("5");
      button5.addActionListener(this);
      addCardButtons.add(button5);

      button6.setIcon(GUICard.getIcon(SuitMatchGame.getHand(1).inspectCard(5)));
      button6.setHorizontalAlignment(JButton.CENTER);
      button6.setActionCommand("6");
      button6.addActionListener(this);
      addCardButtons.add(button6);

      button7.setIcon(GUICard.getIcon(SuitMatchGame.getHand(1).inspectCard(6)));
      button7.setHorizontalAlignment(JButton.CENTER);
      button7.setActionCommand("7");
      button7.addActionListener(this);
      addCardButtons.add(button7);

      exitGame.setText("Exit");
      exitGame.setHorizontalAlignment(JButton.CENTER);
      exitGame.setActionCommand("End Game");
      exitGame.addActionListener(this);
      add(exitGame, BorderLayout.EAST);
   }
   /*
    * setPanels sets the top panel where computer cards will be displayed, sets bottom panel
    * where the cards of the human will be displayed and sets the middle panel where cards that are
    * played will be displayed.
    */

   private void setPanels()
   {
      playHand.setLayout(new GridLayout(2,2));

      //Adding JPanel pnlComputerHand on top in JFrame
      pnlComputerHand = new JPanel();
      pnlComputerHand.setBorder(border);
      add(pnlComputerHand, BorderLayout.NORTH);

      JLabel computerHand = new JLabel("Computer Hand", SwingConstants.CENTER);
      pnlComputerHand.setLayout(new BorderLayout());
      pnlComputerHand.add(computerHand, BorderLayout.NORTH);
      pnlComputerHand.setBorder(border);

      //Adding JPanel pnlPlayArea on center in JFrame
      pnlPlayArea = new JPanel();
      pnlPlayArea.setBorder(border);
      add(pnlPlayArea, BorderLayout.CENTER);

      JLabel playingArea = new JLabel("Playing Area", SwingConstants.CENTER);
      pnlPlayArea.setLayout(new BorderLayout());
      pnlPlayArea.add(playingArea, BorderLayout.NORTH);

      pnlHumanHand = new JPanel();
      pnlHumanHand.setBorder(border);
      add(pnlHumanHand, BorderLayout.SOUTH);

      //Adding JLabel pnlHumanHand on bottom in JFrame
      JLabel yourHand = new JLabel("Your Hand", SwingConstants.CENTER);
      pnlHumanHand.setLayout(new BorderLayout());
      pnlHumanHand.add(yourHand, BorderLayout.NORTH);

      addCardButtons.setLayout(new GridLayout(1,7));
      pnlHumanHand.add(addCardButtons, BorderLayout.SOUTH);

      //JLabel pickCardMessage added to middle of JFrame
      pnlPlayArea.add(pickCardMessage, BorderLayout.CENTER);

   }
   /*
    * createLabels() calls setPanels() to create the panels on the JFrame.
    * Then after createLabels() creates the labels that will be added to the top panel and bottom panel
    * of the JFrame. setButtons() is called at the end.
    */
   private void createLabels()
   {
      setPanels();

      // CREATE LABELS
      for (int k = 0; k < NUM_CARDS_PER_HAND; k++)
      {
         computerLabels[k] = new JLabel(GUICard.getBackCardIcon());
         humanLabels[k] = new JLabel(GUICard.getIcon(SuitMatchGame.getHand(1).inspectCard(k)));
      }

      for (int k = 0; k < NUM_CARDS_PER_HAND; k++)
      {
         computerHand.add(computerLabels[k]);
      }

      pnlComputerHand.add(computerHand);

      setButtons();
   }
   /*
    * callCardtoPlayAre takes in two cards that were are being played by computer
    * and hand. The winner is determined by an if&else statement. Winner is displayed on JFrame.
    */
   public void callCardtoPlayArea(Card humanCard, Card computerHand)
   {
      pnlPlayArea.remove(humanWins);
      pnlPlayArea.remove(computerWins);

      humanPlayCard.removeAll();
      computerPlayCard.removeAll();

      playHand.removeAll();

      playedCardLabels[0] = new JLabel(GUICard.getIcon(computerHand));
      computerPlayCard.setIcon(GUICard.getIcon(computerHand));
      computerPlayCard.setHorizontalAlignment(JLabel.CENTER);

      playedCardLabels[0] = new JLabel(GUICard.getIcon(humanCard));
      humanPlayCard.setIcon(GUICard.getIcon(humanCard));
      humanPlayCard.setHorizontalAlignment(JLabel.CENTER);

      playLabelText[0] = new JLabel("Computer", JLabel.CENTER);
      playLabelText[1] = new JLabel("You", JLabel.CENTER);

      playHand.add(computerPlayCard);
      playHand.add(humanPlayCard);

      playHand.add(playLabelText[0]);
      playHand.add(playLabelText[1]);

      pnlPlayArea.remove(pickCardMessage);
      pnlPlayArea.add(playHand, BorderLayout.CENTER);

      if(humanCard.getSuit() == computerHand.getSuit())
      {
         pnlPlayArea.add(computerWins, BorderLayout.SOUTH);
         pnlPlayArea.revalidate();
         pnlPlayArea.repaint();
         computerWinnings.takeCard(humanCard);
         computerWinnings.takeCard(computerHand);
         System.out.println("Computer Wins");
      }
      else
      {
         pnlPlayArea.add(humanWins, BorderLayout.SOUTH);
         pnlPlayArea.revalidate();
         pnlPlayArea.repaint();
         computerWinnings.takeCard(humanCard);
         humanWinnings.takeCard(computerHand);
         System.out.println("Human Wins");
      }
   }
   /*
    *updateButton method updates button by adding a new Card to the button as an icon.
    */
   public void updateButton(JButton button, int index)
   {
      if(SuitMatchGame.getNumCardsRemainingInDeck()!=0)
      {

         humanLabels[index] = new JLabel(GUICard.getIcon(SuitMatchGame.getCardFromDeck()));
         button.setIcon(GUICard.getIcon(SuitMatchGame.getCardFromDeck()));
         button.setHorizontalAlignment(JButton.CENTER);
         button.revalidate();
         button.repaint();
      }
      else
      {
         addCardButtons.remove(button);
         addCardButtons.revalidate();
         addCardButtons.repaint();
         cardsInHumanHand--;
         if(cardsInHumanHand==0)
         {
            endGame();
         }
      }

   }
   /*
    * updateComputerCards updates the hand of the computer
    */
   public void updateComputerCards(Card card, int index)
   {
      if(SuitMatchGame.getNumCardsRemainingInDeck()!=0)
      {
         computerLabels[index] = new JLabel(GUICard.getIcon(SuitMatchGame.getCardFromDeck()));
      }
      else
      {
         computerHand.remove(computerLabels[index]);
         computerHand.revalidate();
         computerHand.repaint();
         cardsInComputerHand--;
         if(cardsInComputerHand==0)
         {
            endGame();
         }
      }

   }
   /*
    * endGame determines who the winner is according to who had the most cards.
    * place computerWinnings and humanWinnings in winnings[] array
    */
   public void endGame()
   {
      if(humanWinningsCounter < computerWinningsCounter)
      {
         computerWinnings[computerWinningsCounter] = ;
         computerWinningsCounter += 2;
      }
      else
      {
         humanWinnings[humanWinningsCounter] = ;
         humanWinningsCounter += 2;
      }
   }

   public static void main(String[] args)
   {
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
      GamePlay game = new GamePlay("CardTable", NUM_CARDS_PER_HAND, NUM_PLAYERS, SuitMatchGame);


      game.setVisible(true);
   }

   /*
    * actionPerformed used if/else statements to distinct what button is being pressed.
    */
   @Override
   public void actionPerformed(ActionEvent e)
   {
      String actionCommand = e.getActionCommand();

      Random ran = new Random();

      int i = ran.nextInt(NUM_CARDS_PER_HAND);

      if(actionCommand.equals("1"))
      {
         callCardtoPlayArea(SuitMatchGame.getHand(1).inspectCard(0), SuitMatchGame.getHand(0).inspectCard(i));
         updateButton(button1, 0);
         updateComputerCards(SuitMatchGame.getHand(0).inspectCard(i), i);
      }
      else if(actionCommand.equals("2"))
      {
         callCardtoPlayArea(SuitMatchGame.getHand(1).inspectCard(1), SuitMatchGame.getHand(0).inspectCard(i));
         updateButton(button2, 1);
         updateComputerCards(SuitMatchGame.getHand(0).inspectCard(i), i);
      }
      else if(actionCommand.equals("3"))
      {
         callCardtoPlayArea(SuitMatchGame.getHand(1).inspectCard(2), SuitMatchGame.getHand(0).inspectCard(i));
         updateButton(button3, 2);
         updateComputerCards(SuitMatchGame.getHand(0).inspectCard(i), i);

      }
      else if(actionCommand.equals("4"))
      {
         callCardtoPlayArea(SuitMatchGame.getHand(1).inspectCard(3), SuitMatchGame.getHand(0).inspectCard(i));
         updateButton(button4, 3);
         updateComputerCards(SuitMatchGame.getHand(0).inspectCard(i), i);
      }
      else if(actionCommand.equals("5"))
      {
         callCardtoPlayArea(SuitMatchGame.getHand(1).inspectCard(4), SuitMatchGame.getHand(0).inspectCard(i));
         updateButton(button5, 4);
         updateComputerCards(SuitMatchGame.getHand(0).inspectCard(i), i);
      }
      else if(actionCommand.equals("6"))
      {
         callCardtoPlayArea(SuitMatchGame.getHand(1).inspectCard(5), SuitMatchGame.getHand(0).inspectCard(i));
         updateButton(button6, 5);
         updateComputerCards(SuitMatchGame.getHand(0).inspectCard(i), i);

      }
      else if(actionCommand.equals("7"))
      {
         callCardtoPlayArea(SuitMatchGame.getHand(1).inspectCard(6), SuitMatchGame.getHand(0).inspectCard(i));
         updateButton(button7, 6);
         updateComputerCards(SuitMatchGame.getHand(0).inspectCard(i), i);
      }
      else if(actionCommand.equals("End Game"))
      {
         System.exit(0);
      }
   }
}
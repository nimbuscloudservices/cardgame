import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/**
 * Class View displays the GUI
 */
public class View extends JFrame
{
      public int firstStackSpacing = 100, secondStackSpacing = 100, thirdStackSpacing = 100;

      public static Controller.TimerView clock;

      //JButtons
      public JButton button1, button2, button3, button4, button5, button6, button7, cannotPlayButton, timerToggleBtn;

      //JPanels
      public JPanel addCardButtons, computerHand, playPanel, timerDisplay, timerButtons, cannotPlay;
      public JPanel pnlComputerHand, pnlHumanHand, pnlPlayArea, pnlTimerArea;

      //JLabels
      public JLabel  cardsOnDeck, computerWins, humanWins, winner, seconds;
      public JLabel  minutes, computerCP, humanCP, computer, human;
      static JLabel[] computerLabels = new JLabel[7];

      //Border
      private Border border;

      //JLayeredPane
      public JLayeredPane firstStack, secondStack, thirdStack;


      /**
       * filters input, adds panels to the JFrame
       * establishes layouts according to the general description
       * @param title of this game
       * @param numCardsPerHand number of cards for each hand
       * @param numPlayers of players for this game
       */
      public View(String title, int numCardsPerHand, int numPlayers)
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

        initialize();

      }

   /**
    * Initializes JLabels, JPanels, and JButtons on the JFrame
    */
   private void initialize()
      {
         border = BorderFactory.createLineBorder(Color.black);

         //JLabels
         winner = new JLabel();
         humanWins = new JLabel("Human Wins!", SwingConstants.CENTER);
         computerWins = new JLabel("Computer Wins!", SwingConstants.CENTER);
         cardsOnDeck = new JLabel();
         seconds = new JLabel();
         minutes = new JLabel();
         computerCP = new JLabel("", SwingConstants.CENTER);
         computer = new JLabel("Computer", SwingConstants.CENTER);
         human = new JLabel("You", SwingConstants.CENTER);
         humanCP = new JLabel("", SwingConstants.CENTER);

         //JPanels
         pnlTimerArea = new JPanel();
         playPanel = new JPanel();
         computerHand = new JPanel();
         addCardButtons = new JPanel();
         timerDisplay = new JPanel();
         timerButtons = new JPanel();
         cannotPlay = new JPanel();


         //JButtons
         button2 = new JButton();
         button3 = new JButton();
         button5 = new JButton();
         button4 = new JButton();
         button6 = new JButton();
         button7 = new JButton();
         button1 = new JButton();
         timerToggleBtn = new JButton();
         cannotPlayButton = new JButton();

         //JLayeredPane
         firstStack = new JLayeredPane();
         firstStack.setBounds(0,0,5000,5000);

         secondStack = new JLayeredPane();
         secondStack.setBounds(400,0,5000,5000);

         thirdStack = new JLayeredPane();
         thirdStack.setBounds(800,0,5000,5000);

         //Setting fonts for JLabels
         human.setFont(new Font("Verdana", Font.BOLD, 20));
         computer.setFont(new Font("Verdana", Font.BOLD, 20));

         computerCP.setFont(new Font("Verdana", Font.BOLD, 15));
         humanCP.setFont(new Font("Verdana", Font.BOLD, 15));

         computerWins.setFont(new Font("Verdana", Font.BOLD, 15));
         humanWins.setFont(new Font("Verdana", Font.BOLD, 15));

         //Adding JPanel pnlComputerHand on top of JFrame
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

         playPanel.setLayout(new GridLayout(1, 3));
         playPanel.add(firstStack);
         playPanel.add(secondStack);
         playPanel.add(thirdStack);
         pnlPlayArea.add(playPanel, BorderLayout.CENTER);
         pnlPlayArea.add(cardsOnDeck, BorderLayout.SOUTH);

         //Adding JLabel pnlHumanHand on bottom in JFrame
         JLabel yourHand = new JLabel("Your Hand", SwingConstants.CENTER);
         pnlHumanHand.setLayout(new BorderLayout());
         pnlHumanHand.add(yourHand, BorderLayout.NORTH);

         addCardButtons.setLayout(new GridLayout(1, 7));
         pnlHumanHand.add(addCardButtons, BorderLayout.SOUTH);

         //Adding JPanel pnlTimerArea
         clock = new Controller.TimerView(true);
         timerToggleBtn = clock.timerToggle();
         timerToggleBtn.setText("Start/Stop");

         pnlTimerArea.setLayout(new BorderLayout());
         pnlTimerArea.add(clock, BorderLayout.NORTH);
         add(pnlTimerArea, BorderLayout.EAST);

         timerToggleBtn.setLayout(new GridLayout(1,2));
         pnlTimerArea.add(clock, BorderLayout.CENTER);
         pnlTimerArea.add(timerToggleBtn, BorderLayout.SOUTH);

         //Adding cannotPlay JPanel
         cannotPlay.setLayout(new GridLayout(5,1));
         cannotPlay.setBorder(border);
         add(cannotPlay, BorderLayout.WEST);

         cannotPlay.add(computer);
         cannotPlay.add(computerCP);
         cannotPlay.add(human);
         cannotPlay.add(humanCP);

      }

      /**
       * initializePlayArea places 1 card in each stack before the game starts
       *
       * @param firstImage
       * @param secondImage
       * @param thirdImage
       */
      public void initializePlayArea(Icon firstImage, Icon secondImage, Icon thirdImage)
      {
        JLabel firstCard = new JLabel();
        JLabel secondCard = new JLabel();
        JLabel thirdCard = new JLabel();

        firstCard.setIcon(firstImage);
        firstCard.setBounds(155,100, firstImage.getIconWidth(),firstImage.getIconHeight());

        secondCard.setIcon(secondImage);
        secondCard.setBounds(155,100, secondImage.getIconWidth(),secondImage.getIconHeight());

        thirdCard.setIcon(thirdImage);
        thirdCard.setBounds(155,100, secondImage.getIconWidth(),secondImage.getIconHeight());

        firstStack.add(firstCard, Integer.valueOf(0));
        secondStack.add(secondCard, Integer.valueOf(0));
        thirdStack.add(thirdCard, Integer.valueOf(0));

      }
      /**
       * addCardToStack adds a card a stack
       *
       * @param stackNumber
       * @param image
       */
      public void addCardToStack(int stackNumber, Icon image)
      {
         JLabel card = new JLabel();
         card.setIcon(image);

         if(stackNumber == 1)
         {
            firstStackSpacing +=20;
            card.setBounds(155,firstStackSpacing, image.getIconWidth(),image.getIconHeight());
            firstStack.add(card, Integer.valueOf(firstStack.highestLayer()+1));

         }
         if(stackNumber == 2)
         {
            secondStackSpacing +=20;
            card.setBounds(155,secondStackSpacing, image.getIconWidth(),image.getIconHeight());
            secondStack.add(card, Integer.valueOf(secondStack.highestLayer()+1));

         }
         if(stackNumber == 3)
         {
            thirdStackSpacing +=20;
            card.setBounds(155,thirdStackSpacing, image.getIconWidth(),image.getIconHeight());
            thirdStack.add(card, Integer.valueOf(thirdStack.highestLayer()+1));

         }
      }
      /**
       * initializeButtons sets the image for each button the human will use
       *
       * @param controller
       */
      public void initializeButtons(Controller controller)
      {
         button1.setActionCommand("1");
         button1.addActionListener(controller);
         addCardButtons.add(button1);

         button2.setActionCommand("2");
         button2.addActionListener(controller);
         addCardButtons.add(button2);

         button3.setActionCommand("3");
         button3.addActionListener(controller);
         addCardButtons.add(button3);

         button4.setActionCommand("4");
         button4.addActionListener(controller);
         addCardButtons.add(button4);

         button5.setActionCommand("5");
         button5.addActionListener(controller);
         addCardButtons.add(button5);

         button6.setActionCommand("6");
         button6.addActionListener(controller);
         addCardButtons.add(button6);

         button7.setActionCommand("7");
         button7.addActionListener(controller);
         addCardButtons.add(button7);

         cannotPlayButton.setActionCommand("Cannot Play");
         cannotPlayButton.setText("Cannot Play");
         cannotPlayButton.addActionListener(controller);
         cannotPlay.add(cannotPlayButton);
      }
      /**
       * initializeComputerCard sets the card images for the computer's hand.
       *
       * @param controller
       * @param k
       */
      public void initializeComputerCard(Icon image, int k)
      {
         computerLabels[k] = new JLabel(image);

         computerHand.add(computerLabels[k]);

         pnlComputerHand.add(computerHand);
      }
      /**
       * setIconButtons sets the card image in a button
       *
       * @param button
       * @param image
       */
      public void setIconButtons(JButton button, Icon image)
      {
         button.setIcon(image);
         button.setHorizontalAlignment(JButton.CENTER);
         button.revalidate();
         button.repaint();
      }
      /**
       * displayRemainingCards displays the number of cards remaining in the deck.
       *
       * @param numOfCards
       */
      public void displayRemainingCards(int numOfCards)
      {
            cardsOnDeck.setText("Number of cards remaining: "+ String.valueOf(numOfCards));
            cardsOnDeck.setVerticalAlignment(JLabel.CENTER);
            cardsOnDeck.setHorizontalAlignment(JLabel.CENTER);
            pnlPlayArea.add(cardsOnDeck, BorderLayout.SOUTH);

      }
      /**
       * displayWinner displays the winner of the game.
       *
       * @param winner
       */
      public void displayWinner(String winner)
      {
         if (winner == "Human")
         {
            pnlPlayArea.removeAll();
            humanWins.setHorizontalAlignment(JLabel.CENTER);
            pnlPlayArea.add(humanWins, BorderLayout.CENTER);
            pnlPlayArea.revalidate();
            pnlPlayArea.repaint();
         }
         else
         {
            pnlPlayArea.removeAll();
            computerWins.setHorizontalAlignment(JLabel.CENTER);
            pnlPlayArea.add(computerWins, BorderLayout.CENTER);
            pnlPlayArea.revalidate();
            pnlPlayArea.repaint();
         }
      }
      /**
       * emptyHumanHands returns true if human hands are empty or returns false if they're not empty.
       *
       * @return boolean
       */
      public boolean emptyHumanHands()
      {
         return addCardButtons.getComponentCount() == 0;
      }
      /**
       * emptyComputerHands returns true if computer hands are empty or returns false if they're not empty.
       *
       * @return boolean
       */
      public boolean emptyComputerHands()
      {
         if (computerHand.getComponentCount() == 0)
         {
            addCardButtons.getComponent(0).setEnabled(false);
            return true;
         }
         else
         {
            return false;
         }
      }
      /**
       * displayCP displays the # of times each player uses "I cannot play" pass.
       *
       * @param player
       * @param count
       */
      public void displayCP(int player, int count)
      {
         if(player == 0)
         {
            computerCP.setText(String.valueOf(count));
         }
         else
         {
            humanCP.setText(String.valueOf(count));
         }
      }
      /**
       * removeButton removes button.
       *
       * @param button
       */
      public void removeButton(JButton button)
      {
         addCardButtons.remove(button);
         addCardButtons.revalidate();
         addCardButtons.repaint();
      }
      /**
       * removeComputerCard removes computer card.
       *
       * @param index
       */
      public void removeComputerCard(int index)
      {
         computerHand.remove(computerHand.getComponent(index));
         computerHand.revalidate();
         computerHand.repaint();
      }
}

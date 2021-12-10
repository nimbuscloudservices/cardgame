import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;

/**
 *  Class Controller controls the Time Build Game and implements ActionListener
 */
public class Controller implements ActionListener
{
   private Model theModel;
   private View theView;

   /**
    * Constructs controller
    * @param theModel of this game
    * @param theView of this game
    */
   public Controller(Model theModel, View theView)
   {
      this.theModel = theModel;
      this.theView = theView;

      initialize();
   }
   /*
    * initialize calls setIconButtons, initializeComputerCard, displayRemainingCards to display
    * cards for computer, player, and play area.
    */
   public void initialize()
   {
      theView.initializeButtons(this);
      theView.setIconButtons(theView.button1, theModel.getHumanIconCard(0));
      theView.setIconButtons(theView.button2, theModel.getHumanIconCard(1));
      theView.setIconButtons(theView.button3, theModel.getHumanIconCard(2));
      theView.setIconButtons(theView.button4, theModel.getHumanIconCard(3));
      theView.setIconButtons(theView.button5, theModel.getHumanIconCard(4));
      theView.setIconButtons(theView.button6, theModel.getHumanIconCard(5));
      theView.setIconButtons(theView.button7, theModel.getHumanIconCard(6));

      for(int i=0; i<7; i++)
      {
         theView.initializeComputerCard(theModel.initializeComputerCards(), i);
      }

      theView.initializePlayArea(theModel.getIconCardFromStack(0), theModel.getIconCardFromStack(1), theModel.getIconCardFromStack(2));
      theView.displayRemainingCards(theModel.getNumberOfCardsInDeck());
   }

   /*
    * checkHumanHand checks determines if a card can be added to the player hand. If not, the card that
    * was used is removed.
    *
    * @param button
    */
   public void checkHumanHand(JButton button)
   {
      if (theModel.getNumberOfCardsInDeck() > 0)
      {
            theModel.addCardToHand(1);

            theView.setIconButtons(theView.button1, theModel.getHumanIconCard(0));
            theView.setIconButtons(theView.button2, theModel.getHumanIconCard(1));
            theView.setIconButtons(theView.button3, theModel.getHumanIconCard(2));
            theView.setIconButtons(theView.button4, theModel.getHumanIconCard(3));
            theView.setIconButtons(theView.button5, theModel.getHumanIconCard(4));
            theView.setIconButtons(theView.button6, theModel.getHumanIconCard(5));
            theView.setIconButtons(theView.button7, theModel.getHumanIconCard(6));
            theView.displayRemainingCards(theModel.getNumberOfCardsInDeck());
      }
      else
      {
         theView.removeButton(button);
      }
   }
   /*
    * checkHumanHand checks determines if a card can be added to the player hand. If not, the card that
    * was used is removed.
    *
    * @param button
    * @param indexOf
    */
   public void checkComputerHand(int indexOf)
   {
      if (theModel.getNumberOfCardsInDeck() > 0)
      {

         theModel.addCardToHand(0);
         theView.displayRemainingCards(theModel.getNumberOfCardsInDeck());
      }
      else
      {
         theView.removeComputerCard(indexOf);
      }

   }
   /*
    * addCardToStack first calls theModel.playGame to determine if a card can be added
    * to any of the three stacks. In the end it calls computerPlays if the human player doesnt have 2 rounds to play.
    *
    * @param image
    * @param card
    */
   public void addCardToStack(Icon image, Card card)
   {

      String answer = theModel.playGame(card);

      if(answer == "1")
      {
         theModel.bothCP = 0;
         theView.addCardToStack(1, image);
         theModel.addCardsInTheStacks(card, 0);
         theModel.humanRounds = 1;
      }
      else if (answer == "2")
      {
         theModel.bothCP = 0;
         theView.addCardToStack(2, image);
         theModel.addCardsInTheStacks(card, 1);
         theModel.humanRounds = 1;
      }
      else if (answer == "3")
      {
         theModel.bothCP = 0;
         theView.addCardToStack(3, image);
         theModel.addCardsInTheStacks(card, 2);
         theModel.humanRounds = 1;
      }
      else
      {
         theModel.bothCP++;
         theModel.computerRounds = 2;
         theView.displayCP(1, theModel.cannotPlayCounter(1));
      }

      if(theModel.humanRounds!=2)
      {
         computerPlays(theModel.computerRounds);
      }

   }
   /*
    * computerPlays uses a for loop to manage the rounds a computer gets. Method calls theModel.playGame
    * to determine if a card can be added to any of the three stacks.
    *
    * @param rounds
    */
   public void computerPlays(int rounds)
   {
      for(int k = 0; k < rounds; k++)
      {
         Random ran = new Random();

         int i = ran.nextInt(theView.computerHand.getComponentCount());

         Card card = new Card();

         card = theModel.getCard(0, i);

         String answer = theModel.playGame(card);

         if(answer == "Cannot play")
         {
            theModel.bothCP++;
            theModel.computerRounds = 1;
            theModel.humanRounds = 2;
            rounds = 1;
            theView.displayCP(0, theModel.cannotPlayCounter(0));
         }
         if(answer == "1")
         {
            theModel.bothCP = 0;
            theView.addCardToStack(1, theModel.getCard(card));
            theModel.addCardsInTheStacks(card, 0);
            theModel.bothCP = 0;
            checkComputerHand(i);

         }
         if(answer == "2")
         {
            theModel.bothCP = 0;
            theView.addCardToStack(2, theModel.getCard(card));
            theModel.addCardsInTheStacks(card, 1);
            theModel.bothCP = 0;
            checkComputerHand(i);

         }
         if(answer == "3")
         {
            theModel.bothCP = 0;
            theView.addCardToStack(3, theModel.getCard(card));
            theModel.addCardsInTheStacks(card, 2);
            theModel.bothCP = 0;
            checkComputerHand(i);
         }
      }

      theModel.computerRounds = 1;

      Random ran = new Random();

      int i = ran.nextInt(3);

      //If both players used "cannot play" then one card is added to any of the 3 stacks.
      if(theModel.bothCP==2)
      {
         theModel.humanRounds = 1;
         theModel.bothCP = 0;
         theView.addCardToStack(i, theModel.getIconCardFromDeck());
      }
      //Calls emptyHands to check if any hands from either player are empty.
      emptyHands();
   }

   /*
    * emptyHands checks the hands of each player. Ends game when 1 player has no more cards.
    */
   public void emptyHands()
   {
      if (theView.emptyComputerHands() || theView.emptyHumanHands())
      {
         theView.displayWinner(theModel.endGame());
      }
   }
   /**
    * Invoked when an action occurs.
    *
    * @param e the event to be processed
    */
   @Override
   public void actionPerformed(ActionEvent e)
   {
      String actionCommand = e.getActionCommand();

      if (actionCommand.equals("1"))
      {
         addCardToStack(theView.button1.getIcon(), theModel.getCard(1, 0));
         checkHumanHand(theView.button1);
      }

      else if (actionCommand.equals("2"))
      {
         addCardToStack(theView.button2.getIcon(), theModel.getCard(1, 1));
         checkHumanHand(theView.button2);
      }
      else if (actionCommand.equals("3"))
      {
         addCardToStack(theView.button3.getIcon(), theModel.getCard(1, 2));
         checkHumanHand(theView.button3);
      }
      else if (actionCommand.equals("4"))
      {
         addCardToStack(theView.button4.getIcon(), theModel.getCard(1, 3));
         checkHumanHand(theView.button4);
      }
      else if (actionCommand.equals("5"))
      {
         addCardToStack(theView.button5.getIcon(), theModel.getCard(1, 4));
         checkHumanHand(theView.button5);
      }
      else if (actionCommand.equals("6"))
      {
         addCardToStack(theView.button6.getIcon(), theModel.getCard(1, 5));
         checkHumanHand(theView.button6);
      }
      else if (actionCommand.equals("7"))
      {
         addCardToStack(theView.button7.getIcon(), theModel.getCard(1, 6));
         checkHumanHand(theView.button7);
      }
      else if(actionCommand.equals("Cannot Play"))
      {
         theView.displayCP(1, theModel.cannotPlayCounter(1));
         theModel.humanRounds = 1;
         theModel.computerRounds = 2;

         theModel.bothCP++;

         //If both players used "cannot play" then one card is added to any of the 3 stacks.
         if(theModel.bothCP<2)
         {
            computerPlays(theModel.computerRounds);
         }
         else
         {
            Random ran = new Random();
            int i = ran.nextInt(3);

            theModel.bothCP = 0;
            theView.addCardToStack(i, theModel.getIconCardFromDeck());
         }
      }
      //Calls emptyHands to check if any hands from either player are empty.
      emptyHands();
   }

   /**
    * Timer class is the first half of the Timer, this class handles the
    * multithreading aspect of the timer.
    */
   public static class Timer extends Thread
   {
      private int seconds = 0;
      private boolean threadRunning = true;

      /**
       * default constructor that calls the constructor for Thread
       */
      public Timer()
      {
         super();
      }

      /**
       * Constructor with specified time, used for paused timer.
       *
       * @param timeStartValue
       */
      public Timer(int timeStartValue)
      {
         this.seconds = timeStartValue - 1;
      }

      //run() contains all of needed timer code.
      @Override public void run()
      {
         while (isThreadRunning())
         {
            if (this.seconds < 6000)
            {
               this.seconds += 1;
            }
            else
            {
               this.seconds = 0;
            }
            View.clock.setText(this.getFormattedTime(getSeconds()));
            doNothing(1000);
         }
      }

      /**
       * Terminates thread loop running the run() method
       *
       * @return true if successful
       */
      public boolean killThread()
      {
         this.threadRunning = false;
         return true;
      }

      /**
       * gets status of thread
       *
       * @return true if running false if not
       */
      public boolean isThreadRunning()
      {
         return threadRunning;
      }

      /**
       * sets threadrunning status
       *
       * @param threadRunning
       */
      public void setThreadRunning(boolean threadRunning)
      {
         this.threadRunning = threadRunning;
      }

      /**
       * gets the number of seconds elapsed since timer was started.
       *
       * @return seconds elapsed
       */
      public int getSeconds()
      {
         return seconds;
      }

      /**
       * sets seconds elapsed, used for resetting time.
       *
       * @param seconds to set
       */
      public void setSeconds(int seconds)
      {
         this.seconds = seconds;
      }

      /**
       * formats and returns a string that is used for timer's Jlabel. Uses
       * MM:ss format
       *
       * @param totalSeconds seconds
       * @return formatted string in MM:ss format
       */
      private String getFormattedTime(int totalSeconds)
      {
         int hours = totalSeconds / 3600;
         int minutes = (totalSeconds % 3600) / 60;
         int seconds = totalSeconds % 60;
         return String.format("%02d:%02d:%02d", hours, minutes, seconds);
      }

      /**
       * Allows thread to sleep for a number of milliseconds and used for
       * keeping time synchronized.
       *
       * @param milliseconds to donothing
       */
      private void doNothing(int milliseconds)
      {
         try
         {
            Thread.sleep(milliseconds);
         } catch (InterruptedException e)
         {
            System.out.println("Unexpected interrupt");
            System.exit(0);
         }
      }
   }

   /**
    * TimerView class is the second half of the timer. This class is used as a
    * JLabel
    */
   public static class TimerView extends JLabel implements ActionListener
   {
      private JButton timerBtn = new JButton();
      private Timer timerThread = new Timer();

      public TimerView()
      {
         timerBtn.addActionListener(this);
         this.setHorizontalAlignment(SwingConstants.CENTER);
         setText("00:00:00");
         setFont(new Font("San Serif", Font.BOLD, 20));
      }

      /**
       * constructor for Timer GUI, used for starting from paused time
       *
       * @param startTimer
       */
      public TimerView(boolean startTimer)
      {
         this();
         if (startTimer)
         {
            timerThread.start();
         }
      }

      /**
       * Calls timerToggleBtn
       *
       * @return timerBtn
       */
      public JButton timerToggle()
      {
         return timerBtn;
      }

      /**
       * resets timer to zero
       *
       * @return true if successful.
       */
      public boolean rstTimer()
      {
         this.timerThread.setSeconds(0);
         return true;
      }

      /**
       * Invoked when an action occurs.
       *
       * @param e the event to be processed
       */
      @Override public void actionPerformed(ActionEvent e)
      {
         if (timerThread.isAlive())
         {
            timerThread.killThread();
            timerThread = new Timer(timerThread.getSeconds());
         }
         else
         {
            timerThread.start();
         }
      }

   }
}

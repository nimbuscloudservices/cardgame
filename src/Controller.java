import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Controller implements ActionListener
{
   private Model theModel;
   private CardGameOutline SuitMatchGame;
   private View theView;
   private int numPacksPerDeck = 1;
   private int numJokersPerPack = 2;
   private int numUnusedCardsPerPack = 0;
   private Card[] unusedCardsPerPack = null;

   /**
    * Constructs controller
    *
    * @param theModel of this game
    * @param theView of this game
    */
   public Controller(Model theModel,
                     View theView)
   {

      SuitMatchGame = new CardGameOutline(numPacksPerDeck, numJokersPerPack,
            numUnusedCardsPerPack, unusedCardsPerPack, 2, 7);

      SuitMatchGame.deal();
      this.theModel = theModel;
      this.theView = theView;

      initialize();
   }

   /*
    * initialize setIconButtons
    */
   public void initialize()
   {
      theView.initializeButtons(this);
      theView.setIconButtons(theView.button1, theModel.initializeHumanCards(
            SuitMatchGame.getHand(1).inspectCard(0)));
      theView.setIconButtons(theView.button2, theModel.initializeHumanCards(
            SuitMatchGame.getHand(1).inspectCard(1)));
      theView.setIconButtons(theView.button3, theModel.initializeHumanCards(
            SuitMatchGame.getHand(1).inspectCard(2)));
      theView.setIconButtons(theView.button4, theModel.initializeHumanCards(
            SuitMatchGame.getHand(1).inspectCard(3)));
      theView.setIconButtons(theView.button5, theModel.initializeHumanCards(
            SuitMatchGame.getHand(1).inspectCard(4)));
      theView.setIconButtons(theView.button6, theModel.initializeHumanCards(
            SuitMatchGame.getHand(1).inspectCard(5)));
      theView.setIconButtons(theView.button7, theModel.initializeHumanCards(
            SuitMatchGame.getHand(1).inspectCard(6)));
      for (int i = 0; i < 7; i++)
      {
         theView.initializeComputerCard(theModel.initializeComputerCards(), i);
      }
   }

   /**
    * Checks deck
    *
    * @param button to check
    * @param index in hand
    */
   public void checkDeck(JButton button,
                         int index)
   {
      if (SuitMatchGame.takeCard(1))
      {
         if (SuitMatchGame.takeCard(1))
         {
            theView.setIconButtons(button,
                  theModel.updateHand(SuitMatchGame.getCardFromDeck()));
         }
      }
      else
      {
         theView.removeButton(button);
      }

      if (SuitMatchGame.takeCard(0))
      {
         theView.setComputerHand(
               theModel.updateHand(SuitMatchGame.getCardFromDeck()), index);
      }
      else
      {
         theView.removeComputerCard(index);
      }
   }

   @Override public void actionPerformed(ActionEvent e)
   {
      String actionCommand = e.getActionCommand();

      Random ran = new Random();

      int i = ran.nextInt(theView.computerHand.getComponentCount());

      switch (actionCommand)
      {
      case "1" -> {
         theView.setPlayArea(theView.button1, theModel.getComputerCard(
               SuitMatchGame.getHand(0).inspectCard(i)));
         theView.displayRoundWinner(
               theModel.playGame(SuitMatchGame.getHand(1).inspectCard(0),
                     SuitMatchGame.getHand(0).inspectCard(i)));
         checkDeck(theView.button1, i);
      }
      case "2" -> {
         theView.setPlayArea(theView.button2, theModel.getComputerCard(
               SuitMatchGame.getHand(0).inspectCard(i)));
         theView.displayRoundWinner(
               theModel.playGame(SuitMatchGame.getHand(1).inspectCard(1),
                     SuitMatchGame.getHand(0).inspectCard(i)));
         checkDeck(theView.button2, i);
      }
      case "3" -> {
         theView.setPlayArea(theView.button3, theModel.getComputerCard(
               SuitMatchGame.getHand(0).inspectCard(i)));
         theView.displayRoundWinner(
               theModel.playGame(SuitMatchGame.getHand(1).inspectCard(2),
                     SuitMatchGame.getHand(0).inspectCard(i)));
         checkDeck(theView.button3, i);
      }
      case "4" -> {
         theView.setPlayArea(theView.button4, theModel.getComputerCard(
               SuitMatchGame.getHand(0).inspectCard(i)));
         theView.displayRoundWinner(
               theModel.playGame(SuitMatchGame.getHand(1).inspectCard(3),
                     SuitMatchGame.getHand(0).inspectCard(i)));
         checkDeck(theView.button4, i);
      }
      case "5" -> {
         theView.setPlayArea(theView.button5, theModel.getComputerCard(
               SuitMatchGame.getHand(0).inspectCard(i)));
         theView.displayRoundWinner(
               theModel.playGame(SuitMatchGame.getHand(1).inspectCard(4),
                     SuitMatchGame.getHand(0).inspectCard(i)));
         checkDeck(theView.button5, i);
      }
      case "6" -> {
         theView.setPlayArea(theView.button6, theModel.getComputerCard(
               SuitMatchGame.getHand(0).inspectCard(i)));
         theView.displayRoundWinner(
               theModel.playGame(SuitMatchGame.getHand(1).inspectCard(5),
                     SuitMatchGame.getHand(0).inspectCard(i)));
         checkDeck(theView.button6, i);
      }
      case "7" -> {
         theView.setPlayArea(theView.button7, theModel.getComputerCard(
               SuitMatchGame.getHand(0).inspectCard(i)));
         theView.displayRoundWinner(
               theModel.playGame(SuitMatchGame.getHand(1).inspectCard(6),
                     SuitMatchGame.getHand(0).inspectCard(i)));
         checkDeck(theView.button7, i);
      }
      }
      //check if hands for each player are empty
      if (theView.emptyComputerHands() || theView.emptyHumanHands())
      {
         theView.displayWinner(theModel.endGame());
      }

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

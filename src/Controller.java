
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;

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

      theView.setIconButtons(theView.button1, theModel.initializeHumanCards(SuitMatchGame.getHand(1).inspectCard(0)));

      theView.setIconButtons(theView.button2, theModel.initializeHumanCards(SuitMatchGame.getHand(1).inspectCard(1)));

      theView.setIconButtons(theView.button3, theModel.initializeHumanCards(SuitMatchGame.getHand(1).inspectCard(2)));

      theView.setIconButtons(theView.button4, theModel.initializeHumanCards(SuitMatchGame.getHand(1).inspectCard(3)));

      theView.setIconButtons(theView.button5, theModel.initializeHumanCards(SuitMatchGame.getHand(1).inspectCard(4)));

      theView.setIconButtons(theView.button6, theModel.initializeHumanCards(SuitMatchGame.getHand(1).inspectCard(5)));

      theView.setIconButtons(theView.button7, theModel.initializeHumanCards(SuitMatchGame.getHand(1).inspectCard(6)));

      for(int i=0; i<7; i++)
      {
         theView.initializeComputerCard(theModel.initializeComputerCards(), i);
      }

      theView.initializePlayArea(theModel.getHumanCard(theModel.firstCardInStack(SuitMatchGame.getCardFromDeck())), theModel.getHumanCard(theModel.secondCardInStack(SuitMatchGame.getCardFromDeck())), theModel.getHumanCard(theModel.thirdCardInStack(SuitMatchGame.getCardFromDeck())));
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
   public void addCardToStack(String numberOfDeck, Icon image, Card card)
   {
      if(numberOfDeck == "1")
      {
         System.out.println("adding to first stack");
         theView.addCardToStack(1, image);
         theModel.firstCardInStack(card);
         theModel.humanRounds = 1;
      }
      else if (numberOfDeck == "2")
      {
         System.out.println("adding to second stack");
         theView.addCardToStack(2, image);
         theModel.secondCardInStack(card);
         theModel.humanRounds = 1;

      }
      else if (numberOfDeck == "3")
      {
         System.out.println("adding to third stack");
         theView.addCardToStack(3, image);
         theModel.thirdCardInStack(card);
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
   public void computerPlays(int rounds)
   {
      System.out.println("Computer Plays: " + rounds + " round");

      for(int k = 0; k < rounds; k++)
      {
         Random ran = new Random();

         int i = ran.nextInt(theView.computerHand.getComponentCount());

         String answer = theModel.playGame(SuitMatchGame.playCard(0,i));

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
            theView.addCardToStack(1, theModel.getComputerCard(SuitMatchGame.getHand(0).inspectCard(i)));
            theModel.firstCardInStack(SuitMatchGame.playCard(0,i));
            theModel.bothCP = 0;
         }
         if(answer == "2")
         {
            theView.addCardToStack(2, theModel.getComputerCard(SuitMatchGame.getHand(0).inspectCard(i)));
            theModel.secondCardInStack(SuitMatchGame.playCard(0,i));
            theModel.bothCP = 0;

         }
         if(answer == "3")
         {
            theView.addCardToStack(3, theModel.getComputerCard(SuitMatchGame.getHand(0).inspectCard(i)));
            theModel.thirdCardInStack(SuitMatchGame.playCard(0,i));
            theModel.bothCP = 0;
         }
      }

      theModel.computerRounds = 1;

      Random ran = new Random();

      int i = ran.nextInt(3);

      if(theModel.bothCP==2)
      {
         theModel.humanRounds = 1;
         theModel.bothCP = 0;
         theView.addCardToStack(i, theModel.getHumanCard(SuitMatchGame.getCardFromDeck()));
      }

   }
   public void emptyHands()
   {
      //Checks to see if hands for each player are empty
      if (theView.emptyComputerHands() || theView.emptyHumanHands())
      {
         theView.displayWinner(theModel.endGame());
      }
   }
   @Override
   public void actionPerformed(ActionEvent e)
   {

      String actionCommand = e.getActionCommand();

      Random ran = new Random();

      int i = ran.nextInt(theView.computerHand.getComponentCount());

      if (actionCommand.equals("1"))
      {

            String answer = theModel.playGame(SuitMatchGame.playCard(1,0));

            addCardToStack(answer, theView.button1.getIcon(),SuitMatchGame.playCard(1,0));

            checkDeck(theView.button1, i);

      }

      else if (actionCommand.equals("2"))
      {

            String answer = theModel.playGame(SuitMatchGame.playCard(1,1));

            addCardToStack(answer, theView.button2.getIcon(), SuitMatchGame.playCard(1,1));

            checkDeck(theView.button2, i);


      }
      else if (actionCommand.equals("3"))
      {

            String answer = theModel.playGame(SuitMatchGame.playCard(1,2));

            addCardToStack(answer,  theView.button3.getIcon(),SuitMatchGame.playCard(1,2));

            checkDeck(theView.button3, i);


      }
      else if (actionCommand.equals("4"))
      {

            String answer = theModel.playGame(SuitMatchGame.playCard(1,3));

            addCardToStack(answer,  theView.button4.getIcon(), SuitMatchGame.playCard(1,3));

            checkDeck(theView.button4, i);


      }
      else if (actionCommand.equals("5"))
      {

            String answer = theModel.playGame(SuitMatchGame.playCard(1,4));

            addCardToStack(answer,  theView.button5.getIcon(), SuitMatchGame.playCard(1,4));

            checkDeck(theView.button5, i);


      }
      else if (actionCommand.equals("6"))
      {
            String answer = theModel.playGame(SuitMatchGame.playCard(1,5));

            addCardToStack(answer, theView.button6.getIcon(), SuitMatchGame.playCard(1,5));
            checkDeck(theView.button6, i);


      }
      else if (actionCommand.equals("7"))
      {

            String answer = theModel.playGame(SuitMatchGame.playCard(1,6));

            addCardToStack(answer, theView.button7.getIcon(), SuitMatchGame.playCard(1,6));

            checkDeck(theView.button7, i);

      }
      else if(actionCommand.equals("Cannot Play"))
      {
         theView.displayCP(1, theModel.cannotPlayCounter(1));
         theModel.computerRounds = 2;
      }

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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class View extends JFrame
{
      public JPanel pnlComputerHand = new JPanel();
      public JPanel pnlHumanHand = new JPanel();
      public JPanel pnlPlayArea = new JPanel();
      
      //JButtons
      public JButton button1, button2, button3, button4, button5, button6, button7, timerButton;

      //JPanels
      public JPanel addCardButtons, computerHand, playHand;

      //JLabels
      public JLabel computerPlayCard, humanPlayCard, pickCardMessage,
                     computerWins, humanWins, winner;
      private Border border;
      
      static JLabel[] computerLabels = new JLabel[7];
      static JLabel[] playLabelText = new JLabel[7];


      /**
       * filters input, adds panels to the JFrame
       * establishes layouts according to the general description
       * @param title
       * @param numCardsPerHand
       * @param numPlayers
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
      
      private void initialize()
      {
         border = BorderFactory.createLineBorder(Color.black);
         winner = new JLabel();
         humanWins = new JLabel("Human Wins!", SwingConstants.CENTER);
         computerWins = new JLabel("Computer Wins!", SwingConstants.CENTER);
         pickCardMessage = new JLabel("Pick a card from your hand!",
               SwingConstants.CENTER);
         humanPlayCard = new JLabel();
         computerPlayCard = new JLabel();
         playHand = new JPanel();
         computerHand = new JPanel();
         addCardButtons = new JPanel();
         button2 = new JButton();
         button3 = new JButton();
         button5 = new JButton();
         button4 = new JButton();
         button6 = new JButton();
         button7 = new JButton();
         button1 = new JButton();
         
         setLabel();
      }
      
      public void setLabel()
      {
         computerWins.setFont(new Font("Verdana", Font.BOLD, 15));
         humanWins.setFont(new Font("Verdana", Font.BOLD, 15));
         setPanels();
      } 
      
      private void setPanels()
      {
         playHand.setLayout(new GridLayout(2, 2));

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

         addCardButtons.setLayout(new GridLayout(1, 7));
         pnlHumanHand.add(addCardButtons, BorderLayout.SOUTH);

         //JLabel pickCardMessage added to middle of JFrame
         pnlPlayArea.add(pickCardMessage, BorderLayout.CENTER);

      }
      
      public void setPlayArea(JButton humanCard, Icon computerCard)
      {
         pnlPlayArea.removeAll();
         pnlPlayArea.revalidate();
         pnlPlayArea.repaint();

         humanPlayCard.removeAll();
         computerPlayCard.removeAll();

         playHand.removeAll();

         computerPlayCard.setIcon(computerCard);
         computerPlayCard.setHorizontalAlignment(JLabel.CENTER);

         humanPlayCard.setIcon(humanCard.getIcon());
         humanPlayCard.setHorizontalAlignment(JLabel.CENTER);

         humanPlayCard.revalidate();
         humanPlayCard.repaint();

         computerPlayCard.revalidate();
         computerPlayCard.repaint();

         playLabelText[0] = new JLabel("Computer", JLabel.CENTER);
         playLabelText[1] = new JLabel("You", JLabel.CENTER);

         playHand.add(computerPlayCard);
         playHand.add(humanPlayCard);

         playHand.add(playLabelText[0]);
         playHand.add(playLabelText[1]);
         playHand.revalidate();
         playHand.repaint();
         
         pnlPlayArea.add(playHand, BorderLayout.CENTER);
      }
      
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
      }
      
      public void initializeComputerCard(Icon image, int k)
      {
         computerLabels[k] = new JLabel(image);

         computerHand.add(computerLabels[k]);
         
         pnlComputerHand.add(computerHand); 
      }
      
      public void setIconButtons(JButton button, Icon image)
      {
         button.setIcon(image);
         button.setHorizontalAlignment(JButton.CENTER); 
         button.revalidate();
         button.repaint();
      }
      public void setComputerHand(Icon image, int index)
      {
         computerLabels[index] = new JLabel(image);
      }
      public void displayRoundWinner(String winner)
      {
         if(winner == "Human")
         {
            pnlPlayArea.add(humanWins, BorderLayout.SOUTH);
            pnlPlayArea.revalidate();
            pnlPlayArea.repaint();
         }
         else
         {
            pnlPlayArea.add(computerWins, BorderLayout.SOUTH);
            pnlPlayArea.revalidate();
            pnlPlayArea.repaint();
         }

      }
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
      public boolean emptyHumanHands()
      {
         if (addCardButtons.getComponentCount() == 0)
         {
            return true;
         }
         else
         {
            return false;
         }
      }
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
      public void removeButton(JButton button)
      {
         addCardButtons.remove(button);
         addCardButtons.revalidate();
         addCardButtons.repaint();
      }
      public void removeComputerCard(int index)
      {
         computerHand.remove(computerHand.getComponent(index));
         computerHand.revalidate();
         computerHand.repaint();
         
      }
}
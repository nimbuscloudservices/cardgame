import javax.swing.*;

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
    *
    * @param title
    * @param numCardsPerHand
    * @param numPlayers
    */
   CardTable(String title, int numCardsPerHand, int numPlayers)
   {

   }

   public int getNumCardsPerHand()
   {
      return numCardsPerHand;
   }

   public int getNumPlayers()
   {
      return numPlayers;
   }

}

/**
 * GUICard class
 * Manages the reading and building of the card image Icons
 * This class is the benefactor of most of the GUI machinery we tested in Phase 1
 * Reads image files and stores them in a static Icon array
 */
class GUICard {
   // 14 = A thru K + joker
   private static Icon[][] iconCards = new ImageIcon[14][4];
   private static Icon iconBack;
   static boolean iconsLoaded = false;

   /**
    * @return Icons in a 2-D array
    */
   public static void loadCardIcons() {

   }

   /**
    * @param card
    * @return Icon
    */
   public static Icon getIcon(Card card) {
      //return iconCards[valueAsInt(card)][suitAsInt(card)];
   }

   /**
    * @return back of card Icon
    */
   public static Icon getBackCardIcon()
   {

   }
}

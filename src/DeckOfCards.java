import java.util.Random;
import java.util.Scanner;

/**
 * Module 3 Deck of Cards
 *
 * @author Blake, Layla, Saul, Yavik
 * @version 11-13-2021
 */

public class DeckOfCards
{
   /**
    * Gets a number of players between 1-10 from user input
    *
    * @return an int between 1-10 (inclusive)
    */
   private static int getNumPlayers()
   {
      Scanner scanner = new Scanner(System.in);

      System.out.print("Select the number of players (1-10): ");
      int numPlayers = scanner.nextInt();
      // Validate a legal number of players
      if (numPlayers < 1 || numPlayers > 10)
      {
         System.out.println(
               "Invalid input. You must choose a number between 1 and 10.");
         return getNumPlayers();
      }

      return numPlayers;
   }

   /**
    * Deals a deck of cards evenly between any number of hands
    *
    * @param deck  the deck to deal
    * @param hands an array of Hands to deal cards to
    */
   private static void dealDeck(Deck deck, Hand[] hands)
   {
      int handIndex = 0;
      Card nextCard = deck.dealCard();
      Hand nextHand = hands[handIndex];
      while (nextCard != null)
      {
         nextHand.takeCard(nextCard);
         nextCard = deck.dealCard();

         if (handIndex >= hands.length - 1)
         {
            handIndex = 0;
         }
         else
         {
            handIndex++;
         }
         nextHand = hands[handIndex];
      }
   }

   public static void main(String[] args)
   {
      // Tests for deck with 2 packs
      Deck doubleDeck = new Deck(2);
      System.out.println("Double deck, unshuffled:");
      System.out.print(doubleDeck.dealCard());
      for (int i = 1; i < (52 * 2); i++)
      {
         System.out.print(", ");
         System.out.print(doubleDeck.dealCard());
      }
      System.out.println();

      doubleDeck.init(2);
      doubleDeck.shuffle();
      System.out.println("Double deck, shuffled:");
      System.out.print(doubleDeck.dealCard());
      for (int i = 1; i < (52 * 2); i++)
      {
         System.out.print(", ");
         System.out.print(doubleDeck.dealCard());
      }
      System.out.println();

      // Tests for deck with 1 pack
      Deck singleDeck = new Deck(1);
      System.out.println("Single deck, unshuffled:");
      System.out.print(singleDeck.dealCard());
      for (int i = 1; i < 52; i++)
      {
         System.out.print(", ");
         System.out.print(singleDeck.dealCard());
      }
      System.out.println();

      singleDeck.init(1);
      singleDeck.shuffle();
      System.out.println("Single deck, shuffled:");
      System.out.print(singleDeck.dealCard());
      for (int i = 1; i < 52; i++)
      {
         System.out.print(", ");
         System.out.print(singleDeck.dealCard());
      }
      System.out.println();

      // Get the number of players from user input
      int numPlayers = getNumPlayers();

      Deck deck = new Deck();

      // Instantiate a hand for each player
      Hand[] hands = new Hand[numPlayers];
      for (int i = 0; i < numPlayers; i++)
      {
         hands[i] = new Hand();
      }

      // Deal the entire deck, unshuffled
      dealDeck(deck, hands);

      System.out.println("All hands, unshuffled: ");
      for (int i = 0; i < hands.length; i++)
      {
         System.out.println("hand = (" + hands[i] + ")");
      }

      // Shuffle, reset hands, and deal again
      deck.init(1);
      deck.shuffle();
      for (int i = 0; i < hands.length; i++)
      {
         hands[i].resetHand();
      }
      dealDeck(deck, hands);

      System.out.println("All hands, shuffled: ");
      for (int i = 0; i < hands.length; i++)
      {
         System.out.println("hand = (" + hands[i] + ")");
      }
   }
}

/**
 * A class to model playing cards
 */
class Card
{
   //Member Data
   private char value;
   private Suit suit;
   private boolean errorFlag;
   public static char[] valueRanks = {'A', '2', '3', '4', '5', '6', '7',
      '8', '9', 'T', 'J', 'Q', 'K', 'X'};
   public static Suit[] suitRanks = {Suit.clubs, Suit.diamonds, Suit.hearts,
           Suit.spades};

   /**
    * constructor for Card object
    *
    * @param value of the card
    * @param suit  of the card
    */
   public Card(char value, Suit suit)
   {
      set(value, suit);
   }

   /**
    * constructor with default values
    */
   public Card()
   {
      set('A', Suit.spades);
   }

   /**
    * a copy constructor that will create a duplicate of the original which is
    * not a reference
    *
    * @param origCard original card object
    */
   public Card(Card origCard)
   {
      set(origCard.getValue(), origCard.getSuit());
      errorFlag = origCard.getErrorFlag();
   }

   /**
    * changes value and suit to user specified values
    *
    * @param value of this card
    * @param suit  of this card
    * @return true if successfully changes without error, false if error
    */
   public boolean set(char value, Suit suit)
   {

      if (isValid(value, suit))
      {
         this.value = value;
         errorFlag = false;
         this.suit = suit;
         return true;
      }
      else
      {
         errorFlag = true;
         return false;
      }
   }

   /**
    * helper method that determines if passed values are valid
    *
    * @param value of card
    * @param suit  of card
    * @return true if passed values are valid, false if invalid
    */
   private boolean isValid(char value, Suit suit)
   {
      boolean validValue = false;
      boolean validSuit = false;
      char[] cardValues = { 'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T',
            'J', 'Q', 'K' };
      for (char i : cardValues)
      {
         if (i == value)
         {
            validValue = true;
            break;
         }
      }
      for (Suit value1 : Suit.values())
      {
         if (value1 == suit)
         {
            validSuit = true;
            break;
         }

      }
      return validValue && validSuit;

   }

   /**
    * gets this card's suit
    *
    * @return suit of this card
    */
   public Suit getSuit()
   {
      return this.suit;
   }

   /**
    * gets value of this card
    *
    * @return value of this card
    */
   public char getValue()
   {
      return this.value;
   }

   /**
    * gets errorFlag value
    *
    * @return value of errorFlag of this card
    */
   public boolean getErrorFlag()
   {
      return this.errorFlag;
   }

   /**
    * Compares this card object to a user specified card object to confirm if
    * they are equal
    *
    * @param card that is being compared to this card
    * @return true if cards are the same, else false if it does not match
    */
   public boolean equals(Card card)
   {
      if (this.suit == card.suit & this.value == card.value)
      {
         return this.errorFlag == card.errorFlag;
      }
      else
      {
         return false;
      }
   }

   /**
    * returns a string stating the full card name
    *
    * @return "Invalid" if errorFlag is true or full card name
    */
   public String toString()
   {
      if (errorFlag)
         return "Invalid";
      return value + " of " + suit;
   }

   // enum of suits of cards
   public enum Suit
   {
      clubs, diamonds, hearts, spades
   }

   /**
    * Puts the order of the card values in here with the smallest first
    * @param card
    * @return card value
    */
   static int cardValue(Card card)
   {
      for (int i = 0; i < valueRanks.length; i++)
      {
         if (card.getValue() == valueRanks[i])
         {
            return i;
         }
      }
      return - 1;
   }

   /**
    * Sorts the incoming array of cards using a bubble sort routine
    * @param cardArray
    * @param arraySize
    */
   public static void arraySort(Card[] cardArray, int arraySize)
   {
      Card temp;

      for(int card = 0; card < arraySize; card++)
      {
         for(int nextCard = 1; nextCard < (arraySize - card); nextCard++)
         {
            int prevCard = cardValue(cardArray[nextCard - 1]);
            int curCard = cardValue(cardArray[nextCard]);

            if(prevCard  < curCard)
            {
               temp = cardArray[nextCard -1];
               cardArray[nextCard - 1] = cardArray[nextCard];
               cardArray[arraySize] = temp;
            }
         }
      }
   }

}

/**
 * A class to model a hand of playing cards
 */
class Hand
{
   public final int MAX_CARDS = 58;
   Card[] myCards;
   int numCards;

   /**
    * Constructor for hand object
    */
   public Hand()
   {
      resetHand();
   }

   /**
    * Clears hand of all card objects
    */
   public void resetHand()
   {
      myCards = new Card[MAX_CARDS];
      numCards = 0;
   }

   /**
    * Adds a card to next available position in myCards array
    *
    * @param card that is being added to the array
    * @return true if successfully added, false is not
    */
   public boolean takeCard(Card card)
   {
      if (numCards < MAX_CARDS)
      {
         myCards[numCards] = new Card(card);
         numCards++;
         return true;
      }
      else
      {
         return false;
      }
   }

   /**
    * returns and removes the card in the top occupied position of the array.
    *
    * @return card at the top of the myCards array
    */
   public Card playCard(int cardIndex)
   {

      if (numCards == 0) //error
      {
         //Creates a card that does not work
         return new Card('M', Card.Suit.spades);
      }
      //Decreases numCards.
      Card card = myCards[cardIndex];

      numCards--;
      for (int i = cardIndex; i < numCards; i++) {
         myCards[i] = myCards[i + 1];
      }

      myCards[numCards] = null;

      return card;
   }

   /**
    * converts myCards array to string form
    *
    * @return myCards array as a string
    */
   public String toString()
   {
      String displayHand = "";

      displayHand += myCards[0];

      for (int i = 1; i < numCards; i++)
      {
         displayHand += " , ";
         displayHand += myCards[i];
      }
      return displayHand;
   }

   /**
    * gets value of num of cards in hand
    *
    * @return number of cards in hand array
    */
   public int getNumCards()
   {
      return this.numCards;
   }

   /**
    * Accessor for an individual card
    *
    * @param k index of a card
    * @return a card with errorFlag = true if k is bad
    */
   public Card inspectCard(int k)
   {
      if (k < numCards && k >= 0)
      {
         return new Card(myCards[k]);
      }

      return new Card(' ', Card.Suit.spades);
   }

   public void sortHands()
   {
      int k;
      for (k = 0; k < numCards; k++)
         myCards[k].arraySort(myCards, numCards);
   }
}

/**
 * A class to model a deck of cards
 */
class Deck
{
   public static final int PACK = 56;
   public static final int MAX_NUMBER_PACK = 6;
   public static final int MAX_CARDS = PACK * MAX_NUMBER_PACK;
   static boolean allocated = false;
   private static Card[] masterPack;
   int topCard;
   private int numPacks;
   private Card[] cards;

   /**
    * constructor for Deck
    *
    * @param numPacks number of packs of playing cards
    */
   public Deck(int numPacks)
   {
      allocateMasterPack();
      cards = new Card[numPacks * PACK];
      init(numPacks);

   }

   /**
    * Constructor with default of one pack of playing cards
    */
   public Deck()
   {
      this(1);
   }

   /**
    * Used for initializing masterPack, can not be run more that once.
    */
   private static void allocateMasterPack()
   {
      int i = 0;
      char[] cardValues = { 'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T',
            'J', 'Q', 'K', 'X' };
      if (!allocated)
      {
         allocated = true;
         masterPack = new Card[PACK];
         while (i < PACK)
         {
            for (int j = 0; j < Card.Suit.values().length; j++)
            {
               for (int k = 0; k < cardValues.length; k++)
               {
                  masterPack[i] = new Card(cardValues[k],
                        Card.Suit.values()[j]);
                  i++;
               }
            }
         }
      }
   }

   /**
    * repopulates cards[] with the standard 52 x numPacks
    *
    * @param numPacks number of playing card packs used
    */
   public void init(int numPacks)
   {
      if(numPacks > 0 && numPacks <=6)
      {
         this.numPacks = numPacks;
         cards = new Card[numPacks * PACK];
         topCard = numPacks * PACK;
         for (int packNum = 0; packNum < numPacks; packNum++)
         {
            for (int card = 0; card < PACK; card++)
            {
               cards[packNum * PACK + card] = new Card(masterPack[card]);
            }
         }
      }
   }

   /**
    * Mixes up the cards with the help of the standard random number generator
    */
   public void shuffle()
   {
      Random shuffle = new Random();

      for (int i = 0; i < cards.length; i++)
      {
         int randomCard = shuffle.nextInt(PACK * numPacks);
         Card assignCard = cards[randomCard];
         cards[randomCard] = cards[i];
         cards[i] = assignCard;
      }
   }

   /**
    * returns and removes the card in the top occupied position of cards[]
    *
    * @return card being dealt
    */
   public Card dealCard()
   {
      Card dealCard;
      if (topCard > 0)
      {
         dealCard = cards[getTopCard() - 1];
         cards[getTopCard() - 1] = null;

         topCard--;
         return new Card(dealCard);
      }
      return null;
   }

   /**
    * gets value of topCard field
    *
    * @return value of topCard field
    */
   public int getTopCard()
   {
      return this.topCard;
   }

   /**
    * Accessor for an individual card. Returns a card with errorFlag = true if k
    * is bad
    *
    * @param k index of card being checked
    * @return returns an object copy of card with errorFlag = true
    */
   public Card inspectCard(int k)
   {
      if (k >= topCard && k >= 0)
      {
         return new Card(' ', Card.Suit.spades);
      }
      return cards[k];
   }

}

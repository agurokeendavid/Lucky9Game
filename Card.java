import java.util.Random;
/**
 * @author Keen David Aguro
 * TODO:
 * 1. Declare and initiliaze list of suits in arrays
 * 2. Declare and initiliaze list of ranks in arrays
 * 3. Declare and initiliaze list of card values in arrays
 * 4. Shuffle deck of cards
 * 5. Remove first card in deck
 */
public class Card {
    String[] SUITS = { "Clubs", "Diamonds", "Hearts", "Spades" }; // initialising the suits of the deck in a String array named 'SUITS'
    String[] RANKS = { "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King" }; // initialising the ranks of the deck in a String array named 'RANKS'
    int[] CARD_VALUE = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 0, 0, 0 };
    public String[] deckLabels = new String[SUITS.length * RANKS.length];
    public int[] deckValues = new int[SUITS.length * RANKS.length];

    // constructor
    public Card() {
        initilizeDeck();
    }

    private void initilizeDeck() {
        for (int i = 0; i < RANKS.length; i++) {
            for (int j = 0; j < SUITS.length; j++) {
                deckLabels[SUITS.length * i + j] = RANKS[i] + " of " + SUITS[j];
                deckValues[SUITS.length * i + j] = CARD_VALUE[i];
            }
        }
    }
    
    
    public void shuffleDeck() {
        Random random = new Random();
        for (int i = 0; i < deckLabels.length; i++) {
            int r = i + (int) (random.nextDouble() * (deckLabels.length - i));
            String temporaryDeck = deckLabels[r];
            int temporaryDeckValue = deckValues[r];

            // swapping and shuffling
            deckLabels[r] = deckLabels[i];
            deckLabels[i] = temporaryDeck; 

            deckValues[r] = deckValues[i];
            deckValues[i] = temporaryDeckValue;
        }
    }

    // Remove first element from the deck arrays.
    public void removeFirstCardInDeck() {
        for (int j = 0; j < deckLabels.length - 1; j++) {
            deckLabels[j] = deckLabels[j + 1];
            deckValues[j] = deckValues[j + 1];
        }
    }

}

import java.util.Random;
import java.util.Scanner;
/**
 * @author Keen David Aguro
 * TODO:
 * 1. Display Menu Screen
 * 2. Validate number of player
 * 3. Display Second Menu Screen
 * 4. Iterate Players Bet
 * 5. Display Dealer and Player Score
 * 6. Display Dealer Total Fund
 * 7. Display End Menu Screen
 * 8. Display Leaderboard
 */
public class Main2 {
    private static Scanner scanner = new Scanner(System.in);
    private static String[] leaderBoardName = new String[10];
    private static int[] leaderBoardScore = new int[10];

    private static String formatCardNumber(int cardNumber) {
        switch (cardNumber) {
            case 1:
                return "1st";
            case 2:
                return "2nd";
            default:
                return "";
        }
    }

    private static int getLuckyNineScore(int value) {

        if (value > 19)
            value = value - 20;
        else if (value > 9)
            value = value - 10;
        
        return value;
    }

    private static void displayLeaderBoard() {
        System.out.println("Leader Board (TOP 10)");
        System.out.println("--------------------------------------");
        for (int index = 0; index < leaderBoardName.length; index++) {
            String playerName = leaderBoardName[index];
            if (playerName != null)
                System.out.println(String.format("%d) %s (%d)", index + 1, playerName, leaderBoardScore[index]));
        }
    }

    private static void playGame(int numberOfPlayers, int cashFund, String dealerName) {
        Random random = new Random();
        Card card = new Card();
        int dealerScore = 0, winFund = 0, lossFund = 0;
        String[] winners = new String[numberOfPlayers];
        String[] losers = new String[numberOfPlayers];
        String[] draws = new String[numberOfPlayers];
        int[] playersBet = new int[numberOfPlayers];
        char dealerDrawAnotherCard;

        System.out.println("--------------------------------------");
        for (int index = 0; index < numberOfPlayers; index++) {
            int playerBet = random.nextInt(500);
            playersBet[index] = playerBet;
            System.out.println(String.format("Player %d bet: %d", index + 1, playerBet));
        }
        System.out.println("--------------------------------------");

        card.shuffleDeck();

        // print shuffled deck
        for (int i = 0; i < 2; i++) // printing one card at a time
        {
            System.out.println("Your " + formatCardNumber(i + 1) + " card: " + card.deckLabels[i]); // displaying a random card
            dealerScore += card.deckValues[i];
            card.removeFirstCardInDeck();
        }

        System.out.print("Draw another card[Y/N]: ");
        dealerDrawAnotherCard = Character.toLowerCase(scanner.nextLine().charAt(0));
        if (dealerDrawAnotherCard == 'y') {
            for (int i = 0; i < 1; i++) // printing one card at a time
            {
                System.out.println("Your 3rd card: " + card.deckLabels[i]); // displaying a random card
                dealerScore += card.deckValues[i];
                card.removeFirstCardInDeck();
            }
        }

        dealerScore = getLuckyNineScore(dealerScore);
        System.out.println("--------------------------------------");
        System.out.println("Dealer score: " + dealerScore);
        System.out.println("--------------------------------------");
        for (int index = 0; index < numberOfPlayers; index++) {
            int currentPlayer = index + 1;
            System.out.println(String.format("Player %d", currentPlayer));
            int playerScore = 0;
            for (int i = 0; i < 2; i++) // printing one card at a time
            {
                System.out.println(formatCardNumber(i + 1) + " card: " + card.deckLabels[i]); // displaying a random card
                playerScore += card.deckValues[i];
                card.removeFirstCardInDeck();
            }
            boolean isPlayerDrawAnotherCard = random.nextBoolean();
            if (isPlayerDrawAnotherCard) {
                for (int i = 0; i < 1; i++) // printing one card at a time
                {
                    System.out.println("3rd card: " + card.deckLabels[i]); // displaying a random card
                    playerScore += card.deckValues[i];
                    card.removeFirstCardInDeck();
                }
            }
            playerScore = getLuckyNineScore(playerScore);
            System.out.println(String.format("Player %d score: %d", currentPlayer, playerScore));
            System.out.println("--------------------------------------");
            if (playerScore < dealerScore || ((playerScore == dealerScore) && isPlayerDrawAnotherCard && (dealerDrawAnotherCard != 'y'))) {
                losers[index] = String.format("Player %d (%d)", currentPlayer, playersBet[index]);
                winFund += playersBet[index];
                cashFund += playersBet[index];
            } else if (playerScore > dealerScore || ((playerScore == dealerScore) && (isPlayerDrawAnotherCard == false) && (dealerDrawAnotherCard == 'y'))) {
                winners[index] = String.format("Player %d (%d)", currentPlayer, playersBet[index] * 2);
                lossFund += playersBet[index];
                cashFund -= playersBet[index];
            } else {
                draws[index] = String.format("Player %d (%d)", currentPlayer, playersBet[index]);
            }
        }

        displayWinners(winners);
        displayLosers(losers);
        displayDraws(draws);
        displayRemainingFunds(cashFund, winFund, lossFund);
        displayEndMenuScreen(numberOfPlayers, cashFund, dealerName);
    }

    private static void displayEndMenuScreen(int numberOfPlayers, int cashFund, String dealerName) {
        final byte PLAY_AGAIN = 1, VIEW_LEADER_BOARD = 2, NEW_GAME = 3;
        byte action;
        System.out.println("--------------------------------------");
        System.out.println("[1] Play Again");
        System.out.println("[2] View Leader Board");
        System.out.println("[3] New Game");
        action = Byte.parseByte(scanner.nextLine());
        if (action == PLAY_AGAIN) {
            playGame(numberOfPlayers, cashFund, dealerName);
        } else if (action == VIEW_LEADER_BOARD) {
            displayLeaderBoard();
            displayEndMenuScreen(numberOfPlayers, cashFund, dealerName);
        } else if (action == NEW_GAME) {
            updateLeaderBoard(cashFund, dealerName);
        }
    }

    private static void updateLeaderBoard(int cashFund, String dealerName) {
        for (int index = 0; index < leaderBoardScore.length; index++) {
            if (cashFund > leaderBoardScore[index]) {
                leaderBoardName[index] = dealerName;
                leaderBoardScore[index] = cashFund;
                break;
            }
        }
    }

    private static void displayRemainingFunds(int cashFund, int winFund, int lossFund) {
        System.out.println("--------------------------------------");
        System.out.println("Win: " + winFund);
        System.out.println("Loss: " + lossFund);
        System.out.println("--------------------------------------");
        System.out.println("Dealer total fund: " + cashFund);
    }

    private static void displayDraws(String[] draws) {
        System.out.println("--------------------------------------");
        System.out.println("Draw:");
        for (String draw : draws) {
            if (draw != null)
                System.out.println(draw);
        }
    }

    private static void displayLosers(String[] losers) {
        System.out.println("--------------------------------------");
        System.out.println("Losers(s) pay out:");
        for (String loser : losers) {
            if (loser != null)
                System.out.println(loser);
        }
    }

    private static void displayWinners(String[] winners) {
        System.out.println("--------------------------------------");
        System.out.println("Winner(s) pay out:");
        for (String winner : winners) {
            if (winner != null)
                System.out.println(winner);
        }
    }

    private static void startGame() {
        final byte PLAY_GAME = 1;
        String dealerName;
        int cashFund, numberOfPlayers;
        boolean isPlayerValid, isPlayAgain = false;
        byte action;

        System.out.print("Enter your dealer name: ");
        dealerName = scanner.nextLine();
        System.out.print("Enter your cash fund: ");
        cashFund = Integer.parseInt(scanner.nextLine());

        do {
            
            System.out.print("Enter number of player(s): ");
            numberOfPlayers = Byte.parseByte(scanner.nextLine());

            if (numberOfPlayers <= 10) {
                isPlayerValid = true;
                break;
            }
            isPlayerValid = false;
            System.out.println("INVALID: 10 players only");

        } while(isPlayerValid);


        do {
            System.out.println("[1] Play");
            System.out.println("[2] Quit");
            System.out.print("Select your action: ");
            action = Byte.parseByte(scanner.nextLine());
            if (action == PLAY_GAME) {
                playGame(numberOfPlayers, cashFund, dealerName);
                isPlayAgain = false;
            } else {
                System.out.println("Invalid Input!");
            }
        } while (isPlayAgain);
    }

    public static void main(String[] args) {
        final byte START_GAME = 1, EXIT_GAME = 2;
        boolean isStartGame;
        byte action;
        try {
            do {
                System.out.println("Welcome to Lucky 9");
                System.out.println("[1] Start Game");
                System.out.println("[2] Exit");
                System.out.print("Select your action: ");
                action = Byte.parseByte(scanner.nextLine());
                switch(action) {
                    case START_GAME:
                        isStartGame = true;
                        startGame();
                        break;
                    case EXIT_GAME:
                        isStartGame = false;
                        break;
                    default:
                        isStartGame = true;
                        System.out.println("Invalid Input!");
    
                }
                
            } while (isStartGame);
        } catch (Exception exception) {
            System.out.println("Something went wrong. " + exception.getMessage());
        }
        
    }
}
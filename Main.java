import java.util.Scanner;
/**
 * @author Keen David Aguro
 * TODO:
 * 1. Display Menu Screen
 * 2. Create a Game Class.
 * 3. Create a Card Class.
 */
public class Main {
    public static void main(String[] args) {
        final byte START_GAME = 1, EXIT_GAME = 2;
        boolean isStartGame;
        byte action;
        Scanner scanner = new Scanner(System.in);
        Game game = new Game();
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
                        game.startGame();
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
        } finally {
            scanner.close(); // Closing the scanner object
        }
    }
}

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Game of set. Set the initial number of cards, and try to create sets of cards.
 * A set is when 3 cards have all of their properties either match, or be different.
 * Ex. [RED, ONE, RHOMBUS, SEMI], [RED, TWO, WORM, SEMI], [RED, THREE, OVAL, SEMI],
 * Press 0 to get more cards.
 */
public class SetGame {
//    private ArrayList board = new ArrayList();
    SetGame() {
        init();
    }
    private void init() {
        Set set = new Set(9);
//        board = set.getBoard();
//        System.out.println(set.getBoard().size());
//        System.out.println(set.getBoard());
        printBoard(set.getBoard());
        Scanner sc = new Scanner(System.in);
        while (!set.getBoard().isEmpty()) {
            ArrayList<Integer> arrSet = new ArrayList<>();
            System.out.print("ENTER 3 CARD IDs THAT CREATE A SET: ");
            while (arrSet.size() < 3) {
                int input = sc.nextInt();
                if (0 < input && input <= set.getBoard().size()) {
                    arrSet.add(input - 1);
                } else if (input == 0) {
                    set.addCards(3);
//                    board = set.getBoard();
                    System.out.println("ADDING CARDS");
                    printBoard(set.getBoard());
                    System.out.print("ENTER 3 CARD IDs THAT CREATE A SET: ");
                }
            }
            if (set.callSet(arrSet.get(0), arrSet.get(1), arrSet.get(2))) {
                System.out.println("THIS WAS A SET!!!");
                System.out.println("REPLACING CARDS");
//                board = set.getBoard();
                printBoard(set.getBoard());
            } else {
                System.out.println("THIS IS NOT A SET");
            }

        }
    }
    private void printBoard(ArrayList board) {
        for (int i = 0; i < board.size(); ) {
            for (int j = 0; j < 3; j++) {
                if (i < board.size()){
                    System.out.print((i + 1) + ") " + board.get(i) + "\t\t\t");
                    i++;
                }
            }
            System.out.println();
        }
    }

}

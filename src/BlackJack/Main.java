package BlackJack;

import java.util.*;

class Card {
    String suit;
    String value;
    int points;

    Card(String suit, String value, int points) {
        this.suit = suit;
        this.value = value;
        this.points = points;
    }
}

class Deck {
    private List<Card> cards = new ArrayList<>();
    private Random random = new Random();

    Deck() {
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] values = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        int[] points = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11};

        for (String suit : suits) {
            for (int i = 0; i < values.length; i++) {
                cards.add(new Card(suit, values[i], points[i]));
            }
        }
    }

    void shuffle() {
        Collections.shuffle(cards);
    }

    Card drawCard() {
        return cards.remove(cards.size() - 1);
    }
}

class Hand {
    private List<Card> hand = new ArrayList<>();
    private int totalPoints = 0;

    void addCard(Card card) {
        hand.add(card);
        totalPoints += card.points;
        if (totalPoints > 21) {
            for (Card c : hand) {
                if (c.value.equals("A")) {
                    totalPoints -= 10;
                    if (totalPoints <= 21) break;
                }
            }
        }
    }

    int getTotal() {
        return totalPoints;
    }

    void display() {
        for (Card card : hand) {
            System.out.print(card.value + " of " + card.suit + " | ");
        }
        System.out.println("Total: " + totalPoints);
    }

    boolean hasBust() {
        return totalPoints > 21;
    }
}

class BlackjackGame {
    private Deck deck = new Deck();
    private Hand playerHand = new Hand();
    private Hand aiHand = new Hand();
    private Random random = new Random();

    void startGame() {
        deck.shuffle();

        playerHand.addCard(deck.drawCard());
        aiHand.addCard(deck.drawCard());
        playerHand.addCard(deck.drawCard());
        aiHand.addCard(deck.drawCard());

        System.out.println("Your Hand:");
        playerHand.display();

        System.out.println("AI's Hand:");
        aiHand.display();

        playerTurn();
        if (!playerHand.hasBust()) {
            aiTurn();
        }

        determineWinner();
    }

    void playerTurn() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Do you want to [H]it or [S]tand?");
            String choice = scanner.nextLine().toUpperCase();
            if (choice.equals("H")) {
                playerHand.addCard(deck.drawCard());
                playerHand.display();
                if (playerHand.hasBust()) {
                    System.out.println("You bust! AI wins.");
                    return;
                }
            } else if (choice.equals("S")) {
                break;
            }
        }
    }

    void aiTurn() {
        System.out.println("AI's turn:");
        aiHand.display();

        while (aiHand.getTotal() <= 16) {
            aiHand.addCard(deck.drawCard());
            aiHand.display();
        }
        if (aiHand.hasBust()) {
            System.out.println("AI busts! You win.");
        }
    }

    void determineWinner() {
        if (!playerHand.hasBust() && !aiHand.hasBust()) {
            if (playerHand.getTotal() > aiHand.getTotal()) {
                System.out.println("You win!");
            } else if (playerHand.getTotal() < aiHand.getTotal()) {
                System.out.println("AI wins!");
            } else {
                System.out.println("It's a tie!");
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        BlackjackGame game = new BlackjackGame();
        game.startGame();
    }
}

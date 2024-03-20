import java.util.ArrayList;

public class Forca {
    private String word;

    private final ArrayList<Character> guesses = new ArrayList<Character>();

    public Forca(String word) {
        this.word = word.toUpperCase();
    }

    public String getWord() {
        StringBuilder cyphered = new StringBuilder();

        int right_letter = 0;

        for (int i = 0; i < word.length(); i++) {
            if (guesses.contains(word.charAt(i)) || word.charAt(i) == ' ') {
                right_letter++;
                cyphered.append(word.charAt(i));
            } else {
                cyphered.append('_');
            }
        }
        return (right_letter == word.length())? ("You guessesd! The word is:" + word) : cyphered.toString();
    }

    public String guess(char guess) {
        if (guesses.contains(guess) || guess == ' ') {
            return "You already guessed that letter!";
        }
        guesses.add(guess);
        return getWord();
    }

}

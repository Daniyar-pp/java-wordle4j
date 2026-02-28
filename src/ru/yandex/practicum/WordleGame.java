package ru.yandex.practicum;

import java.util.*;

public class WordleGame {

    private final String answer;
    private final WordleDictionary dictionary;
    private int steps;
    private final List<String> guesses;
    private final List<String> hints;

    public WordleGame(String answer, WordleDictionary dictionary) {
        this.answer = answer.toLowerCase().replace('ё', 'е');
        this.dictionary = dictionary;
        this.steps = 0;
        this.guesses = new ArrayList<>();
        this.hints = new ArrayList<>();
    }

    public String analyzeGuess(String word) {
        char[] answerChars = answer.toCharArray();
        char[] guessChars = word.toCharArray();
        boolean[] used = new boolean[5];
        char[] result = new char[5];

        for (int i = 0; i < 5; i++) {
            if (guessChars[i] == answerChars[i]) {
                result[i] = '+';
                used[i] = true;
            }
        }

        for (int i = 0; i < 5; i++) {
            if (result[i] == '+') continue;

            char c = guessChars[i];
            boolean found = false;

            for (int j = 0; j < 5; j++) {
                if (!used[j] && c == answerChars[j]) {
                    found = true;
                    used[j] = true;
                    break;
                }
            }

            result[i] = found ? '^' : '-';
        }

        return new String(result);
    }

    public String getHint() {
        if (guesses.isEmpty()) {
            return dictionary.getRandomWord();
        }

        for (String word : dictionary.getAllWords()) {
            if (!guesses.contains(word)) {
                return word;
            }
        }
        return dictionary.getRandomWord();
    }

    public String makeGuess(String guess) {
        String normalized = guess.toLowerCase().replace('ё', 'е');

        if (normalized.length() != 5) {
            return null;
        }

        if (!dictionary.contains(normalized)) {
            return null;
        }

        String hint = analyzeGuess(normalized);
        guesses.add(normalized);
        hints.add(hint);
        steps++;

        return hint;
    }

    public boolean isGameOver() {
        return steps >= 6 || (!guesses.isEmpty() && guesses.get(guesses.size() - 1).equals(answer));
    }

    public boolean isWin() {
        return !guesses.isEmpty() && guesses.get(guesses.size() - 1).equals(answer);
    }

    public String getAnswer() {
        return answer;
    }

    public List<String> getGuesses() {
        return guesses;
    }

    public List<String> getAllWords() {
        return dictionary.getAllWords();
    }
}
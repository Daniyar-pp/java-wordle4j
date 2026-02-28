package ru.yandex.practicum;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;

class WordleGameTest {

    private WordleDictionary dictionary;
    private WordleGame game;

    @BeforeEach
    void setUp() {
        dictionary = new WordleDictionary(Arrays.asList("герой", "гонец", "арбуз"));
        game = new WordleGame("герой", dictionary);
    }

    @Test
    void makeGuess_returnsCorrectMarkers() {
        assertEquals("+^-^-", game.makeGuess("гонец"));
    }

    @Test
    void makeGuess_withCorrectWord_returnsAllPlus() {
        assertEquals("+++++", game.makeGuess("герой"));
    }

    @Test
    void makeGuess_updatesGuessesList() {
        game.makeGuess("гонец");
        assertEquals(1, game.getGuesses().size());
        assertEquals("гонец", game.getGuesses().get(0));
    }

    @Test
    void makeGuess_withInvalidLength_returnsNull() {
        assertNull(game.makeGuess("дом"));
        assertEquals(0, game.getGuesses().size());
    }

    @Test
    void makeGuess_withWordNotInDictionary_returnsNull() {
        assertNull(game.makeGuess("абвгд"));
        assertEquals(0, game.getGuesses().size());
    }

    @Test
    void gameWins_whenCorrectWordGuessed() {
        game.makeGuess("герой");
        assertTrue(game.isWin());
        assertTrue(game.isGameOver());
    }

    @Test
    void gameContinues_afterWrongGuess() {
        game.makeGuess("гонец");
        assertFalse(game.isWin());
        assertFalse(game.isGameOver());
    }
}
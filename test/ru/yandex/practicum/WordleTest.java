package ru.yandex.practicum;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
    void makeGuess_returnsCorrectMarkers() throws Exception {
        assertEquals("+^-^-", game.makeGuess("гонец"));  // было "+--^-"
    }

    @Test
    void makeGuess_withCorrectWord_returnsAllPlus() throws Exception {
        assertEquals("+++++", game.makeGuess("герой"));
    }

    @Test
    void makeGuess_updatesGuessesList() throws Exception {
        game.makeGuess("гонец");
        assertEquals(1, game.getGuesses().size());
        assertEquals("гонец", game.getGuesses().get(0));
    }

    @Test
    void makeGuess_withInvalidLength_returnsNull() throws Exception {
        assertNull(game.makeGuess("дом"));
        assertEquals(0, game.getGuesses().size());
    }

    @Test
    void makeGuess_withWordNotInDictionary_throwsException() {
        assertThrows(WordleGame.WordNotFoundInDictionary.class, () -> {
            game.makeGuess("абвгд");
        });
    }

    @Test
    void gameWins_whenCorrectWordGuessed() throws Exception {
        game.makeGuess("герой");
        assertTrue(game.isWin());
        assertTrue(game.isGameOver());
    }

    @Test
    void gameContinues_afterWrongGuess() throws Exception {
        game.makeGuess("гонец");
        assertFalse(game.isWin());
        assertFalse(game.isGameOver());
    }
}
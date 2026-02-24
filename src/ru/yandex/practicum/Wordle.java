package ru.yandex.practicum;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class Wordle {

    public static void main(String[] args) {

        PrintWriter logger = null;

        try {
            logger = new PrintWriter(
                    new FileWriter("game.log", StandardCharsets.UTF_8, true)
            );
            logger.println("- Новая игра -");

            WordleDictionaryLoader loader = new WordleDictionaryLoader();
            List<String> words = loader.loadDictionary("C:\\Users\\pp\\java-wordle4j\\words_ru.txt");
            WordleDictionary dictionary = new WordleDictionary(words);

            String secret = dictionary.getRandomWord();
            WordleGame game = new WordleGame(secret, dictionary);

            logger.println("Загадано: " + secret);

            Scanner console = new Scanner(System.in);
            System.out.println("Игра Wordle. Угадайте слово из 5 букв.");
            System.out.println("Пустая строка - подсказка");

            while (!game.isGameOver()) {
                System.out.print("Введите слово: ");
                String input = console.nextLine().trim();

                if (input.isEmpty()) {
                    String hint = game.getHint();
                    System.out.println("Подсказка: " + hint);
                    logger.println("Подсказка: " + hint);
                    continue;
                }

                try {
                    String result = game.makeGuess(input);

                    if (result == null) {
                        System.out.println("Слово должно быть из 5 букв");
                    } else {
                        System.out.println("Результат: " + result);
                        logger.println("Ход: " + input + " -> " + result);
                    }

                } catch (WordleGame.WordNotFoundInDictionary e) {
                    System.out.println("Такого слова нет в словаре");
                    logger.println("Ошибка: " + e.getMessage());
                }
            }

            if (game.isWin()) {
                System.out.println("Поздравляем! Вы угадали!");
                logger.println("Победа");
            } else {
                System.out.println("Вы проиграли. Было загадано: " + secret);
                logger.println("Поражение");
            }

        } catch (IOException e) {
            System.err.println("Ошибка загрузки словаря: " + e.getMessage());
            if (logger != null) {
                logger.println("КРИТИЧЕСКАЯ ОШИБКА: " + e.getMessage());
            }
        } finally {
            if (logger != null) {
                logger.println("=== Конец игры ===");
                logger.close();
            }
        }
    }

}
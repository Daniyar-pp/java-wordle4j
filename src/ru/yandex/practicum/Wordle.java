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
            List<String> words = loader.loadDictionary("words_ru.txt");
            WordleDictionary dictionary = new WordleDictionary(words);

            String secret = dictionary.getRandomWord();
            WordleGame game = new WordleGame(secret, dictionary);

            logger.println("Загадано: " + secret);

            Scanner console = new Scanner(System.in);
            System.out.println("Игра Wordle. Угадайте слово из 5 букв.");
            System.out.println("Команды: 'стоп' - выход, Enter - подсказка");

            int hintsLeft = 5;

            while (!game.isGameOver()) {
                System.out.print("Введите слово: ");
                String input = console.nextLine().trim().toLowerCase();

                if (input.equals("стоп")) {
                    System.out.println("Игра прервана");
                    break;
                }

                if (input.isEmpty() || input.equals("подсказка")) {
                    if (hintsLeft > 0) {
                        String hint = game.getHint();
                        hintsLeft--;
                        System.out.println("Подсказка: " + hint);
                        System.out.println("Осталось подсказок: " + hintsLeft);
                    } else {
                        System.out.println("Подсказки кончились!");
                    }
                    continue;
                }

                if (input.length() != 5) {
                    System.out.println("Ошибка! Нужно 5 букв, а вы ввели " + input.length());
                    continue;
                }

                String result = game.makeGuess(input);

                if (result == null) {
                    System.out.println("Такого слова нет в словаре!");
                } else {
                    System.out.println("Результат: " + result);

                    if (game.isWin()) {
                        System.out.println("ПОБЕДА! Вы угадали слово!");
                    }
                }
            }

            if (!game.isWin() && !game.isGameOver()) {
            } else if (game.isGameOver() && !game.isWin()) {
                System.out.println("Игра окончена. Загаданное слово: " + game.getAnswer());
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
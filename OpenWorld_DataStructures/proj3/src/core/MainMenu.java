package core;


import edu.princeton.cs.algs4.StdDraw;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static edu.princeton.cs.algs4.StdDraw.*;
import static utils.FileUtils.fileExists;

public class MainMenu {
    Boolean menuOn = true;
    Boolean seedPromptOn = false;
    Boolean namePromptOn = false;
    Boolean reload = false;
    String input = "";
    String name = "";
    Boolean updatedMenu = false;

    public void renderMenu() {

        clear(StdDraw.PINK);
        // Set the font and draw the title
        setPenColor(255, 255, 255);
        text(0.5, 0.7, "CS61B: THE GAME");


        text(0.5, 0.6, "New Game (N)");
        text(0.5, 0.55, "Load Game (L)");
        text(0.5, 0.5, "Quit (Q)");
        text(0.5, 0.45, "Player Name (P)");

        // Show the canvas
        show();

    }

    public void renderUpdatedMenu(String name) {
        clear(StdDraw.PINK);
        // Set the font and draw the title
        setPenColor(255, 255, 255);
        text(0.5, 0.7, "CS61B: THE GAME");


        text(0.5, 0.6, "New Game (N)");
        text(0.5, 0.55, "Load Game (L)");
        text(0.5, 0.5, "Quit (Q)");
        text(0.5, 0.45, "Change Player Name (P)");

        text(0.5, 0.35, "Name: " + name);

        this.name = name;

        // Show the canvas
        show();
        updatedMenu = true;
    }

    public void seedPrompt() {
        clear(StdDraw.PINK);
        setPenColor(255, 255, 255);
        text(0.5, 0.6, "Enter a random seed!");
        text(0.5, 0.5, "Press S once ready");
        show();
        seedPromptOn = true;
    }

    public void seedValue() {
        if (hasNextKeyTyped()) {
            clear(StdDraw.PINK);
            char character = nextKeyTyped();
            if (character == 's' && !Objects.equals(input, "") || character == 'S' && !Objects.equals(input, "")) {
                seedPromptOn = false;
            } else {

                Pattern pattern = Pattern.compile("\\d+$");
                Matcher matcher = pattern.matcher(input + character);
                if (matcher.find()) {
                    try {
                        Long s = Long.valueOf(input + character);
                    } catch (NumberFormatException e) {
                        seedPromptOn = false;
                    }
                    if (seedPromptOn) {
                        input += character;
                        text(0.5, 0.6, "Enter a random seed!");
                        text(0.5, 0.5, "Press S once ready");
                        text(0.5, 0.4, input);
                    }
                } else {
                    text(0.5, 0.6, "Enter a random seed!");
                    text(0.5, 0.5, "Press S once ready");
                    text(0.5, 0.4, input);
                }
            }
        }
    }

    public void namePrompt() {
         clear(StdDraw.PINK);
         setPenColor(255, 255, 255);
         text(0.5, 0.6, "Enter your name!");
         text(0.5, 0.5, "Press '+' when ready");
         show();
         namePromptOn = true;
    }

    public void nameValue() {
        if (hasNextKeyTyped()) {
            char character = nextKeyTyped();
            clear(StdDraw.PINK);
            if (character == '+') {
                namePromptOn = false;
                renderUpdatedMenu(this.name);
            } else {
                if (namePromptOn) {
                    this.name += character;
                    text(0.5, 0.6, "Enter your name!");
                    text(0.5, 0.5, "Press '+' when ready");
                    text(0.5, 0.4, name);
                }
            }
        }
    }

    public void gameOverScreen() {
        clear(StdDraw.PINK);
        setPenColor(255, 255, 255);
        text(0.5, 0.6, "Congratulations!");
        text(0.5, 0.5, "Game Over");
        show();
    }

    public boolean whileSeedPrompt() {
        return seedPromptOn;
    }

    public boolean whileNamePrompt() {
        return namePromptOn;
    }

    public boolean whileMenu() {
        return menuOn;
    }

    public boolean whileUpdatedMenu() {
        return updatedMenu;
    }

    public void checkMenuInput() {
        if (hasNextKeyTyped()) {
            char in = nextKeyTyped();
            if (in == 'n' || in == 'N') {
                seedPrompt();
                menuOn = false;
                updatedMenu = false;
            }

            if (in == 'q' || in == 'Q') {
                System.exit(0);
            }

            if (in == 'l' || in == 'L') {
                if (fileExists("Seed.txt")) {
                    reload = true;
                    menuOn = false;
                    seedPromptOn = false;
                    updatedMenu = false;
                }
            }

            if (in == 'p' || in == 'P') {
                this.name = "";
                namePrompt();
                menuOn = false;
            }
        }
    }
}

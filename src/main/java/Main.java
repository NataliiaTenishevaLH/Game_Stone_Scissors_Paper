import java.io.*;
import java.util.Random;
import java.io.IOException;
import java.util.Scanner;

public class Main {
   private static StringBuilder textResults = new StringBuilder();

    public static void main(String[] args) throws IOException {
        DefineWinner fixWinner = new DefineWinner();
        FileWriter fw = null;
        BufferedWriter bw = null;
        Scanner sc = new Scanner(System.in);
        StringBuilder textResults = new StringBuilder();

        int gameCount = Main.readCustomerInput("Введите количество игр", sc);
        if (gameCount != 0)  Main.runGame(gameCount, sc, fixWinner);
        sc.close();
        Main.fixResults("Result:" + fixWinner.getWinner());
        String results = getResults();
        System.out.println(getResults());
        writeToFile(results, "D://results.txt");
    }

    public static void runGame(int gameCount, Scanner sc, DefineWinner fixWinner) {
        StringBuilder textResults = new StringBuilder();
        for (int i = 1; i <= gameCount; i++) {
            String answerFirstPaticipant = readCustomerInput(sc);
            String answerSecondPaticipant = getComputerAnswer();
            if (answerFirstPaticipant != "")
                fixWinner.defineUserWinner(i, answerFirstPaticipant, answerSecondPaticipant);
            textResults.append("Game " + i ).append(". ").append("user: " + answerFirstPaticipant);
            textResults.append(" ").append("comp: " + answerSecondPaticipant).append(" \n");
        }
        Main.fixResults(textResults.toString());
    }

    public static String getComputerAnswer() {
        String[] texts = {"Rock", "Scissors", "Paper"};
        Random random = new Random();
        int pos = random.nextInt(texts.length);
        return texts[pos];
    }

    public static int readCustomerInput(String message, Scanner sc) {
        System.out.println(message);
        String nextLine = sc.nextLine();
        try {
            return Integer.parseInt(nextLine);
        } catch (NumberFormatException ne) {
            System.out.println("Incorrect: " + ne.getMessage());
        }
        return 0;
    }


    public static String readCustomerInput(Scanner sc) {
        System.out.println("Укажите значение: Rock, Paper или Scissors");

        try {
            String nextLine = sc.nextLine();
            Main.interruptGame( nextLine);
            return Main.getLine(nextLine.trim());
        } catch (NumberFormatException | IOException ne) {
            System.out.println("Incorrect: " + ne.getMessage());
        } catch (CorrectInputException incorrectError) {
            System.out.println("Incorrect: " + incorrectError.getMessage());
        }
        return "";
    }

    public static String getLine(String nextLine) throws CorrectInputException {
        if ((!nextLine.equals("Rock")) && (!nextLine.equals("Paper")) && (!nextLine.equals("Scissors"))) {
            throw new CorrectInputException("Указано некорректное значение. Укажите:Rock, Paper или Scissors");
        }
        return nextLine;
    }

    public static void fixResults(String result){
        textResults.append(result);
    }

    public static String getResults(){
       return textResults.toString();
    }

    public static void writeToFile(String results, String path) {
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter(path, false);
            bw = new BufferedWriter(fw);
            try {
                 bw.write(results);
             } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
                bw.flush();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void interruptGame(String nextLine) throws IOException{
              if (nextLine.equals("Q")) {
                System.out.println("Игра была прервана");
                  System.out.println(getResults());
                System.exit(0);
            }
        }
    }
import java.util.HashSet;
import java.util.Scanner;

public class Main {
    static Scanner scan = new Scanner(System.in);
    static final int ARR_SIZE = 24;

    public static void main(String[] args) {
        int[] cardsNumbersArr = createCardsNumberArr();
        int[] randomIndexArr = randomizeIndexOfArray();
        int[] arrScores = createCardsValuesArr(randomIndexArr);
        outputArraies(arrScores, cardsNumbersArr);
        startGame(arrScores, cardsNumbersArr);
    }

    public static void startGame(int[] arrScores, int[] cardsNumbersArr){
        int firstPlayerBank = 0;
        int secondPlayerBank = 0;
        int cardNumber;
        int move;
        int shift = 0;
        do {
            move = 1;
            do{
                if (move == 1){
                    System.out.println("-------First Player choice-------");
                    cardNumber = inputNumber(cardsNumbersArr);
                    firstPlayerBank = takeBank(arrScores, cardNumber, firstPlayerBank);
                }else{
                    System.out.println("-------Second Player choice-------");
                    cardNumber = inputNumber(cardsNumbersArr);
                    secondPlayerBank = takeBank(arrScores, cardNumber, secondPlayerBank);
                    if (shift == 0) {
                        System.out.print("\033[H\033[2J");
                        System.out.flush();
                    }
                    shift++;
                }
                updateArrScores(cardsNumbersArr, cardNumber);
                updateCardsNumbers(arrScores, cardNumber);
                move++;
            }while(move < 3 && firstPlayerBank < 21 && secondPlayerBank < 21);
            outputColumns(firstPlayerBank, secondPlayerBank);
            if (firstPlayerBank < 21 && secondPlayerBank < 21)
                outputCardsNumbers(cardsNumbersArr);

        } while(firstPlayerBank < 21 && secondPlayerBank < 21);
        outputResult(firstPlayerBank, secondPlayerBank);
    }

    public static int[] createCardsNumberArr(){
        int[] cardsArr = new int[ARR_SIZE];
        for (int i = 0; i < cardsArr.length; i++){
            cardsArr[i] = i + 1;
        }
        return cardsArr;
    }

    public static int[] createCardsValuesArr(int[] randomIndexArr){
        int[] cardsValuesArr = new int[]{8, 8, 7, 7, 6, 6, 5, 5, 5, 4, 4, 4, 3, 3, 3, 3, 2, 2, 2, 2, 1, 1, 1, 1};
        int[] arrRandom = new int[ARR_SIZE];
        int counter = 0;
        System.out.println();
        for (int i : randomIndexArr) {
            arrRandom[i] = cardsValuesArr[counter];
            counter++;
        }
        return arrRandom;
    }
    public static void outputArraies(int[] cardsValuesArr, int[] cardsNumberArr){
        for (int i : cardsNumberArr) {
            System.out.print(i);
            System.out.print(" ");
        }
        System.out.println();
        for(int i = 0; i < cardsValuesArr.length; i++){
            if (cardsNumberArr[i] > 9){
                System.out.print(cardsValuesArr[i] + "  ");
            } else{
                System.out.print(cardsValuesArr[i] + " ");
            }
        }
        System.out.println();
    }

    public static int[] randomizeIndexOfArray(){
        int num;
        HashSet<Integer> randomSet = new HashSet<>();
        int[] randomIndexArr = new int[ARR_SIZE];
        for (int i = 0; i < ARR_SIZE;) {
            num = (int) (Math.random() * 24);
            if (randomSet.add(num)) {
                randomIndexArr[i] = num;
                i++;
            }
        }
        return randomIndexArr;
    }

    public static int takeBank(int[] mas, int num, int bank) {
        bank = bank + mas[num];
        return bank;
    }

    private static int inputNumber(int[] mas) {
        final int MIN_VALUE = 1;
        final int MAX_VALUE = 24;
        int num = 0;
        boolean isIncorrect;
        do {
            System.out.print("Input card number: ");
            isIncorrect = false;
            try {
                num = Integer.parseInt(scan.nextLine());
            } catch (Exception q) {
                isIncorrect = true;
                System.out.println("Check data correctness.");
            }
            if (!isIncorrect && (num < MIN_VALUE || num > MAX_VALUE)) {
                isIncorrect = true;
                System.out.println("Invalid input range.");
            }
            if (!isIncorrect && mas[num - 1] == 0) {
                System.out.println("This value was selected earlier!");
                isIncorrect = true;
            }
        } while (isIncorrect);
        return num - 1;
    }

    public static void updateArrScores(int[] cardsNumberArr, int num) {
        cardsNumberArr[num] = 0;
    }

    public static void updateCardsNumbers(int[] cardsValuesArr, int num) {
        cardsValuesArr[num] = 0;
    }

    public static void outputCardsNumbers(int[] cardsNumberArr) {
        final int TWO_DIGIT_NUM = 25;
        final int ONE_DIGIT_NUM = 0;
        for (int i = 0; i < ARR_SIZE; i++) {
            if (cardsNumberArr[i] == TWO_DIGIT_NUM)
                System.out.print("   ");
            else if (cardsNumberArr[i] == ONE_DIGIT_NUM)
                System.out.print("  ");
            else
                System.out.print(cardsNumberArr[i] + " ");
        }
        System.out.println();
    }

    public static void outputColumns(int bank1, int bank2) {
        System.out.print("Bank 1 player");
        System.out.println("  Bank 2 player");
        System.out.print("     " + bank1);
        System.out.println("              " + bank2);
    }


    public static void outputResult(int bank1, int bank2) {
        final int RESULT = 21;
        System.out.println();
        if ((bank1 < RESULT && bank2 > RESULT) || (bank1 == RESULT && bank2 != RESULT))
            System.out.println("First player win!");
        else if ((bank2 < RESULT && bank1 > RESULT) || (bank2 == RESULT && bank1 != RESULT)) {
            System.out.println("Second player win!");
        }
    }
}
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
  public static void main(String[] args) throws Exception {
    Scanner sc = new Scanner(System.in);
    boolean validInput = false;

    while (!validInput) {
      try {
        clearConsole();
        System.out.println("Enter contract data");
        System.out.print("Number: ");
        int number = sc.nextInt();
        if (number < 1) throw new InputMismatchException();
        validInput = true;
      } catch (InputMismatchException e) {
        sc.nextLine();
      }
    }

    validInput = false;
    String date = "";
    Calendar calendar = null;
    while (!validInput) {
      try {
        System.out.print("Date (dd/MM/yyyy): ");
        date = sc.next();
        if (!date.matches("\\d{2}/\\d{2}/\\d{4}")) throw new InputMismatchException();
        calendar = separateDate(date);
        validInput = true;
      } catch (InputMismatchException e) {
        sc.nextLine();
      }
    }

    validInput = false;
    double contractValue = 0;
    while (!validInput) {
      try {
        System.out.print("Contract value: ");
        contractValue = sc.nextDouble();
        if (contractValue < 1) throw new InputMismatchException();
        validInput = true;
      } catch (InputMismatchException e) {
        sc.nextLine();
      }
    }

    validInput = false;
    int numberOfInstallments = 0;
    while (!validInput) {
      try {
        System.out.print("Enter number of installments: ");
        numberOfInstallments = sc.nextInt();
        if (numberOfInstallments < 1) throw new InputMismatchException();
        validInput = true;
      } catch (InputMismatchException e) {
        sc.nextLine();
      }
    }

    System.out.println("\n______________________________________________________");
    System.out.println("Installments:");
    Double[] installments = calculateInstallments(contractValue, numberOfInstallments);
    printInstallments(calendar, installments);

    sc.close();
  }

  private static Calendar separateDate(String date) {
    String[] dateArray = date.split("/");
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateArray[0]));
    calendar.set(Calendar.MONTH, Integer.parseInt(dateArray[1]));
    calendar.set(Calendar.YEAR, Integer.parseInt(dateArray[2]));
    return calendar;
  }

  private static Double[] calculateInstallments(double contractValue, int numberOfInstallments) {
    Double[] installments = new Double[numberOfInstallments];
    for (int i = 0; i < numberOfInstallments; i++) {
      installments[i] = contractValue / numberOfInstallments;
      installments[i] += installments[i] * (0.01 * (i + 1));
      installments[i] += installments[i] * 0.02;
    }
    return installments;
  }

  private static void printInstallments(Calendar calendar, Double[] installments) {
    DecimalFormat df = new DecimalFormat("0.00");
    for (int i = 0; i < installments.length; i++) {
      calendar.add(Calendar.MONTH, 1);
      String dayFormatted = padStart(calendar.get(Calendar.DAY_OF_MONTH), "0", 2);
      String monthFormatted = padStart(calendar.get(Calendar.MONTH), "0", 2);
      String yearFormatted = padStart(calendar.get(Calendar.YEAR), "0", 4);
      System.out.println(
        dayFormatted + "/" +
        monthFormatted  + "/" +
        yearFormatted + " - " +
        "R$" + df.format(installments[i])
      );
    }
  }

  private static String padStart(int number, String filled, int length) {
    String numberString = Integer.toString(number);
    if (numberString.length() >= length) return numberString;
    return filled.repeat(length - numberString.length()) + numberString;
  }

  private static void clearConsole() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }
}

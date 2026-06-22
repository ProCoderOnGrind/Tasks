import java.util.Scanner;

public class ushtrimi_7 {
    public static int collect_data() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the data in this order: card number / card expiration date / verification value / postal zip code / promotion code");

        String card_number = scanner.nextLine();
        if (card_number.length() > 16 || card_number.length() < 12) {
            System.out.println("The card number isn't the correct length");
            return 0;
        }

        String card_expiration = scanner.nextLine();
        if (card_expiration.length() != 4) {
            System.out.println("The card expiration code isn't the correct length");
            return 0;
        }

        int verification_value = scanner.nextInt();
        if (verification_value < 100 || verification_value > 9999) {
            System.out.println("The verification code isn't the correct length");
            return 0;
        }
        scanner.nextLine();

        String postal_zip_code = scanner.nextLine();
        if (postal_zip_code.length() != 5) {
            System.out.println("The postal zip code isn't the correct length");
            return 0;
        }

        String promotion_code = scanner.nextLine();
        if (promotion_code.length() != 8) {
            System.out.println("The promotion code isn't the correct length");
            return 0;
        }
        return 1;
    }

    static void mode_2(double mortgage_amount, double interest_rate, double max_payment) {
        if (mortgage_amount <= 0 || interest_rate <= 0 || max_payment <= 0) {
            System.out.println("Please enter valid positive numbers.");
            return;
        }

        double monthly_rate = interest_rate / 100 / 12;
        double monthly_payment = 0;
        int months = 1;

        while (months < 1000) {
            monthly_payment = mortgage_amount *
                (monthly_rate * Math.pow(1 + monthly_rate, months)) /
                (Math.pow(1 + monthly_rate, months) - 1);

            if (monthly_payment <= max_payment) break;
            months++;
        }

        double total_payment = monthly_payment * months;

        if (total_payment > 1.4 * mortgage_amount) {
            System.out.println("Warning !!! Your total payment is 40% bigger than your mortgage amount!");
        }

        System.out.println("Period of months: " + months);
        System.out.println("Your monthly payment is: " + monthly_payment);
        System.out.println("Your total payment is: " + total_payment);
    }

    static void mode_1(double mortgage_amount, double interest_rate, int years) {
        if (mortgage_amount <= 0 || interest_rate <= 0 || years <= 0) {
            System.out.println("The values you entered are not valid. Try again");
            return;
        }

        double monthly_rate = interest_rate / 100 / 12;
        int months = years * 12;

        double monthly_payment = mortgage_amount *
            (monthly_rate * Math.pow(1 + monthly_rate, months)) /
            (Math.pow(1 + monthly_rate, months) - 1);

        double total_payment = monthly_payment * months;

        System.out.println("Your monthly payment is: " + monthly_payment);
        System.out.println("Your total payment is: " + total_payment);

        if (total_payment > 1.4 * mortgage_amount) {
            System.out.println("Warning !!! Your total payment is 40% bigger than your mortgage amount!");
        }
    }

    public static void main(String[] args) {
        if (collect_data() == 0) return;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose between mode 1 and mode 2");
        int chosen = scanner.nextInt();

        switch (chosen) {
            case 1: {
                System.out.println("Enter these values in this order: amount / annual rate / years");
                double amount = scanner.nextDouble();
                double annual_rate = scanner.nextDouble();
                int years = scanner.nextInt();
                mode_1(amount, annual_rate, years);
                break;
            }
            case 2: {
                System.out.println("Enter these values in this order: amount / annual rate / max payment");
                double amount = scanner.nextDouble();
                double annual_rate = scanner.nextDouble();
                double max_payment = scanner.nextDouble();
                mode_2(amount, annual_rate, max_payment);
                break;
            }
            default:
                System.out.println("error");
        }
    }
}

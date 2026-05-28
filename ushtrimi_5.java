import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ushtrimi_5 {

    static HashMap<String, Double> katalog = new HashMap<>();

    static void inicializoKatalog() {
        katalog.put("Internet Bazik",   29.99);
        katalog.put("Internet Premium", 59.99);
        katalog.put("TV Standarde",     19.99);
        katalog.put("TV Premium",       39.99);
        katalog.put("Plan Mobil",       24.99);
    }

    static double rregulliFiksPerProdukt(ArrayList<Double> cmimet) {
        double zbritja = 0;
        for (double cmimi : cmimet) {
            if      (cmimi < 20) zbritja += 1.0;
            else if (cmimi < 35) zbritja += 3.0;
            else                 zbritja += 6.0;
        }
        return zbritja;
    }

    static double rregulliPctPerProdukt(ArrayList<Double> cmimet) {
        double zbritja = 0;
        for (double cmimi : cmimet) {
            if      (cmimi < 20) zbritja += cmimi * 0.05;
            else if (cmimi < 35) zbritja += cmimi * 0.10;
            else                 zbritja += cmimi * 0.15;
        }
        return zbritja;
    }

    static double rregulliSipasTotalit(double total) {
        if      (total < 50)  return 0;
        else if (total < 100) return 5;
        else if (total < 150) return 12;
        else                  return 20;
    }

    static double rregulliNumriArtikujve(int count) {
        if      (count == 1) return 0;
        else if (count == 2) return 2;
        else if (count == 3) return 5;
        else                 return 10;
    }

    public static void main(String[] args) {
        inicializoKatalog();
        Scanner sc = new Scanner(System.in);

        System.out.println("Hej! Mire se erdhe tek kalkulatori i zbritjeve.");

        while (true) {

            ArrayList<String> emer = new ArrayList<>(katalog.keySet());
            System.out.println("\nJa cfare kemi per momentin:\n");
            for (int i = 0; i < emer.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + emer.get(i) + " - $" + katalog.get(emer.get(i)));
            }

            System.out.println("\nHidh numrat e produkteve qe do te marresh, te ndare me hapesire.");
            System.out.print("Te lutem, shkruaj zgjedhjet: ");
            String line = sc.nextLine().trim();

            ArrayList<String> teZgjedhur = new ArrayList<>();
            ArrayList<Double> cmimet     = new ArrayList<>();

            for (String token : line.split("\\s+")) {
                try {
                    int idx = Integer.parseInt(token) - 1;
                    if (idx >= 0 && idx < emer.size()) {
                        teZgjedhur.add(emer.get(idx));
                        cmimet.add(katalog.get(emer.get(idx)));
                    } else {
                        System.out.println("  (po e leme jashte '" + token + "' sepse nuk eshte numer-valid)");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("  (po e leme jashte '" + token + "' sepse nuk eshte numer)");
                }
            }

            if (teZgjedhur.isEmpty()) {
                System.out.println("Asgje nuk u zgjodh. Po e provojme perseri.");
                continue;
            }

            double totaliOrigjinal = 0;
            for (double p : cmimet) totaliOrigjinal += p;

            System.out.println("\nZgjidh si do te doje ta llogarisnim zbritjen:");
            System.out.println("  1 - Zbritje fikse per secilin produkt");
            System.out.println("  2 - Zbritje me perqindje per produktet");
            System.out.println("  3 - Zbritje bazuar ne total");
            System.out.println("  4 - Zbritje sipas numrit te artikujve");
            System.out.println("  0 - Provo te gjitha dhe shiko rezultatin");
            System.out.print("Zgjidh nje opsion (0-4): ");
            String ruleLine = sc.nextLine().trim();
            int ruleChoice = 0;
            try {
                ruleChoice = Integer.parseInt(ruleLine);
                if (ruleChoice < 0 || ruleChoice > 4) ruleChoice = 0;
            } catch (NumberFormatException e) {
                ruleChoice = 0;
            }

            double z1 = 0, z2 = 0, z3 = 0, z4 = 0;
            if (ruleChoice == 0) {
                z1 = rregulliFiksPerProdukt(cmimet);
                z2 = rregulliPctPerProdukt(cmimet);
                z3 = rregulliSipasTotalit(totaliOrigjinal - z1 - z2);
                z4 = rregulliNumriArtikujve(teZgjedhur.size());
                double totaliZbritje = Math.min(z1 + z2 + z3 + z4, totaliOrigjinal);
                z4 = Math.min(z4, Math.max(0, totaliOrigjinal - (z1 + z2 + z3)));
                double totaliPerTuPaguar = totaliOrigjinal - totaliZbritje;

                System.out.println("\nKëto janë zgjedhjet që bëre:");
                for (int i = 0; i < teZgjedhur.size(); i++) {
                    System.out.println("  - " + teZgjedhur.get(i) + " ($" + String.format("%.2f", cmimet.get(i)) + ")");
                }

                System.out.printf("%nTotali fillestar:    $%.2f%n", totaliOrigjinal);
                System.out.printf("Zbritje fikse:        -$%.2f  (sipas çmimit)%n", z1);
                System.out.printf("Zbritje me perqindje: -$%.2f  (sipas cmimit)%n", z2);
                System.out.printf("Zbritje pakete:       -$%.2f  (sipas totalit)%n", z3);
                System.out.printf("Zbritje besnikerie:   -$%.2f  (sipas numrit te artikujve)%n", z4);
                System.out.printf("Totali per pagese:    $%.2f%n", totaliPerTuPaguar);
            } else {
                if (ruleChoice == 1) z1 = rregulliFiksPerProdukt(cmimet);
                else if (ruleChoice == 2) z2 = rregulliPctPerProdukt(cmimet);
                else if (ruleChoice == 3) z3 = rregulliSipasTotalit(totaliOrigjinal);
                else if (ruleChoice == 4) z4 = rregulliNumriArtikujve(teZgjedhur.size());

                double totaliZbritje = Math.min(z1 + z2 + z3 + z4, totaliOrigjinal);
                double totaliPerTuPaguar = totaliOrigjinal - totaliZbritje;

                System.out.println("\nKëto janë zgjedhjet që bëre:");
                for (int i = 0; i < teZgjedhur.size(); i++) {
                    System.out.println("  - " + teZgjedhur.get(i) + " ($" + String.format("%.2f", cmimet.get(i)) + ")");
                }

                System.out.printf("%nTotali fillestar:    $%.2f%n", totaliOrigjinal);
                switch (ruleChoice) {
                    case 1:
                        System.out.printf("Zbritje fikse:        -$%.2f  (sipas çmimit)%n", z1);
                        break;
                    case 2:
                        System.out.printf("Zbritje me perqindje: -$%.2f  (sipas cmimit)%n", z2);
                        break;
                    case 3:
                        System.out.printf("Zbritje pakete:       -$%.2f  (sipas totalit)%n", z3);
                        break;
                    case 4:
                        System.out.printf("Zbritje besnikerie:   -$%.2f  (sipas numrit te artikujve)%n", z4);
                        break;
                }
                System.out.printf("Totali per pagese:    $%.2f%n", totaliPerTuPaguar);
            }

            System.out.print("\nDeshiron te provosh nje pakete tjeter? (po / jo): ");
            String again = sc.nextLine().trim().toLowerCase();
            if (!again.equals("yes") && !again.equals("y") && !again.equals("po") && !again.equals("p")) {
                System.out.println("\nMire u pafshim! Faleminderit qe perdore programin.");
                break;
            }
        }

        sc.close();
    }
}

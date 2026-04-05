import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

abstract class Figura {
    abstract double Llogarit_sip();
    abstract String getType();
}

class Drejtkendeshi extends Figura {
    private double b;
    private double h;
    Drejtkendeshi(double b, double h) {
        this.b = b;
        this.h = h;
    }
    double Llogarit_sip() {
        return this.b * this.h;
    }
    String getType() {
        return "Drejtkendeshi";
    }
}

class Trekendeshi extends Figura {
    private double b;
    private double h;
    Trekendeshi(double b, double h) {
        this.b = b;
        this.h = h;
    }
    double Llogarit_sip() {
        return (this.b * this.h) / 2;
    }
    String getType() {
        return "Trekendeshi";
    }
}

class Rrethi extends Figura {
    private double r;
    Rrethi(double r) {
        this.r = r;
    }
    double Llogarit_sip() {
        return Math.PI * this.r * this.r;
    }
    String getType() {
        return "Rrethi";
    }
}

public class ushtrimi_2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Figura> figures = new ArrayList<>();
        while (true) {
            System.out.print("Zgjidhni 1 per rreth 2 per drejtkendesh dhe 3 per trekendesh kurse per te dale nga programi vendos -1");
            int a = sc.nextInt();
            if (a == -1) break;
            switch (a) {
                case 1: {
                    System.out.print("Vendose rrezen: ");
                    double r = sc.nextDouble();
                    Rrethi rrethi = new Rrethi(r);
                    figures.add(rrethi);
                    break;
                }
                case 2: {
                    System.out.print("vendosni baze dhe lartesi: ");
                    double b = sc.nextDouble();
                    double h = sc.nextDouble();
                    Drejtkendeshi drejtkendesh = new Drejtkendeshi(b, h);
                    figures.add(drejtkendesh);
                    break;
                }
                case 3: {
                    System.out.print("Vendosi parametrat pra baze dhe lartesi: ");
                    double b = sc.nextDouble();
                    double h = sc.nextDouble();
                    Trekendeshi trekendeshi = new Trekendeshi(b, h);
                    figures.add(trekendeshi);
                    break;
                }
                default:
                    System.out.println("keni vendosur numrat e gabuar");
                    break;
            }
        }
        // Sort by area
        Collections.sort(figures, Comparator.comparingDouble(Figura::Llogarit_sip));
        System.out.println("Figurat e renditura sipas siperfaqes:");
        for (Figura f : figures) {
            System.out.println(f.getType() + " ka siperfaqe: " + f.Llogarit_sip());
        }
    }
}
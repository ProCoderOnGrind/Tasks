import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class Pika {
    double x;
    double y;
    double ruaj_distancen;

    Pika(double x,double y){
        set_X(x);
        set_Y(y);
          
        }
    //Kam shtuar edhe encapsulation
    public void set_X(double x){
        this.x = x;
    }
    public void set_Y(double y){
        this.y = y;
    }
    public double get_X(){
        return this.x;
    }
    public double get_Y(){
        return this.y;
    }

    public void kalkuloDistancen(Pika kryesore){
        double diference_x = kryesore.get_X() - this.x;
        double diference_y = kryesore.get_Y() - this.y;
        this.ruaj_distancen = Math.sqrt(diference_x * diference_x + diference_y * diference_y);
    }
}
public class ushtrimi_1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Pika> pikat = new ArrayList<>();
        System.out.print("Vendosni koordinatat e pikes kryesore: ");
        double kord_x = scanner.nextDouble();
        double kord_y = scanner.nextDouble();
        Pika pika_kryesore = new Pika(kord_x,kord_y);
        pikat.add(pika_kryesore);
        System.out.println("Vendosni kordinatat e pikave te tjera.");
        System.out.print("Per te dale nga programi vendosni -1");
        while(true){
            double x = scanner.nextDouble();
            double y = scanner.nextDouble();
            if(x == -1 || y == -1) break;
            Pika pika_e_re = new Pika(x,y);
            pikat.add(pika_e_re);
            for(Pika p : pikat){
                p.ruaj_distancen = Math.sqrt((pika_e_re.get_X() - p.get_X()) * (pika_e_re.get_X() - p.get_X()) + (pika_e_re.get_Y() - p.get_Y()) * (pika_e_re.get_Y() - p.get_Y()));
            }
            Collections.sort(pikat, new Comparator<Pika>() {
                public int compare(Pika p1, Pika p2){
                    return Double.compare(p1.ruaj_distancen, p2.ruaj_distancen);
                }
            });
            System.out.println("Pikat e renditura: ");
            for(Pika p : pikat){
                System.out.println(p.get_X() + " " + p.get_Y());
            }
        }
        
    }
}

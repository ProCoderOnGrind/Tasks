import java.util.Scanner;
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
        double diference_y = kryesore.get_X() - this.y;
        this.ruaj_distancen = Math.sqrt(diference_x * diference_x + diference_y * diference_y);
    }
}
public class ushtrimi_1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Pika[] arr = new Pika[100];
        System.out.print("Vendosni koordinatat e pikes kryesore: ");
        double kord_x = scanner.nextDouble();
        double kord_y = scanner.nextDouble();
        Pika pika_kryesore = new Pika(kord_x,kord_y);
        System.out.println("Vendosni kordinatat e pikave te tjera.");
        System.out.print("Per te dale nga programi vendosni -1");
        int count = 0;
        while(true){
            double x = scanner.nextDouble();
            double y = scanner.nextDouble();
            int index = count;
            Pika pika_e_re = new Pika(x,y);
            pika_e_re.kalkuloDistancen(pika_kryesore);
            if(x == -1 || y == -1)break;

            for(int i = 0;i < count;i++){
                if(pika_e_re.ruaj_distancen < arr[i].ruaj_distancen){
                    index = i;
                    break;
                }
            }

            for(int i = count;i > index;i--){
                arr[i] = arr[i-1];

            }
            arr[index] = pika_e_re;
            count++;
            System.out.println("Pikat e renditura: ");
            for(int i = 0;i < count;i++){
                System.out.println(arr[i].get_X() + " " + arr[i].get_Y());
            }



                
        }
        
    }
}

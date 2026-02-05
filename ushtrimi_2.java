import java.util.Scanner;
class Figurat{
    protected double b;
    protected double h;
    Figurat(double b,double h){
        this.b = b;
        this.h = h;

    }
    double Llogarit_sip(){
        return this.b * this.h;
    }
   
}
class Drejtkendeshi extends Figurat{
    Drejtkendeshi(double b, double h){
        super(b,h);
    }
}

class Trekendeshi extends Figurat{
    Trekendeshi(double b, double h){
        super(b,h);
    }
    double Llogarit_sip(){
        return (this.b * this.h)/2;
    }
}
class Rrethi{
    double r;
    Rrethi(double r){
        this.r = r;
    }
    double Llogarit_sip(){
        //Po e marr pie si 3.14 nese ska problem
        return 3.14 * this.r * this.r;
    }
}
public class ushtrimi_2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while(true){
        System.out.print("Zgjidhni 1 per rreth 2 per drejtkendesh dhe 3 per trekendesh kurse per te dale nga programi vendos -1");
        int a = sc.nextInt();
        if(a == -1)break;
        switch(a){
            case 1: {
                System.out.print("Vendose rrezen: ");
                double r = sc.nextDouble();
                Rrethi rrethi = new Rrethi(r);
                System.out.println("Siperfaqja e rrethit eshte: " + rrethi.Llogarit_sip());
                break;
            }
              case 2: {
                System.out.print("vendosni baze dhe lartesi: ");
                double b = sc.nextDouble();
                double h = sc.nextDouble();
                Drejtkendeshi drejtkendesh = new Drejtkendeshi(b,h);
                System.out.println("Siperfaqja e drejtkendeshit eshte: " + drejtkendesh.Llogarit_sip());
                break;

            }

            case 3:{
                System.out.print("Vendosi parametrat pra baze dhe lartesi: ");
                double b = sc.nextDouble();
                double h = sc.nextDouble();
                Trekendeshi trekendeshi = new Trekendeshi(b,h);
                System.out.println("Trekendeshi ka nje siperfaqje prej: " + trekendeshi.Llogarit_sip());
                break;
            }
            default: 
            System.out.println("keni vendosur numrat e gabuar");
            break;
                
            
                

        }
        }
       
        



    }}
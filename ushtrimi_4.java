import java.util.Scanner;
public class ushtrimi_4{
        public static boolean isValidEmail(String email) {
        //Per keto funksione hulumtova ne internet sepse nuk dija se si mund te kontrolloja per formatin e emailit.
        if (email == null || email.isEmpty()) return false;
        if (!email.contains("@")) return false;
        if (!email.contains(".")) return false;

        
        int atPos = email.indexOf('@');//Merr indexin e pare ku gjehet @
        int dotPos = email.lastIndexOf('.');//Merr indexin e fundit ku gjehet .
        if (atPos <= 0) return false;//kontrollon nese eshte me e madhe e barabarte me zero pasi nese nuk gjehet do te kthej vleren -1.
        //kontrollon nese @ eshte para . sepse ne cdo format email @ eshte para . perndryshe beje return false
        if (dotPos < atPos) return false;

        return true;
    }

        public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();
            String read = "";
            int index = 0;
            int count = 0;
            boolean[] valid = new boolean[6];
            String[] fields = input.split("~", -1); // keep empty fields
            if (fields.length != 6) {
            System.out.println("Sasia e gabuar e ~ (duhen 5 pra per 6 fusha)");
            return;
            }
            
            if(fields[0].length() <= 18 && fields[0].length() >= 1){
                valid[0] = true;
            } 
            else{
                
                valid[0] = false;
            }

            if(fields[1].length() != 4){
                valid[1] = false;
                
            }
            else valid[1] = true;
            for(int i = 0;i < fields[2].length();i++){
                if(fields[2].charAt(i) == '.')valid[2] = true;
            }
            if(fields[3].length() == 0 || fields[3].length() == 5)valid[3] = true;
            else valid[3] = false;
            if(fields[4].length() <= 10)valid[4] = true;
            else valid[4] = false;
            if(fields[5].length() <= 50){
                if(fields[5].isEmpty())valid[5] = true;
                else if(isValidEmail(fields[5])){
                    valid[5] = true;
                }
            else valid[5] = false;
            }
            else valid[5] = false;
    if(!valid[0]) System.out.println("Numri i kartes eshte gabim");
    if(!valid[1]) System.out.println("Formati i dates eshte gabim");
    if(!valid[2]) System.out.println("Sasia nuk eshte numer dhjetor");
    if(!valid[3]) System.out.println("Formati i zipit eshte gabim");
    if(!valid[4]) System.out.println("Formati i promocionit eshte gabim");
    if(!valid[5]) System.out.println("Formati i emailit eshte gabim");

    if(valid[0] && valid[1] && valid[2] && valid[3] && valid[4] && valid[5]){
        //Kete e mora te gatshme se nuk dija si ta beja json format
        String json = String.format(
    "{\"CardNo\":\"%s\", \"ExpDate\":\"%s\", \"Amount\":\"%s\", \"ZIP\":\"%s\", \"Promotion\":\"%s\", \"Email\":\"%s\"}",
    fields[0], fields[1], fields[2], fields[3], fields[4], fields[5]);
    System.out.println(json);

    }

        }
    }

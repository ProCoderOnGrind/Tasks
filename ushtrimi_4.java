import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.YearMonth;

public class ushtrimi_4{
        public static boolean isValidEmail(String email) {
        //Per keto funksione hulumtova ne internet sepse nuk dija se si mund te kontrolloja per formatin e emailit.
        if (email == null || email.isEmpty()) return false;
        if (!email.contains("@")) return false;
        if (!email.contains(".")) return false;
        
        //Kontrollo nese ka sakte nje @
        int atCount = 0;
        for(int i = 0; i < email.length(); i++){
            if(email.charAt(i) == '@') atCount++;
        }
        if(atCount != 1) return false;
        if (email.contains("..")) return false;

        int atPos = email.indexOf('@');//Merr indexin e pare ku gjehet @
        int dotPos = email.lastIndexOf('.');//Merr indexin e fundit ku gjehet .
        if (atPos <= 0) return false;//kontrollon nese eshte me e madhe e barabarte me zero pasi nese nuk gjehet do te kthej vleren -1.
        //kontrollon nese @ eshte para . sepse ne cdo format email @ eshte para . perndryshe beje return false
        if (dotPos < atPos) return false;

        return true;
    }
    
    public static boolean isValidCardNumber(String card){
        //Kontrollon nese kartat kane gjatesi 1-18 dhe permban vetem shifrat 0-9
        if(card.isEmpty() || card.length() > 18) return false;
        for(int i = 0; i < card.length(); i++){
            if(card.charAt(i) < '0' || card.charAt(i) > '9') return false;
        }
        return true;
    }
    
    public static boolean isValidDate(String date){
        //Kontrollon nese data eshte 4 karaktere dhe muaji 1-12
        if(date.length() != 4) return false;
        for(int i = 0; i < 4; i++){
            if(date.charAt(i) < '0' || date.charAt(i) > '9') return false;
        }
        int month = Integer.parseInt(date.substring(0, 2));
        int year = Integer.parseInt(date.substring(2));
        if(month < 1 || month > 12) return false;
        
        //Kontrollo nese viti nuk eshte ne te kaluaren
        int currentYear = YearMonth.now().getYear() % 100;
        if(year < currentYear) return false;
        
        return true;
    }
    
    public static boolean isValidAmount(String amount){
        //Kontrollon nese sasia eshte nje numer valid decimal
        if(amount.isEmpty()) return false;
        int dotCount = 0;
        for(int i = 0; i < amount.length(); i++){
            char c = amount.charAt(i);
            if(c == '.'){
                dotCount++;
                if(dotCount > 1) return false;
            } else if(c < '0' || c > '9'){
                return false;
            }
        }
        try{
            double val = Double.parseDouble(amount);
            return val >= 0;
        } catch(NumberFormatException e){
            return false;
        }
    }
    
    public static boolean isValidZip(String zip){
        //ZIP mund te jete bosh ose te jete numer
        if(zip.isEmpty()) return true;
        if(zip.length() != 5) return false;
        for(int i = 0; i < zip.length(); i++){
            if(zip.charAt(i) < '0' || zip.charAt(i) > '9') return false;
        }
        return true;
    }
    
    public static boolean isValidPromotion(String promo){
        //Promotion eshte char me gjatesi variabel deri 10, vetem letra dhe numra
        if(promo.isEmpty()) return true;
        if(promo.length() > 10) return false;
        for(int i = 0; i < promo.length(); i++){
            char c = promo.charAt(i);
            if(!((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9') || c == ' ')){
                return false;
            }
        }
        return true;
    }

        public static void main(String[] args) throws Exception {
            String transactionsFile = args.length > 0 ? args[0] : "transactions.txt";
            String outputFile = args.length > 1 ? args[1] : "output.json";
            
            java.util.List<String> lines = Files.readAllLines(Paths.get(transactionsFile), StandardCharsets.UTF_8);
            java.util.List<String> validJsons = new java.util.ArrayList<>();
            
            for(String input : lines){
                String read = "";
                int index = 0;
                int count = 0;
                boolean[] valid = new boolean[6];
                String[] fields = input.split("~", -1); // keep empty fields
                if (fields.length != 6) {
                    System.out.println("Rreshti: " + input + " - Sasia e gabuar e ~ (duhen 5 pra per 6 fusha)");
                    continue;
                }
                
                //Validim i kartes (0-18 karaktere, vetem shifrat 0-9)
                valid[0] = isValidCardNumber(fields[0]);
                
                //Validim i dates (4 karaktere, muaji 1-12, viti jo ne te kaluaren)
                valid[1] = isValidDate(fields[1]);
                
                //Validim i shuames (duhet te jete numer decimal valid)
                valid[2] = isValidAmount(fields[2]);
                
                //Validim i ZIP (bosh ose 5 shifra)
                valid[3] = isValidZip(fields[3]);
                
                //Validim i promocionit (bosh ose 1-10 caractere alfanumerike)
                valid[4] = isValidPromotion(fields[4]);
                
                //Validim i emailit (bosh ose valid email)
                if(fields[5].isEmpty()){
                    valid[5] = true;
                } else {
                    valid[5] = isValidEmail(fields[5]);
                }
                
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
                    validJsons.add(json);
                }
            }
            
            //Shkruaj JSON array ne file
            StringBuilder jsonOutput = new StringBuilder();
            jsonOutput.append("[\n");
            for(int i = 0; i < validJsons.size(); i++){
                jsonOutput.append("  ").append(validJsons.get(i));
                if(i < validJsons.size() - 1) jsonOutput.append(",");
                jsonOutput.append("\n");
            }
            jsonOutput.append("]");
            
            Files.write(Paths.get(outputFile), jsonOutput.toString().getBytes(java.nio.charset.StandardCharsets.UTF_8));
            System.out.println("Transaksione valid: " + validJsons.size());
    }
}

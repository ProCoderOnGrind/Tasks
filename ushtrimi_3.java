import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
public class ushtrimi_3{
    public static void main(String[] args) throws Exception {
        //Ketu kerkova ne internet se si inicializohej nje hashmap dhe gjithashtu funksionet e saj se nuk i di ne java vec ne C++.
        HashMap<String, Integer> map = new HashMap<>();
        String word = "";
        String sentence = "";
        String mx_sentence = "";
        int max_sentence = -9999999;
        int count_sentences = 0;
        int count_longest_sentence = 0;
        String str = Files.readString(Paths.get(args.length > 0 ? args[0] : "input.txt"), StandardCharsets.UTF_8);


        //Per te numeruar sa fjali ka thjesht numerojme sa pika ka
        for(int i = 0; i < str.length();i++){
            char c = str.charAt(i);
            if(c == '.' || c == '!' || c == '?'){
                count_sentences++;
            }
        }
        boolean inWord = false;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            sentence += c;

            if (Character.isLetterOrDigit(c)) {
                if (!inWord) {
                    count_longest_sentence++;
                    inWord = true;
                }
                word += Character.toLowerCase(c);
            } else {
                if (!word.isEmpty()) {
                    map.put(word, map.getOrDefault(word, 0) + 1);
                    word = "";
                }
                inWord = false;
            }

            if (c == '.' || c == '?' || c == '!') {
                if (count_longest_sentence > max_sentence) {
                    max_sentence = count_longest_sentence;
                    mx_sentence = sentence.trim();
                }
                sentence = "";
                count_longest_sentence = 0;
                inWord = false;
            }
        }
        if (!word.isEmpty()) {
            map.put(word, map.getOrDefault(word, 0) + 1);
            word = "";
        }

        System.out.println("Top 10 fjalet me te perdorura:");
        Map<String, Integer> mapCopy = new HashMap<>(map);
        for (int i = 0; i < 10 && !mapCopy.isEmpty(); i++) {
            String maxWord = "";
            int maxCount = 0;
            for (Map.Entry<String, Integer> entry : mapCopy.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    maxWord = entry.getKey();
                }
            }
            if (maxWord.isEmpty()) {
                break;
            }
            System.out.println(maxWord + " : " + maxCount);
            mapCopy.remove(maxWord);
        }

        int uniqueCount = map.size();
        System.out.println("Numri i fjalave unike: " + uniqueCount);
        System.out.println("Fjalet Unike jane: ");
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                System.out.println(entry.getKey());
            }
        }

        System.out.println("Fjalia me e gjate eshte: " + mx_sentence + "|||||||||||||||FJALET: " + max_sentence);
        System.out.println("Ne total kemi kaq fjali: " + count_sentences);
    }
}


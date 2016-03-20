package crypt.lab.two;


import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;

public class Verifier {
    public boolean verify(String text,LinkedHashSet<String> alphabet){
        return verifyLangByBigrams(text)&&verifyLangByLetters(text,alphabet);
    }
    public boolean verifyLangByLetters(String text,LinkedHashSet<String> alphabet){
        char[] txt=text.toLowerCase().toCharArray();
        HashMap<String,Integer> frequency_table= new HashMap<>();
        LinkedList<String> list= new LinkedList<>(alphabet);
        for(String obj:list){
            frequency_table.put(obj,0);
        }
        for (char letter : txt) {
            if (frequency_table.containsKey(String.valueOf(letter))) {
                frequency_table.computeIfPresent(String.valueOf(letter), (k, v) -> v + 1);
            }
        }
        BigramsUtils temp=new BigramsUtils();
        HashMap<String,Integer> ordered_freq_table=temp.sort_dict_by_value(frequency_table);
        HashMap<String,Integer> most_common = temp.get_sub_dict(0,5,ordered_freq_table);
        HashMap<String,Integer> less_common = temp.get_sub_dict(ordered_freq_table.size()-5,ordered_freq_table.size(),ordered_freq_table);
        //System.out.println(most_common);
        //System.out.println(less_common);
        return most_common.containsKey("о") && most_common.containsKey("а") && most_common.containsKey("е")
                && (less_common.containsKey("ф") || less_common.containsKey("щ")|| less_common.containsKey("ь"));
    }
    public boolean verifyLangByBigrams(String text){
        Statistics stats=new Statistics();
        LinkedList<String> unreal=stats.get_unreal_in_russian();
        for (String bigram:unreal){
            if (text.contains(bigram)){
                return false;
            }
        }
        return true;
    }
}

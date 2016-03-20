package crypt.lab.two;

import java.util.LinkedHashSet;
import java.util.LinkedList;

public class Statistics {
    public LinkedHashSet<String> russian_dictionary(){
        LinkedHashSet<String> dict= new LinkedHashSet<>();
        for (char i='а';i<='я';i++){
            if (i=='ъ'){
                continue;
            }
            dict.add(String.valueOf(i));
        }
        return dict;
    }
    public LinkedList<String> get_common_in_russian(){
        LinkedList<String> result = new LinkedList<>();
        result.add("ст");
        result.add("но");
        result.add("то");
        result.add("на");
        result.add("ен");
        return result;
    }
    public LinkedList<String> get_unreal_in_russian(){
        LinkedList<String> result = new LinkedList<>();
        result.add("бб");
        result.add("бв");
        result.add("бй");
        result.add("бп");
        result.add("бт");
        result.add("бх");
        result.add("бч");
        result.add("аь");
        result.add("аы");
        result.add("аы");
        result.add("гб");
        result.add("гг");
        result.add("гй");
        result.add("гм");
        result.add("гп");
        result.add("дй");
        result.add("жж");
        result.add("жж");
        result.add("йй");
        result.add("лй");
        result.add("мй");
        result.add("жы");
        result.add("щы");
        result.add("шы");
        result.add("йю");
        result.add("йя");
        result.add("йь");
        result.add("ьй");
        result.add("иэи");
        return result;
    }
}

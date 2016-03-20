package crypt.lab.two;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class BigramsUtils {
    public HashMap<String,Integer> getFiveTopBigrams(File file){
        try {
            LinkedHashSet<String> letters_dict= create_dictionary_from_file(file);
            HashMap<String,Integer> bigrams_dict=toBigrams(letters_dict);
            bigrams_dict=frequency(bigrams_dict,file);
            HashMap<String,Integer> orderedDictionary= sort_dict_by_value(bigrams_dict);
            MapToFile(orderedDictionary,"result.txt");
            return get_sub_dict(0,5,orderedDictionary);
        } catch (FileNotFoundException e) {
            System.out.println("Error with writng into file");
        }
        return null;
    }
    public LinkedHashSet<String> create_dictionary_from_file(File file) throws FileNotFoundException {
        Scanner sc=new Scanner(file);
        LinkedHashSet<String> dict= new LinkedHashSet<>();
        while (sc.hasNext()){
            char[] word = sc.next().toCharArray();
            for (char letter:word){
                dict.add(new StringBuilder().append(letter).toString());
            }
        }
        sc.close();
        return dict;
    }
    public  LinkedHashSet<String> create_dictionary_from_path(String filename) throws FileNotFoundException {
        File file=new File(filename);
        return create_dictionary_from_file(file);
    }
    public HashMap<String,Integer> toBigrams(LinkedHashSet<String> dictionary){
        HashMap<String,Integer> bigram_dict= new HashMap<>();
        LinkedList<String> list= new LinkedList<>(dictionary);
        Object[] array=list.toArray();
        for (int i=0;i<array.length;i++){
            for (int j=0;j<array.length;j++){
            bigram_dict.put(array[i].toString()+array[j].toString(),0);
            }
        }
        for (Object item : array) {
            bigram_dict.put(item.toString() + item.toString(), 0);
        }
        return bigram_dict;
    }
    public HashMap<String,Integer> frequency(HashMap<String,Integer> dictionary,File file) throws FileNotFoundException {
        Scanner sc=new Scanner(new BufferedReader(new FileReader(file.getPath())));
        while(sc.hasNext()){
            String word=sc.next();
            char[] letters=word.toCharArray();
            String bigram;
            for (int i=1;i<letters.length;i++){
                bigram= new StringBuilder(2).append(letters[i-1]).append(letters[i]).toString();
                if (dictionary.containsKey(bigram)){
                    dictionary.computeIfPresent(bigram, (k, v) -> v + 1);
                }
            }
        }
        return dictionary;
    }
    public void MapToFile(HashMap<String,Integer> dictionary,String filename) throws FileNotFoundException {
        Formatter file=new Formatter(filename);
        dictionary.forEach((k,v)->file.format("%s : %s\n",k,v));
        file.close();
    }
    public HashMap<String,Integer>  sort_dict_by_value(HashMap<String,Integer> bigrams_dict){
        List<Map.Entry<String,Integer>> list = new LinkedList<>(bigrams_dict.entrySet());
        Collections.sort(list, (a, b) -> b.getValue().compareTo(a.getValue()));
        HashMap<String,Integer> orderedDictionary= new LinkedHashMap<>();
        for (Map.Entry<String,Integer> entry: list){
            orderedDictionary.put(entry.getKey(),entry.getValue());
        }
        return orderedDictionary;
    }
    public HashMap<String,Integer> get_sub_dict(int start,int end,HashMap<String,Integer> dictionary){
        Set<Map.Entry<String,Integer>> values=dictionary.entrySet();
        Map.Entry<String,Integer>[] test=new Map.Entry[values.size()];
        values.toArray(test);
        HashMap<String,Integer> result= new LinkedHashMap<>();
        for (int i=start;i<end;i++){
            result.put(test[i].getKey(),test[i].getValue());
        }
        return result;
    }
}

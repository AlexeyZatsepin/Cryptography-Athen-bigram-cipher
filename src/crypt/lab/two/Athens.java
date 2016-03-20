package crypt.lab.two;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Athens {
    public void encrypt(String text,int a,int b) throws FileNotFoundException {
        String bigram,cipher="";
        Statistics stats=new Statistics();
        LinkedHashSet<String> letters_dictionary= stats.russian_dictionary();
        HashMap<String,Integer> alphabet = create_alphabet(letters_dictionary);

        for (int i=2;i<=text.length();i=i+2){
            bigram=text.substring(i-2,i);
            int x=get_number_from_bigram(bigram,alphabet);
            int y=get_Y_from_X(x,a,b);
            String temp=get_bigram_from_number(y,alphabet);
            cipher+=temp;
        }
        System.out.println(cipher);
    }
    public HashMap<String,Integer> create_alphabet(LinkedHashSet<String> letters_dictionary){
        HashMap<String,Integer> alphabet = new HashMap<>();
        int i=0;
        for (String letter:letters_dictionary){
            alphabet.put(letter,i);
            i++;
        }
        return alphabet;
    }
    public HashMap<Integer,String> get_reverse_alphabet(Map<String,Integer> alphabet){
        HashMap<Integer,String> reverse_alphabet= new HashMap<>();
        for (Map.Entry<String,Integer> entry: alphabet.entrySet()){
            reverse_alphabet.put(entry.getValue(),entry.getKey());
        }
        return reverse_alphabet;
    }
    public String get_text_from_file(File file) throws FileNotFoundException {
        Scanner sc = new Scanner(file);
        String text=sc.next();
        while (sc.hasNext()) {
            text += sc.next();
        }
        sc.close();
        return text;
    }
    public Integer get_number_from_bigram(String x1x2,Map<String,Integer> alphabet){
        char[] X=x1x2.toCharArray();
        return 31*alphabet.get(String.valueOf(X[0]))+alphabet.get(String.valueOf(X[1]));
    }
    public String get_bigram_from_number(Integer X,HashMap<String,Integer> alphabet){
        if (X<0){
            X=X+961;
        }
        HashMap<Integer,String> reverse_alphabet=get_reverse_alphabet(alphabet);
        String x1=reverse_alphabet.get(java.lang.Math.abs(X/31));
        String x2=reverse_alphabet.get(X%31);
        return x1+x2;
    }
    public Integer get_Y_from_X(Integer X,int a,int b) {
        return (a * X + b) % (31 * 31);
    }
    public Integer get_X_from_Y(Integer Y,int a,int b){
        Math math=new Math();
        return (math.reverse(a,31*31)*(Y-b))%(31*31);
    }
    public Integer[] findAB(int Y1,int Y2,int X1,int X2,int m){
        Math math=new Math();
        int a=(math.reverse(X1-X2, m * m)*(Y1-Y2))%(m * m);
        int b=(Y1-a*X1)%(m*m);
        if(b<0){
            b=b+961;
        }
        if(a<0){
            a=a+961;
        }
        return new Integer[]{a,b};
    }
    public void decrypt(File file) throws FileNotFoundException {
        BigramsUtils bigramsUtils =new BigramsUtils();
        Statistics stats=new Statistics();

        LinkedHashSet<String> letters_dictionary= stats.russian_dictionary();// letter set
        HashMap<String,Integer> bigrams_dict=bigramsUtils.toBigrams(letters_dictionary);
        bigrams_dict=bigramsUtils.frequency(bigrams_dict,file); //frequency bigrams in cipher
        bigrams_dict=bigramsUtils.sort_dict_by_value(bigrams_dict); //sort it

        Set<String> five_most_common_in_cipher=bigramsUtils.get_sub_dict(0,5,bigrams_dict).keySet();
        LinkedList<String> five_most_common_in_rus=stats.get_common_in_russian();
        ArrayList<Integer> X=new ArrayList<>();
        ArrayList<Integer> Y=new ArrayList<>();
        HashMap<String,Integer> alphabet = create_alphabet(letters_dictionary);
        X.addAll(five_most_common_in_rus.stream().map(xixj -> get_number_from_bigram(xixj, alphabet)).collect(Collectors.toList()));
        Y.addAll(five_most_common_in_cipher.stream().map(yiyj -> get_number_from_bigram(yiyj, alphabet)).collect(Collectors.toList()));
        ArrayList<Integer[]> ABresults=new ArrayList<>();
        for (int i=0;i<X.size();i++){
            for (int j=0;j<Y.size();j++){
                //ABresults.add(findAB(Y.get(i),Y.get(j),X.get(i),X.get(j),letters_dictionary.size()));
            }
        }
        ABresults.add(new Integer[]{12,20});
        String cipher=get_text_from_file(file);
        String bigram;
        String plain_text="";
        Verifier verifier=new Verifier();
        Math math=new Math();
        for (Integer[] ab:ABresults){
            if (math.gcd(ab[0],31*31)!=1) {
                continue;
            }
            for (int i=2;i<=cipher.length();i=i+2){
                bigram=cipher.substring(i-2,i);
                int y=get_number_from_bigram(bigram,alphabet);
                int x=get_X_from_Y(y,ab[0],ab[1]);
                String temp=get_bigram_from_number(x,alphabet);
                plain_text+=temp;
            }
            System.out.println(plain_text);
            if (verifier.verify(plain_text,letters_dictionary)){
                Formatter resultfile=new Formatter("plain_text-"+String.valueOf(ab[0])+"-"+String.valueOf(ab[1])+".txt");
                resultfile.format(plain_text);
                resultfile.close();
            }
            plain_text="";

        }
    }

}


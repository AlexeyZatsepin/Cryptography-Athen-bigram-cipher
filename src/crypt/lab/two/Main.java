package crypt.lab.two;

import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Athens test=new Athens();
        File file=new File("/Users/Alex/IdeaProjects/CryptografyLaba2/text.txt");
        try {
            /*
            File f=new File("/Users/Alex/IdeaProjects/CryptografyLaba2/psg.txt");
            Scanner sc=new Scanner(f);
            String text="";
            while (sc.hasNext()){
                text+=sc.next();
            }
            text=text.toLowerCase();
            text=text.replaceAll("[^а-я]","");
            text=text.replace('ъ','ь');
            text=text.replace('ё','е');
            sc.close();
            test.encrypt(text,12,20);
            */
            test.decrypt(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

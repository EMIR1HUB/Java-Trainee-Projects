package io.ylab.intensive.lesson03.Transliterator;

import java.util.HashMap;
import java.util.Map;

public class TransliteratorImpl implements Transliterator{

    private final Map<Character, String> LETTERS = new HashMap<>(){{
        put('А', "A"); put('Б', "B"); put('В', "V"); put('Г', "G"); put('Д', "D"); put('Е', "E"); put('Ё', "E");
        put('Ж', "ZH"); put('З', "Z"); put('И', "I"); put('Й', "I"); put('К', "K"); put('Л', "L"); put('М', "M");
        put('Н', "N"); put('О', "O"); put('П', "P"); put('Р', "R"); put('С', "S"); put('Т', "T"); put('У', "U");
        put('Ф', "F"); put('Х', "KH"); put('Ц', "TS"); put('Ч', "CH"); put('Ш', "SH"); put('Щ', "SHCH"); put('Ы', "Y");
        put('Ь', ""); put('Ъ', "IE"); put('Э', "E"); put('Ю', "IU"); put('Я', "IA");
    }};

    @Override
    public String transliterate(String source) {
        String newSentence = "";
        for(int i = 0; i < source.length(); i++){
            if(LETTERS.containsKey(source.charAt(i))){
                newSentence += LETTERS.get(source.charAt(i));
            }else {
                newSentence += source.charAt(i);
            }
        }
        return newSentence;
    }
}

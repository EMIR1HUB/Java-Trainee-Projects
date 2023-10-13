package io.ylab.intensive.lesson03.Transliterator;

public class TransliteratorTest {
    public static void main(String[] args) {
        Transliterator transliterator = new TransliteratorImpl();
        System.out.println(transliterator.transliterate("HELLO! ПРИВЕТ! Go, boy!"));
        System.out.println(transliterator.transliterate("Lol! БУДЬ ЩУКОЙ."));
    }
}

package com.javarush.test.level22.lesson09.task03;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/* Составить цепочку слов
В методе main считайте с консоли имя файла, который содержит слова, разделенные пробелом.
В методе getLine используя StringBuilder расставить все слова в таком порядке,
чтобы последняя буква данного слова совпадала с первой буквой следующего не учитывая регистр.
Каждое слово должно участвовать 1 раз.
Метод getLine должен возвращать любой вариант.
Слова разделять пробелом.
В файле не обязательно будет много слов.

Пример тела входного файла:
Киев Нью-Йорк Амстердам Вена Мельбурн

Результат:
Амстердам Мельбурн Нью-Йорк Киев Вена
*/
public class Solution {
    public static void main(String[] args) {
        String  text = "";
        Scanner sc = new Scanner(System.in);
        String filename = sc.nextLine();
        try
        {
            FileReader fr = new FileReader(filename);
            while (fr.ready()){
                text += (char) fr.read();
            }
        }
        catch (java.io.IOException e)
        {
            e.printStackTrace();
        }
        text = text.trim();
        text = text.replaceAll("\\u0020{2,}", "\u0020");
        String[] words = text.split("\\s");

        StringBuilder result = getLine(words);
        System.out.println(result.toString());
    }

    public static StringBuilder getLine(String... words) {
        StringBuilder result = new StringBuilder();
        if(words == null) return result;
        if (words.length == 0) return result;
        if (words.length == 1) return result.append(words[0]);

        List<String> resultList = new ArrayList<>();

        for (String word : words)
        {
            resultList.add(0, word);
            resultList = findWord(resultList, words);
            if (resultList.size() == words.length) break;
            else resultList.remove(0);
        }

        for(int i = 0; i < resultList.size(); i++){
            result.append(resultList.get(i));
            if(i < resultList.size() - 1){
                result.append(" ");
            }
        }

        return result;
    }

    private static List<String> findWord(List<String> resultList, String... words){
        for (String word : words)
        {
            if(!resultList.contains(word)){
                char[] firstWord = resultList.get(0).toLowerCase().toCharArray();
                char[] lastWord = resultList.get(resultList.size() - 1).toUpperCase().toCharArray();
                if (word.charAt(0) == lastWord[lastWord.length - 1])
                {
                    resultList.add(resultList.size(), word);
                    resultList = findWord(resultList, words);
                    if (resultList.size() == words.length) {
                        return resultList;
                    } else {
                        resultList.remove(resultList.size()-1);
                    }
                } else if (word.charAt(word.length() - 1) == firstWord[0])
                {
                    resultList.add(0, word);
                    resultList = findWord(resultList, words);
                    if (resultList.size() == words.length) {
                        return resultList;
                    } else {
                        resultList.remove(0);
                    }
                }
            }
        }
        return resultList;
    }
}

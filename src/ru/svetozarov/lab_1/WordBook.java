package ru.svetozarov.lab_1;

import ru.svetozarov.lab_1.exceptions.DublicateWordException;
import ru.svetozarov.lab_1.exceptions.InvalidCharException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

/**
 * Класс осуществляет работу со словарем
 * @author Evgenij Svetozarov
 */
public class WordBook {
    private volatile HashSet<String> wordBook=new HashSet<>();
    public volatile boolean flagError=false;
    public volatile boolean isShowError=false;
    /**
     * Функция добавляет слово в словарь wordBook
     * @param str слово, добовляемое в словарь
     * @return результат операции добавления слова в словарь,
     * true -  в случае успеха, false - в случае если такое слово уже есть (произойдет перезапись)
     */
    public boolean addWord(String str){
        synchronized (wordBook) {
            boolean result=false;
            if (!flagError) {
                result=wordBook.add(str);
                if(!result){
                    flagError=true;
                }
            }
            return result;
        }
    }

    public HashSet<String> getWordbook() {

        return wordBook;
    }

    public void setWordbook(HashSet<String> wordBook) {
        this.wordBook = wordBook;
    }

    /**
     * функция записи слов, хранящихся в словаре wordBook, в файл
     */
    public void writeFile(){
        String fileName="result.txt";
        try(FileWriter file=new FileWriter(fileName)){
            for (String str:
                    wordBook){
                file.write(str);
                file.append('\n');
            }

        } catch (IOException e) {
            System.out.println("Ошибка записи в файл");
            e.printStackTrace();
        }
    }

    /**
     * Функция останавливает работу со словарем wordBook, выводи сообщение об ошибке,
     * @param strError сообщение об ошибке, ее описание
     */
    public void failedProcess(String strError) {
        synchronized (wordBook){
            flagError=true;
            if(!isShowError){
                System.out.println(strError);
            }
            isShowError=true;
        }
    }
}

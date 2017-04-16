package by.it.smirnov.jd02_06_matlab.Storage;

import by.it.smirnov.jd02_06_matlab.Error.MatLabException;
import by.it.smirnov.jd02_06_matlab.Log.ILog;
import by.it.smirnov.jd02_06_matlab.Log.SingleLogger;
import by.it.smirnov.jd02_06_matlab.Parser.Parser;
import by.it.smirnov.jd02_06_matlab.Vars.Var;
import by.it.smirnov.jd02_06_matlab.Vars.VarCreator;

import java.io.*;
import java.util.*;

/**
 * Класс хранилиша переменных и их значений
 */
public class SingleStorage implements IStorage {
    private static SingleStorage instance;
    private static HashMap<String,Var> hm;
    private static ILog log;

    private SingleStorage() {
        hm = new HashMap<>();
        this.log = SingleLogger.getInstance();
    }

    static public SingleStorage getInstance() {
        if (instance == null) {
            instance = new SingleStorage();
        }
        return instance;
    }


    /**
     * Функция чтения переменных из файла
     * @param fileName имя файла
     */
    @Override
    public void loadFromFile(String fileName){
        String line = "";
        if (new File(fileName).exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))){
                while((line = br.readLine())!=null) {
                    int pos = line.indexOf("=");
                    if (pos!=-1) {
                        hm.put(line.substring(0,pos), VarCreator.getInstance().create(line.substring(pos+1)));
                    }
                }
            } catch (IOException e) {
                log.error("Ошибка чтения файла " + e.getMessage());
            } catch (MatLabException e) {
                log.error("Строка "+line+" неверного формата ("+e.getMessage()+")");
            }
        }
    }

    /**
     * Функция записи переменных в файл
     */
    @Override
    public void saveToFile(String fileName){
        if (fileName!="") {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))){
                Set<Map.Entry<String,Var>> setm = hm.entrySet();
                Iterator<Map.Entry<String,Var>> it = setm.iterator();
                while (it.hasNext()) {
                    Map.Entry<String, Var> me = it.next();
                    bw.write(me.getKey()+"="+me.getValue()+"\n");
                }
            } catch (IOException e) {
                log.error("Ошибка записи файла: " + e.getMessage());
            }
        }
    }

    /**
     * Функция добавления значения в карту
     * @param key ключ
     * @param value значение
     */
    @Override
    public void setMap(String key, Var value) {
        hm.put(key, value);
    }

    /**
     * Функция получения значения из карты по ключу
     * @param key ключ
     */
    @Override
    public Var getValue(String key) {
        return hm.get(key);
    }

    /**
     * Печать списка переменных
     */
    @Override
    public String getListKey() {
        StringBuilder sb = new StringBuilder("Список созданных в ходе присваивания переменных:\n");
        Set<Map.Entry<String, Var>> setv = hm.entrySet();
        Iterator<Map.Entry<String, Var>> i =setv.iterator();
        while (i.hasNext()) {
            Map.Entry<String, Var> me= i.next();
            sb.append(me.getKey()+"\n");
        }
        return sb.toString();
    }

    /**
     * Печать списка переменных и их значений
     */
    @Override
    public String getSortListKeyValue() {
        StringBuilder sb = new StringBuilder("Список имен и значений переменных (сортировка по алфавиту методами коллекций):\n");
        TreeMap<String,Var> tm = new TreeMap<>(hm);
        Set<Map.Entry<String, Var>> setv = tm.entrySet();
        Iterator<Map.Entry<String, Var>> i =setv.iterator();
        while (i.hasNext()) {
            Map.Entry<String, Var> me= i.next();
            sb.append(me.getKey()+" = "+me.getValue()+"\n");
        }
        return sb.toString();
    }

}
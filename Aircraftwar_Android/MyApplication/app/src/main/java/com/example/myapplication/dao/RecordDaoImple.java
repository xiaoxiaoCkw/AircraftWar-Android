package com.example.myapplication.dao;

import android.content.Context;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class RecordDaoImple implements RecordDao {
    private List<Record> records = new ArrayList<Record>();
    private File file;
    private Context context;
    public RecordDaoImple(String name, Context context){
        this.context = context;
        file = new File(context.getFilesDir(),name);
        if(!file.exists()){
            try {
                file.createNewFile();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取文件中所有的记录
     */
    private void getAll(){
        records.clear();
        try (FileInputStream fis = new FileInputStream(file)){
            ObjectInputStream ois;
            while (fis.available()>0) {
                ois = new ObjectInputStream(fis);
                Record t = (Record) ois.readObject();
                records.add(t);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 添加记录
     */
    @Override
    public void addRecord(Record record){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file,true))){
            oos.writeObject(record);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 返回用于model的String数组，标识名次、姓名、分数、时间
     */
    @Override
//    public String[][] printRecord(){
//        getAll();
//        String[][] tableData = new String[records.size()][];
//        Collections.sort(records);
//        for (int i=0; i<records.size(); i++){
//           Record current = records.get(i);
//           int minute = current.getMinute();
//           String minuteString = String.valueOf(minute);
//           if(minute<10){
//               minuteString = '0'+minuteString;
//           }
//           String[] strings = {String.valueOf(i+1),current.getName(),String.valueOf(current.getScore()),current.getDate()+" "+current.getHour()+":"+minuteString};
//           tableData[i] = strings;
//       }
//       return tableData;
//    }
    public List<Record> printRecord(){
        getAll();
        Collections.sort(records);
        return records;
    }

    @Override
    public void deleteRecord(int index){
        records.remove(index);
        //清空当前文件
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        //重新写入
        for(Record curRecord:records){
            addRecord(curRecord);
        }
    }
}

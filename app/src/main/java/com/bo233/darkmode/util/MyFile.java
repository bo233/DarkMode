package com.bo233.darkmode.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

public class MyFile {
    private static FileOutputStream out = null;
    private static BufferedWriter writer = null;
    private static FileInputStream in = null;
    private static BufferedReader reader = null;
    private static final String killAppFile = "kill_list";

    public static void init(Context context){

        if(out==null||writer==null||in==null||reader==null){
            try{
                out = context.openFileOutput(killAppFile, Context.MODE_PRIVATE);
                writer = new BufferedWriter(new OutputStreamWriter(out));
                in = context.openFileInput(killAppFile);
                reader = new BufferedReader(new InputStreamReader(in));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public static void save(String s){
        try{
            writer.write(s);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void save(List<String> strings){
        try{
            for(String s : strings)
                writer.write(s);
        }catch (IOException e){
            e.printStackTrace();
        }
    }


}

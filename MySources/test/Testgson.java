package com.motrex.jsonb2xml.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
public class Testgson {
	public static void main(String [] args){
		try {
            String string = "{\"class\":1, \"students\":[{\"name\":\"jack\", \"age\":21},{\"name\":\"kaka\", \"age\":21},{\"name\":\"lucy\", \"age\":21}]}";
            StringReader sr = new StringReader(string);
            JsonReader jr = new JsonReader(sr);
            jr.beginObject();
            if (jr.nextName().equals("class")) {
                System.out.println("班级: " + jr.nextString());
                if (jr.nextName().equals("students")) {
                    jr.beginArray();
                    while (jr.hasNext()) {
                        jr.beginObject();
                        if (jr.nextName().equals("name"))
                            System.out.print("姓名：" + jr.nextString());
                        if (jr.nextName().equals("age")) {
                            System.out.println(" , 年龄：" + jr.nextInt());
                        }
                        jr.endObject();
                    }
                    jr.endArray();
                }
            }
            jr.endObject();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
}

package com.ryb.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileDecodeUtil {
	
    public static void encodeFile(File oldFile,File newFile,String encodeHeader){
    	if(newFile.exists()){
    		newFile.delete();
    	}
        FileInputStream is = null; 
        FileOutputStream os=null;
        
        try {  
            is = new FileInputStream(oldFile); 
            os=new FileOutputStream(newFile);
            os.write(encodeHeader.getBytes());
            if(encodeHeader.getBytes().length<100){
            	os.write(new byte[100-encodeHeader.getBytes().length]);
            }
            byte buffer[] = new byte[1024];  
            int count;
            while((count=is.read(buffer))!=-1){  
            	os.write(buffer,0,count);
            }  
            is.close();
            os.flush();
            os.close();

        }catch (Exception e) {
		}
    	
    }

    public static void decodeFile(File oldFile,File newFile,Long start){
    	if(newFile.exists()){
    		newFile.delete();
    	}
        FileInputStream is = null; 
        FileOutputStream os=null;
        
        try {  
            is = new FileInputStream(oldFile); 
            os=new FileOutputStream(newFile);
            is.skip(start);
            byte buffer[] = new byte[1024];  
            int count;
            while((count=is.read(buffer))!=-1){  
            	os.write(buffer,0,count);
            }  
            is.close();
            os.flush();
            os.close();

        }catch (Exception e) {
		}
    	
    }

    public static void main(String[] args) {
		FileDecodeUtil.encodeFile(new File("E:\\a.txt"), new File("E:\\a.txt.en"), "test encode hahahaha");
		FileDecodeUtil.decodeFile(new File("E:\\a.txt.en"), new File("E:\\a.txt.decode"), 100L);
		//FileDecodeUtil.decodeFile(new File("E:\\20141031153603.wyn"), new File("E:\\20141031153603.wyn.decode"), 100L);
		//FileDecodeUtil.decodeFile(new File("E:\\20141031153603.wyn"), new File("E:\\20141031153603.wyn.decode"), 100L);
	}
}

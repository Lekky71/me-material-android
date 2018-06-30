package com.teammusa.mematerial.media;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;


/**
 * Created by HashCode on 8:45 AM 05/04/2018.
 */

public class MediaUtility {

    public static File tmpDir() {
        File dir = Environment.getExternalStoragePublicDirectory(".MeMaterialApp");
        if(!dir.exists()){
            boolean folderCreated = dir.mkdirs();
            Log.i("Folder Creation","Folder Created :: "+ folderCreated);
        }
        return dir;
    }

    public static File getTransactionsDir(){
        File dir = Environment.getExternalStoragePublicDirectory(".MeMaterialApp/images");
        if(!dir.exists()){
            boolean folderCreated = dir.mkdirs();
            Log.i("Folder Creation","Folder Created :: "+ folderCreated);
        }
        return dir;
    }

    public static File getDownloadsDir(String dirName){
        File dir = Environment.getExternalStoragePublicDirectory("MeMaterialApp/downloads/"+ dirName);
        if(!dir.exists()){
            boolean folderCreated = dir.mkdirs();
            Log.i("Folder Creation","Download folder created :: "+ folderCreated);
        }
        return dir;
    }



    public static void copyFile(File file, File file2) throws IOException {
        Throwable th;
        Throwable th2;
        if (!file2.getParentFile().exists()) {
            file2.getParentFile().mkdirs();
        }
        if (!file2.exists()) {
            file2.createNewFile();
        }
        FileChannel fileChannel = (FileChannel) null;
        FileChannel fileChannel2 = (FileChannel) null;
        FileChannel channel;
        try {
            channel = new FileInputStream(file).getChannel();
            try {
                fileChannel = new FileOutputStream(file2).getChannel();
            } catch (Throwable th3) {
                th = th3;
                if (channel != null) {
                    channel.close();
                }
                if (fileChannel2 != null) {
                    fileChannel2.close();
                }
                throw th;
            }
            try {
                fileChannel.transferFrom(channel, (long) 0, channel.size());
                if (channel != null) {
                    channel.close();
                }
                if (fileChannel != null) {
                    fileChannel.close();
                }
            } catch (Throwable th4) {
                th2 = th4;
                fileChannel2 = fileChannel;
                th = th2;
                if (channel != null) {
                    channel.close();
                }
                if (fileChannel2 != null) {
                    fileChannel2.close();
                }
                throw th;
            }
        } catch (Throwable th5) {
            th2 = th5;
            channel = fileChannel;
            th = th2;
            if (channel != null) {
                channel.close();
            }
            if (fileChannel2 != null) {
                fileChannel2.close();
            }
        }
    }

    public static void saveImage(Bitmap bitmap, File file){
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

package com.accountapp.accounts.utils;

import android.os.Environment;

import java.io.File;

public class DirectoryCreate {

    public static void createDir(){
        File sourcePath = Environment.getExternalStorageDirectory();
        File path = new File(sourcePath + "/" + "UdeFolder2" + "/");
        path.mkdir();

    }
}

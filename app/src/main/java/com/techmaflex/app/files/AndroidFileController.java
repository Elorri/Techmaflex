package com.techmaflex.app.files;

import android.Manifest;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

public class AndroidFileController implements FileStore {

    private final Context mContext;

    public AndroidFileController(Context context) {
        mContext = context;

    }

    @Override
    public File getAppDir() {
        return mContext.getFilesDir();
    }

    @Override
    public File getAppTempDir() {
        return mContext.getCacheDir();
    }

    @Override
    public File getPublicAppDir() {
        return mContext.getExternalFilesDir(null);
    }

    @Override
    public File getPublicAppTempDir() {
        return mContext.getExternalCacheDir();
    }

    @Override
    public File getPublicDir() {
        return Environment.getExternalStorageDirectory();
    }

    @Override
    public File getPublicDocumentsDir() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
    }

    @Override
    public File createFile(File file) {
        return createFile(file.getParentFile(), file.getAbsoluteFile());
    }

    @Override
    public File createFile(File directory, File file) {
        if (!directory.isDirectory() && !directory.mkdirs()) {
            Log.e("Breath", "Unable to create directory for "
                    + file.getName() + " export " + directory.getAbsolutePath());
            return null;
        }
        if (!file.isFile()) {
            try {
                file.createNewFile();
                return file;
            } catch (IOException e) {
                Log.e("Breath", "Something went wrong while creating " + file.getName() + " file", e);
                return null;
            }
        }
        return file;
    }

    @Override
    public File getFile(String filePath, boolean createFileIfDoesNotExist) {
        File file = new File(filePath);
        if (file.exists()) {
            return file;
        }
        if (createFileIfDoesNotExist) {
            file = createFile(file);
            return file;
        }
        return null;
    }

    @Override
    public String readFile(String path) {
        return readFile(new File(path));
    }

    @Override
    public String readFile(File file) {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        //mContext.requestPermissions(permissions, 0);
        if (file == null || !file.isFile()) {
            return null;
        }

        FileInputStream input = null;
        StringBuilder output = new StringBuilder();
        try {
            input = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = input.read(buffer)) != -1) {
                output.append(new String(buffer, 0, read));
            }
            input.close();
            return output.toString();
        } catch (IOException e) {
            // if any reading error occurs, we can't do anything here
            Log.e("Breath", "Error while reading file content " + file.getAbsolutePath(), e);
            return null;
        }
    }

    @Override
    public String readStream(InputStream input) {
        StringBuilder output = new StringBuilder();
        try {
            byte[] buffer = new byte[1024];
            int read;
            while ((read = input.read(buffer)) != -1) {
                output.append(new String(buffer, 0, read));
            }
            input.close();
            return output.toString();
        } catch (IOException e) {
            // if any reading error occurs, we can't do anything here
            Log.e("Breath", "Error while reading stream content ", e);
            return null;
        }
    }

    @Override
    public void saveString(File fileToSave, String content) {

        try {
            FileWriter fileWriter = new FileWriter(fileToSave);
            for (char c : content.toCharArray()) {
                fileWriter.write(c);
            }
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Marche aussi
//        try {
//            FileWriter fileWriter = new FileWriter(fileToSave);
//            PrintWriter printWriter = new PrintWriter(fileWriter);
//            for (char c : content.toCharArray()) {
//                printWriter.print(c);
//                Log.e("Breath", Thread.currentThread().getStackTrace()[2] + "char : " + c);
//            }
//            fileWriter.flush();
//            fileWriter.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        //Ne copie pas entièrement le fichier
//        Writer output = null;
//        try {
//        FileOutputStream fos = new FileOutputStream(fileToSave);
//        OutputStreamWriter w = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
//        BufferedWriter bw = new BufferedWriter(w);
//        output = new BufferedWriter(bw);
//
//            //FileWriter always assumes default encoding is OK!
//            output.write( content );
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                output.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }


        //Ne copie pas entièrement le fichier
//        try {
//            FileOutputStream fos = new FileOutputStream(fileToSave);
//            OutputStreamWriter w = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
//            BufferedWriter bw = new BufferedWriter(w);
//            //bw.write(content);
//            for (char c : content.toCharArray()) {
//                Log.e("Breath", Thread.currentThread().getStackTrace()[2] + "char : " + c);
//                bw.append(c);
//                bw.flush();
//            }
//            bw.close();
//            w.close();
//            fos.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public File getUniqueFile(String path) {
        File directory=new File(path.substring(0, path.lastIndexOf("/")));
        String fileName=path.substring(path.lastIndexOf("/"), path.lastIndexOf("."));
        String extension=path.substring(path.lastIndexOf("."), path.length());
        File file = new File(directory, fileName + "" + extension);
        return getUniqueFile(file, 0);
    }

    private static File getUniqueFile(File file, int number) {
        if (file == null) {
            Log.e("BreathApp", "Method shouldn't be invoked with a null file.");
            return null;
        }
        if (!file.exists()) {
            return file;
        }
        number++;

        String fileName = file.getName();
        int patternStart = fileName.lastIndexOf("(");
        int extensionStart = fileName.lastIndexOf(".");

        String extension = "";

        //Remove pattern and extension from file name
        if (extensionStart != -1) {
            extension = fileName.substring(extensionStart, fileName.length());
            fileName = fileName.substring(0, extensionStart);
        }

        if (patternStart != -1) {
            fileName = fileName.substring(0, patternStart);
        }

        //Add pattern and extension to file name
        StringBuilder stringBuilder = new StringBuilder(fileName);
        fileName = stringBuilder.append("(").append(number).append(")").append(extension).toString();
        File uniqueFile = new File(file.getParentFile(), fileName);
        if (uniqueFile.exists()) {
            //keep going until we get something unique
            return getUniqueFile(file, number);
        }
        return uniqueFile;
    }


}

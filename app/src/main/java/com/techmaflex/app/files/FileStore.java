package com.techmaflex.app.files;

import java.io.File;
import java.io.InputStream;

public interface FileStore {
    File getAppDir();

    File getAppTempDir();

    File getPublicAppDir();

    File getPublicAppTempDir();

    File getPublicDir();

    File getPublicDocumentsDir();

    File createFile(File file);

    File createFile(File directory, File file);

    File getFile(String filePath, boolean createFileIfDoesNotExist);

    String readFile(String path);

    String readFile(File file);

    String readStream(InputStream input);

    void saveString(File fileToSave, String content) ;

    File getUniqueFile(String path) ;
}

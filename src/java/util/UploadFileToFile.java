/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author Marilena Oshima
 */
public class UploadFileToFile {
    public static File uploadedFileToFileConverter(UploadedFile uf) {
    InputStream inputStream = null;
    OutputStream outputStream = null;
    //Add you expected file encoding here:
    System.setProperty("file.encoding", "UTF-8");
    File newFile = new File(uf.getFileName());
    try {
        inputStream = uf.getInputStream();
        outputStream = new FileOutputStream(newFile);
        int read = 0;
        byte[] bytes = new byte[1024];
        while ((read = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, read);
        }
    } catch (IOException e) {
       //Do something with the Exception (logging, etc.)
       return null;
    }
    return newFile;
}
}

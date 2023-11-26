package org.example;

import org.example.stubs.ProductOuterClass;

import java.io.File;
import java.nio.file.Files;

public class Utility {


    public static int getProtobufObjectSize(ProductOuterClass.Product product){
        return product.toByteArray().length;
    }

    public static int  getFileSize(String filePath) throws Exception{

        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            byte[] fileBytes = Files.readAllBytes(file.toPath());
            return fileBytes.length;
        } else {
            throw new IllegalArgumentException("Le fichier spécifié n'existe pas ou n'est pas valide.");
        }

    }
}

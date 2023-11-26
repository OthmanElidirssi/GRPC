package org.example;
import org.example.Utility;
import org.example.stubs.ProductOuterClass;

public class Main {
    public static void main(String[] args) throws Exception {
        ClassLoader classLoader = Main.class.getClassLoader();
        String jsonFilePath = classLoader.getResource("products.json").getFile();
        String xmlFilePath = classLoader.getResource("products.xml").getFile();
        ProductOuterClass.Product product1 = ProductOuterClass.Product.newBuilder()
                .setId(1)
                .setName("PC")
                .setPrice(11200)
                .build();
        ProductOuterClass.Product product2 = ProductOuterClass.Product.newBuilder()
                .setId(2)
                .setName("Printer")
                .setPrice(900)
                .build();

        int protoBufProductsSize = Utility.getProtobufObjectSize(product1) + Utility.getProtobufObjectSize(product2);
        int jsonFileSize = Utility.getFileSize(jsonFilePath);
        int xmlFileSize = Utility.getFileSize(xmlFilePath);

        System.out.println("xml : " + xmlFileSize + " octets");
        System.out.println("json : " + jsonFileSize + " octets");
        System.out.println("protobuf : " + protoBufProductsSize + " octets");

        double xmlToJsonPercentageGain = ((double) (xmlFileSize - jsonFileSize) / xmlFileSize) * 100;
        double xmlToProtoBufPercentageGain = ((double) (xmlFileSize - protoBufProductsSize) / xmlFileSize) * 100;
        double jsonToProtoBufPercentageGain = ((double) (jsonFileSize - protoBufProductsSize) / jsonFileSize) * 100;

        System.out.println("");

        System.out.println("Pourcentage d'économie de taille de XML à JSON : " + String.format("%.2f", xmlToJsonPercentageGain) + "%");
        System.out.println("Pourcentage d'économie de taille de XML à Protobuf : " + String.format("%.2f", xmlToProtoBufPercentageGain) + "%");
        System.out.println("Pourcentage d'économie de taille de JSON à Protobuf : " + String.format("%.2f", jsonToProtoBufPercentageGain) + "%");
    }
}

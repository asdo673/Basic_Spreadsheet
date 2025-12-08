package uni.aed.tda.treeTDA.SoftwareStore;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import uni.aed.tda.treeTDA.BstNodeTDA;
import uni.aed.tda.treeTDA.BstTDA;

public class SoftwareStore {
    private static final int NOT_FOUND=-1;
    private BstTDA<Software> bst;
    private String filename;

    public SoftwareStore(String filename) {
        bst=new BstTDA<>();
        this.filename = filename;
        readFromFile();
    }
    private void readFromFile(){
        try(Scanner scr=new Scanner(new File(filename))){
            int position=0;
            while(scr.hasNextLine()){
                String line=scr.nextLine();
                String[] parts=line.split("\\s+");
                if(parts.length!=4)
                    continue;
                String name=parts[0];
                String version=parts[1].toString();
                int quantity=Integer.parseInt(parts[2]);
                int price=Integer.parseInt(parts[3]);
                bst.add(new Software(name,version,quantity,price,position));
                position++;
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }
    
    public void addSoftware(String name,String version,int quantity,int price){
        bst.add(new Software(name,version,quantity,price,-1));
        updateFile();
    }
    
    public void sellSoftware(String name,String version,int quantitySold){
        Software sw=new Software(name,version);
        BstNodeTDA<Software> node=bst.search(sw);
        if(node!=null){
            node.getKey().setQuantity(node.getKey().getQuantity() - quantitySold);
            updateFile();
            if(node.getKey().getQuantity()<=0)
                bst.deleteByMerging(sw);
        }
    }
    
    private void updateFile(){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filename))){
            StringBuilder sb=new StringBuilder();
            bst.inorder(sb,"\n");
            writer.write(sb.toString());
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public String inventarioSoftware() {
        StringBuilder buffer = new StringBuilder();
        try (Scanner scanner = new Scanner(new File(filename))) {
            buffer.append(String.format("%-30s %-15s %-10s %-10s%n", "Nombre", "Versión", "Cantidad", "Precio"));
            buffer.append("--------------------------------------------------------------------------------\n");

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.trim().split("\\s+"); // divide por cualquier espacio

                if (parts.length >= 4) {
                    String name = parts[0];
                    String version = parts[1];
                    String quantity = parts[2];
                    String price = parts[3];

                    buffer.append(String.format("%-30s %-15s %-10s %-10s%n", name, version, quantity, price));
                }
            }

            buffer.append("--------------------------------------------------------------------------------\n");

            } catch (FileNotFoundException e) {
                buffer.append("No se pudo encontrar el archivo: ").append(filename).append("\n");
            }

        return buffer.toString();
    }

    public void cleanUpFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename)); BufferedWriter writer = new BufferedWriter(new FileWriter(filename + ".tmp"))) {

            String line;
            int position = 0;
            List<String> lines = new LinkedList<>();
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            boolean stock = false;
            while (position < lines.size()) {
                line = lines.get(position);
                String[] parts = line.split("\\s+");
                int quantity = Integer.parseInt(parts[2]);
                if (quantity == 0) {
                    while (position < lines.size() && !stock) {
                        String lineLast = lines.get(lines.size() - 1);
                        String[] partsLast = lineLast.split("\\s+");
                        int quantityLast = Integer.parseInt(partsLast[2]);
                        if (quantityLast > 0) {
                            stock = true;
                            lines.set(position, lineLast);
                        }
                        lines.remove(lines.size() - 1);
                    }
                }
                position++;
            }
            for (String fline : lines) {
                String[] parts = fline.split("\\s+");
                String name = parts[0];
                String version = parts[1];
                int quantity = Integer.parseInt(parts[2]);
                int price = Integer.parseInt(parts[3]);
                if (quantity > 0) {
                    writer.write(name + " " + version + " " + quantity + " " + price);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        File originalFile = new File(filename);
        if (originalFile.delete()) {
            File tempFile = new File(filename + ".tmp");
            if (tempFile.renameTo(originalFile)) {
                System.out.println("Archivo limpiado y actualizado correctamente.");
            } else {
                System.out.println("Error al renombrar el archivo temporal.");
            }
        } else {
            System.out.println("Error al borrar el archivo original.");
        }
    }

    @Override
    public String toString() {
        return bst.toString();
    }
    
    
}

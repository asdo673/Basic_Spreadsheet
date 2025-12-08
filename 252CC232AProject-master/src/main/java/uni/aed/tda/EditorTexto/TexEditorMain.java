package uni.aed.tda.EditorTexto;

import java.io.IOException;
import java.util.Scanner;

public class TexEditorMain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Ingrese comando (EDIT <archivo>): ");
            String line = sc.nextLine();

            if (line.toUpperCase().startsWith("EDIT")) {
                String[] parts = line.split("\\s+");
                if (parts.length < 2) {
                    System.out.println("Debe especificar un nombre de archivo.");
                } else {
                    String filename = parts[1];
                    TextEditor editor = new TextEditor(filename);
                    editor.start(); // Aqui puede lanzarse IOException u otra excepcion
                }
            } else {
                System.out.println("Comando no valido. Use: EDIT <archivo>");
            }
        } catch (IOException e) {
            System.err.println("Error de entrada/salida: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        } finally {
            sc.close();
        }
    }

}

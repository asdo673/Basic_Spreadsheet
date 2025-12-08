package uni.aed.tda.EditorTexto;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class TextEditor {
    private TextEditorLinkedListTDA<String> editor;
    private String filename;
    private int currentLine;

    public TextEditor(String filename) {
        this.filename = filename;
        this.editor = new TextEditorLinkedListTDA<>();
        this.currentLine = 1;
    }

    public void start() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("EDIT " + filename);

        while (true) {
            System.out.print(currentLine  + "> ");
            String input = sc.nextLine().trim();

            if (input.isEmpty()) {
                editor.add(currentLine - 1, "");
                currentLine++;
                continue;
            }

            String[] parts = input.split("\\s+");
            String command = parts[0].toUpperCase();

            // comando Insertar
            if (command.equals("I")) {
                int lineNum = (parts.length > 1) ? Integer.parseInt(parts[1]) : currentLine;
                System.out.print(lineNum + "> ");
                String text = sc.nextLine();
                editor.add(lineNum - 1, text);
                currentLine = lineNum + 1;
            }

            // comando Delete (eliminar)
            else if (command.equals("D")) {
                int n = (parts.length >= 2) ? Integer.parseInt(parts[1]) : currentLine;
                int m = (parts.length == 3) ? Integer.parseInt(parts[2]) : n;
                editor.deleteRange(n-1, m-1);
                currentLine = currentLine -(m-n+1);    //Math.max(1, n);
            }
            
            // comando listar
            else if (command.equals("L")) {
                int n = (parts.length >= 2) ? Integer.parseInt(parts[1]) : 1;
                int m = (parts.length == 3) ? Integer.parseInt(parts[2]) : editor.size();
                System.out.println(editor.printRange(n-1, m-1));                
            }

            // comando Agregar
            else if (command.equals("A")) {
                System.out.print((editor.size() + 1) + "> ");
                String text = sc.nextLine();
                editor.add(text);
                currentLine = editor.size();
            }

            // comando Exit (Salir)
            else if (command.equals("E")) {
                saveToFile();
                System.out.println("Archivo guardado en: " + filename);
                break;
            }

            // En otro caso, considerelo como texto de nueva linea.
            else {
                editor.add(currentLine - 1, input);
                currentLine++;
            }
        }

        sc.close();
    }
    
    private void saveToFile() throws IOException {
        try (FileWriter fw = new FileWriter(filename)) {
            fw.write(editor.toString("\n"));
        } catch (IOException e) {
            // Propagamos la excepción al nivel superior
            throw new IOException("Error al guardar el archivo: " + e.getMessage(), e);
        }
    }
   
}

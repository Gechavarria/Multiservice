package Modelo;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.itextpdf.text.Image;


public class ArchivoPDF {

    private final String nombre;
    private final Document documento;

    public ArchivoPDF(String nombre) {
        this.nombre = nombre;
        documento = new Document();
    }

    public void abrir() {
        try {
            PdfWriter.getInstance(documento, new FileOutputStream(nombre));
        }  catch (FileNotFoundException | DocumentException ex) {
            System.out.println("Error al crear y abrir archivo");
        }
        documento.open();
    }

    public void cerrar() {
        documento.close();
    }

    public void addParrafo(String titulo, String texto) {

        try {

            Paragraph parrafo = new Paragraph(titulo);
            parrafo.setAlignment(1);
            //parrafo.setFont(FontFactory.getFont("Tahoma",58,Font.BOLDITALIC, BaseColor.RED));
            documento.add(parrafo);

            documento.add(new Paragraph(texto));

        } catch (DocumentException ex) {
            System.out.println("Hubo problema con PDF al añadir el parrafo");
        }

        System.out.println("Se añade parrafo al PDF");

    }

    public void addTabla(int columnas, String vector[]) {

        try {

            // Este codigo genera una tabla de n columnas
            PdfPTable table = new PdfPTable(columnas);

            // addCell() agrega una celda a la tabla, el cambio de fila
            // ocurre automaticamente al llenar la fila
            for (int i = 0; i < vector.length; i = i + 1) {
                table.addCell(vector[i]);
            }

            // Agregamos la tabla al documento            
            documento.add(table);

            System.out.println("Se añade tabla al PDF");

        } catch (Exception e) {
            System.err.println("Ocurrió un error al crear el PDF para la tabla");
        }
    }

    public void addTabla(String vector[][]) {

        try {

            // Este codigo genera una tabla de n columnas
            PdfPTable table = new PdfPTable(vector[0].length);

            // addCell() agrega una celda a la tabla, el cambio de fila
            // ocurre automaticamente al llenar la fila
            for (int i = 0; i < vector.length; i = i + 1) {
                for (int j = 0; j < vector[0].length; j = j + 1) {
                    table.addCell(vector[i][j]);
                }
            }

            // Agregamos la tabla al documento            
            documento.add(table);

            System.out.println("Se añade tabla al PDF");

        } catch (Exception e) {
            System.err.println("Ocurrio un error al crear el PDF para la tabla");
        }
    }

    public void mostrar() {
        try {
            File path = new File(nombre);
            Desktop.getDesktop().open(path);
        } catch (Exception ex) {
            System.out.println("Error al abrir PDF. ¿Cerró el archivo PDF?");
        }
    }

    public void addImagen(String ubicacion) {

        try {
            
            Image imagen = Image.getInstance(ubicacion);
            imagen.setAlignment(Element.ALIGN_LEFT);
            imagen.scaleAbsolute(160, 160);
            documento.add(imagen);

        } catch (Exception e) {
           
        }

    }

}

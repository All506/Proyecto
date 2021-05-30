/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PDF;

import Domain.CircularDoublyLinkList;
import Domain.DoublyLinkList;
import Domain.SinglyLinkList;
import Objects.Career;
import Objects.Course;
import Objects.Student;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FilePDF {

    //Constructor
    public FilePDF() {

    }

    //Elimina el pdf
    public void deleteFile(String fileName) {
        try {
            Files.delete(Paths.get(fileName + ".pdf"));
        } catch (IOException ex) {
            Logger.getLogger(FilePDF.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //Exist?
    public Boolean exist(String file) {
        File archive = new File(file);
        if (archive.exists()) {
            return true;
        }
        return false;
    }

    //Genera el pdf y escribe lo que queremos
    public void careerPDF(String fileName, DoublyLinkList list) throws FileNotFoundException, DocumentException, BadElementException, URISyntaxException, IOException {
        FileOutputStream file = new FileOutputStream(fileName + ".pdf");
        Document document = new Document();
        PdfWriter.getInstance(document, file);

        //Instancia para poder hacer la img, importante poner la imagen en la carpeta img
        Image header = Image.getInstance("src/img/logo-ucr.png");
        header.scaleToFit(150, 250);
        header.setAlignment(Chunk.ALIGN_CENTER);
        //Se abre el documento para poder escribir en el
        document.open();
        document.add(header);//Se agrega la img

        Paragraph parrafo = new Paragraph();
        parrafo.setAlignment(Paragraph.ALIGN_CENTER);
        parrafo.setFont(FontFactory.getFont("Tahoma", 18, Font.BOLD, BaseColor.BLACK));
        parrafo.add("\n\nRegistered Careers\n\n");
        document.add(parrafo);

        //Tabla
        PdfPTable table = new PdfPTable(2);//Columnas y nombres
        table.addCell("Identification");
        table.addCell("Description");

        try {
            for (int i = 1; i <= list.size(); i++) {
                Career career = (Career) list.getNode(i).data;
                table.addCell(String.valueOf(career.getId()));
                table.addCell(career.getDescription());
            }
            document.add(table);//Agrega la tabla al documento

        } catch (Exception e) {
        }

        //Importante cerrar el pdf
        document.close();
    }

    //Genera el pdf y escribe lo que queremos
    public void studentPDF(String fileName, SinglyLinkList list) throws FileNotFoundException, DocumentException, BadElementException, URISyntaxException, IOException {
        FileOutputStream file = new FileOutputStream(fileName + ".pdf");
        Document document = new Document();
        PdfWriter.getInstance(document, file);

        //Instancia para poder hacer la img, importante poner la imagen en la carpeta img
        Image header = Image.getInstance("src/img/logo-ucr.png");
        header.scaleToFit(150, 250);
        header.setAlignment(Chunk.ALIGN_CENTER);
        //Se abre el documento para poder escribir en el
        document.open();
        document.add(header);//Se agrega la img

        Paragraph parrafo = new Paragraph();
        parrafo.setAlignment(Paragraph.ALIGN_CENTER);
        parrafo.setFont(FontFactory.getFont("Tahoma", 18, Font.BOLD, BaseColor.BLACK));
        parrafo.add("\n\nRegistered Students\n\n");
        document.add(parrafo);

        try {
            for (int i = 1; i <= list.size(); i++) {
                Paragraph parrafo1 = new Paragraph();
                Student student = (Student) list.getNode(i).data;
                parrafo1.add("\nIdentification: " + String.valueOf(student.getId()));
                parrafo1.add("\nStudent ID: " + student.getStudentID());
                parrafo1.add("\nLast Name: " + student.getLastname());
                parrafo1.add("\nFirst Name: " + student.getFirstname());
                parrafo1.add("\nBirthday: " + Util.Utility.dateFormat(student.getBirthday2()));
                parrafo1.add("\nPhone Number: " + student.getPhoneNumber());
                parrafo1.add("\nEmail: " + student.getEmail());
                parrafo1.add("\nAddress: " + student.getAddress());
                parrafo1.add("\nCareer: " + Util.Utility.getCarrerByID(String.valueOf(student.getCareerID())).getDescription());
                parrafo1.add("\n----------------------------------------");
                document.add(parrafo1);
            }

        } catch (Exception e) {
        }

        //Importante cerrar el pdf
        document.close();
    }

    //Genera el pdf y escribe lo que queremos
    public void coursePDF(String fileName, CircularDoublyLinkList list) throws FileNotFoundException, DocumentException, BadElementException, URISyntaxException, IOException {
        FileOutputStream file = new FileOutputStream(fileName + ".pdf");
        Document document = new Document();
        PdfWriter.getInstance(document, file);

        //Instancia para poder hacer la img, importante poner la imagen en la carpeta img
        Image header = Image.getInstance("src/img/logo-ucr.png");
        header.scaleToFit(150, 250);
        header.setAlignment(Chunk.ALIGN_CENTER);
        //Se abre el documento para poder escribir en el
        document.open();
        document.add(header);//Se agrega la img

        Paragraph parrafo = new Paragraph();
        parrafo.setAlignment(Paragraph.ALIGN_CENTER);
        parrafo.setFont(FontFactory.getFont("Tahoma", 18, Font.BOLD, BaseColor.BLACK));
        parrafo.add("\n\nRegistered Courses\n\n");
        document.add(parrafo);

        //Tabla
        PdfPTable table = new PdfPTable(4);//Columnas y nombres
        table.addCell("Identification");
        table.addCell("Name");
        table.addCell("Credits");
        table.addCell("Career ID");

        try {
            for (int i = 1; i <= list.size(); i++) {
                Course course = (Course) list.getNode(i).data;
                table.addCell(String.valueOf(course.getId()));
                table.addCell(course.getName());
                table.addCell(String.valueOf(course.getCredits()));
                table.addCell( Util.Utility.getCarrerByID(String.valueOf(course.getCareerId())).getDescription());
            }
            document.add(table);//Agrega la tabla al documento

        } catch (Exception e) {
        }

        //Importante cerrar el pdf
        document.close();
    }

    

}//end class

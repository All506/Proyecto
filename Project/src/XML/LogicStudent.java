/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XML;

import Domain.Student;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Alán
 */
public class LogicStudent {

    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! FOR STUDENT !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public void writeStudent(Student student) {
        FileXML fXML = new FileXML();
        try {
            if (!fXML.exist("Students.xml")) {
                fXML.createXML("StudentsXML", "Students");;

                fXML.writeXML("Students.xml", "Students", student.dataName(), student.data());

            } else {
                if (exists("Students", "studentId", student)) {
                } else {
                    fXML.writeXML("Students.xml", "Students", student.dataName(), student.data());
                }
            }
        } catch (TransformerException ex) {
            Logger.getLogger(LogicStudent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(LogicStudent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LogicStudent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean exists(String fileName, String attribute, Student student) {
        boolean exist = false; //Falso si no existe

        try {
            File inputFile = new File(fileName + ".xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName(attribute);

            for (int indice = 0; indice < nList.getLength(); indice++) {
                Node nNode = nList.item(indice);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String aux = eElement.getAttribute("studentId");
                    if (aux.equals(student.getStudentID())) {
                        exist = true; //True si existe
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return exist;
    }
    
}

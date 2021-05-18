/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XML;

import Objects.Student;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
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

  //!!->DIVISION<-!!!!->DIVISION<-!!!!->DIVISION<-!!!!->DIVISION<-!!!!->DIVISION<-!!!!->DIVISION<-!!!!->DIVISION<-!!!!->DIVISION<-!!!!->DIVISION<-!!!!->DIVISION<-!!
    
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
    
      //!!->DIVISION<-!!!!->DIVISION<-!!!!->DIVISION<-!!!!->DIVISION<-!!!!->DIVISION<-!!!!->DIVISION<-!!!!->DIVISION<-!!!!->DIVISION<-!!!!->DIVISION<-!!!!->DIVISION<-!!

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
    
    //!!->DIVISION<-!!!!->DIVISION<-!!!!->DIVISION<-!!!!->DIVISION<-!!!!->DIVISION<-!!!!->DIVISION<-!!!!->DIVISION<-!!!!->DIVISION<-!!!!->DIVISION<-!!!!->DIVISION<-!!
    
    
    public void deleteStudent(Student student) throws ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, TransformerException {
        File inputFile = new File("Students.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);

        NodeList nodes = doc.getElementsByTagName("Students");

        //Busca por la persona de acuerdo al nodo Usuario
        for (int indice = 0; indice < nodes.getLength(); indice++) {
            Element std = (Element) nodes.item(indice);

            String studentId = std.getAttribute("studentId");
            System.out.println("nodo " + indice + "=" + studentId);
            if (studentId.equalsIgnoreCase(student.getStudentID())) {
                std.getParentNode().removeChild(std);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(inputFile);
            transformer.transform(source, result);

        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XML;

import Domain.SinglyLinkList;
import Domain.Student;
import java.io.File;
import java.io.IOException;
import static java.lang.String.format;
import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;  
import java.util.Locale;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Alán
 */
public class FileXML {

    public void createXML(String objectName, String fileName) {

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement(objectName);
            doc.appendChild(rootElement);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(new File(/*address + "\\" + */fileName + ".xml"));
            transformer.transform(source, result);

            System.out.println("Archivo Creado");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeXML(String FileName, String elementType, String[] dataName, String[] data) throws TransformerException, org.xml.sax.SAXException, IOException {

        try {

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new File(FileName));
            doc.getDocumentElement().normalize();

            Element rootElement = doc.getDocumentElement();

            Element ele = doc.createElement(elementType);
            rootElement.appendChild(ele);

            Attr attr = doc.createAttribute(dataName[0]);
            attr.setValue(data[0]);
            ele.setAttributeNode(attr);

            for (int i = 1; i < data.length; i++) {

                Element dato = doc.createElement(dataName[i]);

                dato.appendChild(doc.createTextNode(data[i]));

                ele.appendChild(dato);
            }
            //escribirmos el contenido en un archivo xml
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(new File(FileName));
            transformer.transform(source, result);

            System.out.println("Registro Guardado");

        } catch (ParserConfigurationException pce) {

            pce.printStackTrace();

        }
    }

    public Boolean exist(String file) {
        File archive = new File(file);

        if (archive.exists()) {
            return true;
        }

        return false;
    }

    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!STUDENTS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public SinglyLinkList readXMLtoStudentList(String elementType) {

        SinglyLinkList sList = new SinglyLinkList();

        try {
            File inputFile = new File("Students.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName(elementType);

            for (int indice = 0; indice < nList.getLength(); indice++) {
                Student std = new Student(0, 0, "", "", "", "", "", "", null);
                Node nNode = nList.item(indice);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    std.setStudentID(eElement.getAttribute("studentId"));
                    std.setId(Integer.valueOf(eElement.getElementsByTagName("id").item(0).getTextContent()));
                    std.setCareerID(Integer.parseInt(eElement.getElementsByTagName("carrerId").item(0).getTextContent()));
                    std.setLastname(eElement.getElementsByTagName("lastName").item(0).getTextContent());
                    std.setFirstname(eElement.getElementsByTagName("firstName").item(0).getTextContent());
                    std.setPhoneNumber(eElement.getElementsByTagName("phoneNumber").item(0).getTextContent());
                    std.setEmail(eElement.getElementsByTagName("email").item(0).getTextContent());
                    std.setAddress(eElement.getElementsByTagName("address").item(0).getTextContent());
                    //Seteo de la fecha
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = format.parse(eElement.getElementsByTagName("birthday").item(0).getTextContent());
                    std.setBirthday(date);
                   // std.setBirthday(SimpleDateFormat("dd/MM/yyyy").parse(eElement.getElementsByTagName("birthday").item(0).getTextContent()));
                }
                System.out.println("Nodo " + "i: " + std.toString());
                sList.add(std);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sList;
    }

    //Se debe de probar
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

    public void editStudent(Student std) throws ParserConfigurationException, SAXException, IOException, TransformerException {
        File inputFile = new File("Students.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

        Document doc = dBuilder.parse(inputFile);
        NodeList nodes = doc.getElementsByTagName("Students");
        Element student = null;

        for (int i = 0; i < nodes.getLength(); i++) {
            
            student = (Element) nodes.item(i);
            
            String studendId = student.getAttribute("studentId");
            if (studendId.equals(std.getStudentID())) {
                    Element eElement = (Element) student;
                    student.setAttribute("studentId",std.getStudentID());
                    student.setAttribute("id",String.valueOf(std.getId()));
                    student.setAttribute("carrerId", String.valueOf(std.getCareerID()));
                    student.setAttribute("lastName",std.getLastname());
                    student.setAttribute("firstName",std.getFirstname());
                    student.setAttribute("phoneNumber",std.getPhoneNumber());
                    student.setAttribute("email",std.getEmail());
                    student.setAttribute("address",std.getAddress());
                    student.setAttribute("birthday","");
            }
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        DOMSource source = new DOMSource(doc);

        StreamResult result = new StreamResult(inputFile);
        transformer.transform(source, result);

        System.out.println("Registro Modificado");
    }
}

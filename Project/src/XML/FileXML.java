/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XML;

import Domain.CircularDoublyLinkList;
import Domain.CircularLinkList;
import Objects.Career;
import Domain.DoublyLinkList;
import Domain.SinglyLinkList;
import Objects.Course;
import Objects.DeEnrollment;
import Objects.Enrollment;
import Objects.Security;
import Objects.Student;
import Objects.TimeTable;
import Security.AES;
import java.io.File;
import java.io.IOException;
import static java.lang.String.format;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
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

    public void deleteFile(String fileName) { //Se encarga de eliminar un archivo
        try {
            Files.delete(Paths.get(fileName + ".xml"));
        } catch (IOException ex) {
            Logger.getLogger(FileXML.class.getName()).log(Level.SEVERE, null, ex);
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
                sList.add(std);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sList;
    }

    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Career !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public DoublyLinkList readXMLtoCareertList() {

        DoublyLinkList lCareer = new DoublyLinkList();

        try {
            File inputFile = new File("Careers.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("Careers");

            for (int indice = 0; indice < nList.getLength(); indice++) {
                Career car = new Career(0, "");
                Node nNode = nList.item(indice);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    car.setId(Integer.parseInt(eElement.getAttribute("id")));
                    car.setDescription(eElement.getElementsByTagName("description").item(0).getTextContent());

                }
                System.out.println("Nodo " + "i: " + car.toString());
                lCareer.add(car);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lCareer;
    }

    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Security  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public CircularLinkList readXMLtoSecurityList() {

        CircularLinkList lSecurity = new CircularLinkList();

        try {
            File inputFile = new File("Security.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("User");

            for (int indice = 0; indice < nList.getLength(); indice++) {
                Security sec = new Security("", "");
                Node nNode = nList.item(indice);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    sec.setUser(eElement.getAttribute("user"));
                    sec.setPassword(eElement.getElementsByTagName("password").item(0).getTextContent());

                }
                AES deEnc = new AES();
                Security desUser = new Security(deEnc.deCrypt(sec.getUser(), "Proyecto"), deEnc.deCrypt(sec.getPassword(), "Proyecto"));
                lSecurity.add(desUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lSecurity;
    }

    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Course  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public CircularDoublyLinkList readXMLtoCourseList() {

        CircularDoublyLinkList lCourse = new CircularDoublyLinkList();

        try {
            File inputFile = new File("Courses.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("Courses");

            for (int indice = 0; indice < nList.getLength(); indice++) {
                Course cou = new Course();
                Node nNode = nList.item(indice);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    cou.setId(eElement.getAttribute("id"));
                    cou.setCareerId(Integer.valueOf(eElement.getElementsByTagName("careerId").item(0).getTextContent()));
                    cou.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
                    cou.setCredits(Integer.valueOf(eElement.getElementsByTagName("credits").item(0).getTextContent()));
                }
                lCourse.add(cou);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lCourse;
    }

    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Schedules  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public SinglyLinkList readXMLtoScheduleList() {

        SinglyLinkList lSchedule = new SinglyLinkList();

        try {
            File inputFile = new File("Schedules.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("Schedules");

            for (int indice = 0; indice < nList.getLength(); indice++) {
                TimeTable schedule = new TimeTable("", "", "", "");
                Node nNode = nList.item(indice);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    schedule.setID(eElement.getAttribute("id"));
                    schedule.setPeriod(eElement.getElementsByTagName("period").item(0).getTextContent());
                    schedule.setSchedule1(eElement.getElementsByTagName("schedule1").item(0).getTextContent());
                    schedule.setSchedule2(eElement.getElementsByTagName("schedule2").item(0).getTextContent());
                }
                lSchedule.add(schedule);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lSchedule;
    }

    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Enrollment  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public CircularDoublyLinkList readXMLtoEnrollmentList() {

        CircularDoublyLinkList lEnrollment = new CircularDoublyLinkList();

        try {
            File inputFile = new File("Enrollments.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("Enrollments");

            for (int indice = 0; indice < nList.getLength(); indice++) {
                Enrollment enr = new Enrollment();
                Node nNode = nList.item(indice);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    enr.setId(Integer.parseInt(eElement.getAttribute("id")));
                    //Seteo de la fecha
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date d = java.sql.Date.valueOf(eElement.getElementsByTagName("date").item(0).getTextContent());
                    enr.setDate(d);
                    enr.setStudentID(eElement.getElementsByTagName("studentId").item(0).getTextContent());
                    enr.setCourseID(eElement.getElementsByTagName("courseId").item(0).getTextContent());
                    enr.setSchedule(eElement.getElementsByTagName("schedule").item(0).getTextContent());
                }
                lEnrollment.add(enr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lEnrollment;
    }
    
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Enrollment  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public CircularDoublyLinkList readXMLtoDeEnrollmentList() {

        CircularDoublyLinkList lDeEnrollment = new CircularDoublyLinkList();

        try {
            File inputFile = new File("DeEnrollment.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("DeEnrollment");

            for (int indice = 0; indice < nList.getLength(); indice++) {
                DeEnrollment deEnr = new DeEnrollment();
                Node nNode = nList.item(indice);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    deEnr.setId(Integer.parseInt(eElement.getAttribute("id")));
                    //Seteo de la fecha
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date d = java.sql.Date.valueOf(eElement.getElementsByTagName("date").item(0).getTextContent());
                    deEnr.setDate(d);
                    deEnr.setStudentID(eElement.getElementsByTagName("studentId").item(0).getTextContent());
                    deEnr.setCourseID(eElement.getElementsByTagName("courseId").item(0).getTextContent());
                    deEnr.setSchedule(eElement.getElementsByTagName("schedule").item(0).getTextContent());
                    deEnr.setRemark(eElement.getElementsByTagName("remark").item(0).getTextContent());
                }
                lDeEnrollment.add(deEnr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lDeEnrollment;
    }
    
    ///////////////////////////////////////////// LAST ID /////////////////////////////////////////////////////////////////////////////////////////////
    
    public int getLastEnroll(){
        int contador = 0;

            File inputFile = new File("Enrollments.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("Enrollments");
            
            
            contador = nList.getLength();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(FileXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(FileXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileXML.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        return contador;
        
    }
    
        public int getLastDeEnroll(){
        int contador = 0;

            File inputFile = new File("DeEnrollment.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("DeEnrollment");
            
            
            contador = nList.getLength();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(FileXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(FileXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileXML.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        return contador;
        
    }
}

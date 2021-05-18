/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XML;

import Domain.Career;
import Domain.Student;
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
public class LogicCareer {
    
    public void writeCareer(Career car) {
        FileXML fXML = new FileXML();
        try {
            if (!fXML.exist("Careers.xml")) {
                fXML.createXML("CareersXML", "Careers");;

                fXML.writeXML("Careers.xml", "Careers", car.dataName(), car.data());

            } else {
                if (exists("Careers", "id", car)) {
                } else {
                    fXML.writeXML("Careers.xml", "Careers", car.dataName(), car.data());
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
    
    public boolean exists(String fileName, String attribute, Career car) {
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
                    String aux = eElement.getAttribute("id");
                    if (aux.equals(car.getId())) {
                        exist = true; //True si existe
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return exist;
    }
    
     //Se debe de probar
    public void deleteCareer(Career car) throws ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, TransformerException {
        File inputFile = new File("Careers.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);

        NodeList nodes = doc.getElementsByTagName("Careers");

        //Busca por la persona de acuerdo al nodo Usuario
        for (int indice = 0; indice < nodes.getLength(); indice++) {
            Element carNode = (Element) nodes.item(indice);

            Integer id = Integer.parseInt(carNode.getAttribute("id"));
            if (id == car.getId()) {
                carNode.getParentNode().removeChild(carNode);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            DOMSource source = new DOMSource(doc);

            StreamResult result = new StreamResult(inputFile);
            transformer.transform(source, result);

        }
    }
}

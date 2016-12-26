package com.sudoku.iostream;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.sudoku.exception.CustomException;
import com.sudoku.model.Nodes;
import com.sudoku.util.ExceptionCode;

public class HandleXMLData implements IHandleSource {
	private final static Logger log = Logger.getLogger(HandleXMLData.class);
	@Override
	public List<Nodes> readFile(String path) {
		List<Nodes> liseNode = new ArrayList<>();
		try {	
	         File inputFile = new File(path);
	         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.parse(inputFile);
	         doc.getDocumentElement().normalize();
	         NodeList nList = doc.getElementsByTagName("Node");
	         for (int temp = 0; temp < nList.getLength(); temp++) {
	            Node nNode = nList.item(temp);
	            Nodes nodes = new Nodes();
	            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	            	Element eElement = (Element) nNode;
	                nodes.setRow(Integer.parseInt(eElement.getElementsByTagName("row").item(0).getTextContent())) ;
	                nodes.setCol(Integer.parseInt(eElement.getElementsByTagName("col").item(0).getTextContent())) ;
	                nodes.setValue(Integer.parseInt(eElement.getElementsByTagName("value").item(0).getTextContent())) ;
	                nodes.setStatus(Boolean.parseBoolean(eElement.getElementsByTagName("status").item(0).getTextContent()));
	                liseNode.add(nodes);
	            }
	         }
	      } catch (Exception e) {
	         log.error("Cannot read file ", e);
	      }
		try {
			validate(liseNode);
		} catch (CustomException e) {
			log.error(e);
		}
		return liseNode;
	}

	@Override
	public void modifyFile(String path, Stack<Nodes> nodesStack) {
		try {
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
	        Document document = documentBuilder.parse(path);
	        Element root = document.getDocumentElement();

	        for (Nodes n : nodesStack) {
	            // node elements
	            Element newNode = document.createElement("Node");

	            Element row = document.createElement("row");
	            row.appendChild(document.createTextNode(n.getRow()+""));
	            newNode.appendChild(row);

	            Element col = document.createElement("col");
	            col.appendChild(document.createTextNode(n.getCol()+""));
	            newNode.appendChild(col);
	            
	            Element value = document.createElement("value");
	            value.appendChild(document.createTextNode(n.getValue()+""));
	            newNode.appendChild(value);
	            
	            Element status = document.createElement("status");
	            status.appendChild(document.createTextNode(n.isStatus()+""));
	            newNode.appendChild(status);

	            root.appendChild(newNode);
	        }

	        DOMSource source = new DOMSource(document);

	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        Transformer transformer;
			
			transformer = transformerFactory.newTransformer();
			StreamResult result = new StreamResult(path);
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
	        transformer.transform(source, result);
	        
			} catch (TransformerConfigurationException e) {
				log.error("TransformerConfigurationException", e);
			} catch (TransformerException e) {
				log.error("TransformerException", e);
			} catch (ParserConfigurationException e) {
				log.error("ParserConfigurationException", e);
			} catch (SAXException e) {
				log.error("SAXException", e);
			} catch (IOException e) {
				log.error("IOException", e);
			}
	        
	}

	@Override
	public boolean validate(List<Nodes> nodeList) throws CustomException{
		if (nodeList == null ) {
			return false;
		}
		return true;
	}
	
//	public static void main(String[] args) {
//		HandleXMLData han = new HandleXMLData();
//		Nodes node = new Nodes(8, 8, 1, true);
//		List<Nodes> nodes= new ArrayList<>();
//		nodes.add(node);
//		NodeListDTO nodeListDTO = new NodeListDTO();
//		nodeListDTO.setNodeList(nodes);
//		han.modifyFile(pathRoot, nodeListDTO);
//		System.out.println(han.readFile(pathRoot).getNodeList().size());
//	}
}

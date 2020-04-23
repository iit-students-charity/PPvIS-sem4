package sabbaken.laba2.helpers;

import org.xml.sax.SAXException;
import sabbaken.laba2.model.Product;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FileHelperProductXML {

    public FileHelperProductXML(){
    }

    public ArrayList<Product> openFile(File file){
        ArrayList<Product> productsList = new ArrayList<Product>(0);
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();

        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            SAXHandler saxHandler = new SAXHandler();
            saxParser.parse(file, saxHandler);
            productsList = saxHandler.getPersonList();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return productsList;
    }

    public boolean saveFile(File file, ArrayList<Product> arrayOfModelsToSave) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            //Корневой элемент
            Document document = documentBuilder.newDocument();
            Element rootElement = document.createElement("root");
            document.appendChild(rootElement);

            for (Product product : arrayOfModelsToSave) {
                //Элемент "Person"
                Element productElement = document.createElement("product");
                rootElement.appendChild(productElement);

                //Элемент "id"
                Element idElement = document.createElement("name");
                idElement.appendChild(document.createTextNode(product.name));
                productElement.appendChild(idElement);

                //Элемент "manufacturer"
                Element manufacturerElement = document.createElement("manufacturer");
                manufacturerElement.appendChild(document.createTextNode(product.manufacturer));
                productElement.appendChild(manufacturerElement);

                //Элемент "manufacturerId"
                Element manufacturerIdElement = document.createElement("manufacturerID");
                manufacturerIdElement.appendChild(document.createTextNode(product.manufacturerID));
                productElement.appendChild(manufacturerIdElement);

                //Элемент "stock"
                Element stockElement = document.createElement("stock");
                stockElement.appendChild(document.createTextNode(String.valueOf(product.stock)));
                productElement.appendChild(stockElement);

                //Элемент "warehouseAddress"
                Element warehouseAddressElement = document.createElement("warehouseAddress");
                warehouseAddressElement.appendChild(document.createTextNode(product.warehouseAddress));
                productElement.appendChild(warehouseAddressElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);

            StreamResult streamResult = new StreamResult(file);

            transformer.transform(domSource, streamResult);
        } catch (ParserConfigurationException | TransformerException pce) {
            System.out.println(pce.getLocalizedMessage());
            pce.printStackTrace();
        }

        return true;
    }
}

package sabbaken.laba2.helpers;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import sabbaken.laba2.model.Product;

import java.util.ArrayList;

public class SAXHandler extends DefaultHandler {
    private ArrayList<Product> productsList = null;
    private Product product = null;
    private StringBuilder data = null;

    public ArrayList<Product> getPersonList() {
        return productsList;
    }

    boolean bName = false;
    boolean bManufacturer = false;
    boolean bManufacturerID = false;
    boolean bStock = false;
    boolean bWarehouseAddress = false;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if (qName.equalsIgnoreCase("product")) {
            product = new Product("", "", "", 0, "");
            if (productsList == null)
                productsList = new ArrayList<>();

        } else if (qName.equalsIgnoreCase("name")) {
            bName = true;
        } else if (qName.equalsIgnoreCase("manufacturer")) {
            bManufacturer = true;
        } else if (qName.equalsIgnoreCase("manufacturerID")) {
            bManufacturerID = true;
        } else if (qName.equalsIgnoreCase("stock")) {
            bStock = true;
        } else if (qName.equalsIgnoreCase("warehouseAddress")) {
            bWarehouseAddress = true;
        }
        // create the data container
        data = new StringBuilder();
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (bName) {
            product.name = data.toString();
            bName = false;
        } else if (bManufacturer) {
            product.manufacturer = data.toString();
            bManufacturer = false;
        } else if (bManufacturerID) {
            product.manufacturerID = data.toString();
            bManufacturerID = false;
        } else if (bStock) {
            product.stock = Integer.parseInt(data.toString());
            bStock = false;
        } else if (bWarehouseAddress) {
            product.warehouseAddress = data.toString();
            bWarehouseAddress = false;
        }

        if (qName.equalsIgnoreCase("product")) {
            // add Employee object to list
            productsList.add(product);
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        data.append(new String(ch, start, length));
    }
}

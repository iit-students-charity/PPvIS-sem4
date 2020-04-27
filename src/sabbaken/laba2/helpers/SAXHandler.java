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

    private boolean productNameInput = false,
            productManufacturerInput = false,
            productManufacturerIdInput = false,
            productStockInput = false,
            productWarehouseAddressInput = false;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if (qName.equalsIgnoreCase("product")) {
            product = new Product("", "", "", 0, "");
            if (productsList == null)
                productsList = new ArrayList<>();

        } else if (qName.equalsIgnoreCase("name")) {
            productNameInput = true;
        } else if (qName.equalsIgnoreCase("manufacturer")) {
            productManufacturerInput = true;
        } else if (qName.equalsIgnoreCase("manufacturerID")) {
            productManufacturerIdInput = true;
        } else if (qName.equalsIgnoreCase("stock")) {
            productStockInput = true;
        } else if (qName.equalsIgnoreCase("warehouseAddress")) {
            productWarehouseAddressInput = true;
        }
        // create the data container
        data = new StringBuilder();
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (productNameInput) {
            product.name = data.toString();
            productNameInput = false;
        } else if (productManufacturerInput) {
            product.manufacturer = data.toString();
            productManufacturerInput = false;
        } else if (productManufacturerIdInput) {
            product.manufacturerID = data.toString();
            productManufacturerIdInput = false;
        } else if (productStockInput) {
            product.stock = Integer.parseInt(data.toString());
            productStockInput = false;
        } else if (productWarehouseAddressInput) {
            product.warehouseAddress = data.toString();
            productWarehouseAddressInput = false;
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

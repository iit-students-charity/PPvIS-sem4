package sabbaken.laba2.controller;

import sabbaken.laba2.helpers.FileHelperProductXML;
import sabbaken.laba2.model.Product;

import java.io.File;
import java.util.ArrayList;

public class ProductController {
    FileHelperProductXML fileHelper = new FileHelperProductXML();
    public ArrayList<Product> products = new ArrayList<Product>();

    public ProductController() {
    }

    public ArrayList<Product> getProductList() {
        return products;
    }

    public void add(String name, String manufacturer, String manufacturerID, int stock, String warehouseAddress) {
        products.add(new Product(name, manufacturer, manufacturerID, stock, warehouseAddress));
    }

    public void remove(Product product) {
        products.remove(product);
    }

    public void openFile(File file) {
        products = fileHelper.openFile(file);
    }

    public void saveFile(File file) {
        fileHelper.saveFile(file, products);
    }

    public void newDoc() {
        this.products = new ArrayList<Product>();
    }
}

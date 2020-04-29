package sabbaken.laba2.controller;

import sabbaken.laba2.helpers.FileHelperProductXML;
import sabbaken.laba2.model.Product;
import sabbaken.laba2.view.View;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProductController {
    private FileHelperProductXML fileHelper = new FileHelperProductXML();
    private ArrayList<Product> products = new ArrayList<Product>();

    public ProductController() {
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public ArrayList<Product> getProductList() {
        return products;
    }

    public void add(String name, String manufacturer, String manufacturerID, int stock, String warehouseAddress) {
        products.add(new Product(name, manufacturer, manufacturerID, stock, warehouseAddress));
    }

    public void delete(List<Product> indexList) {
        for (Product productToDelete : indexList) {
            products.remove(productToDelete);
        }
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

    public List<Product> search(View.SEARCH_TYPE selectedItem, List<String> criteriaList) {
        List<Product> productList = products;
        List resultList;

        resultList = new ArrayList<Product>();

        switch (selectedItem) {
            case BY_NAME_AND_STOCK:
                final String NAME = criteriaList.get(0);
                Integer STOCK = 0;
                if (!criteriaList.get(3).equals("")) {
                    STOCK = Integer.parseInt(criteriaList.get(3));
                }

                if (!NAME.equals("")) {
                    for (Product product : productList) {
                        if (product.getName().equals(NAME)) {
                            resultList.add(product);
                        }
                    }
                } else {
                    for (Product product : productList) {
                        if (product.getStock() == STOCK) {
                            resultList.add(product);
                        }
                    }
                }
                break;
            case BY_MANUFACTURER_NAME_AND_UPN:
                final String M_NAME = criteriaList.get(1),
                        UPN = criteriaList.get(2);

                if (!M_NAME.equals("")) {
                    for (Product product : productList) {
                        if (product.getManufacturer().equals(M_NAME)) {
                            resultList.add(product);
                        }
                    }
                } else {
                    for (Product product : productList) {
                        if (product.getManufacturerID().equals(UPN)) {
                            resultList.add(product);
                        }
                    }
                }
                break;
            case BY_ADDRESS:
                final String ADDRESS = criteriaList.get(4);

                if (!ADDRESS.equals("")) {
                    for (Product product : productList) {
                        if (product.getWarehouseAddress().equals(ADDRESS)) {
                            resultList.add(product);
                        }
                    }
                }
                break;
        }

        return resultList;
    }
}

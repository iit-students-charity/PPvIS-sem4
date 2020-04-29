package sabbaken.laba2.model;


public final class Product {
    public String name, manufacturer, manufacturerID, warehouseAddress;
    public int stock;

    public Product(String name, String manufacturer, String manufacturerID, int stock, String warehouseAddress) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.manufacturerID = manufacturerID;
        this.stock = stock;
        this.warehouseAddress = warehouseAddress;
    }

    public String getName() {
        return this.name;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public String getManufacturerID() {
        return this.manufacturerID;
    }

    public int getStock() {
        return this.stock;
    }

    public String getWarehouseAddress() {
        return this.warehouseAddress;
    }
}

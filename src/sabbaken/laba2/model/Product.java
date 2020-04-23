package sabbaken.laba2.model;


public final class Product extends Model {
    public String name, manufacturer, manufacturerID, warehouseAddress;
    public int stock;

    public Product(String name, String manufacturer, String manufacturerID, int stock, String warehouseAddress) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.manufacturerID = manufacturerID;
        this.stock = stock;
        this.warehouseAddress = warehouseAddress;
    }
}

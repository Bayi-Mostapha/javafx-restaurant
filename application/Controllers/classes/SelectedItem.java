package application.Controllers.classes;

public class SelectedItem {

    private int itemid;
    private int quantity;
    private String itemname;
    private double itemprice;

    public SelectedItem(int itemid, String itemname,  double itemprice, int quantity) {
        this.itemid = itemid;
        this.quantity = quantity;
        this.itemname = itemname;
        this.itemprice = itemprice;
    }

    public int getItemId() {
        return itemid;
    }
    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public String getItemName() {
        return itemname;
    }
    public void setItemName(String itemname) {
        this.itemname = itemname;
    }
    
    public double getItemPrice() {
        return itemprice;
    }
    public void setItemPrice(double itemprice) {
        this.itemprice = itemprice;
    }
}

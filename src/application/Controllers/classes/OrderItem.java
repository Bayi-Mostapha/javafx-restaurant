package application.Controllers.classes;

public class OrderItem {
    private int itemId;
    private String name;
    private int quantity;

    // Constructor
    public OrderItem(int itemId, String name, int quantity) {
        this.itemId = itemId;
        this.name = name;
        this.quantity = quantity;
    }

    // Getters
    public int getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    // Setters
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

package application.Controllers.classes;

public class Item {
	private int id;
	private String name;
	private double price;
	private int categoryId;
	private String picture;
	
	public Item(int id, String name, double price, int categoryId, String picture) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.categoryId = categoryId;
		this.picture = picture;
	}
	
	public Item(String name, double price, String picture) {
		super();
		this.name = name;
		this.price = price;
		this.picture = picture;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	
}

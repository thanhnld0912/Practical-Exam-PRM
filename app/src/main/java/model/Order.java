package model;

public class Order {
    private int id;
    private double total;
    private String date;

    public Order() {}

    public Order(int id, double total, String date) {
        this.id = id;
        this.total = total;
        this.date = date;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}

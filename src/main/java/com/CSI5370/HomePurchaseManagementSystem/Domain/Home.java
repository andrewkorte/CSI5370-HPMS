package com.CSI5370.HomePurchaseManagementSystem.Domain;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
public class Home {

 private int id;
  private  int address;
  private String street;
   private String city;
    private String state;
    private float price;
    private int squareFeet;

 public int getId() {return id;}

 public void setId(int id) {this.id = id;}

 public int getAddress() {return address;}

    public void setAddress(int address) {this.address = address;}

    public String getStreet() {return street;}

    public void setStreet(String street) {this.street = street;}

    public String getCity() {return city;}

    public void setCity(String city) {this.city = city;}

    public String getState() {return state;}

    public void setState(String state) {this.state = state;}

    public float getPrice() {return price;}

    public void setPrice(float price) {this.price = price;}

    public int getSquareFeet() {return squareFeet;}

    public void setSquareFeet(int squareFeet) {this.squareFeet = squareFeet;}
}

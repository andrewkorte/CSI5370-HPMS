package com.CSI5370.HomePurchaseManagementSystem.Domain;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class Purchase {

    private int id;
    private int customerid;
    private int realtorid;
    private int homeid;
    private int loan;
    private int downpayment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerid() {
        return customerid;
    }

    public void setCustomerid(int customerid) {
        this.customerid = customerid;
    }

    public int getRealtorid() {
        return realtorid;
    }

    public void setRealtorid(int realtorid) {
        this.realtorid = realtorid;
    }

    public int getHomeid() {
        return homeid;
    }

    public void setHomeid(int homeid) {
        this.homeid = homeid;
    }

    public int getLoan() {
        return loan;
    }

    public void setLoan(int loan) {
        this.loan = loan;
    }

    public int getDownpayment() {
        return downpayment;
    }

    public void setDownpayment(int downpayment) {
        this.downpayment = downpayment;
    }
}

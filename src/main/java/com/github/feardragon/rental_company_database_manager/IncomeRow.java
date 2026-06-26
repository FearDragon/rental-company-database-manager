package com.github.feardragon.rental_company_database_manager;

import java.math.BigDecimal;
import java.sql.Date;

public class IncomeRow {
    private int incomeID;
    private String incomeHouse;
    private String incomeName;
    private BigDecimal incomeAmount;
    private Date datePaid;
    private Date dateDue;

    public IncomeRow(int incomeID, String incomeHouse, String incomeName,
                     BigDecimal incomeAmount, Date datePaid, Date dateDue){
        this.incomeID = incomeID;
        this.incomeHouse = incomeHouse;
        this.incomeName = incomeName;
        this.incomeAmount = incomeAmount;
        this.datePaid = datePaid;
        this.dateDue = dateDue;
    }

    public int getIncomeID(){
        return incomeID;
    }

    public void setIncomeID(int incomeID){
        this.incomeID = incomeID;
    }

    public String getIncomeHouse(){
        return incomeHouse;
    }

    public void setIncomeHouse(String incomeHouse){
        this.incomeHouse = incomeHouse;
    }

    public String getIncomeName(){
        return incomeName;
    }

    public void setIncomeName(String incomeName){
        this.incomeName = incomeName;
    }

    public BigDecimal getIncomeAmount(){
        return incomeAmount;
    }

    public void setIncomeAmount(BigDecimal incomeAmount){
        this.incomeAmount = incomeAmount;
    }

    public Date getDatePaid(){
        return datePaid;
    }

    public void setDatePaid(Date datePaid){
        this.datePaid = datePaid;
    }

    public Date getDateDue(){
        return dateDue;
    }

    public void setDateDue(Date dateDue){
        this.dateDue = dateDue;
    }

    @Override
    public String toString(){
        return incomeID + " | " + incomeHouse + " | " + incomeName + " | " + incomeAmount + " | " + datePaid + " | " + dateDue;
    }
}

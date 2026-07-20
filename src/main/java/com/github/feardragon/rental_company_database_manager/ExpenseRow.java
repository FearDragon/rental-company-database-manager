package com.github.feardragon.rental_company_database_manager;

import java.math.BigDecimal;
import java.sql.Date;

public class ExpenseRow {
    private int expenseID;
    private String expenseHouse;
    private String expenseName;
    private BigDecimal expensePrice;
    private Date expenseDate;
     public ExpenseRow(int expenseID, String expenseHouse, String expenseName,
                       BigDecimal expensePrice, Date expenseDate){
         this.expenseID = expenseID;
         this.expenseHouse = expenseHouse;
         this.expenseName = expenseName;
         this.expensePrice = expensePrice;
         this.expenseDate = expenseDate;
     }

     public int getExpenseID() {
         return expenseID;
     }

     public void setExpenseID(int expenseID){
         this.expenseID = expenseID;
     }

     public String getExpenseHouse(){
         return expenseHouse;
     }

     public void setExpenseHouse(String expenseHouse){
         this.expenseHouse = expenseHouse;
     }

     public String getExpenseName(){
         return expenseName;
     }

     public void setExpenseName(String expenseName){
         this.expenseName = expenseName;
     }

     public BigDecimal getExpensePrice(){
         return expensePrice;
     }

     public void setExpensePrice(BigDecimal expensePrice){
         this.expensePrice = expensePrice;
     }

     public Date getExpenseDate(){
         return expenseDate;
     }

     public void setExpenseDate(Date expenseDate){
         this.expenseDate = expenseDate;
     }

     @Override
     public String toString(){
         return expenseID + " | " + expenseHouse + " | " + expenseName + " | " + expensePrice + " | " + expenseDate;
     }
}

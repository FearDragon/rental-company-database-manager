package com.github.feardragon.rental_company_database_manager;
import com.zaxxer.hikari.HikariDataSource;
import com.github.feardragon.rental_company_database_manager.rental_company_database_manager.BuildConfig;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
    private static final String URL = BuildConfig.URL;
    private static final String USERNAME = BuildConfig.USER;
    private static final String PASSWORD = BuildConfig.PASS;
    private static final HikariDataSource dataSource;

    static {
        dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
    }

    @Override
    public void start(Stage stage) throws Exception{
        Expenses expenses = new Expenses(dataSource);
        Income income = new Income(dataSource);
        Houses houses = new Houses(dataSource);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("scene.fxml"));
        Parent root = loader.load();

        FXMLController controller = loader.getController();
        controller.setObjects(expenses, income, houses);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        stage.setTitle("Rental Database Manager");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }

    public static BigDecimal profitByMonthYear(Income income, Expenses expenses, int month, int year) throws SQLException{
        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal totalExpenses = BigDecimal.ZERO;
        BigDecimal profit;
        ArrayList<Integer> expensePositions = expenses.getIDByMonthYear(month, year);
        ArrayList<Integer> incomePositions = income.getIDByMonthYearPaid(month, year);
        for (Integer position: incomePositions){
            totalIncome = totalIncome.add(income.getEntryByID(position).getIncomeAmount());
        }
        for (Integer position: expensePositions){
            totalExpenses = totalExpenses.add(expenses.getEntryByID(position).getExpensePrice());
        }
        profit = totalIncome.subtract(totalExpenses);
        return profit;
    }

    public static void printExpenseByYear(Expenses expenses, int year) throws SQLException{
        ArrayList<Integer> positions = expenses.getIDByYear(year);
        for (Integer position: positions){
            System.out.println(expenses.getEntryByID(position));
        }
    }

    public static void printExpenseByMonth(Expenses expenses, int month) throws SQLException{
        ArrayList<Integer> positions = expenses.getIDByMonth(month);
        for (Integer position: positions){
            System.out.println(expenses.getEntryByID(position));
        }
    }

    public static void printIncomeByMonthPaid(Income income, int month) throws SQLException{
        ArrayList<Integer> positions = income.getIDByMonthPaid(month);
        for (Integer position: positions){
            System.out.println(income.getEntryByID(position));
        }
    }

    public static void printIncomeByMonthDue(Income income, int month) throws SQLException{
        ArrayList<Integer> positions = income.getIDByMonthDue(month);
        for (Integer position: positions){
            System.out.println(income.getEntryByID(position));
        }
    }
}
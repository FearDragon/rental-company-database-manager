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
}
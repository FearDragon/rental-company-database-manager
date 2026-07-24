package com.github.feardragon.rental_company_database_manager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;
import java.util.function.UnaryOperator;

public class DeleteIncomeController {

    @FXML
    private Label deleteIncomeFailed;
    @FXML
    private Label deleteIncomeSuccess;
    @FXML
    private Label deleteIncomeWarning;
    @FXML
    private TextField idField;

    private Income income;

    public void setIncome(Income income) {
        this.income = income;
    }

    @FXML
    public void initialize() {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            return newText.matches("\\d*") ? change :  null;
        };
        idField.setTextFormatter(new TextFormatter<>(filter));
    }

    public void deleteIncome(ActionEvent event) {
        if (idField.getText().equals("")) {
            deleteIncomeFailed.setVisible(false);
            deleteIncomeSuccess.setVisible(false);
            deleteIncomeWarning.setVisible(true);
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this Enty? \nThis cannot be undone.", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Delete Expense?");
        Optional<ButtonType> input = alert.showAndWait();
        if (input.get() == ButtonType.YES) {
            try{
                int id = Integer.parseInt(idField.getText());
                income.deleteEntry(id);
                deleteIncomeSuccess.setVisible(true);
                deleteIncomeFailed.setVisible(false);
                deleteIncomeWarning.setVisible(false);
            } catch (Exception e) {
                deleteIncomeFailed.setVisible(true);
                deleteIncomeWarning.setVisible(false);
                deleteIncomeSuccess.setVisible(false);
            }
        }
    }
}

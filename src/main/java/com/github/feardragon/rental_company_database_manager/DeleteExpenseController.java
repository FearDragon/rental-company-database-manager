package com.github.feardragon.rental_company_database_manager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;
import java.util.function.UnaryOperator;


public class DeleteExpenseController {

    @FXML
    private TextField idField;
    @FXML
    private Label deleteExpenseFailed;
    @FXML
    private Label deleteExpenseSuccess;
    @FXML
    private Label deleteExpenseWarning;

    private Expenses expenses;

    public void setExpenses(Expenses expenses) {
        this.expenses = expenses;
    }

    @FXML
    public void initialize(){
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            return newText.matches("\\d*") ? change :  null;
        };
        idField.setTextFormatter(new TextFormatter<>(filter));
    }

    public void deleteExpense(ActionEvent actionEvent) {
        if (idField.getText().equals("")) {
            deleteExpenseFailed.setVisible(false);
            deleteExpenseSuccess.setVisible(false);
            deleteExpenseWarning.setVisible(true);
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this Enty? \nThis cannot be undone.", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Delete Expense?");
        Optional<ButtonType> input = alert.showAndWait();
        if (input.get() == ButtonType.YES) {
            try{
                int id = Integer.parseInt(idField.getText());
                expenses.deleteEntry(id);
                deleteExpenseSuccess.setVisible(true);
                deleteExpenseFailed.setVisible(false);
                deleteExpenseWarning.setVisible(false);
            } catch (Exception e) {
                deleteExpenseFailed.setVisible(true);
                deleteExpenseWarning.setVisible(false);
                deleteExpenseSuccess.setVisible(false);
            }
        }
    }
}

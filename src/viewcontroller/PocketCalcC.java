package viewcontroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;

public class PocketCalcC implements Initializable
{
    @FXML
    TextField inputField = new TextField();

    @FXML
    ListView<Double> enteredValues = new ListView<>();

    Stack<Double> stack = new Stack<>();

    private Stage stage;

    public void setStage(Stage stage)
    {
        this.stage = stage;
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {}

    public static void show(Stage stage)
    {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(PocketCalcC.class.getResource("PocketCalcV.fxml"));
            Parent root = fxmlLoader.load();

            PocketCalcC ctrl = fxmlLoader.getController();
            ctrl.setStage(stage);

            stage.setTitle("UPN Controller");
            stage.setScene(new Scene(root, 400, 400));
            stage.show();
        }
        catch (IOException ex) {}
    }

    public void handle_buttons(ActionEvent actionEvent)
    {
        Object obj = actionEvent.getSource();
        Button btn = (Button)obj;

        int input = 0;
        String operator;
        boolean isValidInput = true;

        try
        {
            input = Integer.parseInt(btn.getText());
        }
        catch (Exception e)
        {
            System.out.println("Please only use numbers as input!");
            isValidInput = false;
        }

        if (isValidInput)
        {
            inputField.appendText("" + input);
        }
        else
            {
                operator = btn.getText();

                switch (btn.getText())
                {
                    case "Enter":
                        addValueToList();
                        break;

                    case "C":
                        clearAll();
                        break;

                    case "CE":
                        inputField.clear();
                        break;

                    case "+":
                    case "-":
                    case "*":
                    case "/":
                        calc(operator);
                        break;

                    case "1/x":
                        inputField.setText(String.format("%f",1/Double.parseDouble(inputField.getText())).replace(",", "."));
                        break;

                    case "x==y":

                    case ".":
                    inputField.appendText(operator);
                    break;

                    default:
                        clearAll();
                        inputField.setText("ERROR");
                }
            }
    }

    void calc(String op)
    {
        initStack();

        double input = stack.pop();

        switch (op)
        {
            case "+":
                while(stack.size()!=0)
                {
                    input += stack.pop();
                }
                break;

            case "-":
                while(stack.size()!=0)
                {
                    input -= stack.pop();
                }
                break;

            case "*":
                while(stack.size()!=0)
                {
                    input *= stack.pop();
                }
                break;

            case "/":
                while(stack.size()!=0)
                {
                    input /= stack.pop();
                }
                break;
        }

        enteredValues.getItems().clear();
        enteredValues.getItems().add(input);
    }

    void clearAll()
    {
        stack.clear();
        enteredValues.getItems().removeAll(enteredValues.getItems());
    }

    void addValueToList()
    {
        enteredValues.getItems().add(Double.parseDouble(inputField.getText()));
        inputField.clear();
    }

    void initStack()
    {
        try
        {
            addValueToList();
            System.out.println("success at initialization");
        }
        catch(Exception ignored)
        {
            System.out.println("failed initialization");
        }

        stack.clear();

        for (int i = enteredValues.getItems().size() - 1; i >= 0; i--)
        {
            stack.push(enteredValues.getItems().get(i));
        }
    }
}
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class CurrencyConverterApp extends Application {

    public static void main(String[] args) {
        launch(args); // Start JavaFX Application
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Currency Converter");

        // --- UI Elements ---
        ComboBox<String> fromCurrency = new ComboBox<>();
        ComboBox<String> toCurrency = new ComboBox<>();
        TextField amountField = new TextField();
        Button convertButton = new Button("Convert");
        Label resultLabel = new Label();

        // --- Supported currencies ---
        fromCurrency.getItems().addAll("USD", "EUR", "INR", "JPY");
        toCurrency.getItems().addAll("USD", "EUR", "INR", "JPY");

        fromCurrency.setValue("USD");
        toCurrency.setValue("INR");

        // --- Layout setup ---
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);

        grid.add(new Label("From Currency:"), 0, 0);
        grid.add(fromCurrency, 1, 0);
        grid.add(new Label("To Currency:"), 0, 1);
        grid.add(toCurrency, 1, 1);
        grid.add(new Label("Amount:"), 0, 2);
        grid.add(amountField, 1, 2);
        grid.add(convertButton, 0, 3);
        grid.add(resultLabel, 1, 3);

        // --- Convert button action ---
        convertButton.setOnAction(e -> {
            try {
                String from = fromCurrency.getValue();
                String to = toCurrency.getValue();
                double amount = Double.parseDouble(amountField.getText());

                double rate = getExchangeRate(from, to); // Use dummy rates
                double converted = amount * rate;

                resultLabel.setText(String.format("%.2f %s = %.2f %s", amount, from, converted, to));
            } catch (NumberFormatException ex) {
                resultLabel.setText("Invalid amount entered.");
            } catch (Exception ex) {
                resultLabel.setText("Error: " + ex.getMessage());
            }
        });

        Scene scene = new Scene(grid, 400, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // --- Dummy exchange rates (no internet/API required) ---
    private double getExchangeRate(String from, String to) {
        if (from.equals(to)) return 1.0;

        switch (from + "_" + to) {
            case "USD_INR": return 83.15;
            case "USD_EUR": return 0.92;
            case "USD_JPY": return 155.36;

            case "INR_USD": return 0.012;
            case "INR_EUR": return 0.011;
            case "INR_JPY": return 1.87;

            case "EUR_USD": return 1.09;
            case "EUR_INR": return 90.21;
            case "EUR_JPY": return 169.5;

            case "JPY_USD": return 0.0064;
            case "JPY_INR": return 0.53;
            case "JPY_EUR": return 0.0059;
        }

        return 1.0; // default if not matched
    }
}

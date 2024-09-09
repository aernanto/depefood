
package assignments.assignment4.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import assignments.assignment3.DepeFood;
import assignments.assignment3.Menu;
import assignments.assignment3.Order;

public class BillPrinter {
    public BillPrinter(Stage stage) {
    }

    // Membuat form pencetak bill
    private Scene createBillPrinterForm() {
        StackPane layout = new StackPane();
        layout.setStyle("-fx-background-color: linear-gradient(to bottom right, #6a11cb, #2575fc);");

        VBox formLayout = new VBox(20);
        formLayout.setAlignment(Pos.CENTER);
        formLayout.setPadding(new Insets(20));

        Label orderIdLabel = new Label("Order ID:");
        orderIdLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");
        TextField orderIdInput = new TextField();
        orderIdInput.setStyle("-fx-background-radius: 20;");

        TextArea billOutput = new TextArea();
        billOutput.setEditable(false);
        billOutput.setPrefHeight(300);
        billOutput.setStyle("-fx-background-radius: 20;");

        Button printButton = new Button("Print Bill");
        printButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 20;");
        printButton.setOnAction(e -> {
            String orderId = orderIdInput.getText().trim();
            printBill(orderId, billOutput);
        });

        formLayout.getChildren().addAll(orderIdLabel, orderIdInput, printButton, billOutput);

        layout.getChildren().addAll(createBackgroundShapes(), formLayout);

        return new Scene(layout, 500, 700);
    }

    // Mencetak bill berdasarkan Order ID
    private void printBill(String orderId, TextArea billOutput) {
        Order order = DepeFood.getOrderOrNull(orderId);
        if (order == null) {
            billOutput.setText("Order ID tidak ditemukan.");
            return;
        }
        billOutput.setText(outputBillPesanan(order));
    }

    // Menghasilkan output bill pesanan
    private String outputBillPesanan(Order order) {
        return String.format(
                "Bill:%nOrder ID: %s%nTanggal Pemesanan: %s%nLokasi Pengiriman: %s%nStatus Pengiriman: %s%nPesanan:%n%s%nBiaya Ongkos Kirim: Rp %s%nTotal Biaya: Rp %s%n",
                order.getOrderId(), order.getTanggal(), order.getRestaurant().getNama(), 
                order.getOrderFinished() ? "Finished" : "Not Finished", 
                getMenuPesananOutput(order), 
                formatCurrency(order.getOngkir()), 
                formatCurrency(order.getTotalHarga()));
    }

    // Menghasilkan output menu pesanan
    private String getMenuPesananOutput(Order order) {
        StringBuilder pesananBuilder = new StringBuilder();
        for (Menu menu : order.getSortedMenu()) {
            pesananBuilder.append("- ").append(menu.getNamaMakanan()).append(" ").append(formatCurrency(menu.getHarga())).append("\n");
        }
        if (pesananBuilder.length() > 0) {
            pesananBuilder.deleteCharAt(pesananBuilder.length() - 1);
        }
        return pesananBuilder.toString();
    }

    // Memformat nilai ke dalam mata uang
    private String formatCurrency(double amount) {
        DecimalFormat decimalFormat = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        decimalFormat.setDecimalFormatSymbols(symbols);
        return decimalFormat.format(amount);
    }

    // Membuat bentuk latar belakang
    private StackPane createBackgroundShapes() {
        StackPane shapesLayout = new StackPane();

        Circle circle1 = new Circle(200, Color.web("#ffffff33"));
        circle1.setTranslateX(-100);
        circle1.setTranslateY(-200);

        Circle circle2 = new Circle(150, Color.web("#ffffff44"));
        circle2.setTranslateX(150);
        circle2.setTranslateY(-100);

        Rectangle rectangle = new Rectangle(500, 700, Color.TRANSPARENT);
        rectangle.setStyle("-fx-border-color: white; -fx-border-width: 5; -fx-background-radius: 20; -fx-border-radius: 20;");
        rectangle.setArcHeight(20);
        rectangle.setArcWidth(20);
        rectangle.setEffect(new javafx.scene.effect.DropShadow(10, Color.BLACK));

        shapesLayout.getChildren().addAll(circle1, circle2, rectangle);

        return shapesLayout;
    }

    // Mengembalikan scene dari form pencetak bill
    public Scene getScene() {
        return this.createBillPrinterForm();
    }
}
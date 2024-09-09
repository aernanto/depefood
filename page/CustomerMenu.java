
package assignments.assignment4.page;

import assignments.assignment3.DepeFood;
import assignments.assignment3.Order;
import assignments.assignment3.Restaurant;
import assignments.assignment3.User;
import assignments.assignment3.payment.CreditCardPayment;
import assignments.assignment3.payment.DepeFoodPaymentSystem;
import assignments.assignment4.MainApp;
import assignments.assignment4.OrderGenerator;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

public class CustomerMenu extends MemberMenu {
    private Stage stage;
    private Scene addOrderScene;
    private Scene printBillScene;
    private Scene payBillScene;
    private Scene cekSaldoScene;
    private MainApp mainApp;
    private User user;

    public CustomerMenu(Stage stage, MainApp mainApp) {
        this.stage = stage;
        this.mainApp = mainApp;
        createBaseMenu();
    }

    // Mengatur user
    public void setUser(User user) {
        this.user = user;
        this.addOrderScene = createTambahPesananForm();
        this.printBillScene = createBillPrinterForm();
        this.payBillScene = createBayarBillForm();
        this.cekSaldoScene = createCekSaldoScene();
    }

    @Override
    // Membuat menu utama Customer
    public Scene createBaseMenu() {
        StackPane layout = new StackPane();
        layout.setStyle("-fx-background-color: linear-gradient(to bottom right, #6a11cb, #2575fc);");

        VBox menuLayout = new VBox(20);
        menuLayout.setAlignment(Pos.CENTER);

        Label title = new Label("Customer Menu");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        Button addOrderButton = new Button("Create Order");
        addOrderButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 20;");
        addOrderButton.setOnAction(e -> stage.setScene(addOrderScene));

        Button printBillButton = new Button("Print Bill");
        printBillButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 20;");
        printBillButton.setOnAction(e -> stage.setScene(printBillScene));

        Button payBillButton = new Button("Pay Bill");
        payBillButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 20;");
        payBillButton.setOnAction(e -> stage.setScene(payBillScene));

        Button checkBalanceButton = new Button("Check Balance");
        checkBalanceButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 20;");
        checkBalanceButton.setOnAction(e -> stage.setScene(cekSaldoScene));

        Button logoutButton = new Button("Logout");
        logoutButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 20;");
        logoutButton.setOnAction(e -> mainApp.logout());

        menuLayout.getChildren().addAll(title, addOrderButton, printBillButton, payBillButton, checkBalanceButton, logoutButton);

        layout.getChildren().addAll(createBackgroundShapes(), menuLayout);

        return new Scene(layout, 500, 700);
    }

    // Membuat form penambahan pesanan
    private Scene createTambahPesananForm() {
        StackPane layout = new StackPane();
        layout.setStyle("-fx-background-color: linear-gradient(to bottom right, #6a11cb, #2575fc);");

        VBox menuLayout = new VBox(20);
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.setPadding(new Insets(20));

        Label title = new Label("Add Order");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label restoLabel = new Label("Restaurant Name:");
        restoLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");
        TextField restoInput = new TextField();
        restoInput.setStyle("-fx-background-radius: 20;");

        Label dateLabel = new Label("Order Date (DD/MM/YYYY):");
        dateLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");
        TextField dateInput = new TextField();
        dateInput.setStyle("-fx-background-radius: 20;");

        Label itemsLabel = new Label("Order Items:");
        itemsLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");
        TextArea itemsInput = new TextArea();
        itemsInput.setStyle("-fx-background-radius: 20;");

        Button submitButton = new Button("Submit");
        submitButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 20;");
        submitButton.setOnAction(e -> {
            String restoName = restoInput.getText().trim();
            String date = dateInput.getText().trim();
            String[] items = itemsInput.getText().trim().split("\n");

            handleBuatPesanan(restoName, date, List.of(items));
        });

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 20;");
        backButton.setOnAction(e -> stage.setScene(createBaseMenu()));

        restoInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                submitButton.fire();
            }
        });

        dateInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                submitButton.fire();
            }
        });

        itemsInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                submitButton.fire();
            }
        });

        menuLayout.getChildren().addAll(title, restoLabel, restoInput, dateLabel, dateInput, itemsLabel, itemsInput, submitButton, backButton);

        layout.getChildren().addAll(createBackgroundShapes(), menuLayout);

        return new Scene(layout, 500, 700);
    }

    // Membuat form pencetakan bill
    private Scene createBillPrinterForm() {
        StackPane layout = new StackPane();
        layout.setStyle("-fx-background-color: linear-gradient(to bottom right, #6a11cb, #2575fc);");

        VBox menuLayout = new VBox(20);
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.setPadding(new Insets(20));

        Label title = new Label("Print Bill");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label orderIdLabel = new Label("Order ID:");
        orderIdLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");
        TextField orderIdInput = new TextField();
        orderIdInput.setStyle("-fx-background-radius: 20;");

        Button printButton = new Button("Print Bill");
        printButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 20;");
        printButton.setOnAction(e -> {
            String orderId = orderIdInput.getText().trim();
            handleCetakBill(orderId);
        });

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 20;");
        backButton.setOnAction(e -> stage.setScene(createBaseMenu()));

        orderIdInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                printButton.fire();
            }
        });

        menuLayout.getChildren().addAll(title, orderIdLabel, orderIdInput, printButton, backButton);

        layout.getChildren().addAll(createBackgroundShapes(), menuLayout);

        return new Scene(layout, 500, 700);
    }

    // Membuat form pembayaran bill
    private Scene createBayarBillForm() {
        StackPane layout = new StackPane();
        layout.setStyle("-fx-background-color: linear-gradient(to bottom right, #6a11cb, #2575fc);");

        VBox menuLayout = new VBox(20);
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.setPadding(new Insets(20));

        Label title = new Label("Pay Bill");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label orderIdLabel = new Label("Order ID:");
        orderIdLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");
        TextField orderIdInput = new TextField();
        orderIdInput.setStyle("-fx-background-radius: 20;");

        Label paymentMethodLabel = new Label("Payment Method:");
        paymentMethodLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");
        ComboBox<String> paymentMethodInput = new ComboBox<>();
        paymentMethodInput.setItems(FXCollections.observableArrayList("Credit Card", "Debit"));
        paymentMethodInput.setStyle("-fx-background-radius: 20;");

        Button payButton = new Button("Pay");
        payButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 20;");
        payButton.setOnAction(e -> {
            String orderId = orderIdInput.getText().trim();
            int paymentMethod = paymentMethodInput.getSelectionModel().getSelectedIndex() + 1;
            handleBayarBill(orderId, paymentMethod);
        });

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 20;");
        backButton.setOnAction(e -> stage.setScene(createBaseMenu()));

        orderIdInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                payButton.fire();
            }
        });

        paymentMethodInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                payButton.fire();
            }
        });

        menuLayout.getChildren().addAll(title, orderIdLabel, orderIdInput, paymentMethodLabel, paymentMethodInput, payButton, backButton);

        layout.getChildren().addAll(createBackgroundShapes(), menuLayout);

        return new Scene(layout, 500, 700);
    }

    // Membuat form cek saldo
    private Scene createCekSaldoScene() {
        StackPane layout = new StackPane();
        layout.setStyle("-fx-background-color: linear-gradient(to bottom right, #6a11cb, #2575fc);");

        VBox menuLayout = new VBox(20);
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.setPadding(new Insets(20));

        Label title = new Label("Check Balance");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label saldoLabel = new Label("Balance:");
        saldoLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

        Label saldoValueLabel = new Label(user != null ? formatCurrency(user.getSaldo()) : "N/A");
        saldoValueLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 20;");
        backButton.setOnAction(e -> stage.setScene(createBaseMenu()));

        menuLayout.getChildren().addAll(title, saldoLabel, saldoValueLabel, backButton);

        layout.getChildren().addAll(createBackgroundShapes(), menuLayout);

        return new Scene(layout, 500, 700);
    }

    // Menangani pembuatan pesanan
    private void handleBuatPesanan(String namaRestoran, String tanggalPemesanan, List<String> menuItems) {
        Restaurant restaurant = DepeFood.getRestaurantByName(namaRestoran);
        if (restaurant == null) {
            showAlert("Error", "Restoran tidak terdaftar pada sistem.", Alert.AlertType.ERROR);
            return;
        }

        if (!OrderGenerator.validateDate(tanggalPemesanan)) {
            showAlert("Error", "Masukkan tanggal sesuai format (DD/MM/YYYY)", Alert.AlertType.ERROR);
            return;
        }

        if (!DepeFood.validateRequestPesanan(restaurant, menuItems)) {
            showAlert("Error", "Mohon memesan menu yang tersedia di Restoran!", Alert.AlertType.ERROR);
            return;
        }

        Order order = new Order(
                OrderGenerator.generateOrderID(namaRestoran, tanggalPemesanan, user.getNomorTelepon()),
                tanggalPemesanan,
                OrderGenerator.calculateDeliveryCost(user.getLokasi()),
                restaurant,
                DepeFood.getMenuRequest(restaurant, menuItems));

        user.addOrderHistory(order);
        showAlert("Success", String.format("Pesanan dengan ID %s diterima!", order.getOrderId()), Alert.AlertType.INFORMATION);
    }

    // Menangani pencetakan bill
    private void handleCetakBill(String orderId) {
        Order order = DepeFood.getOrderOrNull(orderId);
        if (order == null) {
            showAlert("Error", "Order ID tidak dapat ditemukan.", Alert.AlertType.ERROR);
            return;
        }
        showAlert("Bill", outputBillPesanan(order), Alert.AlertType.INFORMATION);
    }

    // Menangani pembayaran bill
    private void handleBayarBill(String orderId, int paymentOption) {
        Order order = DepeFood.getOrderOrNull(orderId);

        if (order == null) {
            showAlert("Error", "Order ID tidak dapat ditemukan.", Alert.AlertType.ERROR);
            return;
        }

        if (order.getOrderFinished()) {
            showAlert("Error", "Pesanan dengan ID ini sudah lunas!", Alert.AlertType.ERROR);
            return;
        }

        if (paymentOption != 1 && paymentOption != 2) {
            showAlert("Error", "Pilihan tidak valid, silakan coba kembali", Alert.AlertType.ERROR);
            return;
        }

        DepeFoodPaymentSystem paymentSystem = user.getPaymentSystem();

        boolean isCreditCard = paymentSystem instanceof CreditCardPayment;

        if ((isCreditCard && paymentOption == 2) || (!isCreditCard && paymentOption == 1)) {
            showAlert("Error", "User belum memiliki metode pembayaran ini!", Alert.AlertType.ERROR);
            return;
        }

        long amountToPay;
        try {
            amountToPay = paymentSystem.processPayment(user.getSaldo(), (long) order.getTotalHarga());
        } catch (Exception e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
            return;
        }

        long saldoLeft = user.getSaldo() - amountToPay;
        user.setSaldo(saldoLeft);
        DepeFood.handleUpdateStatusPesanan(order);

        showAlert("Success", String.format("Berhasil Membayar Bill sebesar Rp %s", formatCurrency(amountToPay)), Alert.AlertType.INFORMATION);
    }

    // Menampilkan alert
    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Menghasilkan bill pesanan dalam bentuk teks
    private String outputBillPesanan(Order order) {
        StringBuilder bill = new StringBuilder();
        bill.append("Bill:\n")
                .append("Order ID: ").append(order.getOrderId()).append("\n")
                .append("Tanggal Pemesanan: ").append(order.getTanggal()).append("\n")
                .append("Lokasi Pengiriman: ").append(user.getLokasi()).append("\n")
                .append("Pesanan:\n");

        for (assignments.assignment3.Menu menu : order.getSortedMenu()) {
            bill.append("- ").append(menu.getNamaMakanan()).append(" ")
                    .append(formatCurrency(menu.getHarga())).append("\n");
        }

        bill.append("Biaya Ongkos Kirim: Rp ").append(formatCurrency(order.getOngkir())).append("\n")
                .append("Total Biaya: Rp ").append(formatCurrency(order.getTotalHarga()));

        return bill.toString();
    }

    // Memformat mata uang
    private String formatCurrency(double amount) {
        DecimalFormat decimalFormat = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        decimalFormat.setDecimalFormatSymbols(symbols);
        return decimalFormat.format(amount);
    }

    // Membuat bentuk latar belakang
    protected StackPane createBackgroundShapes() {
        StackPane shapesLayout = new StackPane();

        Circle circle1 = new Circle(200, Color.web("#ffffff33"));
        circle1.setTranslateX(-100);
        circle1.setTranslateY(-200);

        Circle circle2 = new Circle(150, Color.web("#ffffff44"));
        circle2.setTranslateX(150);
        circle2.setTranslateY(-100);

        Rectangle rectangle = new Rectangle(400, 600, Color.TRANSPARENT);
        rectangle.setStyle("-fx-border-color: white; -fx-border-width: 5; -fx-border-radius: 20;");

        shapesLayout.getChildren().addAll(circle1, circle2, rectangle);

        return shapesLayout;
    }
}
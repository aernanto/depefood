
package assignments.assignment4.page;

import assignments.assignment3.DepeFood;
import assignments.assignment3.Restaurant;
import assignments.assignment3.User;
import assignments.assignment4.MainApp;
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
import java.util.stream.Collectors;

public class AdminMenu extends MemberMenu {
    private Stage stage;
    private Scene scene;
    private Scene addRestaurantScene;
    private Scene addMenuScene;
    private Scene viewRestaurantsScene;
    private MainApp mainApp;
    private ComboBox<String> restaurantComboBox = new ComboBox<>();

    public AdminMenu(Stage stage, MainApp mainApp, User user) {
        this.stage = stage;
        this.mainApp = mainApp;
        this.scene = createBaseMenu();
        this.addRestaurantScene = createAddRestaurantForm();
        this.addMenuScene = createAddMenuForm();
        this.viewRestaurantsScene = createViewRestaurantsForm();
    }

    @Override
    // Membuat menu utama Admin
    public Scene createBaseMenu() {
        StackPane layout = new StackPane();
        layout.setStyle("-fx-background-color: linear-gradient(to bottom right, #6a11cb, #2575fc);");

        VBox menuLayout = new VBox(20);
        menuLayout.setAlignment(Pos.CENTER);

        Label title = new Label("Admin Menu");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        Button addRestaurantButton = new Button("Add Restaurant");
        addRestaurantButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 20;");
        addRestaurantButton.setOnAction(e -> stage.setScene(addRestaurantScene));

        Button addMenuButton = new Button("Add Restaurant Menu");
        addMenuButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 20;");
        addMenuButton.setOnAction(e -> stage.setScene(addMenuScene));

        Button viewRestaurantsButton = new Button("View Restaurants");
        viewRestaurantsButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 20;");
        viewRestaurantsButton.setOnAction(e -> stage.setScene(viewRestaurantsScene));

        Button logoutButton = new Button("Logout");
        logoutButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 20;");
        logoutButton.setOnAction(e -> mainApp.logout());

        menuLayout.getChildren().addAll(title, addRestaurantButton, addMenuButton, viewRestaurantsButton, logoutButton);

        layout.getChildren().addAll(createBackgroundShapes(), menuLayout);

        return new Scene(layout, 500, 700);
    }

    // Membuat form penambahan restoran
    private Scene createAddRestaurantForm() {
        StackPane layout = new StackPane();
        layout.setStyle("-fx-background-color: linear-gradient(to bottom right, #6a11cb, #2575fc);");

        VBox formLayout = new VBox(20);
        formLayout.setAlignment(Pos.CENTER);
        formLayout.setPadding(new Insets(20));

        Label title = new Label("Add Restaurant");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label nameLabel = new Label("Restaurant Name:");
        nameLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");
        TextField nameInput = new TextField();
        nameInput.setStyle("-fx-background-radius: 20;");

        Button submitButton = new Button("Add Restaurant");
        submitButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 20;");
        submitButton.setOnAction(e -> {
            String name = nameInput.getText().trim();
            handleTambahRestoran(name);
        });

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 20;");
        backButton.setOnAction(e -> stage.setScene(scene));

        nameInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                submitButton.fire();
            }
        });

        formLayout.getChildren().addAll(title, nameLabel, nameInput, submitButton, backButton);

        layout.getChildren().addAll(createBackgroundShapes(), formLayout);

        return new Scene(layout, 500, 700);
    }

    // Membuat form penambahan menu restoran
    private Scene createAddMenuForm() {
        StackPane layout = new StackPane();
        layout.setStyle("-fx-background-color: linear-gradient(to bottom right, #6a11cb, #2575fc);");

        VBox formLayout = new VBox(20);
        formLayout.setAlignment(Pos.CENTER);
        formLayout.setPadding(new Insets(20));

        Label title = new Label("Add Menu Item");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label restaurantLabel = new Label("Select Restaurant:");
        restaurantLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");
        restaurantComboBox.setItems(FXCollections.observableArrayList(
                DepeFood.getRestoList().stream().map(Restaurant::getNama).collect(Collectors.toList())
        ));
        restaurantComboBox.setStyle("-fx-background-radius: 20;");

        Label itemNameLabel = new Label("Menu Item Name:");
        itemNameLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");
        TextField itemNameInput = new TextField();
        itemNameInput.setStyle("-fx-background-radius: 20;");

        Label priceLabel = new Label("Price:");
        priceLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");
        TextField priceInput = new TextField();
        priceInput.setStyle("-fx-background-radius: 20;");

        Button submitButton = new Button("Add Menu");
        submitButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 20;");
        submitButton.setOnAction(e -> {
            String restaurantName = restaurantComboBox.getValue();
            String itemName = itemNameInput.getText().trim();
            double price;
            try {
                price = Double.parseDouble(priceInput.getText().trim());
            } catch (NumberFormatException ex) {
                showAlert("Error", "Price must be a number.", itemName, Alert.AlertType.ERROR);
                return;
            }

            Restaurant restaurant = DepeFood.getRestaurantByName(restaurantName);
            handleTambahMenuRestoran(restaurant, itemName, price);
        });

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 20;");
        backButton.setOnAction(e -> stage.setScene(scene));

        itemNameInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                submitButton.fire();
            }
        });

        priceInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                submitButton.fire();
            }
        });

        restaurantComboBox.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                submitButton.fire();
            }
        });

        formLayout.getChildren().addAll(title, restaurantLabel, restaurantComboBox, itemNameLabel, itemNameInput, priceLabel, priceInput, submitButton, backButton);

        layout.getChildren().addAll(createBackgroundShapes(), formLayout);

        return new Scene(layout, 500, 700);
    }

    // Membuat form melihat restoran
    private Scene createViewRestaurantsForm() {
        StackPane layout = new StackPane();
        layout.setStyle("-fx-background-color: linear-gradient(to bottom right, #6a11cb, #2575fc);");

        VBox formLayout = new VBox(20);
        formLayout.setAlignment(Pos.CENTER);
        formLayout.setPadding(new Insets(20));

        Label title = new Label("Restaurant Name:");
        title.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

        TextField restaurantInput = new TextField();
        restaurantInput.setPromptText("Enter restaurant name to view menu");
        restaurantInput.setStyle("-fx-background-radius: 10; -fx-padding: 10; -fx-font-size: 14px;");

        ListView<String> menuListView = new ListView<>();
        menuListView.setStyle("-fx-background-radius: 10; -fx-padding: 10;");

        Button searchButton = new Button("Search");
        searchButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-background-radius: 10;");
        searchButton.setOnAction(e -> {
            String restaurantName = restaurantInput.getText().trim();
            Restaurant restaurant = DepeFood.getRestaurantByName(restaurantName);
            if (restaurant == null) {
                showAlert("Error", "Restaurant not found", "Please check the restaurant name and try again.", Alert.AlertType.ERROR);
                return;
            }
            menuListView.setItems(FXCollections.observableArrayList(
                restaurant.getMenu().stream()
                    .map(menu -> menu.getNamaMakanan() + " - Rp" + formatCurrency(menu.getHarga()))
                    .collect(Collectors.toList())
            ));
        });

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-background-radius: 10;");
        backButton.setOnAction(e -> stage.setScene(scene));

        formLayout.getChildren().addAll(title, restaurantInput, searchButton, new Label("Menu:"), menuListView, backButton);

        layout.getChildren().addAll(createBackgroundShapes(), formLayout);

        return new Scene(layout, 500, 700);
    }

    // Menangani penambahan restoran baru
    private void handleTambahRestoran(String nama) {
        String validName = DepeFood.getValidRestaurantName(nama);
        if (!validName.equals(nama)) {
            showAlert("Error", validName, validName, Alert.AlertType.ERROR);
            return;
        }

        DepeFood.handleTambahRestoran(nama);
        showAlert("Success", "Restaurant successfully added.", validName, Alert.AlertType.INFORMATION);
        restaurantComboBox.setItems(FXCollections.observableArrayList(
                DepeFood.getRestoList().stream().map(Restaurant::getNama).collect(Collectors.toList())
        ));
        stage.setScene(addMenuScene);
    }

    // Menangani penambahan menu restoran
    private void handleTambahMenuRestoran(Restaurant restaurant, String itemName, double price) {
        if (restaurant == null || itemName.isEmpty() || price <= 0) {
            showAlert("Error", "Invalid menu data.", itemName, Alert.AlertType.ERROR);
            return;
        }

        DepeFood.handleTambahMenuRestoran(restaurant, itemName, price);
        showAlert("Success", "Menu successfully added.", itemName, Alert.AlertType.INFORMATION);
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

        Rectangle rectangle = new Rectangle(500, 700, Color.TRANSPARENT);
        rectangle.setStyle("-fx-border-color: white; -fx-border-width: 5; -fx-background-radius: 20; -fx-border-radius: 20;");
        rectangle.setArcHeight(20);
        rectangle.setArcWidth(20);
        rectangle.setEffect(new javafx.scene.effect.DropShadow(10, Color.BLACK));

        shapesLayout.getChildren().addAll(circle1, circle2, rectangle);

        return shapesLayout;
    }

    private String formatCurrency(double amount) {
        DecimalFormat decimalFormat = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        decimalFormat.setDecimalFormatSymbols(symbols);
        return decimalFormat.format(amount);
    }
}

package assignments.assignment4.components.form;

import assignments.assignment3.DepeFood;
import assignments.assignment3.User;
import assignments.assignment4.MainApp;
import assignments.assignment4.page.AdminMenu;
import assignments.assignment4.page.CustomerMenu;
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

public class LoginForm {
    private Stage stage;
    private MainApp mainApp;
    private TextField nameInput;
    private TextField phoneInput;

    public LoginForm(Stage stage, MainApp mainApp) {
        this.stage = stage;
        this.mainApp = mainApp;
    }

    // Membuat form login
    private Scene createLoginForm() {
        StackPane layout = new StackPane();
        layout.setStyle("-fx-background-color: linear-gradient(to bottom right, #6a11cb, #2575fc);");
    
        VBox formLayout = new VBox(20);
        formLayout.setAlignment(Pos.CENTER);
    
        Label title = new Label("Welcome back!");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white; -fx-font-family: 'Arial';");
    
        nameInput = new TextField();
        nameInput.setPromptText("Username");
        nameInput.setStyle("-fx-background-color: #ffffff; -fx-font-size: 14px; -fx-padding: 15; -fx-font-family: 'Arial';");
    
        phoneInput = new TextField();
        phoneInput.setPromptText("Telephone number");
        phoneInput.setStyle("-fx-background-color: #ffffff; -fx-font-size: 14px; -fx-padding: 15; -fx-font-family: 'Arial';");
    
        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10 20; -fx-font-family: 'Arial'; -fx-font-weight: bold;");
        loginButton.setOnAction(e -> handleLogin());
    
        nameInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                loginButton.fire();
            }
        });
    
        phoneInput.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                loginButton.fire();
            }
        });
    
        formLayout.getChildren().addAll(title, nameInput, phoneInput, loginButton);
    
        layout.getChildren().addAll(createBackgroundShapes(), formLayout);
    
        return new Scene(layout, 400, 600);
    }    

    // Menangani login pengguna
    private void handleLogin() {
        String name = nameInput.getText();
        String phone = phoneInput.getText();
        User user = DepeFood.handleLogin(name, phone);

        if (user == null) {
            showAlert("Login gagal!", "Kredensial tidak valid", "Mohon cek kembali nama dan nomor telepon Anda. Silakan coba kembali.");
        } else {
            mainApp.setUser(user);
            CustomerMenu customerMenu = new CustomerMenu(stage, mainApp);
            customerMenu.setUser(user);
            mainApp.setScene(user.getRole().equalsIgnoreCase("Admin") ? new AdminMenu(stage, mainApp, user).getScene() : customerMenu.getScene());
        }
    }

    // Menampilkan alert
    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
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

        Rectangle rectangle = new Rectangle(400, 600, Color.TRANSPARENT);
        rectangle.setStyle("-fx-border-color: white; -fx-border-width: 5; -fx-border-radius: 20;");

        shapesLayout.getChildren().addAll(circle1, circle2, rectangle);

        return shapesLayout;
    }

    // Mengembalikan scene dari form login
    public Scene getScene() {
        return this.createLoginForm();
    }
}
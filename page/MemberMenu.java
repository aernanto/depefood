
package assignments.assignment4.page;

import assignments.assignment3.DepeFood;
import assignments.assignment3.Restaurant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.List;
import java.util.stream.Collectors;

public abstract class MemberMenu {
    private Scene scene;
    protected ObservableList<String> restaurantNames;
    protected ComboBox<String> restaurantComboBox;
    protected ListView<String> restaurantListView;
    protected Button orderButton;
    protected Button billButton;
    protected Button logoutButton;

    public MemberMenu() {
        this.restaurantNames = FXCollections.observableArrayList();
        this.restaurantComboBox = new ComboBox<>(restaurantNames);
        this.restaurantListView = new ListView<>(restaurantNames);
        this.orderButton = new Button("Generate Order");
        this.billButton = new Button("Generate Bill");
        this.logoutButton = new Button("Logout");
        refresh();
        this.scene = createBaseMenu();
    }

    // Membuat menu dasar
    abstract protected Scene createBaseMenu();

    // Menampilkan alert
    protected void showAlert(String title, String header, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Mengembalikan scene yang sedang diakses
    public Scene getScene() {
        return this.scene;
    }

    // Merefresh data restoran
    protected void refresh() {
        List<Restaurant> restaurants = DepeFood.getRestoList();
        restaurantNames.setAll(restaurants.stream().map(Restaurant::getNama).collect(Collectors.toList()));
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
}
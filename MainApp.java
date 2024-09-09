
package assignments.assignment4;

import assignments.assignment3.DepeFood;
import assignments.assignment3.User;
import assignments.assignment4.components.form.LoginForm;
import assignments.assignment4.page.CustomerMenu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class MainApp extends Application {

    private Stage window;
    private Map<String, Scene> allScenes = new HashMap<>();
    
    @Override
    // Memulai aplikasi
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("DepeFood Ordering System");
        DepeFood.initUser();

        CustomerMenu customerMenu = new CustomerMenu(primaryStage, this);
        Scene customerScene = customerMenu.getScene();
        allScenes.put("CustomerMenu", customerScene);

        LoginForm loginForm = new LoginForm(primaryStage, this);
        Scene loginScene = loginForm.getScene();
        setScene(loginScene);

        window.show();
    }

    // Mengatur user baru
    public void setUser(User newUser) {
    }

    // Mengatur scene saat ini
    public void setScene(Scene scene) {
        window.setScene(scene);
    }

    // Mengambil scene berdasarkan nama
    public Scene getScene(String sceneName) {
        return allScenes.get(sceneName);
    }

    // Menambahkan scene baru
    public void addScene(String sceneName, Scene scene) {
        allScenes.put(sceneName, scene);
    }

    // Logout dari aplikasi
    public void logout() {
        setUser(null); // Clear user
        LoginForm loginForm = new LoginForm(window, this);
        Scene loginScene = loginForm.getScene();
        setScene(loginScene); // Switch ke login scene
    }    

    // Main method
    public static void main(String[] args) {
        launch(args);
    }
}
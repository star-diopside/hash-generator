package jp.gr.java_conf.star_diopside.hash_generator.application;

import java.io.Closeable;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp extends Application {

    private ApplicationContext applicationContext;

    @Override
    public void init() throws Exception {
        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/file-hash.fxml"));
        loader.setControllerFactory(clazz -> applicationContext.getBean(clazz));

        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        if (applicationContext instanceof Closeable) {
            ((Closeable) applicationContext).close();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

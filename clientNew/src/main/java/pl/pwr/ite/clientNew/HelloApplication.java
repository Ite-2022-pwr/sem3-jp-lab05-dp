package pl.pwr.ite.clientNew;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.pwr.ite.clientNew.service.FXUtils;
import pl.pwr.ite.clientNew.service.job.JobExecutor;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1700, 900);
        FXUtils.setMainController(fxmlLoader.getController());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        //Threads in thread pool are not automatically interrupted, so we need to do this ourselves
        JobExecutor.interruptAll();
    }

    public static void main(String[] args) {
        launch();
    }
}
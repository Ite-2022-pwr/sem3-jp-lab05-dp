package pl.pwr.ite.clientNew.view.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import pl.pwr.ite.clientNew.model.Painter;
import pl.pwr.ite.clientNew.service.Repository;
import pl.pwr.ite.clientNew.service.job.JobExecutor;
import pl.pwr.ite.clientNew.service.job.PaintProviderJob;
import pl.pwr.ite.clientNew.service.job.PainterJob;
import pl.pwr.ite.clientNew.view.model.RailLabel;
import pl.pwr.ite.clientNew.view.model.RailProgressBar;
import pl.pwr.ite.clientNew.view.model.Updatable;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class HelloController implements Initializable {
    private final Repository repository = Repository.getInstance();

    @FXML protected TableView<Painter> paintersTableView;
    @FXML protected Button startButton;
    @FXML protected GridPane fenceGridPane;
    @FXML protected Label painBucketsLabel;

    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        paintersTableView.getColumns().clear();
        createPaintersTable();
        init();
    }

    @FXML
    protected void startButtonClick(ActionEvent e) {
        startButton.setDisable(true);
        JobExecutor.execute(new PaintProviderJob());
        var painters = repository.getPainters();
        for(var painter : painters) {
            var job = new PainterJob(painter);
            JobExecutor.execute(job);
        }
    }

    private void init() {
        var segments = repository.getSegments();
        synchronized (segments) {
            int i = 0;
            for(var segment : segments) {
                for(var rail : segment.getRails()) {
                    var bar = new RailProgressBar(rail);
                    var label = new RailLabel(rail);
                    fenceGridPane.add(bar, i, 0);
                    fenceGridPane.add(label, i, 1);
                    GridPane.setHalignment(label, HPos.CENTER);
                    i++;
                }
                var pane = new Pane();
                pane.setPrefWidth(10);
                pane.setStyle("-fx-background-color: #000000");
                fenceGridPane.add(pane, i, 0, 1, 2);
                i++;
            }
        }
    }

    public void fireDataChange() {
        Platform.runLater(() -> {
            for(var child : fenceGridPane.getChildren()) {
                if(Updatable.class.isAssignableFrom(child.getClass())) {
                    ((Updatable)child).update();
                }
            }
            var painters = repository.getPainters();
            synchronized (painters) {
                paintersTableView.getItems().clear();
                for(var painter : painters) {
                    paintersTableView.getItems().add(painter);
                }
            }
            var painBuckets = repository.getPaintBuckets();
            synchronized (painBuckets) {
                painBucketsLabel.setText("Paint buckets in magazine: " + painBuckets.size());
            }
        });
    }

    public void finishPainting() {
        fenceGridPane.setStyle("-fx-background-color: lightgreen");
    }


    private void createPaintersTable() {
        var idColumn = new TableColumn<Painter, UUID>("Painter ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        var letterColumn = new TableColumn<Painter, String>("Letter");
        letterColumn.setCellValueFactory(new PropertyValueFactory<>("letter"));
        var speedColumn = new TableColumn<Painter, String>("Speed");
        speedColumn.setCellValueFactory(new PropertyValueFactory<>("speed"));
        var bucketColumn = new TableColumn<Painter, String>("Paints left");
        bucketColumn.setCellValueFactory(new PropertyValueFactory<>("leftBucket"));
        paintersTableView.getColumns().add(idColumn);
        paintersTableView.getColumns().add(letterColumn);
        paintersTableView.getColumns().add(speedColumn);
        paintersTableView.getColumns().add(bucketColumn);

        var painters = repository.getPainters();
        for(var painter : painters) {
            paintersTableView.getItems().add(painter);
        }
    }
}
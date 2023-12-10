package pl.pwr.ite.clientNew.view.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pl.pwr.ite.clientNew.model.Painter;
import pl.pwr.ite.clientNew.service.Repository;
import pl.pwr.ite.clientNew.service.job.JobExecutor;
import pl.pwr.ite.clientNew.service.job.PaintProviderJob;
import pl.pwr.ite.clientNew.service.job.PainterJob;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class HelloController implements Initializable {
    private final Repository repository = Repository.getInstance();

    @FXML protected TableView<Painter> paintersTableView;
    @FXML protected Button startButton;
    @FXML protected Label fenceLabel;

    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        paintersTableView.getColumns().clear();
        createPaintersTable();
    }

    @FXML
    protected void startButtonClick(ActionEvent e) {
        JobExecutor.execute(new PaintProviderJob());
        var painters = repository.getPainters();
        for(var painter : painters) {
            var job = new PainterJob(painter);
            JobExecutor.execute(job);
        }
    }

    public void setFenceLabel(String text) {
        Platform.runLater(() -> {
            fenceLabel.setText(text);
        });
    }

    public void fireDataChange() {
        Platform.runLater(() -> {

        });
    }

    private void createPaintersTable() {
        var idColumn = new TableColumn<Painter, UUID>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        var letterColumn = new TableColumn<Painter, UUID>("Letter");
        letterColumn.setCellValueFactory(new PropertyValueFactory<>("letter"));
        var speedColumn = new TableColumn<Painter, UUID>("Speed");
        speedColumn.setCellValueFactory(new PropertyValueFactory<>("speed"));
        paintersTableView.getColumns().add(idColumn);
        paintersTableView.getColumns().add(letterColumn);
        paintersTableView.getColumns().add(speedColumn);

        var painters = repository.getPainters();
        for(var painter : painters) {
            paintersTableView.getItems().add(painter);
        }
    }
}
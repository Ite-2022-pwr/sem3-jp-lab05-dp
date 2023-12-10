package pl.pwr.ite.clientNew.view.model;

import javafx.scene.layout.GridPane;
import pl.pwr.ite.clientNew.model.Segment;
import pl.pwr.ite.clientNew.service.Repository;

import java.util.List;

public class FenceGridPane extends GridPane {
    private final Repository repository = Repository.getInstance();
    private List<Segment> segments;

    public FenceGridPane() {
        this.segments = repository.getSegments();
        init();
    }

    private void init() {

    }
}

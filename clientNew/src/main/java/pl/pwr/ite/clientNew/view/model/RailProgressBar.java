package pl.pwr.ite.clientNew.view.model;

import javafx.scene.control.ProgressBar;
import pl.pwr.ite.clientNew.model.Rail;

public class RailProgressBar extends ProgressBar implements Updatable {

    private final Rail rail;

    public RailProgressBar(Rail rail) {
        this.rail = rail;
        super.setProgress(0.0f);
        super.setRotate(270);
    }

    @Override
    public void update() {
        synchronized (rail) {
            setProgress(rail.getProgress());
            if(rail.getProgress() >= 1.0) {
                super.setStyle("-fx-accent: green");
            }
        }
    }
}

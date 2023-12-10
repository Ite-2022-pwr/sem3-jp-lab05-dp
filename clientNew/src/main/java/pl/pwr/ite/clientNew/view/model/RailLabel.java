package pl.pwr.ite.clientNew.view.model;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import pl.pwr.ite.clientNew.model.Rail;

public class RailLabel extends Label implements Updatable {

    private final Rail rail;

    public RailLabel(Rail rail) {
        this.rail = rail;
        super.setText("-");
        super.setFont(new Font("Arial Bold", 20));
        super.setAlignment(Pos.CENTER);
    }

    @Override
    public void update() {
        synchronized (rail) {
            if(rail.getPaintedBy() != null) {
                setText(rail.getPaintedBy().toString());
            }
        }
    }
}

package pl.pwr.ite.clientNew.model;

import javafx.scene.paint.Color;
import lombok.Data;

import java.util.UUID;

@Data
public class Painter {

    private UUID id;

    private Integer speed;

    private Character letter;

    private Color color;

    private PaintBucket currentBucket;

    public String getLeftBucket() {
        if(currentBucket == null) {
            return "0";
        }
        return currentBucket.getUsages().toString();
    }
}

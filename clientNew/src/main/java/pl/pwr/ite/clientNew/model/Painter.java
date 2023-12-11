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
}

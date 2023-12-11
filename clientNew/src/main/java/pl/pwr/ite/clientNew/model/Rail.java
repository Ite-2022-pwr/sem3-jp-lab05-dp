package pl.pwr.ite.clientNew.model;

import lombok.Data;
import pl.pwr.ite.clientNew.model.enums.RailStatus;

import java.util.UUID;

@Data
public class Rail {

    private UUID id;

    private volatile RailStatus status;

    private Double progress;

    private Painter paintedBy;
}

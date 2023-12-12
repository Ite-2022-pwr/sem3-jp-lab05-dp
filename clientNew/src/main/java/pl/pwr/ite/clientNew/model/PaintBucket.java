package pl.pwr.ite.clientNew.model;

import lombok.Data;

import java.util.UUID;

@Data
public class PaintBucket {

    private UUID id;

    private volatile Integer usages;
}

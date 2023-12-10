package pl.pwr.ite.clientNew.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class Segment {

    private UUID id;

    private List<Rail> rails = new ArrayList<>();
}

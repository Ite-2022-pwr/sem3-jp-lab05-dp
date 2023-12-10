package pl.pwr.ite.clientNew.service;

import pl.pwr.ite.clientNew.model.Segment;
import pl.pwr.ite.clientNew.model.enums.RailStatus;

public class SegmentService {
    private static SegmentService INSTANCE = null;

    private final Repository repository = Repository.getInstance();

    public synchronized Segment findUnpaintedSegment() {
        var segments = repository.getSegments();
        return segments.stream().filter(s ->
                s.getRails().stream()
                        .allMatch(r -> RailStatus.Unpainted.equals(r.getStatus()))
        ).findFirst().orElse(null);
    }

    public static SegmentService getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new SegmentService();
        }
        return INSTANCE;
    }
}

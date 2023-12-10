package pl.pwr.ite.clientNew.service;

import lombok.Data;
import lombok.Getter;
import pl.pwr.ite.clientNew.model.PaintBucket;
import pl.pwr.ite.clientNew.model.Painter;
import pl.pwr.ite.clientNew.model.Rail;
import pl.pwr.ite.clientNew.model.Segment;
import pl.pwr.ite.clientNew.model.enums.RailStatus;

import java.util.*;
import java.util.stream.Collectors;

@Getter
public class Repository {
    private static Repository INSTANCE = null;
    //Synchronized collections are used to make sure that collections will
    //be correctly synchronized across multiple threads (nothing will break when element will be added or removed)
    private final List<Segment> segments = Collections.synchronizedList(new ArrayList<>());
    private final List<PaintBucket> paintBuckets = Collections.synchronizedList(new ArrayList<>());
    private final List<Painter> painters = new ArrayList<>();
    private boolean shouldProvideBucket = true;

    public Repository() {
        segments.addAll(DataFactory.generateSegments(3));
        painters.addAll(DataFactory.generatePainters(2));
    }

    public synchronized boolean isShouldProvideBucket() {
        return shouldProvideBucket;
    }

    public SegmentReference findUnpaintedSegment() {
        synchronized (segments) {
            var segment = segments.stream().filter(s -> s.getRails().stream().allMatch(r -> RailStatus.Unpainted.equals(r.getStatus()))).findFirst().orElse(null);
            var segmentReference = new SegmentReference();
            if(segment != null) {
                segmentReference.setSegment(segment);
                segmentReference.setStartingRailIndex(0);
                return segmentReference;
            }
            for(var seg : segments) {
                var all = seg.getRails().size();
                var unpainted = seg.getRails().stream().filter(s -> RailStatus.Unpainted.equals(s.getStatus())).count();
                if(unpainted >= all / 2) {
                    segmentReference.setSegment(seg);
                    segmentReference.setStartingRailIndex(all % 2 == 0 ? all / 2 : (int)Math.ceil((double) all / 2));
                    return segmentReference;
                }
            }
            return null;
        }
    }

    public void addBuckets(Collection<PaintBucket> buckets) {
        synchronized (paintBuckets) {
            paintBuckets.addAll(buckets);
            paintBuckets.sort((b1, b2) -> b2.getUsages() - b1.getUsages());
        }
    }

    public PaintBucket getBucket() {
        synchronized (paintBuckets) {
            if(paintBuckets.isEmpty()) {
                return null;
            }
            var bucket = paintBuckets.get(0);
            paintBuckets.remove(bucket);
            return bucket;
        }
    }

    public static Repository getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new Repository();
        }
        return INSTANCE;
    }

    @Data
    public static class SegmentReference {
        private Segment segment;
        private Integer startingRailIndex;
    }
}

package pl.pwr.ite.clientNew.service.job;

import lombok.RequiredArgsConstructor;
import pl.pwr.ite.clientNew.model.PaintBucket;
import pl.pwr.ite.clientNew.model.Painter;
import pl.pwr.ite.clientNew.model.Rail;
import pl.pwr.ite.clientNew.model.Segment;
import pl.pwr.ite.clientNew.model.enums.RailStatus;
import pl.pwr.ite.clientNew.service.FXUtils;
import pl.pwr.ite.clientNew.service.Repository;
import pl.pwr.ite.clientNew.service.SegmentService;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class PainterJob implements Runnable {

    private final Painter painter;
    private final Repository repository = Repository.getInstance();
    private PaintBucket paintBucket;

    @Override
    public void run() throws JobException {
        try {
            Repository.SegmentReference segmentReference;
            while((segmentReference = repository.findUnpaintedSegment()) != null) {
                Rail rail;
                while ((rail = getRail(segmentReference.getSegment(), segmentReference.getStartingRailIndex())) != null) {
                    if(paintBucket == null || paintBucket.getUsages() == 0) {
                        System.out.println(painter.getLetter() + " out of paint. Taking new bucket");
                        while((paintBucket = repository.getBucket()) == null) {
                            System.out.println("No paint bucket available," + painter.getLetter() + " is waiting...");
                            TimeUnit.SECONDS.sleep(1);
                        }
                        System.out.println(painter.getLetter() + " took bucket: " + paintBucket.getId());
                    }
                    while (rail.getProgress() < 1.0) {
                        rail.setStatus(RailStatus.InProgress);
                        rail.setProgress(rail.getProgress() + 0.20);
                        FXUtils.getMainController().fireDataChange();
                        Thread.sleep(painter.getSpeed());
                    }
                    paintBucket.setUsages(paintBucket.getUsages() - 1);
                    rail.setPaintedBy(painter);
                    rail.setStatus(RailStatus.Finished);
                }
            }
        } catch (InterruptedException ex) {
            System.out.println("Painter " + painter.getId() + " job interrupted.");
        }
    }

    private Rail getRail(Segment segment, Integer startIndex) {
        var rails = segment.getRails();
        for(int i = startIndex; i < rails.size(); i ++) {
            var rail = rails.get(i);
            if(RailStatus.Unpainted.equals(rail.getStatus()) || RailStatus.Reserved.equals(rail.getStatus())) {
                return rail;
            }
        }
        return null;
    }
}

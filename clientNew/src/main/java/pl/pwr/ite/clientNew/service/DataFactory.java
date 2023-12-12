package pl.pwr.ite.clientNew.service;

import javafx.scene.paint.Color;
import pl.pwr.ite.clientNew.model.Painter;
import pl.pwr.ite.clientNew.model.Rail;
import pl.pwr.ite.clientNew.model.Segment;
import pl.pwr.ite.clientNew.model.enums.RailStatus;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DataFactory {

    private static final Random random = new Random();
    private static final Integer PAINTER_BASE_SPEED = 1 * 100;

    public static List<Painter> generatePainters(int limit) {
        var painters = new ArrayList<Painter>();
        for(int i = 0; i < limit; i++) {
            var painter = new Painter();
            painter.setSpeed(PAINTER_BASE_SPEED + getRandom(1, 10) * 100);
            painter.setId(UUID.randomUUID());
            painter.setColor(Color.color(random.nextFloat(), random.nextFloat(), random.nextFloat()));
            painter.setLetter((char) ((char) 65 + i));
            painters.add(painter);
        }
        return painters;
    }

    public static List<Segment> generateSegments(int limit) {
        var segments = new ArrayList<Segment>();
        for(int i = 0; i < limit; i++) {
            var segment = new Segment();
            segment.setId(UUID.randomUUID());
            for(int j = 0; j < 20; j++) {
                segment.getRails().add(generateRail());
            }
            segments.add(segment);
        }
        return segments;
    }

    private static Rail generateRail() {
        var rail = new Rail();
        rail.setId(UUID.randomUUID());
        rail.setStatus(RailStatus.Unpainted);
        rail.setProgress(0.0);
        return rail;
    }

    public static int getRandom(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }
}

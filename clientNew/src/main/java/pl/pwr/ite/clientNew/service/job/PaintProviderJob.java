package pl.pwr.ite.clientNew.service.job;

import lombok.RequiredArgsConstructor;
import pl.pwr.ite.clientNew.model.PaintBucket;
import pl.pwr.ite.clientNew.service.Repository;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class PaintProviderJob implements Runnable {

    private static final Random random = new Random();
    private final Repository repository = Repository.getInstance();

    @Override
    public void run() {

        try {
            while (repository.isShouldProvideBucket()) {
                var buckets = new ArrayList<PaintBucket>();
                for(int i = 0; i < getRandom(2, 6); i++) {
                    var bucket = new PaintBucket();
                    bucket.setId(UUID.randomUUID());
                    bucket.setUsages(5);
                    buckets.add(bucket);
                }
                repository.addBuckets(buckets);
                System.out.println("Added " + buckets.size() + " buckets");
                TimeUnit.SECONDS.sleep(getRandom(15, 30));
            }
        } catch (InterruptedException ex) {
            System.out.println("Paint provider job interrupted.");
        }
    }

    private int getRandom(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }
}

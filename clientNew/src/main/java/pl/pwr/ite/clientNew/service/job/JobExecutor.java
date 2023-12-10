package pl.pwr.ite.clientNew.service.job;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class JobExecutor {

    //CachedThreadPool executor create new thread when needed and automatically terminates thread if it's not used
    //for 60 seconds to save memory
    private static final ThreadPoolExecutor poolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

    public static void execute(Runnable job) {
        poolExecutor.submit(job);
    }

    public static void interruptAll() {
        //Interrupts all active threads that are currently handled by executor
        poolExecutor.shutdownNow();
    }

}

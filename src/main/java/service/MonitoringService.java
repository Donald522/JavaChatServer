package service;

/**
 * Created by Anton Tolkachev.
 * Since 23.02.17
 */

public interface MonitoringService {

    default void start() {
        new Thread(this::observe).start();
    }

    void observe();

}

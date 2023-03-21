package searchengine.services.indexing.core.check.lifeThread;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LifeThread {

    @Getter
    private static final List<Thread> threadList = Collections.synchronizedList(new ArrayList<>());

    public static void addThread(Thread thread) {
        threadList.add(thread);
    }

    public static void removeThread(Thread thread) {
        threadList.remove(thread);
    }

    public static void clearAllThread() {
        threadList.clear();
    }

    public static boolean isAliveThread() {
        return threadList.size() > 0;
    }

}

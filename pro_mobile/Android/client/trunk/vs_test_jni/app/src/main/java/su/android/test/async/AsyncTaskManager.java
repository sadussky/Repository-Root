package su.android.test.async;

import android.content.Context;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <BR> Copyright (c) 2012-2016 by SaduStephen. ALL RIGHTS RESERVED.
 * <BR> Consult your license regarding permissions and restrictions.
 * <BR> >>>> Add flow here:
 * <BR> SINCE 2016年5月18日 上午10:50:12
 */
public class AsyncTaskManager {


    private final static int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private final static int DEF_CORE_POOL_SIZE = CPU_COUNT * 50;
    private final static int DEF_MAXIMUM_POOL_SIZE = CPU_COUNT * 100 + 1;
    private final static int DEF_KEEP_ALIVE = 1;
    private final static TimeUnit DEF_TIMEUNIT = TimeUnit.SECONDS;
    private static final BlockingQueue<Runnable> sPoolWorkQueue = new LinkedBlockingQueue<Runnable>(128);
    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "AsyncTaskManager #" + mCount.getAndIncrement());
        }
    };
    private static AsyncTaskManager sIntance;
    private Context mContext;
    private ThreadPoolExecutor mWorkThreadPool = null; // 线程池
    private ScheduledExecutorService mScheduledExecutorService = null; // 调度线程池
    private Thread mScheduledThread = null; // 调度Runnable
    private Queue<Runnable> mWaitTasksQueue = null; // 等待任务队列
    private RejectedExecutionHandler mRejectedExecutionHandler = null; // 任务被拒绝执行的处理器
    private Object mLockWaitTasksQueue = new Object();


    private AsyncTaskManager() {
        //        mWaitTasksQueue = new ConcurrentLinkedQueue<Runnable>();
        mWaitTasksQueue = new ConcurrentLinkedQueue<Runnable>();
        mScheduledThread = new ScheduledThread();
        mScheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        mScheduledExecutorService.scheduleAtFixedRate(mScheduledThread, 0, 1000, TimeUnit.MILLISECONDS);
        initRejectedExecutionHandler();
        mWorkThreadPool = new ThreadPoolExecutor(DEF_CORE_POOL_SIZE, //<BR>
                DEF_MAXIMUM_POOL_SIZE,//<BR>
                DEF_KEEP_ALIVE,//<BR>
                DEF_TIMEUNIT,//<BR>
                sPoolWorkQueue,//<BR>
                sThreadFactory,//<BR>
                mRejectedExecutionHandler);
    }

    public static AsyncTaskManager getInstance() {
        if (null == sIntance) {
            synchronized (AsyncTaskManager.class) {
                if (null == sIntance) {
                    sIntance = new AsyncTaskManager();
                }
            }
        }
        return sIntance;
    }

    private void initRejectedExecutionHandler() {
        mRejectedExecutionHandler = new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                // 把被拒绝的任务重新放入到等待队列中
                synchronized (mLockWaitTasksQueue) {
                    mWaitTasksQueue.offer(r);
                }
            }
        };
    }

    public boolean hasMoreWaitTask() {
        boolean result = false;
        if (mWaitTasksQueue != null && !mWaitTasksQueue.isEmpty()) {
            result = true;
        }
        return result;
    }

    /*public void execute(Runnable task) {
        if (mWorkThreadPool != null && task != null) {
            mWorkThreadPool.execute(task);
        }
    }

    public void cancel(Runnable task) {
        if (task != null) {
            synchronized (mLockWaitTasksQueue) {
                if (mWaitTasksQueue != null && mWaitTasksQueue.contains(task)) {
                    mWaitTasksQueue.remove(task);
                }
            }
            if (mWorkThreadPool != null) {
                mWorkThreadPool.remove(task);
            }
        }
    }*/

    public boolean isShutdown() {
        boolean result = true;
        if (null != mWorkThreadPool) {
            result = mWorkThreadPool.isShutdown();
        }
        return result;
    }

    public void cleanUp() {
        if (mWorkThreadPool != null) {
            if (!mWorkThreadPool.isShutdown()) {
                try {
                    mWorkThreadPool.shutdownNow();
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
            mWorkThreadPool = null;
        }
        mRejectedExecutionHandler = null;
        if (mScheduledExecutorService != null) {
            if (!mScheduledExecutorService.isShutdown()) {
                try {
                    mScheduledExecutorService.shutdownNow();
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
            mScheduledExecutorService = null;
        }
        mScheduledThread = null;
        synchronized (mLockWaitTasksQueue) {
            if (mWaitTasksQueue != null) {
                mWaitTasksQueue.clear();
                mWaitTasksQueue = null;
            }
        }
    }


    /**
     * Execute Download Task just
     *
     * @param task
     */
    public void execute(DownloadTask task) {
        task.executeOnExecutor(mWorkThreadPool, task.getParams());
    }

    private class ScheduledThread extends Thread {
        @Override
        public void run() {
            synchronized (mLockWaitTasksQueue) {
                if (hasMoreWaitTask()) {
                    Runnable runnable = mWaitTasksQueue.poll();
                    if (runnable != null) {
                        mWorkThreadPool.execute(runnable);
                    }
                }
            }
        }
    }
}

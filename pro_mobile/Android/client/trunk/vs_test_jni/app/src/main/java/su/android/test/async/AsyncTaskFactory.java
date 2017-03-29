package su.android.test.async;

public class AsyncTaskFactory {


    public static AsyncTaskV2 execute(DownloadInfo downloadInfo, DownloadHook hook) {
        DownloadTask task = new DownloadTask(downloadInfo, hook);
        AsyncTaskManager.getInstance().execute(task);
        return task;
    }


}

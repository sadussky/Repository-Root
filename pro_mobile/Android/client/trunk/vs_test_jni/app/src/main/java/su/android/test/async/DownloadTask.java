package su.android.test.async;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

/**
 * <BR> Copyright (c) 2012-2016 by SaduStephen. ALL RIGHTS RESERVED.
 * <BR> Consult your license regarding permissions and restrictions.
 * <BR> >>>> Add flow here:
 * <BR> SINCE 2016年5月18日 上午11:23:30
 */
public class DownloadTask extends AsyncTaskV2<DownloadInfo, Integer, Void> {

    private DownloadInfo params;
    private DownloadHook hook;

    public DownloadTask(DownloadInfo params, DownloadHook hook) {
        this.params = params;
        this.hook = hook;
    }

    public DownloadInfo getParams() {
        return params;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (hook != null)
            hook.onPreExecute(getParams());
    }

    @Override
    protected Void doInBackground(DownloadInfo... params) {

        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        if (hook != null)
            hook.onProgressUpdate(getParams(), values);
    }

    @Override
    protected void onPostExecute(Void result) {
        if (hook != null)
            hook.onPostExecute(getParams(), result);

    }

    @Override
    protected void onCancelled() {
        if (hook != null)
            hook.onCancelled(getParams());
    }

    @Override
    protected void onInterruptedException(InterruptedException ex) {
        if (hook != null)
            hook.onInterruptedException(getParams(), ex);
    }

    @Override
    protected void onExecutionException(ExecutionException ex) {
        if (hook != null)
            hook.onExecutionException(getParams(), ex);
    }

    @Override
    protected void onCancellationException(CancellationException ex) {
        if (hook != null)
            hook.onCancellationException(getParams(), ex);
    }
}

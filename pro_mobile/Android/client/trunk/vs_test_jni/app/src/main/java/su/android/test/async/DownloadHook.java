package su.android.test.async;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

/**
 * <BR> Copyright (c) 2012-2016 by SaduStephen. ALL RIGHTS RESERVED.
 * <BR> Consult your license regarding permissions and restrictions.
 * <BR> >>>> Add flow here:
 * <BR> SINCE 2016年5月18日 上午10:50:12
 */
public interface DownloadHook<Params, Progress, Result> {

    /**
     * Run on UI Thread .
     *
     * @param ex
     */
    public void onInterruptedException(Params params, InterruptedException ex);

    public void onExecutionException(Params params, ExecutionException ex);

    public void onCancellationException(Params params, CancellationException ex);

    public void onPreExecute(Params params);

    public void onPostExecute(Params params, Result result);

    public void onProgressUpdate(Params params, Progress... progress);

    public void onCancelled(Params params);

}

package cientopolis.cientopolis;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by nicolas.valentini on 4/7/17.
 */

public class VolleyManager{
    private static VolleyManager volleyManager;
    private RequestQueue mRequestQueue;
    private static Context mContext;

    private VolleyManager(Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public static synchronized VolleyManager getInstance(Context context) {
        if (volleyManager == null) {
            volleyManager = new VolleyManager(context);
        }
        return volleyManager;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(tag);
        getRequestQueue().add(req);
    }
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
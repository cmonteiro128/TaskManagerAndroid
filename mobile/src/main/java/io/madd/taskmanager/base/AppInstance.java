package io.madd.taskmanager.base;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class AppInstance extends Application {
    /**
     * Simple Tag for Debug purposes.
     */
    private static final String TAG = AppInstance.class.getSimpleName();

    /**
     * This variable is an instance of this class. this makes this
     * makes it easier to call the variables and only initiate them
     * once because this class extend Application, which will stay in
     * memory as long as the app is alive. so all other variable
     * in this class are initiated once and only once. Use
     * {@link AppInstance#getInstance()} to retrieve this variable
     */
    private static AppInstance mInstance;

    /**
     * This variable will take network request using
     * {@link com.android.volley.Request} and send a response back
     * to the requester. this will only be initiated once and will
     * be used any class that need it by using
     * {@link AppInstance#getInstance()#getRequestQueue()}
     */
    private RequestQueue mRequestQueue;

    /**
     * This function will return {@link AppInstance#mInstance} which
     * is and instance of {@link AppInstance}. It has a class level
     * lock, (synchronized) so that there are no concurrent calls on
     * the method (making sure each call is one by one),
     * making this threadsafe.
     *
     * @return {@link AppInstance#mInstance}
     */
    public static synchronized AppInstance getInstance() {
        // return an instance of this class.
        return mInstance;
    }

    /**
     * This function override {@link Application#onCreate()}. this
     * is used to initiate the {@link AppInstance#mInstance} variable
     * on the start of when this class is called.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        // set mInstance to this class
        mInstance = this;
    }

    /**
     * This function initiates {@link AppInstance#mRequestQueue} if null
     * and returns it. use {@link AppInstance#getInstance()#getRequestQueue()}
     * to get {@link AppInstance#mRequestQueue} from the
     * {@link AppInstance#mInstance} variable if previously called anywhere
     * in the application, it will reuse the {@link AppInstance#mRequestQueue}
     * instead of making a new one
     *
     * @return {@link AppInstance#mRequestQueue}
     */
    public RequestQueue getRequestQueue() {
        // if the RequestQueue variable is null,
        // initiate it.
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext(), 25);
        }

        // return the RequestQueue variable.
        return this.mRequestQueue;
    }

    /**
     * This function will add the network request to the request queue
     * of {@link AppInstance#mRequestQueue}.
     *
     * @param req The type of request being made, (json, http, etc..)
     * @param <T> This will be able to take any type of request. So
     *            instead of declaring the function type, this will generate
     *            the response based on the request type passed in
     */
    public <T> void addToRequestQueue(Request<T> req) {
        // Setting the tag to the tag of this class.
        req.setTag(TAG);

        // adding the request to the request queue.
        getRequestQueue().add(req);
    }

    /**
     * This function will add the network request to the request queue
     * of {@link AppInstance#mRequestQueue}.
     * s
     *
     * @param req The type of request being made, (json, http, etc..).
     * @param tag name of the request.
     * @param <T> This will be able to take any type of request. So
     *            instead of declaring the function type, this will generate
     *            the response based on the request type passed in.
     */
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // Setting the tag of the request to the tag param.
        // Making sure the tag that was passed in isn't empty
        // if it is, set the tag of the request to the tag
        // of this class.
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);

        // adding the request to the request queue.
        getRequestQueue().add(req);
    }

    /**
     * This function will cancel all requests that are associated with
     * the tag that is passed in
     *
     * @param tag name of the request.
     * @param <T> This will be able to take any type of request. So
     *            instead of declaring the function type, this will generate
     *            the response based on the request type passed in.
     */
    public <T> void cancelToRequestQueue(Object tag) {
        // Checking if mRequestQueue is null. if not, then
        // cancel the request that is associated to the
        // tag param.
        if (mRequestQueue != null) {
            // cancel all request associated with the tag.
            mRequestQueue.cancelAll(tag);
        }
    }
}

package com.adrianlesniak.beautifulthailand.utilities;

import android.util.Log;

import java.util.Stack;

import okhttp3.Request;

/**
 * Created by adrian on 21/02/2017.
 */

public class RequestObjectPool {

    private static final String LOG_TAG = "REQUEST_STACK";

    private static RequestObjectPool sPool = new RequestObjectPool();

    private Stack<Request> mRequestStack;

    private RequestObjectPool() {
        this.mRequestStack = new Stack<>();
    }

    public static RequestObjectPool getInstance() {
        return sPool;
    }

    public Request getRequestObject() {
        if(mRequestStack.isEmpty()) {
            Request newRequest = new Request.Builder().build();
            return mRequestStack.push(newRequest);
        }

        return mRequestStack.pop();
    }

    public void returnRequestObject(Request request) {
        mRequestStack.push(request);
        Log.d(LOG_TAG, String.valueOf(mRequestStack.size()));
    }

}

package com.example.demo.util;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class SimpleCallback implements Callback {

    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    private static Logger logger = LoggerFactory.getLogger(SimpleCallback.class);

    @Override
    public void onFailure(Call call, IOException e) {
        ErrorPrintUtil.printErrorMsg(logger, e);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        this.response = response;
    }
}

package com.appslab.oracle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.internal.framed.Header;

import java.io.IOException;
import java.util.List;

public final class HttpConnect {
    private static HttpConnect instance;
    private final OkHttpClient httpClient;
    private final Gson gson;
    private String payload = null;
    private final static MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");

    private HttpConnect() {
        httpClient = buildHttpClient();
        gson = buildGson().create();
    }

    public static HttpConnect getInstance() {
        if (instance == null) {
            instance = new HttpConnect();
        }
        return instance;
    }

    private static OkHttpClient buildHttpClient() {
        // TODO: set up httpClient
        return new OkHttpClient();
    }

    private static GsonBuilder buildGson() {
        // TODO: set up GsonBuilder
        return new GsonBuilder();
    }

    public HttpConnect post(final Object object) {
        payload = gson.toJson(object);
        return this;
    }

    public ServiceResponse request(final String endpoint, final Class responseType, final List<Header> requestHeaders) {
        Response response;
        int responseCode = 0;
        Headers responseHeaders = null;
        String rawResponse;
        Object objectResponse = null;

        Request.Builder requestBuilder = new Request.Builder()
                .url(endpoint);

        if (payload != null) {
            // Create a RequestBody with the JSON Obj
            RequestBody body = RequestBody.create(JSON_MEDIA_TYPE, payload);

            requestBuilder.post(body);
        }

        if (requestHeaders != null) {
            for (Header h : requestHeaders) {
                requestBuilder.addHeader(h.name.utf8(), h.value.utf8());
            }
        }

        Request request = requestBuilder.build();
        try {
            response = httpClient.newCall(request).execute();
            // We can obtain the response code from the last call we made.
            responseCode = response.code();

            // Obtain the Response Headers
            responseHeaders = response.headers();

            // String representation of the service response
            rawResponse = response.body().string();

            // Closing connection
            response.body().close();

            // return the response Obj
            objectResponse = gson.fromJson(rawResponse, responseType);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            payload = null;
        }
        return new ServiceResponse(objectResponse, responseHeaders, responseCode);
    }
}

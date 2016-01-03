package com.appslab.oracle;

import com.squareup.okhttp.Headers;

/**
 * Created by Osvaldo Villagrana on 12/23/15.
 */
public class ServiceResponse {
    private Object objectResponse;
    private Headers headers;
    private int responseCode;

    public ServiceResponse(Object objectResponse, Headers headers, int responseCode) {
        this.objectResponse = objectResponse;
        this.headers = headers;
        this.responseCode = responseCode;
    }

    public Object getObjectResponse() {
        return objectResponse;
    }

    public void setObjectResponse(Object objectResponse) {
        this.objectResponse = objectResponse;
    }

    public Headers getHeaders() {
        return headers;
    }

    public void setHeaders(Headers headers) {
        this.headers = headers;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }
}

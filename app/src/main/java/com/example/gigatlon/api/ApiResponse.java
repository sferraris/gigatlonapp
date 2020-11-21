package com.example.gigatlon.api;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.gigatlon.api.model.ErrorModel;
import retrofit2.Response;

public class ApiResponse<T> {

    private T data;
    private ErrorModel errorModel;

    public T getData() {
        return data;
    }

    public ErrorModel getError() {
        return errorModel;
    }

    public ApiResponse(Response<T> response) {
        parseResponse(response);
    }

    public ApiResponse(Throwable throwable) {
        this.errorModel = buildError(throwable.getMessage());
    }

    private void parseResponse(Response<T> response) {
        if (response.isSuccessful()) {
            this.data = response.body();
            return;
        }

        if (response.errorBody() == null) {
            this.errorModel = buildError("Missing error body");
            return;
        }

        String message = null;

        try {
            message = response.errorBody().string();
        } catch (IOException exception) {
            Log.e("API", exception.toString());
            this.errorModel = buildError(exception.getMessage());
            return;
        }

        if (message != null && message.trim().length() > 0) {
            Gson gson = new Gson();
            this.errorModel =  gson.fromJson(message, new TypeToken<ErrorModel>() {}.getType());
        }
    }

    private static ErrorModel buildError(String message) {
        ErrorModel errorModel = new ErrorModel(ErrorModel.LOCAL_UNEXPECTED_ERROR, "Unexpected error");
        if (message != null) {
            List<String> details = new ArrayList<>();
            details.add(message);
            errorModel.setDetails(details);
        }
        return errorModel;
    }
}
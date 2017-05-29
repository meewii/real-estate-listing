package com.immo.realestatelistingapp.http;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.immo.realestatelistingapp.core.App;
import com.immo.realestatelistingapp.http.models.Request;
import com.immo.realestatelistingapp.http.models.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpClient {

    @SuppressWarnings("FieldCanBeLocal")
    private final String API_ENDPOINT = "https://private-e618e0-mobiletask2.apiary-mock.com/realestates";

    public @Nullable Response execute(@NonNull Request request) {
        String tag = "HttpClient.execute() - ";

        Log.d(App.TAG, tag + "Request method: '" + request.getMethod() + "'");

        URL url;
        try {
            url = new URL(API_ENDPOINT);
        } catch (MalformedURLException e) {
            Log.e(App.TAG, tag + "Error while parsing URL: " + e.getMessage());
            return null;
        }

        HttpsURLConnection conn;
        try {
            conn = (HttpsURLConnection) url.openConnection();
        } catch (IOException e) {
            Log.e(App.TAG, tag + "Could not create connection: " + e.getMessage());
            return null;
        }

        conn.setRequestProperty("Content-Type", "application/json");

        // TODO: add switch for other HTTP methods
        try {
            conn.setRequestMethod(request.getMethod());
        } catch (ProtocolException e) {
            Log.e(App.TAG, tag + "Could not set request method: " + e.getMessage());
            return null;
        }

        int responseCode = 0;
        String responseMsg = null;
        try {
            responseCode = conn.getResponseCode();
            responseMsg = conn.getResponseMessage();
        } catch (IOException e) {
            Log.e(App.TAG, tag + "Could not set response code/message: " + e.getMessage());
        }

        Response response = new Response();
        response.setCode(responseCode);
        response.setMessage(responseMsg);

        Log.i(App.TAG, tag + "Response - " + responseCode + ": " + responseMsg);

        BufferedReader in;
        try {
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } catch (IOException e) {
            Log.e(App.TAG, tag + "Could not get input stream: " + e.getMessage());
            return response;
        }

        String inputLine;
        StringBuilder str = new StringBuilder();

        try {
            while ((inputLine = in.readLine()) != null) {
                str.append(inputLine);
            }
            in.close();
        } catch (IOException e) {
            Log.e(App.TAG, tag + "Could not read input line: " + e.getMessage());
        }

        response.setContent(str.toString());

        Log.i(App.TAG, "Response content - " + str.toString());

        return response;
    }

}



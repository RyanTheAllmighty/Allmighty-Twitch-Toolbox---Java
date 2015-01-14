package me.ryandowling.allmightytwitchtoolbox.data;

import me.ryandowling.allmightytwitchtoolbox.Logger;
import me.ryandowling.allmightytwitchtoolbox.AllmightyTwitchToolbox;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public abstract class APIRequest {
    private String BASE_URL;
    public HttpsURLConnection connection;
    private String path = "/";

    public APIRequest(String base, String path) {
        this.BASE_URL = base;

        if (path.length() == 0 || !path.substring(0, 1).equals("/")) {
            this.path = "/" + path;
        } else {
            this.path = path;
        }
    }

    public String get() throws IOException {
        this.connect("GET");

        InputStream in = this.connection.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = br.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();

        this.disconnect();

        return response.toString();
    }

    public String put(Object object) throws IOException {
        this.connect("PUT", object);

        InputStream in = this.connection.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = br.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();

        this.disconnect();

        return response.toString();
    }

    private void connect(String requestMethod) throws IOException {
        this.connect(requestMethod, null);
    }

    private void connect(String requestMethod, Object object) throws IOException {
        Logger.log("Connecting to " + BASE_URL + this.path);
        URL url = new URL(BASE_URL + this.path);
        this.connection = (HttpsURLConnection) url.openConnection();
        this.connection.setRequestMethod(requestMethod);
        this.connection.setUseCaches(false);
        this.connection.setDefaultUseCaches(false);
        setRequestProperties();

        if (object != null) {
            this.connection.setRequestProperty("Content-Length", "" + AllmightyTwitchToolbox.GSON.toJson(object).getBytes()
                    .length);
            connection.setDoOutput(true);
        }

        this.connection.connect();

        if (object != null) {
            DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
            writer.write(AllmightyTwitchToolbox.GSON.toJson(object).getBytes());
            writer.flush();
            writer.close();
        }
    }

    protected void setRequestProperties() {
        this.connection.setRequestProperty("Cache-Control", "no-store,max-age=0,no-cache");
        this.connection.setRequestProperty("Expires", "0");
        this.connection.setRequestProperty("Pragma", "no-cache");
        this.connection.setRequestProperty("Content-Type", "application/json");
    }

    private void disconnect() {
        if (this.connection != null) {
            this.connection.disconnect();
        }
    }
}

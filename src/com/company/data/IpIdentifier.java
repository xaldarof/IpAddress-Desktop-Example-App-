package com.company.data;

import com.company.utils.DataSources;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class IpIdentifier implements Connectable {

    private boolean isRequesting = false;
    private ConnectionCallBack connectionCallBack;

    public void registerCallBack(ConnectionCallBack connectionCallBack) {
        this.connectionCallBack = connectionCallBack;
    }

    public boolean isRequesting() {
        return isRequesting;
    }

    @Override
    public void getIpByConnection() {
        if (!isRequesting) {
            connect();
        }
    }

    private void connect() {
        try {
            isRequesting = true;
            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(DataSources.SITE))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString());

            connectionCallBack.onSuccess(response.body());
            isRequesting = false;

        }catch (Exception e) {
            connectionCallBack.onError(e);
            isRequesting = false;
        }
    }
}

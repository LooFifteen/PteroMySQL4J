package dev.sllcoding.pteromysql4j.ptero.utils;

import dev.sllcoding.pteromysql4j.mysql.Data;
import dev.sllcoding.pteromysql4j.ptero.Panel;
import dev.sllcoding.pteromysql4j.ptero.auth.Account;
import kong.unirest.*;
import kong.unirest.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class RequestManager {

    private final TimeUnit unit;
    private final int allowRequestEvery;

    private final List<CustomRequest> queue = new ArrayList<>();
    private final UnirestInstance unirest = Unirest.spawnInstance();

    public RequestManager(TimeUnit unit, int allowRequestEvery) {
        this.unit = unit;
        this.allowRequestEvery = allowRequestEvery;

        unirest.config().followRedirects(true);

        Thread seconds = new Thread("RequestManager Thread") {
            @Override
            public void run() {
                while (true) {
                    try {
                        unit.sleep(allowRequestEvery);
                        if (queue.size() > 0) {
                            CustomRequest request = queue.get(0);
                            HttpResponse<JsonNode> response = request.getRequest().asJson();
                            request.consumer.accept(response);
                            queue.remove(0);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        seconds.start();
    }

    public void get(Panel panel, String endpoint, Account account, Consumer<HttpResponse<JsonNode>> success) {
        GetRequest request = Unirest.get(panel.getUrl() + endpoint);
        queue.add(new CustomRequest(request.header("User-Agent", "PostmanRuntime/7.26.3").header("Accept", "Application/vnd.pterodactyl.v1+json").header("Authorization", "Bearer " + account.getKey()), success));
    }

    public void post(Panel panel, String endpoint, Account account, Consumer<HttpResponse<JsonNode>> success, Data... data) {
        HttpRequestWithBody request = Unirest.post(panel.getUrl() + endpoint);
        JSONObject json = new JSONObject();
        for (Data data1 : data) {
            json.put(data1.getKey(), data1.getData().toString());
        }
        queue.add(new CustomRequest(request.body(json).header("User-Agent", "PostmanRuntime/7.26.3").header("Content-Type", "application/json").header("Accept", "Application/vnd.pterodactyl.v1+json").header("Authorization", "Bearer " + account.getKey()), success));
    }

    private static class CustomRequest {
        private final HttpRequest request;
        public final Consumer<HttpResponse<JsonNode>> consumer;

        private CustomRequest(HttpRequest request, Consumer<HttpResponse<JsonNode>> consumer) {
            this.request = request;
            this.consumer = consumer;
        }

        public Consumer<HttpResponse<JsonNode>> getConsumer() {
            return consumer;
        }

        public HttpRequest getRequest() {
            return request;
        }

    }

}

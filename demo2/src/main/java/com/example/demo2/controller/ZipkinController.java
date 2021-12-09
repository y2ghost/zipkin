package com.example.demo2.controller;

import com.github.kristofa.brave.Brave;
import com.github.kristofa.brave.http.DefaultSpanNameProvider;
import com.github.kristofa.brave.http.HttpClientRequest;
import com.github.kristofa.brave.http.HttpClientRequestAdapter;
import com.github.kristofa.brave.http.HttpClientResponseAdapter;
import com.github.kristofa.brave.http.HttpResponse;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Request;
import com.ning.http.client.RequestBuilder;
import com.ning.http.client.Response;
import io.swagger.annotations.Api;
import java.net.URI;
import java.util.concurrent.Future;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("demo2 相关API")
@RestController
@RequestMapping("/demo2/zipkin")
public class ZipkinController {
    private AsyncHttpClient asyncHttpClient;
    private Brave brave;

    public ZipkinController(AsyncHttpClient asyncHttpClient, Brave brave) {
        this.asyncHttpClient = asyncHttpClient;
        this.brave = brave;
    }

    @GetMapping("/test")
    public String test() {
        try {
            // 调用demo3的服务
            String response3Body = asyncHttpRequest("http://127.0.0.1:8083/demo3/zipkin/test");
            // 调用demo4的服务
            String response4Body = asyncHttpRequest("http://127.0.0.1:8084/demo4/zipkin/test");
            return response3Body + "====" + response4Body;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            return "";
        }
    }

    private String asyncHttpRequest(String url) throws Exception {
        RequestBuilder builder = new RequestBuilder();
        builder.setUrl(url);
        Request request = builder.build();
        clientRequestInterceptor(request);
        Future<Response> response = asyncHttpClient.executeRequest(request);
        clientResponseInterceptor(response.get());
        return response.get().getResponseBody();
    }

    private void clientRequestInterceptor(Request request) {
        brave.clientRequestInterceptor().handle(new HttpClientRequestAdapter(new HttpClientRequest() {
            @Override
            public void addHeader(String key, String value) {
                request.getHeaders().add(key, value);
            }

            @Override
            public URI getUri() {
                return URI.create(request.getUrl());
            }

            @Override
            public String getHttpMethod() {
                return request.getMethod();
            }
        }, new DefaultSpanNameProvider()));
    }

    private void clientResponseInterceptor(Response response) {
        brave.clientResponseInterceptor().handle(new HttpClientResponseAdapter(new HttpResponse() {
            @Override
            public int getHttpStatusCode() {
                return response.getStatusCode();
            }
        }));
    }
}


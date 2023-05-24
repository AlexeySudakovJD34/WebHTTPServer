package org.example.server;

import java.util.ArrayList;
import java.util.List;

public class HttpRequestParser {
    private final Request resultRequest;

    public HttpRequestParser() {
        this.resultRequest = new Request();
    }

    public Request parseRequest(String request) {

        String[] requestsLines = request.split("\r\n");
        String[] requestLine = requestsLines[0].split(" ");
        resultRequest.setMethod(requestLine[0]);
        resultRequest.setPath(requestLine[1]);
        resultRequest.setVersion(requestLine[2]);
        resultRequest.setHost(requestsLines[1].split(" ")[1]);

        List<String> headers = new ArrayList<>();
        for (int h = 2; h < requestsLines.length; h++) {
            String header = requestsLines[h];
            headers.add(header);
        }
        resultRequest.setHeaders(headers);

        return resultRequest;
    }
}
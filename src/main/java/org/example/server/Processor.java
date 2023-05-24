package org.example.server;

import org.example.utils.*;

import java.io.*;

import java.net.Socket;
import java.net.URISyntaxException;
import java.util.*;

public class Processor implements Runnable {

    private Socket socket;
    private Map<String, Map<String, Handler>> handlers;
    private HttpRequestParser parser;

    public Processor(Socket socket, Map<String, Map<String, Handler>> handlers) {
        this.socket = socket;
        this.handlers = handlers;
        this.parser = new HttpRequestParser();
    }

    @Override
    public void run() {
        try (var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             var out = new BufferedOutputStream(socket.getOutputStream())) {

            processRequest(in, out);

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void processRequest(BufferedReader in, BufferedOutputStream out)
            throws IOException, URISyntaxException {

        StringBuilder requestBuilder = new StringBuilder();
        String line;
        while (!(line = in.readLine()).isBlank()) {
            requestBuilder.append(line + "\r\n");
        }

        String requestLine = requestBuilder.toString();

        Request request = parser.parseRequest(requestLine);

        if (!handlers.containsKey(request.getMethod())) {
            ResponseUtils.sendMethodNotAllowed(out);
        }
        if (!request.getPath().startsWith("/")) {
            ResponseUtils.sendBadRequest(out);
        }

        Handler handler = handlers.get(request.getMethod()).get(request.getPath());

        if (handler == null) {
            ResponseUtils.sendNotFound(out);
            System.out.println(request + " handled with 404 error!");
        } else {
            handler.handle(request, out);
            System.out.println(request + " handled!");
        }
    }
}
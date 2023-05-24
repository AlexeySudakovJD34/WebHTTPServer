package org.example.server;

import java.util.Hashtable;
import java.util.List;

public class Request {

    private String method;
    private String path;
    private String version;
    private String host;
    private List<String> headers;

    public String getMethod() {
        return method;
    }
    public void setMethod(String method) {
        this.method = method;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }
    public String getHost() { return host; }
    public void setHost(String host) { this.host = host; }
    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }
    public List<String> getHeaders() {
        return headers;
    }

    @Override
    public String toString() {
        return "Request {\n" +
                "\tmethod='" + method + "',\n" +
                "\tpath='" + path + "',\n" +
                "\tversion='" + version + "',\n" +
                "\thost='" + host + "',\n" +
                "\theaders='" + headers.toString() + "',\n" +
                '}';
    }
}
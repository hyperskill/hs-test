package org.hyperskill.hstest.mocks.web.request;

import org.apache.http.entity.ContentType;
import org.hyperskill.hstest.mocks.web.constants.Headers;
import org.hyperskill.hstest.mocks.web.constants.Methods;
import org.hyperskill.hstest.mocks.web.constants.Schemas;
import org.hyperskill.hstest.mocks.web.response.HttpResponse;

import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.hyperskill.hstest.common.Utils.sleep;
import static org.hyperskill.hstest.mocks.web.constants.Headers.AUTHORIZATION;
import static org.hyperskill.hstest.mocks.web.constants.Headers.CONTENT_TYPE;
import static org.hyperskill.hstest.mocks.web.constants.Methods.POST;
import static org.hyperskill.hstest.mocks.web.constants.UrlSeparators.anchorSep;
import static org.hyperskill.hstest.mocks.web.constants.UrlSeparators.hostSep;
import static org.hyperskill.hstest.mocks.web.constants.UrlSeparators.paramsSep;
import static org.hyperskill.hstest.mocks.web.constants.UrlSeparators.portSep;
import static org.hyperskill.hstest.mocks.web.constants.UrlSeparators.schemaSep;
import static org.hyperskill.hstest.mocks.web.request.HttpRequestExecutor.packUrlParams;

public class HttpRequest {

    String method;
    String version = "";

    String schema = "http";
    String host = "localhost";
    int port = -1;
    String endpoint = "/";
    String anchor = "";

    Map<String, String> params = new LinkedHashMap<>();
    Map<String, String> headers = new LinkedHashMap<>();

    String content = "";
    int contentLength;

    public HttpRequest() {
        this(Methods.GET, "");
    }

    public HttpRequest(String uri) {
        this(Methods.GET, uri);
    }

    public HttpRequest(Methods method) {
        this(method, "");
    }

    public HttpRequest(Methods method, String uri) {
        this.method = method.name();
        if (uri.length() != 0) {
            setUri(uri);
        }
    }

    public String getMethod() {
        return method;
    }

    public String getUri() {
        String httpHost = schema + schemaSep + host;
        if (port >= 0) {
            httpHost += portSep + port;
        }
        return httpHost + getLocalUri();
    }

    public String getLocalUri() {
        String localUri = endpoint;
        if (params.size() > 0) {
            localUri += paramsSep + packUrlParams(params);
        }
        if (anchor.length() > 0) {
            localUri += anchorSep + anchor;
        }
        return localUri;
    }

    public HttpRequest setUri(String uri) {
        if (uri.contains(schemaSep)) {
            int index = uri.indexOf(schemaSep);
            schema = uri.substring(0, index);
            uri = uri.substring(index + schemaSep.length());
        }

        if (!uri.contains(hostSep)) {
            uri += hostSep;
        }

        int hostIndex = uri.indexOf(hostSep);
        String hostAndPort = uri.substring(0, hostIndex);

        if (hostAndPort.contains(portSep)) {
            int portIndex = hostAndPort.indexOf(portSep);
            host = hostAndPort.substring(0, portIndex);
            port = Integer.parseInt(hostAndPort.substring(portIndex + portSep.length()));
        } else if (hostAndPort.length() != 0) {
            host = hostAndPort;
        }

        uri = uri.substring(hostIndex);

        if (uri.contains(anchorSep)) {
            int index = uri.indexOf(anchorSep);
            setAnchor(uri.substring(index + anchorSep.length()));
            uri = uri.substring(0, index);
        }

        if (uri.contains(paramsSep)) {
            int index = uri.indexOf(paramsSep);
            String strGetParams = uri.substring(index + 1);
            String[] arrayParams = strGetParams.split("&");

            for (String param : arrayParams) {
                String[] parts = param.split("=");
                String key = parts[0];
                String value = parts.length == 1 ? "" : parts[1];
                addParam(key, value);
            }

            uri = uri.substring(0, index);
        }

        setEndpoint(uri);
        return this;
    }

    public String getSchema() {
        return schema;
    }

    public HttpRequest setSchema(Schemas schema) {
        return setSchema(schema.toString());
    }

    public HttpRequest setSchema(String schema) {
        this.schema = schema;
        return this;
    }

    public String getHost() {
        return host;
    }

    public HttpRequest setHost(String host) {
        this.host = host;
        return this;
    }

    public int getPort() {
        return port;
    }

    public HttpRequest setPort(int port) {
        this.port = port;
        return this;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public HttpRequest setEndpoint(String endpoint) {
        if (!endpoint.startsWith("/")) {
            endpoint = "/" + endpoint;
        }
        this.endpoint = endpoint;
        return this;
    }

    public String getAnchor() {
        return anchor;
    }

    public HttpRequest setAnchor(String anchor) {
        this.anchor = anchor;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public Map<String, String> getParams() {
        return params;
    }

    @Deprecated
    public Map<String, String> getGetParams() {
        return getParams();
    }

    public HttpRequest addParam(String param, String value) {
        params.put(param, value);
        return this;
    }

    public HttpRequest addParams(Map<String, String> newParams) {
        for (String key : newParams.keySet()) {
            addParam(key, newParams.get(key));
        }
        return this;
    }

    @Deprecated
    public HttpRequest setGetParam(String getParam, String value) {
        return addParam(getParam, value);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public HttpRequest addHeader(Headers header, String value) {
        return addHeader(header.toString(), value);
    }

    public HttpRequest addHeader(String header, String value) {
        headers.put(header, value);
        return this;
    }

    public HttpRequest addHeaders(Map<String, String> newHeaders) {
        for (String key : newHeaders.keySet()) {
            addHeader(key, newHeaders.get(key));
        }
        return this;
    }

    public String getContent() {
        return content;
    }

    public int getContentLength() {
        return contentLength;
    }

    public HttpRequest setContent(String content) {
        this.content = content;
        contentLength = content.length();
        return this;
    }

    public HttpRequest setContentType(ContentType type) {
        return addHeader(CONTENT_TYPE, type.getMimeType());
    }

    public HttpRequest basicAuth(String login, String pass) {
        String beforeEncoding = login + ":" + pass;
        String afterEncoding = Base64.getEncoder().encodeToString(beforeEncoding.getBytes());
        String headerValue = "Basic " + afterEncoding;
        return addHeader(AUTHORIZATION, headerValue);
    }

    public HttpResponse send() {
        if (method.equals(POST.toString()) && !params.isEmpty()) {
            content = packUrlParams(params);
        }
        HttpResponse response = HttpRequestExecutor.send(this);
        sleep(1); // So that we cannot send 2 requests in the same microsecond
        return response;
    }
}

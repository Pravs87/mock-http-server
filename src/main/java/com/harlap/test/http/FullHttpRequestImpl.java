package com.harlap.test.http;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Represents a HTTP request. Contains following properties:
 * <ul>
 * <li>method: HTTP method (GET, PUT, POST, DELETE, HEAD, ...).
 * <li>contentType: Content type of the message body. Content of Content-Type header.
 * <li>content: Message body.
 * <li>domain: host name part of url. For example in following example:
 * http://localhost:8081/persons?name=Smith&gender=female host name is localhost
 * <li>port: port part of url. For example in following example: http://localhost:8081/persons?name=Smith&gender=female port
 * is 8081
 * <li>path: Path part of url. For example in following example: http://localhost:8081/persons?name=Smith&gender=female path
 * part is /persons
 * <li>query parameters: query parameters part of url. For example in following example:
 * http://localhost:8081/persons?name=Smith&gender=female query parameters are name=Smith, gender=female.
 * </ul>
 * 
 * @author kristof
 */
public final class FullHttpRequestImpl implements FullHttpRequest {

    private static final String QUERY_SEPARATOR = "?";
    private static final String HTTP_SCHEME = "http";
    private static final String PORT_SEPARATOR = ":";
    private static final String NOT_SPECIFIED = "null";
    private final HttpRequestImpl httpRequest;

    // Address properties.
    private String domain;
    private Integer port;

    /**
     * Creates a new HTTP request object with no parameters set.
     */
    public FullHttpRequestImpl() {
        httpRequest = new HttpRequestImpl();
    }

    /**
     * Copy constructor.
     * 
     * @param request Will copy properties from this request.
     */
    public FullHttpRequestImpl(final FullHttpRequest request) {
        httpRequest = new HttpRequestImpl(request);
        domain = request.getDomain();
        port = request.getPort();
    }

    /**
     * Sets method for request.
     * 
     * @param method Method for request.
     * @return This http request.
     */
    public FullHttpRequestImpl method(final Method method) {
        httpRequest.method(method);
        return this;
    }

    /**
     * Sets content type of message body for request.
     * 
     * @param contentType Content type of message body for request.
     * @return This http request.
     */
    public FullHttpRequestImpl contentType(final String contentType) {
        httpRequest.contentType(contentType);
        return this;
    }

    /**
     * Sets content of message body for request.
     * 
     * @param content Message body for request.
     * @return This http request.
     */
    public FullHttpRequestImpl content(final String content) {
        httpRequest.content(content);
        return this;
    }

    /**
     * Sets host for request.
     * 
     * @param domain Sets domain / host for request.
     * @return This http request.
     */
    public FullHttpRequestImpl domain(final String domain) {
        this.domain = domain;
        return this;
    }

    /**
     * Sets port for request.
     * 
     * @param port Sets port for request.
     * @return This http request.
     */
    public FullHttpRequestImpl port(final Integer port) {
        this.port = port;
        return this;
    }

    /**
     * Sets path for request.
     * 
     * @param path Sets path for request.
     * @return This http request.
     */
    public FullHttpRequestImpl path(final String path) {
        httpRequest.path(path);
        return this;
    }

    /**
     * Adds a query parameter for request.
     * 
     * @param key Parameter key. Should not be empty or <code>null</code>.
     * @param value Parameter value. Should not be empty or <code>null</code>
     * @return This http request.
     */
    public FullHttpRequestImpl queryParameter(final String key, final String value) {
        httpRequest.queryParameter(key, value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Method getMethod() {
        return httpRequest.getMethod();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getContentType() {
        return httpRequest.getContentType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getContent() {
        return httpRequest.getContent();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDomain() {
        return domain;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getPort() {
        return port;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPath() {
        return httpRequest.getPath();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<QueryParameter> getQueryParameters() {
        return httpRequest.getQueryParameters();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUrl() {
        String httpRequestUrl = "";

        if (domain != null) {
            httpRequestUrl += HTTP_SCHEME + "://" + domain;
        } else {
            httpRequestUrl += HTTP_SCHEME + "://";
        }

        if (port != null) {
            httpRequestUrl += PORT_SEPARATOR + port;
        }

        if (httpRequest.getPath() != null) {
            httpRequestUrl += httpRequest.getPath();
        } else {
            httpRequestUrl += "/";
        }

        if (!httpRequest.getQueryParameters().isEmpty()) {
            httpRequestUrl += QUERY_SEPARATOR + getQueryParamsAsString();
        }
        return httpRequestUrl;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {

        final String httpRequestString = httpRequest.toString();
        final String domainString = add("Domain: ", domain);
        final String portString = add("Port: ", port);

        final String[] array = {httpRequestString, domainString, portString};

        return StringUtils.join(array, "\n");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj, false);
    }

    private String add(final String value, final Object object) {
        if (object != null) {
            return value + object;
        }
        return value + NOT_SPECIFIED;
    }

    private String getQueryParamsAsString() {

        String queryParamsAsString = "";
        boolean first = true;
        for (final QueryParameter parameter : httpRequest.getQueryParameters()) {
            if (first) {
                first = false;
            } else {
                queryParamsAsString += "&";
            }
            queryParamsAsString += parameter.toString();
        }
        return queryParamsAsString;
    }

}

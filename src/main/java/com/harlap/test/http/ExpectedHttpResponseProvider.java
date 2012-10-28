package com.harlap.test.http;

/**
 * Provides responses for given {@link HttpRequest http requests}. Abstracts where the responses come from.
 * <p>
 * Used with {@link MockHttpServer}.
 * 
 * @see MockHttpServer
 * @author kristof
 */
public interface ExpectedHttpResponseProvider {

    /**
     * Gets expected response for given request.
     * 
     * @param request HttpRequest.
     * @return Response or <code>null</code> in case we don't know given request.
     */
    HttpResponse getResponse(final HttpRequest request);

    /**
     * Should be executed when all requests have been submitted. Checks if all expected requests have been requested.
     * 
     * @throws UnsatisfiedExpectationException In case expectation were not as expected.
     */
    void verify() throws UnsatisfiedExpectationException;

}

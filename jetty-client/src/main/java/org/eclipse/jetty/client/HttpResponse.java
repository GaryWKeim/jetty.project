//
//  ========================================================================
//  Copyright (c) 1995-2012 Mort Bay Consulting Pty. Ltd.
//  ------------------------------------------------------------------------
//  All rights reserved. This program and the accompanying materials
//  are made available under the terms of the Eclipse Public License v1.0
//  and Apache License v2.0 which accompanies this distribution.
//
//      The Eclipse Public License is available at
//      http://www.eclipse.org/legal/epl-v10.html
//
//      The Apache License v2.0 is available at
//      http://www.opensource.org/licenses/apache2.0.php
//
//  You may elect to redistribute this code under either of these licenses.
//  ========================================================================
//

package org.eclipse.jetty.client;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.api.Response;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpVersion;

public class HttpResponse implements Response
{
    private final HttpFields headers = new HttpFields();
    private final Request request;
    private final List<ResponseListener> listeners;
    private HttpVersion version;
    private int status;
    private String reason;

    public HttpResponse(Request request, List<ResponseListener> listeners)
    {
        this.request = request;
        this.listeners = listeners;
    }

    public HttpVersion getVersion()
    {
        return version;
    }

    public HttpResponse version(HttpVersion version)
    {
        this.version = version;
        return this;
    }

    @Override
    public int getStatus()
    {
        return status;
    }

    public HttpResponse status(int status)
    {
        this.status = status;
        return this;
    }

    public String getReason()
    {
        return reason;
    }

    public HttpResponse reason(String reason)
    {
        this.reason = reason;
        return this;
    }

    @Override
    public HttpFields getHeaders()
    {
        return headers;
    }

    @Override
    public long getConversationID()
    {
        return request.getConversationID();
    }

    @Override
    public <T extends ResponseListener> List<T> getListeners(Class<T> type)
    {
        ArrayList<T> result = new ArrayList<>();
        for (ResponseListener listener : listeners)
            if (type == null || type.isInstance(listener))
                result.add((T)listener);
        return result;
    }

    @Override
    public boolean abort(Throwable reason)
    {
        return request.abort(reason);
    }

    @Override
    public String toString()
    {
        return String.format("%s[%s %d %s]", HttpResponse.class.getSimpleName(), getVersion(), getStatus(), getReason());
    }
}

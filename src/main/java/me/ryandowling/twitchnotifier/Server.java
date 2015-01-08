package me.ryandowling.twitchnotifier;

import fi.iki.elonen.NanoHTTPD;

public class Server extends NanoHTTPD {
    public Server(int port) {
        super(port);
    }

    @Override
    public Response serve(IHTTPSession session) {
        switch (session.getUri()) {
            case "/":
                return found("Test");
            default:
                return notFound();
        }
    }

    private Response found(String response) {
        return new NanoHTTPD.Response(response);
    }

    private Response notFound() {
        return new NanoHTTPD.Response(Response.Status.NOT_FOUND, "text/html", "404 Not Found");
    }
}

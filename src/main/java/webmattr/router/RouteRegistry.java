package webmattr.router;

import java.util.HashMap;

/**
 *
 */
public class RouteRegistry {
    final static RouteRegistry instance = new RouteRegistry();

    private final HashMap<String, String> proxyMap = new HashMap<>();

    private RouteRegistry() {
    }
}

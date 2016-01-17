package webmattr.router;

import webmattr.router.Location;
import webmattr.router.RoutesCallback;

/**
 *
 */
public abstract class ModuleLoader {
    private Location location;
    private RoutesCallback callback;

    public void load(Location location, RoutesCallback callback) {
        this.location = location;
        this.callback = callback;
        dispatch();
    }

    public Location getLocation() {
        return location;
    }

    public RoutesCallback getCallback() {
        return callback;
    }

    protected abstract void dispatch();
}

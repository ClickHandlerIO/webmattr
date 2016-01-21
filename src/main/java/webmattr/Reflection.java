package webmattr;

/**
 *
 */
public class Reflection {
    public static native void iterate(Object obj, Func.Run2<String, Object> callback) /*-{
        if (!callback || !obj) {
            return;
        }

        for (var p in obj) {
            if (obj.hasOwnProperty(p)) {
                callback(p, obj[p]);
            }
        }
    }-*/;

    /**
     *
     * @param obj
     * @param name
     * @param <T>
     * @return
     */
    public static native <T> T get(Object obj, String name) /*-{
        return obj[name];
    }-*/;

    /**
     *
     * @param obj
     * @param name
     * @param value
     * @param <T>
     * @return
     */
    public static native <T> T set(Object obj, String name, Object value) /*-{
        return obj[name] = value;
    }-*/;

    /**
     *
     * @param obj
     * @param name
     * @param <T>
     * @return
     */
    public static native <T> T delete(Object obj, String name) /*-{
        delete obj[name];
    }-*/;

    /**
     *
     * @param obj
     * @param nativeClass
     */
    public static void extend(Object obj, String nativeClass) {
        String[] parts = nativeClass.split("[.]");
        extend_(obj, parts);
    }

    /**
     *
     * @param obj
     * @param nativeClass
     */
    public static native void extend_(Object obj, String[] nativeClass) /*-{
        var superClass = $wnd;
        for (var i = 0; i < nativeClass.length; i++) {
            superClass = superClass[nativeClass[i]];
        }
        for (var i in superClass) {
            if (superClass.hasOwnProperty(i)) {
                obj[i] = superClass[i];
            }
        }
    }-*/;

    /**
     *
     * @param nativeObject
     * @param javaPrototype
     * @param <T>
     * @return
     */
    public static native <T> T copy(Object nativeObject, Object javaPrototype) /*-{
        if (!nativeObject) {
            nativeObject = {};
        }
        var extension = Object.create(javaPrototype.__proto__);
        for (var i in nativeObject) {
            if (nativeObject.hasOwnProperty(i)) {
                extension[i] = nativeObject[i];
            }
        }
        return extension;
    }-*/;

    public static native void assign(Object target, Object sources) /*-{
        Object.assign(target, sources);
    }-*/;

    /**
     *
     * @param obj
     * @param <T>
     * @return
     */
    public static native <T> T clone(Object obj) /*-{
        return Object.create(obj.__proto__);
    }-*/;

    /**
     *
     * @return
     */
    public static native String createShortUID() /*-{
        return ("0000" + (Math.random() * Math.pow(36, 4) << 0).toString(36)).slice(-4)
    }-*/;
}
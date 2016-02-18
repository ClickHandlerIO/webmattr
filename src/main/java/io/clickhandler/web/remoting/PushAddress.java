package io.clickhandler.web.remoting;

import io.clickhandler.web.Bus;

/**
 *
 */
public class PushAddress<T> {
    private String name;
    private String id;
    private Bus.TypeName<T> typeName;

    public PushAddress(String name, Bus.TypeName<T> typeName) {
        this.name = name;
        this.typeName = typeName;
    }

    public PushAddress(String name, String id, Bus.TypeName<T> typeName) {
        this.name = name;
        this.id = id;
        this.typeName = typeName;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public Bus.TypeName<T> getTypeName() {
        return typeName;
    }

    public String getAddress() {
        if (name == null) name = "";
        if (id == null) id = "";
        return id.isEmpty() ? name : name.isEmpty() ? id : name + "|" + id;
    }
}

package com.rationalfx.clearbankgateway.model;

public class HalLink {
    private String name;
    private String href;
    private boolean templated;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public boolean isTemplated() {
        return templated;
    }

    public void setTemplated(boolean templated) {
        this.templated = templated;
    }

    @Override
    public String toString() {
        return "HalLink{" +
                "name='" + name + '\'' +
                ", href='" + href + '\'' +
                ", templated=" + templated +
                '}';
    }
}

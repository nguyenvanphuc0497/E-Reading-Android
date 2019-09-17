package com.dtu.capstone2.ereading.network.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Create by Nguyen Van Phuc on 2019-07-04
 */
@Root(name = "entry", strict = false)
public class VnExpressItemRss {
    @Element(name = "title", required = false)
    private String title;

    @Element(name = "summary", required = false)
    private String summary;

    @Element(name = "published", required = false)
    private String published;

    @Element(name = "updated", required = false)
    private String updated;

    @Element(name = "link", required = false)
    private String link;

    @Element(name = "id", required = false)
    private String id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

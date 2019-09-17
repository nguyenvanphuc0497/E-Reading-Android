package com.dtu.capstone2.ereading.network.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Create by Nguyen Van Phuc on 2019-07-04
 */
@Root(name = "feed", strict = false)
public class BaseFeedResponse {
    @Element(name = "title", required = false)
    private String title;

    @Element(name = "subtitle", required = false)
    private String subtitle;

    @Element(name = "updated", required = false)
    private String updated;

    @Element(name = "generator", required = false)
    private String generator;

    @Element(name = "link", required = false)
    private String link;

    @ElementList(name = "entry", required = false, inline = true)
    private List<VnExpressItemRss> entries;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getGenerator() {
        return generator;
    }

    public void setGenerator(String generator) {
        this.generator = generator;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<VnExpressItemRss> getEntries() {
        return entries;
    }

    public void setEntries(List<VnExpressItemRss> entries) {
        this.entries = entries;
    }
}

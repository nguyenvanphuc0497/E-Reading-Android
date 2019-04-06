package com.dtu.capstone2.ereading.network.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Create by Nguyen Van Phuc on 4/6/19
 */
@Root(name = "item", strict = false)
public class RssItemResponse {

    @Element(name = "title")
    private String title;

    @Element(name = "link")
    private String link;

    @Element(name = "description", required = false)
    private String description;

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the link
     */
    public String getLink() {
        return link;
    }

    /**
     * @param link the link to set
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
}

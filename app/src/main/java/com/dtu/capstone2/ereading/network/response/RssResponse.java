package com.dtu.capstone2.ereading.network.response;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Create by Nguyen Van Phuc on 4/6/19
 */
@Root(name = "rss", strict = false)
public class RssResponse {

    @Element(name = "title")
    @Path("channel")
    private String channelTitle;

    @ElementList(name = "item", inline = true)
    @Path("channel")
    private List<RssItemResponse> articleList;

    /**
     * @return the channelTitle
     */
    public String getChannelTitle() {
        return channelTitle;
    }

    /**
     * @param channelTitle the channelTitle to set
     */
    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    /**
     * @return the articleList
     */
    public List<RssItemResponse> getArticleList() {
        return articleList;
    }

    /**
     * @param articleList the articleList to set
     */
    public void setArticleList(List<RssItemResponse> articleList) {
        this.articleList = articleList;
    }

}

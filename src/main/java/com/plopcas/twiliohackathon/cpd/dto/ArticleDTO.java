package com.plopcas.twiliohackathon.cpd.dto;

public class ArticleDTO {
    private String url;
    private String publishedAt;
    private String title;
    private String description;
    private String content;

    public ArticleDTO() {
    }

    public ArticleDTO(String url, String publishedAt, String title, String description, String content) {
        this.url = url;
        this.publishedAt = publishedAt;
        this.title = title;
        this.description = description;
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

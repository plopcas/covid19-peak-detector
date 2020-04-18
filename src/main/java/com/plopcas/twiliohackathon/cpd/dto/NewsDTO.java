package com.plopcas.twiliohackathon.cpd.dto;

import java.util.List;

public class NewsDTO {
    private String status;
    private Integer totalResults;
    private List<ArticleDTO> articles;

    public NewsDTO() {
    }

    public NewsDTO(String status, Integer totalResults, List<ArticleDTO> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public List<ArticleDTO> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleDTO> articles) {
        this.articles = articles;
    }
}

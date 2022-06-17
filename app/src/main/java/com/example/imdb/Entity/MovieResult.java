package com.example.imdb.Entity;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieResult {

    @SerializedName("searchType")
    @Expose
    private String searchType;
    @SerializedName("expression")
    @Expose
    private String expression;
    @SerializedName("results")
    @Expose
    private List<Result> results = null;
    @SerializedName("errorMessage")
    @Expose
    private String errorMessage;

    public MovieResult() {
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}


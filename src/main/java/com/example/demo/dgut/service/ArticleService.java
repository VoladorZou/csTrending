package com.example.demo.dgut.service;

import com.example.demo.dgut.model.Article;

import java.util.List;

public interface ArticleService {

    boolean saveArticle(Article save);

    List<Article> getArticleByUserId(int userId);
}

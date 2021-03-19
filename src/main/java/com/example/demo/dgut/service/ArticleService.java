package com.example.demo.dgut.service;

import com.example.demo.dgut.model.Article;
import com.example.demo.dgut.model.User;

import java.util.List;

public interface ArticleService {

    boolean saveArticle(Article save);

    // 根据用户ID获取文章信息
    List<Article> getArticleByUserId(int userId);

    // 根据文章ID获取文章信息
    Article getArticleByArticleId(int articleid);

    // 获取全部文章信息
    List<Article> getArticleList();
}

package com.example.demo.dgut.service.impl;

import com.example.demo.dgut.dao.ArticleDao;
import com.example.demo.dgut.model.Article;
import com.example.demo.dgut.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Override
    public boolean saveArticle(Article article) {
        try {
            articleDao.saveArticle(article);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Article> getArticleByUserId(int userId) {
        try {
            List<Article> articleList;
            articleList = articleDao.getArticleByUserId(userId);
            return articleList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

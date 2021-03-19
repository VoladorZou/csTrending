package com.example.demo.dgut.dao;

import com.example.demo.dgut.model.Article;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ArticleDao {
    int deleteByPrimaryKey(Integer articleid);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(Integer articleid);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKey(Article record);

    // 保存文章
    boolean saveArticle(Article save);

    // 根据用户ID获取文章信息
    List<Article> getArticleByUserId(int userId);

    // 根据文章ID获取文章信息
    Article getArticleByArticleId(int articleid);

    // 获取全部文章信息
    List<Article> getArticleList();

    // 模糊查询（根据文章标题来进行查询结果）
    List<Article> getArticleListByArticle(String articleTitle);
}

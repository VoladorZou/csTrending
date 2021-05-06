package com.example.demo.dgut.dao;

import com.example.demo.dgut.model.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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

    // 保存文章的其它信息
    boolean updateArticle(Article update);

    // 记录阅读量
    boolean recordViews(int articleid);

    // 审核文章
    boolean permitArticle(@Param("articleid") int articleid, @Param("isPermited") Boolean isPermited);

    // 删除文章
    boolean deleteArticle(@Param("articleid") int articleid, @Param("isDeleted") Boolean isDeleted);

    // 修改文章
    boolean updateMarkdown(@Param("articleid") int articleid, @Param("markdown") String markdown);

    // 根据用户ID获取文章信息
    List<Article> getArticleByUserId(int userId);

    // 根据文章ID获取文章信息
    Article getArticleByArticleId(int articleid);

    // 根据文章标签获取文章信息
    List<Article> getArticleByTag(String tag);

    // 获取全部文章信息
    List<Article> getArticleList();

    // 模糊查询（根据文章标题来进行查询结果）
    List<Article> getArticleListByArticle(String articleTitle);

    // 上传文章封面
    Boolean upLoadArticleCover(@Param("articleId") int articleId, @Param("articleCover") String articleCover);
}

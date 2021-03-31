package com.example.demo.dgut.dao;

import com.example.demo.dgut.model.Collection;
import com.example.demo.dgut.model.Fellow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CollectionDao {
    int deleteByPrimaryKey(Integer collectionid);

    int insert(Collection record);

    int insertSelective(Collection record);

    Collection selectByPrimaryKey(Integer collectionid);

    int updateByPrimaryKeySelective(Collection record);

    int updateByPrimaryKey(Collection record);

    // 添加收藏
    Boolean collecting(@Param("userId") int userId, @Param("articleId") int articleId);

    // 取消收藏
    Boolean uncollect(@Param("userId") int userId, @Param("articleId") int articleId);

    // 查询收藏状态
    Boolean collectState(@Param("userId") int userId, @Param("articleId") int articleId);

    // 根据当前登录账号ID获取关注列表
    List<Collection> getCollectionList(int userId);
}

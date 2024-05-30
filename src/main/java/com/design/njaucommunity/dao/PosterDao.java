package com.design.njaucommunity.dao;

import com.design.njaucommunity.bean.Comment;
import com.design.njaucommunity.bean.Poster;
import com.design.njaucommunity.bean.PosterImg;
import com.design.njaucommunity.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PosterDao {
    List<Poster> getPostersByPageSize(@Param("pageindex") Integer page, @Param("size") Integer size);

    List<String> getimgurlBypid(@Param("pid") String pid);

    void delPosterByPid(@Param("pid") Integer pid);

    void addPostercontent(@Param("userid") Integer uid, @Param("content") String content);

    Integer getPid(@Param("uid") Integer uid);

    void addPosterimg(List<PosterImg> postersImg);

    void addComment(@Param("uid") Integer uid, @Param("content") String content, @Param("rid") Integer rid, @Param("pid") Integer pid);

    void updatecommentcount(@Param("pid") Integer pid);

    List<Comment> getCommentsByPidPageSize(@Param("pid") Integer pid, @Param("pageindex") Integer page,
                                           @Param("size") Integer size);

    void delComment(@Param("cid") Integer cid);

    Poster getPosterByPid(@Param("pid") Integer pid);

    void addclickcount(@Param("pid") Integer pid);

    Integer getlikebypiduid(@Param("pid") Integer pid, @Param("uid") Integer uid);

    void addlikeByPidUid(@Param("pid") Integer pid, @Param("uid") Integer uid);

    void dellikeByPidUid(@Param("pid") Integer pid, @Param("uid") Integer uid);

    void updatelikecount(@Param("pid") Integer pid);

    List<User> getlikeusersBypid(@Param("pid")Integer pid);

    List<Poster> getpostersByuidpagesize(@Param("uid") Integer uid,@Param("pageindex") Integer  index,@Param("size") Integer size);

    List<Poster> getlikepostersByuidpagesize(@Param("uid") Integer uid,@Param("pageindex") Integer  index,@Param("size") Integer size);

    List<Comment> getlikecommentsByuid(@Param("uid") Integer uid,@Param("pageindex") Integer  index,@Param("size") Integer size);

}


package com.design.njaucommunity.service;


import com.design.njaucommunity.bean.Comment;
import com.design.njaucommunity.bean.Poster;
import com.design.njaucommunity.bean.PosterImg;
import com.design.njaucommunity.bean.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Service
@Transactional
public interface PosterService {
    List<Poster> getPostersByPageSize(Integer page,Integer size);

    List<String> getimgurlByPid(String pid);

    void deletePosterByPid(Integer pid);



    void addPoster(Integer uid, String content);

    Integer getPidByUid(Integer uid);

    void  addPosterImg(List<PosterImg> postersImg);

    Poster getPosterBypid(Integer pid);

    void addComment(Integer uid,String content,Integer rid,Integer pid);

    List<Comment> getComments(Integer pid,Integer pageindex,Integer size);

    void delComment(Integer cid,Integer pid);

    Boolean getiflike(Integer pid,Integer uid);

    void updatelike(Integer pid,Integer uid,Boolean iflike);

    List<User> getlikeusersBypid(Integer pid);

    List<Poster> getpostersByuid(Integer uid,Integer index,Integer size);

    List<Poster> getlikepostersByuid(Integer uid,Integer index,Integer size);

    List<Comment>  getCommentsByuid(Integer uid,Integer index,Integer size);


//    void deletePosterImgByPid(Integer pid);
//
//    void deletelikesByPid(Integer pid);
}

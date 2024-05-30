package com.design.njaucommunity.service;



import com.design.njaucommunity.bean.Comment;
import com.design.njaucommunity.bean.Poster;
import com.design.njaucommunity.bean.PosterImg;
import com.design.njaucommunity.bean.User;
import com.design.njaucommunity.dao.PosterDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional //控制事务
public class PosterServiceImpl implements PosterService{
    @Autowired
    private PosterDao posterDao;

    @Override
   public List<Poster> getPostersByPageSize(Integer page, Integer size){
        System.out.println("进入service");
        return posterDao.getPostersByPageSize(page, size);
    }
    public List<String> getimgurlByPid(String pid){
       return posterDao.getimgurlBypid(pid);
    }

   public void deletePosterByPid(Integer pid){
        posterDao.delPosterByPid(pid);
    }

   public void addPoster(Integer uid, String content){
      posterDao.addPostercontent( uid ,content);
   }

   public Integer getPidByUid(Integer uid){
       Integer pid  = posterDao.getPid( uid);
       return pid;
   }
   public  void  addPosterImg(List<PosterImg> postersImg){
        posterDao.addPosterimg(postersImg);
   }

   public void addComment(Integer uid,String content,Integer rid,Integer pid){
        posterDao.addComment(uid,content,rid,pid);
        posterDao.updatecommentcount(pid);
    }

    public List<Comment> getComments(Integer pid, Integer pageindex, Integer size){
        return posterDao.getCommentsByPidPageSize(pid,pageindex,size);
    }

    public void delComment(Integer cid,Integer pid){
        posterDao.delComment(cid);
        posterDao.updatecommentcount(pid);
    }

    public Poster getPosterBypid(Integer pid){
        //获取动态，也增加clickcount
        posterDao.addclickcount(pid);

        return posterDao.getPosterByPid(pid);
    }

    public Boolean getiflike(Integer pid,Integer uid){

        if(posterDao.getlikebypiduid(pid,uid)==0){
            return false;
        }
        return true;
    }

    public void updatelike(Integer pid,Integer uid,Boolean iflike){
        if(iflike){
            posterDao.addlikeByPidUid(pid,uid);
        }else {
            posterDao.dellikeByPidUid(pid,uid);
        }
        posterDao.updatelikecount(pid);
    }

    public List<User> getlikeusersBypid(Integer pid){
        return posterDao.getlikeusersBypid(pid);
    }

    public List<Poster> getpostersByuid(Integer uid,Integer index,Integer size){
        return posterDao.getpostersByuidpagesize(uid,index,size);
    }

    public List<Poster> getlikepostersByuid(Integer uid,Integer index,Integer size){
        return posterDao.getlikepostersByuidpagesize(uid,index,size);
    }

    public List<Comment>  getCommentsByuid(Integer uid,Integer index,Integer size){
        return posterDao.getlikecommentsByuid(uid,index,size);
    }

//    public void deletePosterImgByPid(Integer pid){
//        posterDao.deletePosterImgByPid(pid);
//    }
//
//   public void deleteCommentsByPid(Integer pid){
//        posterDao.deleteCommentsByPid(pid);
//    }
//
//   public void deletelikesByPid(Integer pid){
//        posterDao.deletelikesByPid(pid);
//    }
}

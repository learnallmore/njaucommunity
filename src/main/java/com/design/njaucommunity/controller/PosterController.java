package com.design.njaucommunity.controller;


import com.design.njaucommunity.bean.*;
import com.design.njaucommunity.service.PosterService;
import com.design.njaucommunity.service.UserService;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("posters")
public class PosterController {
    @Autowired
    PosterService posterService;
    @Autowired
    UserService userService;



    @PostMapping("get")
    public HashMap<String,Object> getPostersInfoByPageSize(@RequestBody HashMap<String,Integer> limit){
        Integer page = limit.get("page");
        Integer size = limit.get("size");
        Integer uid = limit.get("userid");//返回当前用户的点赞列表
        int index = (page-1)*15;
        List<Poster> posters = posterService.getPostersByPageSize(index,size);
        System.out.println(posters.size());
        List<User> users = new LinkedList<User>() ;
        List<Boolean> iflike = new LinkedList<Boolean>();
        //获取每个poster对应用户信息
        for(int i=0;i<posters.size();i++){
            Integer id = posters.get(i).getUid();
            String idstr = id.toString();
            users.add(userService.getUserByUid(idstr));
            Integer pid = posters.get(i).getPid();
            String pidstr = pid.toString();
            List<String> posterImgurllist = posterService.getimgurlByPid(pidstr);
            //poster对象加入imgurl数组
            posters.get(i).setImgurl(posterImgurllist.toArray(
                    new String[posterImgurllist.size()]
            ));

            iflike.add(posterService.getiflike(pid,uid));
        }
        HashMap<String,Object> res = new HashMap<String, Object>();
        res.put("posters",posters);
        res.put("users",users);
        res.put("iflike",iflike);
        //返回给前端
        return res;
    }


    @PostMapping("delete")
    public Response delPosterByPid(@RequestBody HashMap<String,Integer> pid){
//            Integer pidInt = Integer.valueOf(pid.get("posterId"));
            posterService.deletePosterByPid(pid.get("posterId"));
            return new Response(true,"success");
    }

    @PostMapping("add")
    public Response addPosterAndImg(@RequestBody HashMap<String,Object> poster){
        Integer uid = (Integer) poster.get("userId");
        String content = (String) poster.get("content");
    //    boolean isArray =  poster.get("images") instanceof ArrayList;
        posterService.addPoster(uid,content);
    //    String uidstr = uid.toString();
       Integer pid =  posterService.getPidByUid(uid);
       System.out.println(pid);
            ArrayList<String> imgurls = (ArrayList<String>) poster.get("images");
            List<PosterImg> postersimg = new LinkedList<PosterImg>();
            for(int i=0;i<imgurls.size();i++){
                PosterImg posterImg = new PosterImg();
                posterImg.setPid(pid);
                posterImg.setImgurl(imgurls.get(i));
                postersimg.add(i,posterImg);
            }

            posterService.addPosterImg(postersimg);
        return new Response(true,"success");
    }

    @PostMapping("getdetail")
    public HashMap<String,Object> getPosterDetail(@RequestBody HashMap<String,Integer> pid){
        Integer pidint = pid.get("posterid");
        String pidstr = pidint.toString();
       Poster poster =  posterService.getPosterBypid(pidint);
       List<User> likeusers = posterService.getlikeusersBypid(pidint);


     List<String> imgurls =   posterService.getimgurlByPid(pidstr);
     poster.setImgurl(imgurls.toArray(new String[imgurls.size()]));

        Integer uid = poster.getUid();
       String uidstr = uid.toString();
       User user = userService.getUserByUid(uidstr);
       HashMap<String,Object> res = new HashMap<String, Object>();
       res.put("poster",poster);
       res.put("user",user);
       res.put("likes",likeusers);
       return res;
    }

    @PostMapping("getcomments")
    public HashMap<String,Object> getCommentsByPidPageSize(@RequestBody HashMap<String, Object> pidAndlimit){
        String pid =(String) pidAndlimit.get("posterid");
        Integer page = (Integer)pidAndlimit.get("page");
        Integer size =(Integer) pidAndlimit.get("size");
        int index = (page-1)*15;
        Integer pidint = Integer.parseInt(pid);
        List<Comment> comments = posterService.getComments(pidint,index,size);
        List<User> users = new LinkedList<User>(); //comments的用户
        List<User> replyusers = new LinkedList<User>(); // comments reply的用户
        for(int i=0;i<comments.size();i++){
            Integer uid = comments.get(i).getUid();
            String uidstr = uid.toString();
            users.add(userService.getUserByUid(uidstr));
        }
        for(int i=0;i<comments.size();i++){
            Integer rid = comments.get(i).getReplyid();
            String ridstr = rid.toString();
            System.out.println(ridstr+" what");
            replyusers.add(userService.getUserByUid(ridstr));
        }
        System.out.println(replyusers.size());
        System.out.println("回复");

        HashMap<String,Object> res = new HashMap<String, Object>();
        //返回两个数组
        res.put("comments",comments);
        res.put("commentusers",users);
        res.put("replyusers",replyusers);
        return res;
    }

    @PostMapping("addcomment")
    public Response addcomment(@RequestBody HashMap<String,Object> comment){
        String content = (String) comment.get("content");
        Integer pid = (Integer) comment.get("posterid");
        Integer replyid = (Integer) comment.get("replyid");
        Integer uid = (Integer) comment.get("userid");

        posterService.addComment(uid,content,replyid,pid);
        return new Response(true,"success");
    }

    @PostMapping("deletecomment")
    public Response delcomment(@RequestBody HashMap<String,Integer> limit){
        Integer cid = limit.get("commentid");
        Integer pid = limit.get("posterid");
        posterService.delComment(cid,pid);

        return new Response(true,"success");
    }

    @PostMapping("updatelikes")
    public void updatelikes(@RequestBody HashMap<String,Object> limit){
        Integer pid =(Integer) limit.get("posterid");
        Boolean iflike =(Boolean) limit.get("iflike");
        Integer userid = (Integer) limit.get("userid");
        posterService.updatelike(pid,userid,iflike);
        
    }





}

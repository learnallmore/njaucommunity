package com.design.njaucommunity.controller;

import com.design.njaucommunity.bean.Comment;
import com.design.njaucommunity.bean.Poster;
import com.design.njaucommunity.bean.Response;
import com.design.njaucommunity.bean.User;
import com.design.njaucommunity.service.PosterService;
import com.design.njaucommunity.service.UserService;
import com.sun.corba.se.spi.ior.ObjectKey;
import com.sun.xml.internal.ws.api.ha.StickyFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/*
* 2021.04.19
* 完成登录与注册功能
* */

@RestController
@CrossOrigin
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PosterService posterService;



    @PostMapping("login")//和前端对应，参数也对应
    public Response login(@RequestBody HashMap<String, String> user){
            String phonenumber =  user.get("phonenumber");
           String password =  user.get("password");//这里不知道key的类型应该是string
            String url = user.get("avatarurl");
            String nickname = user.get("nickname");

        User getuser = userService.getUserByPhonenumber(phonenumber);
        if(getuser==null) {
            userService.reg(nickname,password,phonenumber,url);
            return new Response(true,"注册成功！");
        }else{
            if(password.equals(getuser.getPassword())==false){
                return new Response(false,"账号或密码错误？");
            }
            Integer uid = getuser.getUid();
            userService.updateUserInfo(nickname,url,phonenumber);
            return new Response(true,uid.toString());
        }
    }

    @PostMapping("getdetail")
    public HashMap<String,User> getUserByuid(@RequestBody HashMap<String,Integer> limit){
        Integer userid = limit.get("userid");
        String uidstr = userid.toString();
        User user = userService.getUserFollowedFollowing(uidstr);
        HashMap<String,User> res = new HashMap<String, User>();
        res.put("user",user);
        return res;
    }

    @PostMapping("getposters")
    public HashMap<String,Object> getpostersByuid(@RequestBody HashMap<String,Integer> limit){
        Integer page = limit.get("page");
        Integer size = limit.get("size");
        Integer uid = limit.get("userid");
        int index = (page-1)*15;
        List<Poster> posters = posterService.getpostersByuid(uid,index,size);
        for(int i=0;i<posters.size();i++){
            Integer pid = posters.get(i).getPid();
            String pidstr = pid.toString();
            List<String> posterImgurllist = posterService.getimgurlByPid(pidstr);
            //poster对象加入imgurl数组
            posters.get(i).setImgurl(posterImgurllist.toArray(
                    new String[posterImgurllist.size()]
            ));
        }
        HashMap<String,Object> res= new HashMap<String, Object>();
        res.put("posters",posters);
        return res;
    }

    @PostMapping("getlikes")
    public HashMap<String,Object> getlikesByuid(@RequestBody HashMap<String,Integer> limit){
        Integer page = limit.get("page");
        Integer size = limit.get("size");
        Integer uid = limit.get("userid");
        Integer index = (page-1)*15;
        List<Poster> posters = posterService.getlikepostersByuid(uid,index,size);
        List<User> users = new LinkedList<User>() ;
        for(int i=0;i<posters.size();i++){
            users.add(userService.getUserByUid(posters.get(i).getUid().toString()));
            Integer pid = posters.get(i).getPid();
            String pidstr = pid.toString();
            List<String> posterImgurllist = posterService.getimgurlByPid(pidstr);
            //poster对象加入imgurl数组
            posters.get(i).setImgurl(posterImgurllist.toArray(
                    new String[posterImgurllist.size()]
            ));
        }
        HashMap<String,Object> res= new HashMap<String, Object>();
        res.put("likeposters",posters);
        res.put("likeusers",users);
        return res;
    }

    @PostMapping("getcomments")
    public HashMap<String,Object>  getusercomments(@RequestBody HashMap<String,Integer> limit){
        Integer page = limit.get("page");
        Integer size = limit.get("size");
        Integer uid = limit.get("userid");
        Integer index = (page-1)*15;
        List<Comment> comments = posterService.getCommentsByuid(uid,index,size);
        List<Poster> posters = new LinkedList<Poster>();
        List<User> users = new LinkedList<User>();
        for(int i=0;i<comments.size();i++){
            posters.add(posterService.getPosterBypid(comments.get(i).getPid()));
        }
        for(int i=0;i<posters.size();i++){
            Integer pid = posters.get(i).getPid();
            String pidstr = pid.toString();
            List<String> posterImgurllist = posterService.getimgurlByPid(pidstr);
            //poster对象加入imgurl数组
            posters.get(i).setImgurl(posterImgurllist.toArray(
                    new String[posterImgurllist.size()]
            ));
        }


        for(int i=0;i<posters.size();i++){
            users.add(userService.getUserByUid(posters.get(i).getUid().toString()));
        }
        HashMap<String,Object> res = new HashMap<String, Object>();
        res.put("comments",comments);
        res.put("commentposters",posters);
        res.put("commentusers",users);
        return res;
    }

    @PostMapping("/follow")
    public Response setfollow(@RequestBody HashMap<String,Integer> followuserid){
        int followUserId = followuserid.get("followUserId");
        int userid = followuserid.get("userid");
        if(followUserId==userid){
            return new Response(false,"fail");
        }
        int res = userService.getifFollow(followUserId,userid);

        if(res==-1){
            userService.addFollow(followUserId,userid);
        }else{
            userService.delFollow(followUserId,userid);
        }
        return new Response(true,"success");
    }

    //用户关注的人的列表
    @PostMapping("getfollowinglist")
    public HashMap<String,Object> getfollowinglist(@RequestBody HashMap<String,Integer> limit){
        int uid = limit.get("userid");
        int size = limit.get("size");
        int page = limit.get("page");
        int index = (page-1)*15;
        List<Integer> userids = userService.getfollowinglist(uid,index,size);
        List<User> users = new LinkedList<User>();
        for(int i=0;i<userids.size();i++){
            users.add(userService.getUserFollowedFollowing(userids.get(i).toString()));
        }
        HashMap<String,Object> res = new HashMap<String, Object>();
        res.put("followinglist",users);
        return res;
    }

    @PostMapping("getfollowerlist")
    public HashMap<String,Object> getfollowerslist(@RequestBody HashMap<String,Integer> limit){
        int uid = limit.get("userid");
        int size = limit.get("size");
        int page = limit.get("page");
        int index = (page-1)*15;

        List<Integer> userids = userService.getfollowerlist(uid,index,size);
        List<User> users = new LinkedList<User>();
        for(int i=0;i<userids.size();i++){
            users.add(userService.getUserFollowedFollowing(userids.get(i).toString()));
        }
        HashMap<String,Object> res = new HashMap<String, Object>();
        res.put("followerList",users);
        return res;

    }


}

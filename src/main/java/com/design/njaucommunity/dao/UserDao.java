package com.design.njaucommunity.dao;

import com.design.njaucommunity.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDao {

    //在登录,注册和主页显示用户信息时使用

    User loadUserByPhonenumber(@Param("number") String phonenumber);

    void reg(@Param("name")String nickname,@Param("password") String password,
            @Param("phonenumber") String phonenumber,@Param("avatarurl") String avatarurl);

    void updateuserinfo(@Param("name")String nickname,@Param("avatarurl") String avatarurl,
                        @Param("number")String number);

    User getUserByUid(@Param("uid")String uid);

    List<User> getUserByUserName(@Param("name") String username);

    //展示谁关注你的列表
    List<Integer> getFollowerUidsByUid(@Param("uid") String uid);

    //展示关注的人的列表
    List<Integer> getFollowingUidsByUid(@Param("uid") String uid);

    Integer getifFollow(@Param("uid") Integer uid,@Param("followeduid") Integer followeduid);

    void addFollower(@Param("uid") Integer uid,@Param("followeduid") Integer followeduid);

    void delFollower(@Param("uid") Integer uid,@Param("followeduid") Integer followeduid);

    //关注列表
    List<Integer> getfollowinglist(@Param("uid")Integer uid,@Param("index")Integer index,@Param("size")Integer size);

    //粉丝列表
    List<Integer> getfollowerlist(@Param("uid")Integer uid,@Param("index")Integer index,@Param("size")Integer size);








}

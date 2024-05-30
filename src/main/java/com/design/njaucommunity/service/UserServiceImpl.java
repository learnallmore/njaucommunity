package com.design.njaucommunity.service;

import com.design.njaucommunity.bean.User;
import com.design.njaucommunity.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@Transactional //控制事务

public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;
    @Override
    public User getUserByPhonenumber(String number) {
        User user = userDao.loadUserByPhonenumber(number);
     //   System.out.println(user.getPassword());
        return user;
    }

    public void reg(String name,String password,String phonenumber,String avatarurl){
         userDao.reg(name,password,phonenumber,avatarurl);
    }

    public void updateUserInfo(String name,String url,String number){
        userDao.updateuserinfo(name,url,number);
    }


    public User getUserByUid(String uid){
        if(uid=="-1"){//if replyid=-1 ,则为user对象
            User user =  new User();
        }
            User user =  userDao.getUserByUid(uid);
        return user;
    }

    public User getUserFollowedFollowing(String uid){
        User user = userDao.getUserByUid(uid);
        List<Integer> followed  = userDao.getFollowerUidsByUid(uid);
        List<Integer> following = userDao.getFollowingUidsByUid(uid);
//        if(followed==null&&following==null){
//            return user;
//        }
//        if(followed==null){
//            user.setFollowing(following);
//            return user;
//        }
//        if(following==null){
//            user.setFollowers(followed);
//            return user;
//        }
        user.setFollowers(followed).setFollowing(following);
        return user;
    }

    public Integer getifFollow(Integer uid,Integer followuid){
        if(userDao.getifFollow(uid,followuid)==null){
            return -1;
        }
        return userDao.getifFollow(uid,followuid);

    }

    public void addFollow(Integer uid,Integer followuid){
        userDao.addFollower(uid, followuid);
    }


    public void delFollow(Integer uid,Integer followuid){
        userDao.delFollower(uid,followuid);
    }

    public List<Integer> getfollowinglist(Integer uid,Integer index,Integer size){
        return userDao.getfollowinglist(uid,index,size);
    }

    public List<Integer> getfollowerlist(Integer uid,Integer index,Integer size){
        return userDao.getfollowerlist(uid,index,size);
    }









}

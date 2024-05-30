package com.design.njaucommunity.service;

import com.design.njaucommunity.bean.User;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface UserService {

    User getUserByPhonenumber(String number);

    void reg(String name,String password,String phonenumber,String avatarurl);

    void updateUserInfo(String nickname,String url,String number);

    User getUserByUid(String uid);

    User getUserFollowedFollowing(String uid);

    Integer getifFollow(Integer uid,Integer followuid);

    void addFollow(Integer uid,Integer followuid);

    void delFollow(Integer uid,Integer followuid);

    List<Integer> getfollowinglist(Integer uid, Integer index, Integer size);

    List<Integer> getfollowerlist(Integer uid,Integer index,Integer size);


}

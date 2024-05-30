package com.design.njaucommunity.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)//set方法返回this，可以链式设值

public class User {
    private Integer uid;
    private String phonenumber;
    private String nickname;
    private String password;
    private Boolean isAdmin;
    private String avatarURL;
    private List<Integer> following;//关注的人
    private List<Integer> followers;//粉丝
}

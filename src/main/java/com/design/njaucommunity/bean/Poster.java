package com.design.njaucommunity.bean;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;


import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)//set方法返回this，可以链式设值






public class Poster {

  private Integer pid;
  private Integer uid;
  private Integer clickcount;
  private Integer likecount;
  private Integer commentcount;
  private String content;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date datetime;
  private String[] imgurl;



}

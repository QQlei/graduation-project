package com.graduation.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 随便看看右侧关注使用
 * Created by JiangL on 2018/2/19.
 */
@Entity
@Data
public class UserIsFollow implements Serializable{
    @Id
    private Long id;
    private String userName;
    private String profilePicture;
    private String isFollow;

}

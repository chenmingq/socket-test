package com.github.chenmingq.server.system.entry;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserAll {

    private long id;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 创建时间
     */
    private Date createDate;

    @Override
    public String toString() {
        return "UserAll{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", account='" + account + '\'' +
                '}';
    }
}

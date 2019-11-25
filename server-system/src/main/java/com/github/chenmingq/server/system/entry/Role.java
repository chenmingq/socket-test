package com.github.chenmingq.server.system.entry;

import io.protostuff.Tag;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: chenmingqin
 * date: 2019/11/22 19:48
 * description:
 */


@Getter
@Setter
public class Role {

    @Tag(1)
    private long id;

    @Tag(2)
    private String userName;

    @Tag(3)
    private String account;

    @Tag(4)
    private String password;

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

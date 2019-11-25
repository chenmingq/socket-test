package com.github.chenmingq.msg.template;

import lombok.Getter;
import lombok.Setter;

/**
 * @author : chengmingqin
 * @date : 2019/11/21 20:59
 * description :
 */

@Getter
@Setter
public class SubNodeAttr {

    private String cmdId;

    private String name;

    private String desc;

    private String type;

    private String massageName;

    @Override
    public String toString() {
        return "SubNodeAttr{" +
                "cmdId='" + cmdId + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", type='" + type + '\'' +
                ", massageName='" + massageName + '\'' +
                '}';
    }
}

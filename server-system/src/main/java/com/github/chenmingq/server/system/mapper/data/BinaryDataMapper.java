package com.github.chenmingq.server.system.mapper.data;

import com.github.chenmingq.common.common.annotation.MapperScan;
import org.apache.ibatis.annotations.Insert;

/**
 * @author: chenmingqin
 * date: 2019/11/23 10:44
 * description:
 */

@MapperScan
public interface BinaryDataMapper {

    /**
     * 添加数据
     *
     * @param id
     * @param data
     */
    @Insert("insert into role(id,data) values (#{id},#{data}")
    void insert(long id, byte[] data);

}

package com.github.chenmingq.server.system.mapper.user;

import com.github.chenmingq.common.common.annotation.MapperScan;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author: chenmingqin
 * date: 2019/11/22 19:50
 * description:
 */

@MapperScan
public interface RoleMapper {

    /**
     * 添加数据
     *
     * @param id
     * @param datas
     */
    @Insert("insert into role(id,datas) values(#{id},#{datas})")
    void insertDatas(@Param("id") long id, @Param("datas") byte[] datas);

    /**
     * 查询所有的data 数据
     *
     * @return
     */
    @Select("select datas from role")
    List<byte[]> selectAllData();

}

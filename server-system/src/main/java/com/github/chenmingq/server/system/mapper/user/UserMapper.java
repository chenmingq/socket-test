package com.github.chenmingq.server.system.mapper.user;

import com.github.chenmingq.common.common.annotation.MapperScan;
import com.github.chenmingq.server.system.entry.UserAll;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@MapperScan
public interface UserMapper {

    /**
     * 查询单个数据
     *
     * @param id
     * @return
     */
    @Select("SELECT * FROM user_all where id = #{id}")
    UserAll selectById(int id);

    /**
     * 查询所有的数据
     *
     * @return
     */
    @Select("select * from user_all")
    List<UserAll> selectAllList();


    /**
     * 添加用户
     *
     * @param userAll
     */
    @Insert("insert into user_all (user_name, account, password,create_date) values (#{userName}, #{account}, #{password},#{createDate})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void insertUser(UserAll userAll);

    /**
     * 根据账号查询
     *
     * @param account
     * @return
     */
    @Select("select * from user_all where account = #{account}")
    UserAll selectAccount(String account);
}

package org.bbsgroup.bbs.dao;


import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Mapper;
import org.bbsgroup.bbs.entity.User;

@Mapper
public interface UserDao {
    @Insert("insert into bbs.user (user_id, username, password, phone, email, job, company) values (#{userId}, #{username}, #{password}, #{phone}, #{email}, #{job}, #{company})")
    void insertUser(User user);

    @Select("select * from bbs.user where username = #{username}")
    User selectByUsername(String username);

    @Select("select * from bbs.user where username = #{username} and password = #{password}")
    User selectUserByUsernameAndPassword(@Param("username") String username,@Param("password") String password);

    //    暂不支持更新 password
    @Update("update bbs.user set username = #{username}, email = #{email}, phone = #{phone}, job = #{job}, company = #{company} where user_id = #{userId}")
    void updateUser(User user);
}

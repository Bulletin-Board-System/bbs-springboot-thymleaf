package org.bbsgroup.bbs.dao;


import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Mapper;
import org.bbsgroup.bbs.entity.User;

import java.util.List;

@Mapper
public interface UserDao {
    //  新建用户
    @Insert("insert into bbs.user (user_id, username, password, phone, email, job, company) values (#{userId}, #{username}, #{password}, #{phone}, #{email}, #{job}, #{company})")
    void insertUser(User user);

    //  根据 username 获取用户
    @Select("select * from bbs.user where username = #{username}")
    User selectByUsername(String username);

    //  根据  username, password  获取用户
    @Select("select * from bbs.user where username = #{username} and password = #{password}")
    User selectUserByUsernameAndPassword(String username, String password);

    //  根据 userId 获取用户
    @Select("select * from bbs.user where user_id = #{userId}")
    User selectUserByUserId(Integer userId);

    //  根据 userId 列表获取用户列表
    @Select({
            "<script>",
            "select * from bbs.user where user_id in",
            "<foreach item='userId' collection='userIds' open='(' separator=',' close=')'>",
            "#{userId}",
            "</foreach>",
            "</script>"
    })
    List<User> selectAllByUserIds(List<Integer> userIds);

    //    暂不支持更新 password
    @Update("update bbs.user set username = #{username}, email = #{email}, phone = #{phone}, job = #{job}, company = #{company} where user_id = #{userId}")
    void updateUserByUserId(User user);
}

package org.bbsgroup.bbs.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.bbsgroup.bbs.entity.Admin;

import java.util.List;

@Mapper
public interface AdminDao {
    //  新建用户
    @Insert("insert into bbs.admin (user_id, username, password) values (#{userId}, #{username}, #{password})")
    void insertAdmin(Admin admin);

    //  根据 username 获取管理员
    @Select("select * from bbs.admin where username = #{username}")
    Admin selectByUsername(String username);

    //  根据  username, password  获取用户
    @Select("select * from bbs.admin where username = #{username} and password = #{password}")
    Admin selectAdminByUsernameAndPassword(String username, String password);

    //  根据 userId 获取管理员
    @Select("select * from bbs.admin where user_id = #{userId}")
    Admin selectAdminByUserId(Integer userId);

    //  根据 userId 列表获取管理员列表
    @Select({
            "<script>",
            "select * from bbs.admin where user_id in",
            "<foreach item='userId' collection='userIds' open='(' separator=',' close=')'>",
            "#{userId}",
            "</foreach>",
            "</script>"
    })
    List<Admin> selectAllByUserIds(List<Integer> userIds);

    //    暂不支持更新 password
    @Update("update bbs.admin set username = #{username}, email = #{email}, phone = #{phone}, job = #{job}, company = #{company} where user_id = #{userId}")
    void updateUserByUserId(Admin admin);
}

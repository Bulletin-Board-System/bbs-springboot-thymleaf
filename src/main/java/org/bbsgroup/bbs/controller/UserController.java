package org.bbsgroup.bbs.controller;

import jakarta.servlet.http.HttpSession;
import org.bbsgroup.bbs.common.Constants;
import org.bbsgroup.bbs.dao.UserDao;
import org.bbsgroup.bbs.entity.User;
import org.bbsgroup.bbs.util.Result;
import org.bbsgroup.bbs.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/user")
@Transactional
public class UserController {
    @Autowired
    private UserDao userDao;

    @PostMapping("/register")
    @ResponseBody
    public Result register(
            @RequestParam("username") String username,
            @RequestParam("password") String password
    ) {
        //  是否为空值
        if (!StringUtils.hasLength(username)) {
            return ResultGenerator.genBadRequestResult("用户名不能为空！");
        }

        if (!StringUtils.hasLength(password)) {
            return ResultGenerator.genBadRequestResult("密码不能为空！");
        }

        //  是否有重复用户名
        if (userDao.selectByUsername(username) != null) {
            return ResultGenerator.genConflictResult("用户名已存在！");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        // 数据库操作 DAO
        try {
            userDao.insertUser(user);
            return ResultGenerator.genSuccessResult(null);
        } catch (Exception e) {
            return ResultGenerator.genInternalServerError("服务器未知错误！");
        }
    }

    @PostMapping("/login")
    @ResponseBody
    public Result login(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) {
        //  是否为空值
        if (!StringUtils.hasLength(username)) {
            return ResultGenerator.genBadRequestResult("用户名不能为空！");
        }

        if (!StringUtils.hasLength(password)) {
            return ResultGenerator.genBadRequestResult("密码不能为空！");
        }

        // 数据库操作 DAO
        try {
            User user = userDao.selectUserByUsernameAndPassword(username, password);
            if(ObjectUtils.isEmpty(user)){
                return ResultGenerator.genBadRequestResult("学号或密码错误！");
            }
            session.setAttribute(Constants.USER_SESSION_KEY, user);
            return ResultGenerator.genSuccessResult(null);
        } catch (Exception e) {
            return ResultGenerator.genInternalServerError("服务器未知错误！");
        }
    }
}

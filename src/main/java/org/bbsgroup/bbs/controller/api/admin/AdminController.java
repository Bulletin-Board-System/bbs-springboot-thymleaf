package org.bbsgroup.bbs.controller.api.admin;

import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.http.HttpSession;
import org.bbsgroup.bbs.common.Constants;
import org.bbsgroup.bbs.dao.AdminDao;
import org.bbsgroup.bbs.entity.Admin;
import org.bbsgroup.bbs.util.Result;
import org.bbsgroup.bbs.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/admin")
@Transactional
public class AdminController {
    @Autowired
    private AdminDao adminDao;

    // 管理员注册
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
        if (adminDao.selectByUsername(username) != null) {
            return ResultGenerator.genConflictResult("用户名已存在！");
        }

        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(password);

        // 数据库操作 DAO
        try {
            adminDao.insertAdmin(admin);
            return ResultGenerator.genSuccessResult(null);
        } catch (Exception e) {
            return ResultGenerator.genInternalServerError("服务器未知错误！");
        }
    }

    // 管理员登录
    @PostMapping("/login")
    @ResponseBody
    public Result login(
            @RequestBody JSONObject jsonParam,
            HttpSession session
    ) {
        String username = jsonParam.getString("username");
        String password = jsonParam.getString("password");

        //  是否为空值
        if (!StringUtils.hasLength(username)) {
            return ResultGenerator.genBadRequestResult("用户名不能为空！");
        }

        if (!StringUtils.hasLength(password)) {
            return ResultGenerator.genBadRequestResult("密码不能为空！");
        }

        // 数据库操作 DAO
        try {
            Admin admin = adminDao.selectAdminByUsernameAndPassword(username, password);
            if(ObjectUtils.isEmpty(admin)){
                return ResultGenerator.genBadRequestResult("用户名或密码错误！");
            }
            session.setAttribute(Constants.ADMIN_SESSION_KEY, admin);
            return ResultGenerator.genSuccessResult(Constants.ADMIN_SESSION_KEY);
        } catch (Exception e) {
            return ResultGenerator.genInternalServerError("服务器未知错误！");
        }
    }

    // 管理员登出
    @GetMapping("/logout")
    @ResponseBody
    public Result logout(HttpSession httpSession) {
        httpSession.removeAttribute(Constants.ADMIN_SESSION_KEY);
        return ResultGenerator.genSuccessResult(null);
    }

}

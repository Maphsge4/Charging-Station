package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.demo.commonutils.Response;
import com.example.demo.entity.Account;
import com.example.demo.entity.Station;
import com.example.demo.service.AccountService;
import com.example.demo.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author machenxiang
 * @since 2022-04-17
 */
@RestController
@RequestMapping("/dataservice/account")
@CrossOrigin
public class AccountController {

    @Autowired
    private AccountService accountService;

//    @Autowired
//    private StationService stationService;

    @GetMapping("login")
    public Response login(String username, String password) {
        if (username == null || username.length() == 0
                || password == null || password.length() == 0) {
            return Response.fail().message("Username or password can't be empty.");
        }

        QueryWrapper<Account> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        int res = accountService.count(wrapper);

        if (1 == res) {
            Account account = accountService.getOne(wrapper);
            if (password.equals(account.getPassword())) {
                if (account.getType() == 0) {
                    return Response.ok().message("Admin");
                } else if (account.getVerification() == 1) {
                    return Response.ok().message("User");
                } else {
                    return Response.fail().message("Account unapproved.");
                }
            } else {
                return Response.fail().message("Wrong password.");
            }
        } else {
            return Response.fail().message("No such Account.");
        }
    }

    @GetMapping("register")
    public Response register(String username, String password) {
        if (username == null || username.length() == 0
                || password == null || password.length() == 0) {
            return Response.fail().message("Username or password can't be empty.");
        }

        QueryWrapper<Account> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        int res = accountService.count(wrapper);
        if (0 == res) {
            Account account = new Account();
            account.setUsername(username)
                    .setPassword(password)
                    .setType(1)
                    .setVerification(1);
            accountService.save(account);
            return Response.ok();
        } else {
            return Response.fail().message("Username already exists.");
        }
    }

}


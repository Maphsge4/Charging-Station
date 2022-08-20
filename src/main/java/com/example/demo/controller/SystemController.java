package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.demo.commonutils.Response;
import com.example.demo.entity.Account;
import com.example.demo.entity.Station;
import com.example.demo.service.AccountService;
import com.example.demo.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/system")
@CrossOrigin
public class SystemController {

    @GetMapping("para")
    public Response para(int quick_num, int slow_num , int wait_num , int que_num ) {

        return Response.ok();
    }
}

package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 如何将前端界面以静态html形式嵌入到spring boot工程中：
 *
 * 1. 在pom.xml中添加spring-boot-starter-thymeleaf
 * 2. 在templates下放入本实验所需的两个html文件
 * 3. 在controller中写一个返回该页面的controller【此时不能使用@RestController，否则会返回字符串】，模板如本文件
 * 4. 运行本工程，在浏览器中输入 ip:端口/url，即可打开相应界面
 */

// 注：静态文件放置为，index.html放置在templates中，其他的如js文件夹、css文件夹、image文件夹等放在static目录下
//    html文件中，要使用类似绝对路径的形式，如：/image/1.png，不能写成../static/image/1.png
@Controller
public class HtmlController {
    @RequestMapping("/user")
    public String userHtml(){
        return "user"; // 返回templates文件夹中的user.html
    }

    @RequestMapping("/admin")
    public String adminHtml(){
        return "admin"; // 返回templates文件夹中的admin.html
    }
}



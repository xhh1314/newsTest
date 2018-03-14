package com.hww.cms.webadmin.controller;

//import java.io.IOException;
//import java.io.PrintWriter;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.alibaba.fastjson.JSONException;
//import com.baidu.ueditor.ActionEnter;
//
//@Controller  
//@RequestMapping("/")  
//public class CommonController {  
//	
//    @RequestMapping(value = "/ueditor")  
//    public void config(HttpServletRequest request, HttpServletResponse response) {  
//        response.setContentType("application/json");  
//        String rootPath = request.getSession().getServletContext()  
//                .getRealPath("/");  
//        try {  
//            String exec = new ActionEnter(request, rootPath).exec();  
//            PrintWriter writer = response.getWriter();  
//            writer.write(exec);  
//            writer.flush();  
//            writer.close();  
//        } catch (IOException | JSONException e) {  
//            e.printStackTrace();  
//        }  
//    }  
//}  

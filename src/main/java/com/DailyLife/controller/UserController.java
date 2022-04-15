package com.DailyLife.controller;

import com.DailyLife.dto.User;
import com.DailyLife.mapper.UserMapper;
import com.DailyLife.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/addUser")
    public String addUserForm(Model model) {
        model.addAttribute("user", new User());
        return "user/joinForm";
    }

    @PostMapping("/addUser")
    public String signUp (@ModelAttribute User user , Model model , HttpSession session) throws NoSuchAlgorithmException {
        System.out.println("user = " + user);
        userService.addUser(user);
        return "redirect:/index";
    }
    @GetMapping("/deleteUser")
    public String deleteForm(@ModelAttribute User user, Model model) {
        model.addAttribute("user",user);
        return "user/deleteForm";
    }
    @PostMapping(value="/deleteUser")
    public String delete(HttpServletRequest request) throws Exception{
        HttpSession session =  request.getSession();
        User user = (User)session.getAttribute("user");
        userService.deleteUser(user);
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/test")
    @ResponseBody
    public String Test(User user){
        return "ok";
    }

    @GetMapping("/follower")
    public String follower() {
        return "user/follower";
    }

    @GetMapping("/following")
    public String following() {
        return "user/following";
    }


    @GetMapping("/findUser")
    public String findUser(Model model) {
        model.addAttribute("findUser" , new User());
        return "user/findForm";
    }

    @ResponseBody
    @GetMapping("/findById")
    public String findById(@RequestParam("userEmail") String userEmail) {

        String result= userService.findByEmail(userEmail);


        return result;
    }

    @ResponseBody
    @GetMapping("/findByIdToPw")
    public int findByIdToPw(@RequestParam("userId") String userId) {


        int result= userService.CheckById(userId);

        return result;

    }


    @PostMapping("/main")
    public String login(@ModelAttribute User userLoginRequest, Model model, HttpSession session) throws NoSuchAlgorithmException {
        if(userService.login(userLoginRequest)==1){
            session.setAttribute("user" , findByUserId(userLoginRequest.getUserId()));
            return "redirect:/index";
        }else
            return "main";
    }

    private User findByUserId(String userId) {
        return userService.findByUserId(userId);
    }

    @GetMapping("/userInfo")
    public String userInfo(){
        return "user/Information";
    }

    @GetMapping("/directMessage")
    public String directMessage() {return  "user/dm";}

    @GetMapping(value = "/checkId")
    @ResponseBody
    public String userIdCheck(String userId) throws Exception {

        int result = userService.CheckById(userId);
        if (result == 1) { // result로 받은 값이 1이라면 이미 있는 id로 fail 리턴
            return "fail";
        }
        else { // result 값이 1이 아니라면 없는 아이디로 success 리턴
            return "success";  }
    }

    @GetMapping(value = "/checkNickName")
    @ResponseBody
    public String userNickNameCheck(String userNickName) throws Exception {
        int result = userService.CheckByUserNickName(userNickName);
        if (result == 1) { // result로 받은 값이 1이라면 이미 있는 id로 fail 리턴
            return "fail";
        }
        else { // result 값이 1이 아니라면 없는 아이디로 success 리턴
            return "success";  }
    }
    @GetMapping("/userInfo/userUpdate")
    public String userUpdate(Model model, HttpSession session) {
        model.addAttribute("user", session.getAttribute("user"));
        return "user/infoUpdateForm";
    }
    @PostMapping("/userInfo/userUpdate")
    public String update ( HttpSession session) throws NoSuchAlgorithmException {
        User user = (User) session.getAttribute("user");
        log.info("user = {}" , user);
        userService.updateUser(user);
        return "user/Information";
    }
}





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

    public static final String AUTH_TIME_OUT_ADD = "AuthTimeOutForAdd";
    public static final String AUTH_TIME_OUT_FIND = "AuthTimeOutForFind";
    public static final String AUTH_NUM_CHECK = "authNumCheck";
    private final UserService userService;
    private final UserMapper userMapper;

    /*
      회원가입
    */

    @GetMapping("/addUser")
    public String addUserForm(Model model) {
        model.addAttribute("user", new User());
        return "user/joinForm";
    }

    @PostMapping("/addUser")
    public String signUp (@ModelAttribute User user , Model model , HttpSession session) throws NoSuchAlgorithmException {
        user.setUserPassword(user.getUserPasswordCheck());
        log.info("user = {}" , user);
        userService.addUser(user);
        return "index";
    }

    @PostMapping("/test")
    @ResponseBody
    public String Test(User user){
        System.out.println("user = " + user);
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



    @GetMapping("/EmailAuthor")
    @ResponseBody
    public Map<String, String> EmailCheck(@CookieValue(value = AUTH_TIME_OUT_ADD, required = false) String authKeyForAdd,
                                          String userEmail, HttpServletResponse response,
                                          HttpSession session, Model model) {
        log.info("이메일 전송 시작");
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(userEmail);
        Map<String, String> msg = new HashMap<>();


        if (!matcher.matches()) {
            msg.put("key", "fail");
            msg.put("msg", "이메일 양식을 확인해주세요.");

            session.setAttribute("message", msg.get("msg"));
            return msg;
        }

        /*
        Cookie 값 찾기
         */
//        Cookie cookie1 = Arrays.stream(request.getCookies()).
//                filter(cookie -> cookie.getName().equals(AUTH_TIME_OUT)).
//                findAny().orElse(null);

        if (matcher.matches() && authKeyForAdd == null) {
            authKeyForAdd = userService.emailSend(userEmail);
            System.out.println("authKey = " + authKeyForAdd);
            session.setAttribute("authKey", authKeyForAdd);

            Cookie cookie = new Cookie(AUTH_TIME_OUT_ADD, authKeyForAdd);
            cookie.setMaxAge(60);
            response.addCookie(cookie);

            msg.put("key", "succese");
            msg.put("msg", "메일이 전송되었습니다");
            session.setAttribute("message", msg.get("msg"));
            return msg;
        }

        if (authKeyForAdd != null) {
            msg.put("key", "emailDuplicate");
            msg.put("msg", "60초후에 다시 전송해주세요.");
            session.setAttribute("message", msg.get("msg"));
            return msg;
        }

        return null;
    }


    @PostMapping("/emailAuthorCheck")
    @ResponseBody
    public Map<String, String > AuthorCheck(String emailAuthor , HttpSession session) {

        System.out.println("emailAuthor = " + emailAuthor);

        String authKey = (String)session.getAttribute("authKey");
        Map<String , String > msg = new HashMap<>();

        if(emailAuthor.equals(authKey)) {
            msg.put("key" , "success");
            msg.put("msg" , "확인되었습니다.");
            session.removeAttribute("authKey");
            session.setAttribute("emailCertification" , msg);
            session.setAttribute("message2" , msg.get("msg"));
            return msg;
        }
        else {
            msg.put("key" , "fail");
            msg.put("msg" , "일치하지 않습니다.");
            session.setAttribute("message2" , msg.get("msg"));
        }
        return msg;
    }

    /*
    회원정보 찾기
     */

    @GetMapping("/findUser")
    public String findUser(Model model) {
        model.addAttribute("findUser" , new User());
        return "user/findForm";
    }

    @ResponseBody
    @GetMapping("/findById")
    public String findById(@RequestParam("userEmail") String userEmail) {

        String result= userService.findByEmail(userEmail);

        System.out.println("result = " + result);

        return result;
    }

    @ResponseBody
    @GetMapping("/findByIdToPw")
    public int findByIdToPw(@RequestParam("userId") String userId) {

        int result= userService.CheckById(userId);

        return result;

    }

    @ResponseBody
    @GetMapping("/AuthNumSend")
    public String AuthNumSend(@RequestParam("authorNum") String authorNum , HttpSession session) {

        if(((String)session.getAttribute(AUTH_NUM_CHECK)).equals(authorNum)) {
            return "OK";
        }
        return "Fail";
    }


    @ResponseBody
    @RequestMapping(value = "/sendEmailtoFind" , method = RequestMethod.GET , produces = "application/text; charset=utf8")
    public String sendEmailtoAuth( @CookieValue(value = AUTH_TIME_OUT_FIND, required = false) String authKeyForFind ,
                                   @RequestParam("userEmail") String userEmail , HttpSession session , HttpServletResponse response) {

        log.info("authKeyForFind {} " , authKeyForFind);

        if (authKeyForFind == null) {
            authKeyForFind = userService.emailSend(userEmail);
            session.setAttribute(AUTH_NUM_CHECK, authKeyForFind);
            Cookie cookie = new Cookie(AUTH_TIME_OUT_FIND, authKeyForFind);
            cookie.setMaxAge(60);
            response.addCookie(cookie);

            return "complete";

        } else {
            return "fail";
        }

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

        return userMapper.findByUserId(userId);

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

        int result = userMapper.CheckById(userId);
        if (result == 1) { // result로 받은 값이 1이라면 이미 있는 id로 fail 리턴
            return "fail";
        }
        else { // result 값이 1이 아니라면 없는 아이디로 success 리턴
            return "success";  }
    }

    @GetMapping(value = "/checkNickName")
    @ResponseBody
    public String userNickNameCheck(String userNickName) throws Exception {
        int result = userMapper.CheckByUserNickName(userNickName);
        if (result == 1) { // result로 받은 값이 1이라면 이미 있는 id로 fail 리턴
            return "fail";
        }
        else { // result 값이 1이 아니라면 없는 아이디로 success 리턴
            return "success";  }
    }
}





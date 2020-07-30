package com.xr.web.admin;

import com.xr.pojo.User;
import com.xr.service.UserService;
import com.xr.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login.html")
    public String loginPage(){
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes redirectAttributes){

        User user = userService.checkUser(username, MD5Utils.code(password));
        if(user != null){
            user.setPassword(null);
            session.setAttribute("user",user);
            return "admin/index";
        }else{
            redirectAttributes.addFlashAttribute("message","用户名和密码错误");
            return "redirect:/admin/login.html";
        }

    }

    @GetMapping("/logout")
    public String logput(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin/login.html";
    }
}

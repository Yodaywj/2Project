package controller;

import domain.ResultEntity;
import domain.Tb_User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.IUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.registry.infomodel.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService iUserService;

    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login/login";
    }

    @RequestMapping("/toRegister")
    public String toRegister() {
        return "login/register";
    }

    @RequestMapping("/index")
    public String index() {
        return "index/index";
    }

    @RequestMapping("/question")
    public String question() {
        return "center/question";
    }

    @RequestMapping("/login")
    //@ResponseBody
    public void login(String userName, String password, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Tb_User user = iUserService.login(userName);
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8;");
        PrintWriter out = response.getWriter();
        if (user == null) {
            out.println("<script>");
            out.println("alert('无此用户');");
            out.println("window.location.href='/user/toLogin'");
            out.println("</script>");
        } else {
            if (user.getPassword().equals(password)) {
                request.getSession().setAttribute("user", user);
                out.println("<script>");
                out.println("alert('登录成功');");
                out.println("window.location.href='/user/index'");
                out.println("</script>");
            } else {
                out.println("<script>");
                out.println("alert('密码错误');");
                out.println("window.location.href='/user/toLogin'");
                out.println("</script>");
            }
        }
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        return "redirect:/user/index";
    }

    @RequestMapping("/register")
    //@ResponseBody
    public void register(String userName, String password, HttpServletRequest request, HttpServletResponse response)  throws Exception{
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8;");
            iUserService.save(userName, password);
            ResultEntity resultEntity = new ResultEntity();
            resultEntity.setResult(true);
            resultEntity.setMessage("注册成功");
            PrintWriter out =response.getWriter();
            out.println("<script>");
            out.println("alert('注册成功');");
            out.println("window.location.href='/user/toLogin'");
            out.println("</script>");
        } catch (Exception e) {
            PrintWriter out_e =response.getWriter();
            out_e.println("<script>");
            out_e.println("alert('注册失败，用户重复');");
            out_e.println("window.location.href='/user/toRegister'");
            out_e.println("</script>");
        }
    }

    @RequestMapping("/session")
    @ResponseBody
    public ResultEntity session(HttpServletRequest request) {
        try {
            Tb_User user = (Tb_User) request.getSession().getAttribute("user");
            if (user == null) throw new Exception("");
            ResultEntity resultEntity = new ResultEntity();
            resultEntity.setResult(true);
            resultEntity.setOther("user", user);
            return resultEntity;
        } catch (Exception e) {
            //e.printStackTrace();
            return ResultEntity.failure("登录超时，重新登录");
        }
    }
}

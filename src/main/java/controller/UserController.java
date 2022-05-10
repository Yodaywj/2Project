package controller;

import domain.*;
import mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.IUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService iUserService;

    @Autowired
    UserMapper userMapper;

    @RequestMapping("/Admin")
    public String toAdmin() {
        return "login/admin";
    }

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
    public void login( String userName, String password, HttpServletRequest request, HttpServletResponse response) throws Exception {
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
            if (!user.getAdmin().equals("普通用户")){
                out.println("<script>");
                out.println("alert('无此用户');");
                out.println("window.location.href='/user/toLogin'");
                out.println("</script>");
            } else if (user.getPassword().equals(password)) {
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
    @RequestMapping("/admin")
    //@ResponseBody
    public void admin( String userName, String password, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Tb_User user = iUserService.login(userName);
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8;");
        PrintWriter out = response.getWriter();
        if (user == null) {
            out.println("<script>");
            out.println("alert('无此用户');");
            out.println("window.location.href='/user/Admin'");
            out.println("</script>");
        } else if (!user.getAdmin().equals("管理员")&&!user.getAdmin().equals("root")){
            out.println("<script>");
            out.println("alert('该账户非管理员');");
            out.println("window.location.href='/user/Admin'");
            out.println("</script>");
        }else {
            if (user.getPassword().equals(password)) {
                request.getSession().setAttribute("user", user);
                out.println("<script>");
                out.println("alert('登录成功');");
                out.println("window.location.href='/user/index'");
                out.println("</script>");
            } else {
                out.println("<script>");
                out.println("alert('密码错误');");
                out.println("window.location.href='/user/Admin'");
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
            ResultEntity resultEntity = new ResultEntity();
            if (user == null) throw new Exception("");
            if (user.getAdmin().equals("普通用户")){
                resultEntity.setResult(true);
                resultEntity.setOther("user", user);
                resultEntity.setOther("message", "no permission");
                return resultEntity;
            }else {
                resultEntity.setResult(true);
                resultEntity.setOther("user", user);
                return resultEntity;
            }
        } catch (Exception e) {
            //e.printStackTrace();
            return ResultEntity.failure("登录超时，重新登录");
        }
    }

    @RequestMapping("/manage")
    @ResponseBody
    public ResultEntity manage(HttpServletRequest request) {
        try {
            int num = 9999;
            String user = "";
            List<Tb_User> users = userMapper.select(num,user);
            ResultEntity resultEntity = new ResultEntity();
            resultEntity.setOther("rows",users);
            resultEntity.setResult(true);
            return resultEntity;
        } catch (Exception e) {
            //e.printStackTrace();
            return ResultEntity.failure("查询超时");
        }
    }

    @RequestMapping("/manageAfter")
    @ResponseBody
    public ResultEntity manageAfter(HttpServletRequest request,int num) {
        try {
            if (num==0){
                num=9999;
            }
            String user =request.getParameter("user");
            List<Tb_User> users = userMapper.select(num,user);
            ResultEntity resultEntity = new ResultEntity();
            resultEntity.setOther("rows",users);
            resultEntity.setResult(true);
            return resultEntity;
        } catch (Exception e) {
            //e.printStackTrace();
            return ResultEntity.failure("查询超时");
        }
    }

    @RequestMapping("/delete")
    @ResponseBody
    public ResultEntity delete(int id,String admin,HttpServletRequest request) {
        Tb_User user = (Tb_User) request.getSession().getAttribute("user");
        if (!Objects.equals(user.getAdmin(), "root")) {
            return ResultEntity.failure("非root用户，删除失败");
        } else {
            userMapper.delete(id);
            return ResultEntity.success("删除成功");
        }
    }

    @RequestMapping("/mute")
    @ResponseBody
    public ResultEntity mute(int id,String state,String admin,HttpServletRequest request) {
        Tb_User user = (Tb_User) request.getSession().getAttribute("user");
        if ((admin.equals("管理员")&&!Objects.equals(user.getAdmin(), "root"))||admin.equals("root")) {
            return ResultEntity.failure("权限不足，操作失败");
        } else{
            userMapper.mute(id,state);
            return ResultEntity.success("操作成功");
        }
    }

    @RequestMapping("/addCase")
    @ResponseBody
    public ResultEntity addCase() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(new Date());
       userMapper.addCase(time);
       return ResultEntity.success("新增案例成功");
    }

    @RequestMapping("/editCase")
    @ResponseBody
    public ResultEntity editCase(int id, String content) {
        userMapper.editCase(id,content);
        return ResultEntity.success("案件实例编辑成功");
    }

    @RequestMapping("/editJudgment")
    @ResponseBody
    public ResultEntity editJudgment(int id, String judgment) {
        userMapper.editJudgment(id,judgment);
        return ResultEntity.success("案件判决编辑成功");
    }

    @RequestMapping("/deleteCase")
    @ResponseBody
    public ResultEntity deleteCase(int id) {
        userMapper.deleteCase(id);
        return ResultEntity.success("案件删除成功");
    }

    @RequestMapping("/showCase")
    @ResponseBody
    public ResultEntity showCase(int num, String keyword) {
        List<Tb_Case> cases = userMapper.showCase(num,keyword);
        ResultEntity result = new ResultEntity();
        result.setResult(true);
        result.setOther("cases",cases);
        return result;
    }

    @RequestMapping("/setShow")
    @ResponseBody
    public ResultEntity setShow(int id, String isShow) {
        userMapper.setShow(id,isShow);
        return ResultEntity.success("修改成功");
    }
    @RequestMapping("/powerChange")
    @ResponseBody
    public ResultEntity powerChange(int id, int power) {
        userMapper.powerChange(id,power);
        return ResultEntity.success("权重已修改");
    }

    @RequestMapping("/changePassword")
    //@ResponseBody
    public void changePassword(String userName, String password, HttpServletRequest request, HttpServletResponse response)  throws Exception{
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8;");
            userMapper.changePassword(userName, password);
            ResultEntity resultEntity = new ResultEntity();
            resultEntity.setResult(true);
            resultEntity.setMessage("注册成功");
            PrintWriter out =response.getWriter();
            out.println("<script>");
            out.println("alert('用户密码修改成功');");
            out.println("window.location.href='/user/index'");
            out.println("</script>");
        } catch (Exception e) {
            PrintWriter out_e =response.getWriter();
            out_e.println("<script>");
            out_e.println("alert('修改失败，用户不存在');");
            out_e.println("window.location.href='/login/user.html'");
            out_e.println("</script>");
        }
    }

    @RequestMapping("/changeUserPower")
    //@ResponseBody
    public void changeUserPower(String userName, String power, HttpServletRequest request, HttpServletResponse response)  throws Exception{
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8;");
            userMapper.changeUserPower(userName, power);
            ResultEntity resultEntity = new ResultEntity();
            resultEntity.setResult(true);
            resultEntity.setMessage("注册成功");
            PrintWriter out =response.getWriter();
            out.println("<script>");
            out.println("alert('用户权限修改成功');");
            out.println("window.location.href='/login/power.html'");
            out.println("</script>");
        } catch (Exception e) {
            PrintWriter out_e =response.getWriter();
            out_e.println("<script>");
            out_e.println("alert('修改失败，用户不存在');");
            out_e.println("window.location.href='/login/power.html'");
            out_e.println("</script>");
        }
    }

    @RequestMapping("/check")
    @ResponseBody
    public ResultEntity check() {
        Tb_Power state = userMapper.check();
        ResultEntity result =new ResultEntity();
        result.setResult(true);
        result.setOther("state",state);
        return result;
    }

}



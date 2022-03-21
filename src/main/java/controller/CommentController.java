package controller;

import domain.ResultEntity;
import domain.Tb_Comment;
import domain.Tb_User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.CommentService;
//import service.CommentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @RequestMapping("/tolawCase")
    public String tolawCase() {
        return "display/lawCase";
    }

    @RequestMapping("/list")
    @ResponseBody
    public ResultEntity list10() {
        try {
            int num = 50;
            String user = "";
            List<Tb_Comment> questions = commentService.listAll(num, user);
            ResultEntity resultEntity = new ResultEntity();
            resultEntity.setResult(true);
            resultEntity.setOther("rows", questions);
            return resultEntity;
        } catch (Exception e) {
            return ResultEntity.failure("查询失败");
        }
    }
    @RequestMapping("/listComment")
    @ResponseBody
    public ResultEntity listComment(int num,String user) {
        try {
            if (num==0){
                num=99;
            }
            List<Tb_Comment> questions = commentService.listAll(num,user);
            ResultEntity resultEntity = new ResultEntity();
            resultEntity.setResult(true);
            resultEntity.setOther("rows", questions);
            return resultEntity;
        } catch (Exception e) {
            return ResultEntity.failure("查询失败");
        }
    }

    @RequestMapping("/del")
    @ResponseBody
    public ResultEntity del(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8;");
        int id = Integer.parseInt(request.getParameter("id"));
        Tb_User u = (Tb_User) request.getSession().getAttribute("user");
        Tb_Comment questions = commentService.listid(id);
        ResultEntity resultEntity = new ResultEntity();
        if (questions.getUserName().equals(u.getUserName())) {
            commentService.del(id);
            resultEntity.setResult(true);
            return resultEntity;
        } else {
            return ResultEntity.failure("查询失败");
        }
    }

    @RequestMapping("/add")
    @ResponseBody
    public ResultEntity register(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8;");
            String correct = request.getParameter("correct");
            Tb_Comment t = new Tb_Comment();
            Tb_User u = (Tb_User) request.getSession().getAttribute("user");
            t.setUserName(u.getUserName());
            t.setcorrect(correct);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Date date = new java.util.Date();
            String str = sdf.format(date);
            t.settime(str);
            commentService.save(t);
            ResultEntity resultEntity = new ResultEntity();
            resultEntity.setResult(true);
            return resultEntity;
        } catch (Exception e) {
            return ResultEntity.failure("失败");
        }
    }
}
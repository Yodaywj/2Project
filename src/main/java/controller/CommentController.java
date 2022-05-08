package controller;

import domain.ResultEntity;
import domain.Tb_Case;
import domain.Tb_Comment;
import domain.Tb_User;
import mapper.CommentMapper;
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
            int commentId = -1;
            List<Tb_Comment> questions = commentService.listAll(num, user ,commentId);
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
    public ResultEntity listComment(int num,String user,int commentId) {
        try {
            if (num==0){
                num=99;
            }
            List<Tb_Comment> questions = commentService.listAll(num,user,commentId);
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
        String id = request.getParameter("id");
        String time = request.getParameter("time");
        Tb_User u = (Tb_User) request.getSession().getAttribute("user");
        ResultEntity resultEntity = new ResultEntity();
        if (id.equals(u.getUserName())||u.getAdmin().equals("root")) {
            commentService.del(id,time);
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
            int commentId = Integer.parseInt(request.getParameter("commentId"));
            Tb_Comment t = new Tb_Comment();
            Tb_User u = (Tb_User) request.getSession().getAttribute("user");
            if (u == null) throw new Exception("");
            if (u.getState().equals("禁言")){
                return ResultEntity.failure("该账户已禁言");
            } else {
                t.setUserName(u.getUserName());
                t.setcorrect(correct);
                t.setCommentId(commentId);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                java.util.Date date = new java.util.Date();
                String str = sdf.format(date);
                t.settime(str);
                commentService.save(t);
                ResultEntity resultEntity = new ResultEntity();
                resultEntity.setResult(true);
                return ResultEntity.success("留言成功");
            }
        } catch (Exception e) {
            return ResultEntity.failure("请先登录");
        }
    }
    @RequestMapping("/showNewCases")
    @ResponseBody
    public ResultEntity showNewCases() {
        List<Tb_Case> cases = commentService.showNewCases();
        ResultEntity result = new ResultEntity();
        result.setResult(true);
        result.setOther("cases",cases);
        return result;
    }
}
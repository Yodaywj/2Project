package controller;

import domain.ResultEntity;
import domain.Tb_Question;
import domain.Tb_User;
import mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.QuestionService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionMapper questionMapper;
    @RequestMapping("/answer")
    public void answer(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        String[] list = new String[10];
        String type = request.getParameter("type");
        for (int i = 0; i < 10; i++) {
            list[i] = request.getParameter("answer" + (i + 1));
        }
        String answerA = "A,B,A,B,A,C,C,D,B,A";
        String answerB = "A,C,A,C,C,D,C,C,D,D";
        String answerC = "D,A,D,A,D,C,D,C,A,C";
        String[] answer;
        if (type.equals("q1")) {
            answer = answerA.split(",");
            type = "问答一";
        } else if (type.equals("q2")) {
            answer = answerB.split(",");
            type = "问答二";
        } else {
            answer = answerC.split(",");
            type = "问答三";
        }
        String wrong = "";
        PrintWriter out = response.getWriter();
        int count = 0;
        Tb_User user = (Tb_User) request.getSession().getAttribute("user");
        Tb_Question question = new Tb_Question();
        if (user == null) {
            out.println("请登录后答题");
        }
        else {
                question.setUserName(user.getUserName());
                question.setType(type);
            for (int i = 0; i < 10; i++) {
                if (i == 0) question.setAnswer1(list[i]);
                if (i == 1) question.setAnswer2(list[i]);
                if (i == 2) question.setAnswer3(list[i]);
                if (i == 3) question.setAnswer4(list[i]);
                if (i == 4) question.setAnswer5(list[i]);
                if (i == 5) question.setAnswer6(list[i]);
                if (i == 6) question.setAnswer7(list[i]);
                if (i == 7) question.setAnswer8(list[i]);
                if (i == 8) question.setAnswer9(list[i]);
                if (i == 9) question.setAnswer10(list[i]);
                if (answer[i].equals(list[i])) {
                    count += 1;
                } else {
                    wrong += ((i + 1) + " ");
                }
            }
            question.setCorrect((count * 10) + "%");
            questionService.save(question);
            out.println("第" + wrong + "题错误，共答对" + count + "道题目，正确率为" + (count * 10) + "%,正确答案为：" + Arrays.toString(answer));
        }
    }

    @RequestMapping("/list10")
    @ResponseBody
    public ResultEntity list10() {
        try {
            int num = 15;
            String user = "";
            List<Tb_Question> questions = questionService.listQuestionLimti10(num,user);
            ResultEntity resultEntity = new ResultEntity();
            resultEntity.setResult(true);
            resultEntity.setOther("rows", questions);
            return resultEntity;
        } catch (Exception e) {
            return ResultEntity.failure("查询失败");
        }
    }

    @RequestMapping("/listnum")
    @ResponseBody
    public ResultEntity listnum(HttpServletRequest request, int num) {
        try {
            if (num==0){
                num=99;
            }
            String user =request.getParameter("user");
            List<Tb_Question> questions = questionService.listQuestionLimti10(num,user);
            ResultEntity resultEntity = new ResultEntity();
            resultEntity.setResult(true);
            resultEntity.setOther("rows", questions);
            return resultEntity;
        } catch (Exception e) {
            return ResultEntity.failure("查询失败");
        }
    }

    @RequestMapping("/delete")
    @ResponseBody
    public ResultEntity delete(String time,String userName,HttpServletRequest request) {
        Tb_User user = (Tb_User)request.getSession().getAttribute("user");
        if (!user.getAdmin().equals("普通用户")) {
            questionMapper.deleteQuestion(userName,time);
            return ResultEntity.success("删除成功");
        }else {
            return ResultEntity.failure("非管理员不能删除");
        }
    }
}

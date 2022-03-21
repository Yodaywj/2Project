package service.impl;

import domain.Tb_Question;
import mapper.QuestionMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import service.QuestionService;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class QuestionServiceImpl implements QuestionService {
    @Resource
    private QuestionMapper questionMapper;
    @Override
    public void save(Tb_Question question) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        question.setTime(format.format(new Date()));
        questionMapper.save(question);
    }

    @Override
    public List<Tb_Question> listQuestionLimti10(int num, String user) {
        return questionMapper.listQuestionLimti10(num,user);
    }
}

package service;

import domain.Tb_Question;

import java.util.List;

public interface QuestionService {
    void save(Tb_Question question);
    List<Tb_Question> listQuestionLimti10(int num,String user);
}

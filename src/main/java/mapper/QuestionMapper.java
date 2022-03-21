package mapper;

import domain.Tb_Question;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuestionMapper {
    void save(Tb_Question question);
    List<Tb_Question> listQuestionLimti10(@Param("num") int num,@Param("user") String user);
}

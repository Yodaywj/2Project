package mapper;

import domain.Tb_Case;
import domain.Tb_Question;
import domain.Tb_User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    Tb_User login(String userName);
    void save(@Param("userName") String userName, @Param("password") String password);
    List<Tb_User> select(@Param("num") int num, @Param("user") String user);
    void delete(@Param("id") int id);
    void mute(@Param("id") int id,@Param("state") String state);

    void addCase(@Param("time") String time);

    void editCase(@Param("id") int id, @Param("content") String content);

    void editJudgment(@Param("id") int id, @Param("judgment") String judgment);

    void deleteCase(@Param("id") int id);

    List<Tb_Case> showCase(@Param("num") int num, @Param("keyword") String keyword);

    void setShow(@Param("id") int id, @Param("isShow")String isShow);

    void powerChange(@Param("id") int id, @Param("power")int power);
}

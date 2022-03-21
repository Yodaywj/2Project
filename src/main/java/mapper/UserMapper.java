package mapper;

import domain.Tb_User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    Tb_User login(String userName);
    void save(@Param("userName") String userName, @Param("password") String password);
}

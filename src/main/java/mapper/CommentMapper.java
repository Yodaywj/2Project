package mapper;

import domain.Tb_Comment;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentMapper {
    void save (Tb_Comment t);

    List<Tb_Comment> Findall(@Param("num") int num,@Param("user") String user);

    Tb_Comment selid(int id);

    void del(int id);
}

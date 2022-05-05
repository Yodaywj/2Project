package service;

import domain.Tb_Case;
import domain.Tb_Comment;

import java.util.List;

/**
 * @author
 */
public interface CommentService {

    List<Tb_Comment> listAll(int num,String user);

    void save(Tb_Comment t);

    Tb_Comment listid(int id);

    void del(int id);

    List<Tb_Case> showNewCases();
}

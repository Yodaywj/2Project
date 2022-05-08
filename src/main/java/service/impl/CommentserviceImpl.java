package service.impl;

//import service.CommentService;


import domain.Tb_Case;
import domain.Tb_Comment;
import mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.CommentService;

import java.util.List;

@Service
//@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class CommentserviceImpl implements CommentService {
  //  @Resource
    @Autowired
    private CommentMapper commentMapper;


    @Override
    public List<Tb_Comment> listAll(int num,String user,int commentId) {
        return commentMapper.Findall(num,user,commentId);
    }

  @Override
  public void save(Tb_Comment t) {
    commentMapper.save(t);
  }

  @Override
  public Tb_Comment listid(int id) {
    return commentMapper.selid(id);
  }

  @Override
  public void del(String userName,String time) {
    commentMapper.del(userName,time);
  }

    @Override
    public List<Tb_Case> showNewCases() {
       return commentMapper.showNewCases();
    }
}

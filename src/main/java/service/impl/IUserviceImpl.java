package service.impl;

import domain.Tb_User;
import mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import service.IUserService;

import javax.annotation.Resource;


@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class IUserviceImpl implements IUserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public Tb_User login(String userName) {
        return userMapper.login(userName);
    }

    @Override
    public void save(String userName, String password) {
        userMapper.save(userName, password);
    }
}

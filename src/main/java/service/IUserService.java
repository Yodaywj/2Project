package service;

import domain.Tb_User;


public interface IUserService {
    Tb_User login(String userName);
    void save(String userName, String password);
}

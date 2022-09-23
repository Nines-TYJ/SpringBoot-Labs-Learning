package com.nines.lab42.testdemo.service;

import com.nines.lab42.testdemo.dao.UserDao;
import com.nines.lab42.testdemo.dataobject.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public UserDO get(Integer id) {
        return userDao.selectById(id);
    }

}

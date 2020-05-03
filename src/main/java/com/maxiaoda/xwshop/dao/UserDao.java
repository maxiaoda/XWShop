package com.maxiaoda.xwshop.dao;

import com.maxiaoda.xwshop.generate.User;
import com.maxiaoda.xwshop.generate.UserExample;
import com.maxiaoda.xwshop.generate.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDao {
    private final SqlSessionFactory sqlSessionFactory;

    @Autowired
    public UserDao(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public void insertUser(User user) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            mapper.insert(user);
        }
    }

    public User getUserByTel(String tel) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession(true)) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            UserExample userExample = new UserExample();
            userExample.createCriteria().andTelEqualTo(tel);
            return mapper.selectByExample(userExample).get(0);
        }

    }
}

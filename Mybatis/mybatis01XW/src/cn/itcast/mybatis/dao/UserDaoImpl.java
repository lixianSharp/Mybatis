package cn.itcast.mybatis.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import cn.itcast.mybatis.po.User;
/**
 * 原始dao开发方式
 * @author 贤元
 *
 */
public class UserDaoImpl  implements UserDao{

	private SqlSessionFactory sqlSessionFactory;
	
	/**
	 * 将sqlSessionFactory注入
	 * @param sqlSessionFactory
	 */
	public UserDaoImpl(SqlSessionFactory sqlSessionFactory){
		this.sqlSessionFactory = sqlSessionFactory;
	}
	
	@Override
	public User findUserById(int id) throws Exception {
		//创建sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
		//通过sqlSession操作数据库
		User user = sqlSession.selectOne("test.findUserById", id);
		
		sqlSession.close();
		return user;
	}

	@Override
	public List<User> findUserByUsername(String username) throws Exception {
		//创建sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
		//通过sqlSession操作数据库
		List<User> list = sqlSession.selectList("test.findUserByUsername",username);
		return list;
	}

	@Override
	public void insertUser(User user) throws Exception {
		//创建sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
		//通过sqlSession操作数据库
		sqlSession.insert("test.insertUser", user);
		//进行插入、修改、删除都需要提交
		sqlSession.commit();
		sqlSession.close();
	}

}

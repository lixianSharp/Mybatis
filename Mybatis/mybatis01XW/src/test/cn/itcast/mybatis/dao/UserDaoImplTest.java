package test.cn.itcast.mybatis.dao;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import cn.itcast.mybatis.dao.UserDao;
import cn.itcast.mybatis.dao.UserDaoImpl;
import cn.itcast.mybatis.mapper.UserMapper;
import cn.itcast.mybatis.po.User;
import cn.itcast.mybatis.po.UserCustom;
import cn.itcast.mybatis.po.UserQueryVo;

public class UserDaoImplTest {

	// 会话工厂
	private SqlSessionFactory sqlSessionFactory;

	// 创建工厂
	@Before
	public void init() throws Exception {
		// 配置文件(SqlMapConfig.xml)
		String resource = "SqlMapConfig.xml";
		// 加载配置文件到输入 流
		InputStream inputStream = Resources.getResourceAsStream(resource);
		// 创建会话工厂
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

	}

	@Test
	public void testFindUserById() throws Exception {
		UserDao userDao = new UserDaoImpl(sqlSessionFactory);
		User user = userDao.findUserById(1);
		System.out.println(user);
	}

	// 通过包装类型查询用户信息
	@Test
	public void testFindUserList() throws Exception {

		// 创建sqlSession对象
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 创建代理对象
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		// 构造查询条件
		UserQueryVo userQueryVo = new UserQueryVo();

		UserCustom userCustom = new UserCustom();
		userCustom.setUsername("小明");

		userQueryVo.setUserCustom(userCustom);

		List<User> list = userMapper.findUserList(userQueryVo);

		sqlSession.close();

		System.out.println(list);
		System.out.println("ok");
	}

	// 返回查询记录总数
	@Test
	public void testFindUserCount() throws Exception {
		// 通过sqlSessionFactory创建sqlSession对象
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 通过sqlSession创建代理对象
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

		// 构造查询条件
		UserQueryVo userQueryVo = new UserQueryVo();

		UserCustom userCustom = new UserCustom();
		userCustom.setUsername("小明");

		userQueryVo.setUserCustom(userCustom);

		int count = userMapper.findUserCount(userQueryVo);
		// 关闭sqlSession
		sqlSession.close();
		System.out.println(count);

	}

	// 查询用户，使用resultMap进行映射
	@Test
	public void testfindUserListResultMap() throws Exception {
		// 通过sqlSessionFactory创建sqlSession对象
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 通过sqlSession创建代理对象
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

		// 构造查询条件
		UserQueryVo userQueryVo = new UserQueryVo();

		UserCustom userCustom = new UserCustom();
		userCustom.setUsername("小明");

		userQueryVo.setUserCustom(userCustom);

		List<User> list = userMapper.findUserListResultMap(userQueryVo);
		// 关闭sqlSession
		sqlSession.close();
		System.out.println(list);

	}

	// 查询用户，测试foreach
	@Test
	public void testfindUserListForeach() throws Exception {
		// 通过sqlSessionFactory创建sqlSession对象
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 通过sqlSession创建代理对象
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

		// id集合
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(16);
		ids.add(22);

		// 构造查询条件
		UserQueryVo userQueryVo = new UserQueryVo();
		userQueryVo.setIds(ids);
		
		List<User> list = userMapper.findUserList(userQueryVo);
		System.out.println(list);
	}

}

package test.cn.itcast.mybatis.mapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import cn.itcast.mybatis.mapper.OrdersMapperCustom;
import cn.itcast.mybatis.mapper.UserMapper;
import cn.itcast.mybatis.po.OrderCustom;
import cn.itcast.mybatis.po.Orders;
import cn.itcast.mybatis.po.User;

public class OrdersMapperCustomTest {

	// 会话工厂
	private SqlSessionFactory sqlSessionFactory;

	// 创建工厂
	@Before
	public void init() throws IOException {

		// 配置文件（SqlMapConfig.xml）
		String resource = "SqlMapConfig.xml";

		// 加载配置文件到输入 流
		InputStream inputStream = Resources.getResourceAsStream(resource);

		// 创建会话工厂
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

	}

	@Test
	public void testFindOrderUserList() throws Exception {

		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 创建mapper代理对象
		OrdersMapperCustom ordersMapperCustom = sqlSession
				.getMapper(OrdersMapperCustom.class);

		// 调用方法
		List<OrderCustom> list = ordersMapperCustom.findOrderUserList();

		System.out.println(list);
	}

	// 一对一查询使用resultMap
	@Test
	public void testFindOrderUserListResultMap() throws Exception {

		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 创建mapper代理对象
		OrdersMapperCustom ordersMapperCustom = sqlSession
				.getMapper(OrdersMapperCustom.class);

		// 调用方法
		List<Orders> list = ordersMapperCustom.findOrderUserListResultMap();

		System.out.println(list);
	}

	// 一对多查询使用resultMap
	@Test
	public void testFindOrderAndOrderDetails() throws Exception {

		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 创建mapper代理对象
		OrdersMapperCustom ordersMapperCustom = sqlSession
				.getMapper(OrdersMapperCustom.class);

		// 调用方法
		List<Orders> list = ordersMapperCustom.findOrderAndorderDetail();

		System.out.println(list);
	}

	// 一对多查询使用resultMap
	@Test
	public void testFindUserOrderDetail() throws Exception {

		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 创建mapper代理对象
		OrdersMapperCustom ordersMapperCustom = sqlSession
				.getMapper(OrdersMapperCustom.class);

		// 调用方法
		List<User> list = ordersMapperCustom.findUserOrderDetail();

		System.out.println(list);
	}
	
	//一对一查询 延迟加载
	@Test
	public void testfindOrderUserListLazyLoading()throws Exception{
		//通过sqlSessionFactory创建sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		//创建mapper代理对象
		OrdersMapperCustom ordersMapperCustom = sqlSession.getMapper(OrdersMapperCustom.class);
		//调用方法
		List<Orders> list = ordersMapperCustom.findOrderUserListLazyLoading();
		//这里执行延迟加载，要发出sql
		User user = list.get(0).getUser();
		System.out.println(user);
	}
	
	
	//一级缓存测试
	@Test
	public void testCache1() throws Exception{
		//通过sqlSessionFactory创建sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
		//通过sqlSession创建mapper代理对象
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		//通过mapper进行操作数据库
		//第一次查询用户id为1的用户
		User user = userMapper.findUserById(1);
		System.out.println(user);
		
		//中间修改用户要清空缓存，目的防止查询出脏数据
		/**
		 * 如果两次中间出现commit操作（修改、添加、删除），
		 * 		本sqlsession中的一级缓存区域全部清空，
		 * 		下次再去缓存中查询不到所以要从数据库查询，
		 * 		从数据库查询到再写入缓存。
		 */
		/*user.setUsername("测试用户2");
		userMapper.updateUser(user);
		sqlSession.commit();*/
		//sqlSession.clearCache();

		//第二次查询用户id为1的用户
		User user2 = userMapper.findUserById(1);
		System.out.println(user);
		
		sqlSession.close();//关闭sqlSession
	}
	
	//二级缓存的测试
	@Test
	public void testCache2() throws Exception {
		SqlSession sqlSession1 = sqlSessionFactory.openSession();
		SqlSession sqlSession2 = sqlSessionFactory.openSession();
		SqlSession sqlSession3 = sqlSessionFactory.openSession();
		UserMapper userMapper1 = sqlSession1.getMapper(UserMapper.class);
		UserMapper userMapper2 = sqlSession2.getMapper(UserMapper.class);
		UserMapper userMapper3 = sqlSession3.getMapper(UserMapper.class);
		
		//第一次查询用户id为1的用户
		User user = userMapper1.findUserById(1);
		System.out.println(user);
		sqlSession1.close();
		
		//中间修改用户要清空缓存，目的防止查询出脏数据
		/*user.setUsername("测试用户2");
		userMapper3.updateUser(user);
		sqlSession3.commit();
		sqlSession3.close();*/
		
		//第二次查询用户id为1的用户
		User user2 = userMapper2.findUserById(1);
		System.out.println(user2);
		sqlSession2.close();
	}
	
	
}

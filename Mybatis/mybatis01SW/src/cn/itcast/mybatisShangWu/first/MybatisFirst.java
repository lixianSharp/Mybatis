package cn.itcast.mybatisShangWu.first;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import cn.itcast.mybatisShangWu.po.User;

public class MybatisFirst {
	
	//会话工厂
	private SqlSessionFactory sqlSessionFactory;
	
	//创建工程
	@Before
	public void init()throws Exception{
		//加载配置文件(SqlMapConfig.xml)到输入 流
		InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
		
		//创建会话工厂
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}
	
	
	//测试根据id查询用户(得到单条记录)
	@Test
	public void testFindUserById(){
		//通过sqlSessionFactory创建sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		User user = null;
		/**
		 * 通过sqlSession操作数据库
		 * 第一个参数：statement的位置，等于namespace+statement的id
		 * 第二个参数：传入的参数
		 */
		try {
			user = sqlSession.selectOne("test.findUserById", 1);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//关闭sqlSession
			sqlSession.close();
		}
		
		System.out.println(user);
	}
	
	
	//测试根据用户查询(得到单条记录)
	@Test
	public void testFindUserByName(){
		//通过sqlSessionFactory创建sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		List<User> list = null;
		//通过sqlSession操作数据库
		//第一个参数：statement的位置，等于namespace+statement的id
		try {
			list = sqlSession.selectList("test.findUserByName","张三");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//关闭sqlSession
			sqlSession.close();
		}
		
		System.out.println(list.get(0).getUsername());
		
	}
	
	
	//插入数据，并返回主键
	@Test
	public void testInsertUser(){
		//通过sqlSessionFactory创建sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		//通过sqlSession操作数据库
		//创建插入数据对象
		User user = new User();
		user.setUsername("脸生");
		user.setAddress("福建龙岩");
		user.setBirthday(new Date());
		user.setSex("1");
		
		try {
			int result = sqlSession.insert("test.insertUser", user);
			//需要提交事务
			sqlSession.commit();
			System.out.println(result);//若插入成功，则返回的result的结果为1。
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//关闭sqlSession
			sqlSession.close();
		}
		System.out.println("用户的id="+user.getId());//28
	}
	
	
	//测试根据id删除用户(得到单条记录)
	@Test
	public void testDeleteUser(){
		//通过sqlSessionFactory创建sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
		
		try {
			/**
			 * 通过sqlSession操作数据库
			 * 第一个参数：statement的位置，等于namespace+statement的id
			 * 第二个参数：传入的参数
			 */
			int result = sqlSession.delete("test.deleteUser", 28);
			//需要提交事务
			sqlSession.commit();
			System.out.println(result);//如果更新成功，则返回1
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//关闭sqlSession
			sqlSession.close();
		}
	}
	
	
	//修改 将数据库中user表中的id=27的数据进行修改
	@Test
	public void updateUser(){
		//通过sqlSessionFactory创建sqlSession
		SqlSession sqlSession = sqlSessionFactory.openSession();
		User user = new User();
		user.setId(27);
		user.setBirthday(new Date());
		user.setSex("1");
		user.setUsername("以前以后");
		user.setAddress("福建武平");
		try {
			//通过sqlSession操作数据库
			int result = sqlSession.update("test.updateUser",user);
			//需要提交事务
			sqlSession.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			//关闭sqlSession
			sqlSession.close();
		}
	}
	
	
	
}

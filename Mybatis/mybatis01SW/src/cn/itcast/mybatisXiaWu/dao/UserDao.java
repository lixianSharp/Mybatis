package cn.itcast.mybatisXiaWu.dao;

import java.util.List;

import cn.itcast.mybatisShangWu.po.User;
/**
 * 原始dao开发方式
 * @author 贤元
 *
 */
public interface UserDao {
	/**
	 * 根据id查询用户信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public User findUserById(int id) throws Exception;
	/**
	 * 根据用户名称模糊查询用户列表
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public List<User> findUserByUsername(String username)throws Exception;
	/**
	 * 插入用户
	 * @param user
	 * @throws Exception
	 */
	public void insertUser(User user) throws Exception;
}

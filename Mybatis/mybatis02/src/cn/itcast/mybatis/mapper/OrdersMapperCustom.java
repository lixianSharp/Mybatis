package cn.itcast.mybatis.mapper;

import java.util.List;

import cn.itcast.mybatis.po.OrderCustom;
import cn.itcast.mybatis.po.Orders;
import cn.itcast.mybatis.po.User;

public interface OrdersMapperCustom {
	
	//一对一查询，查询订单关联查询用户
	public List<OrderCustom> findOrderUserList()throws Exception;
	
	//一对一查询，使用resultMap
	public List<Orders> findOrderUserListResultMap()throws Exception;
	
	//一对多查询，使用resultMap
	public List<Orders> findOrderAndorderDetail()throws Exception;
	
	//一对多查询，使用resultMap
	public List<User> findUserOrderDetail()throws Exception;
	
	/**
	 * 一对一查询，延迟加载
	 * @return
	 * @throws Exception
	 */
	public List<Orders> findOrderUserListLazyLoading()throws Exception;
}

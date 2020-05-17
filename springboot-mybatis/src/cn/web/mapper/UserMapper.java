package cn.web.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.web.po.UserPO;

@Mapper
public interface UserMapper{

	/**
	 * 根据主键获取用户信息
	 * @param user 用户编号 主键
	 * @return 用户信息
	 */
	public UserPO findByID(Integer userId);
	
	/**
	 * 插入用户并返回新插入用户的自增主键
	 * @param user
	 * @return
	 */
	public void add(UserPO user);
	
	/**
	 * 根据用户主键删除用户
	 * @param user
	 * @return
	 */
	public int delete(String userId);
	
	/**
	 * 条件查询用户信息
	 * @param user
	 * @return
	 */
	public List<UserPO> find(UserPO user);
	
	/**
	 * 条件查询用户记录数
	 * @param user
	 * @return
	 */
	public Integer findCount(UserPO user);
	
	/**
	 * 根据主键更新用户信息
	 * @param user
	 * @return
	 */
	public int update(UserPO user);
	
	/**
	 * 根据登陆名查询用户信息
	 * @param userNo
	 * @return
	 */
	public UserPO findByUserName(String userName);
	
	
}
 
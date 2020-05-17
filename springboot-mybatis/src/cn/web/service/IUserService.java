package cn.web.service;

import java.util.List;
import cn.web.po.UserPO;
import cn.web.vo.UserVO;

public interface IUserService {

	/**
	 * 用户登录校验
	 * @param username
	 * @param password
	 * @return
	 */
	public UserVO login(String username,String password);

	/**
	 * 根据主键查询用户信息
	 * @param userVO
	 * @return
	 */
	public UserVO findUserByID(String userId);

	/**
	 * 新增用户
	 * @param userVO
	 */
	public void addUser(UserVO userVO);

	/**
	 * 修改用户
	 * @param userVO
	 */
	public void updateUser(UserVO userVO);

	/**
	 * 删除用户
	 * @param userId
	 */
	public void deleteUser(String userId);
	
	
	/**
	 * 根据登陆名与密码查询用户信息
	 * @param userNo
	 * @param password
	 * @return
	 */
	public UserVO findUserByUserName(String userName);
	
	/**
	 * 分页查询用户信息
	 * @param user
	 * @return
	 */
	public List<UserPO> findUsers();
	
	public void trans();
	
}

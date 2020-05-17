package cn.web.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.BeanUtils;
import cn.web.mapper.UserMapper;
import cn.web.po.UserPO;
import cn.web.vo.UserVO;

@Service("userService")
public class UserService implements IUserService {

	@Autowired
	private UserMapper userMapper;
	/**
	 * 用户登录校验
	 * @param username
	 * @param password
	 * @return
	 *
	public UserVO login(String userName,String password){
		System.out.println(" *** service *** "+this);
		UserPO userPO = userMapper.findByUserName(userName);
		//UserVO user = BeanUtils.copyObject(userPO, UserVO.class);
		//return user;
		return null;
	}
	*/
	/**
	 * 根据主键查询用户信息
	 * @param userPO
	 * @return
	 */
	@Transactional(readOnly = true)
	public UserVO findUserByID(String userId){
		UserPO po = userMapper.findByID(Integer.valueOf(userId));
		UserVO user = BeanUtils.copyObject(po, UserVO.class);
		user.setUserId(po.getUserId());
		return user;
	}

	/**
	 * 新增用户
	 * @param userVO
	 */
	public void addUser(UserVO userVO){
		UserPO po = BeanUtils.copyObject(userVO, UserPO.class);
		userMapper.add(po);
	}

	/**
	 * 修改用户
	 * @param userVO
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void updateUser(UserVO userVO){
		UserPO po = BeanUtils.copyObject(userVO, UserPO.class);
		po.setUserId(Integer.valueOf(userVO.getUserId()));
		int i = userMapper.update(po);
	}

	/**
	 * 删除用户
	 * @param userDomain
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteUser(String userId){
		int i = userMapper.delete(userId);
	}
	
	
	/**
	 * 根据登陆名与密码查询用户信息
	 * @param userNo
	 * @param password
	 * @return
	 *
	public UserVO findUserByUserName(String userName){
		UserPO po = userMapper.findByUserName(userName);
		UserVO user = BeanUtils.copyObject(po, UserVO.class);
		return user;
	}

	/**
	 * 查询最近登陆时间
	 *
	public LoginLogVO findByUserId(String userId) {
		LoginLogPO po = loginLogMapper.findByUserId(userId);
		LoginLogVO log = BeanUtils.copyObject(po, LoginLogVO.class);
		return log;
	}

	/**
	 * 新增登陆日志
	 *
	public void add(LoginLogVO log) {
		LoginLogPO po = BeanUtils.copyObject(log, LoginLogPO.class);
		loginLogMapper.add(po);
	}

	*/
	/**
	 * 分页查询用户信息
	 * @param user
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<UserPO> findUsers(){
		//获取第1页，10条内容，默认查询总数count
		//PageHelper.startPage(1, 10);
		//UserPO po = BeanUtils.copyObject(user, UserPO.class);
		//PageHelper.startPage(1, 2);
		//PageHelper.startPage(curPage, pageSize);
		List<UserPO> list = userMapper.find(new UserPO());
		//PageInfo<UserPO> pageInfo = new PageInfo(list);
		return list;
	}
	@Override
	public UserVO login(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public UserVO findUserByUserName(String userName) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Transactional(propagation=Propagation.SUPPORTS,rollbackFor = Exception.class)
	public void trans() {
		System.out.println("1.trans");
		UserVO user = this.findUserByID("200");
		user.setEmail(new Date().toString());
		this.updateUser(user);
		System.out.println("2.update");
		this.deleteUser("1111");
		System.out.println("3.delete");
	}
}

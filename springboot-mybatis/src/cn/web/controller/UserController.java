package cn.web.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.util.GrabRedPacketThread;
import cn.web.po.UserPO;
import cn.web.service.IRedPacketService;
import cn.web.service.IUserService;
import cn.web.service.RedisService;
import cn.web.vo.UserVO;

/**
 * 用户管理
 * @author lenovo
 *
 */
@Controller("user_controller")
@RequestMapping("/user")
public class UserController{
	
	@Autowired
	private RedisService redisService;
	
	private ReentrantLock lock = new ReentrantLock();
	
//	@Autowired
//	JdbcConfig jdbcConfig;
	
	@Autowired
	@Qualifier("userService") /* 一个接口多个实现用name区分*/
	private IUserService userService;
	
	@Resource
	private IRedPacketService redPacketService;
	
	@AuthorityValid(validate=true)
	@Logable
	@RequestMapping("/")
	public String list(Model model,HttpServletResponse response) {
		
		String token = UUID.randomUUID().toString();
		Cookie cookie=new Cookie("token",token);
		cookie.setMaxAge(60*60*24);
		cookie.setPath("/");
		response.addCookie(cookie);
		
		System.out.println(" *** controller *** "+this);
//		System.out.println("jdbcConfig: "+jdbcConfig.toString());
//		System.out.println("token: "+token);
		//单例模式
		//Singleton s = Singleton.getInstance();
		//System.out.println("单例模式：" + s);
		
//		Singleton2 s2 = Singleton2.getInstance();
//		System.out.println("单例模式2：" + s2);
		
//		Singleton3 s3 = Singleton3.getInstance();
//		System.out.println("单例模式3：" + s3);
		
		
		//List<UserPO> list = userService.findUsers();
		List<UserPO> list = null;
		model.addAttribute("userList", list);
		
//		int redPacketId = 1;
//		int userId = 2;
//		redPacketService.grapRedPacket(redPacketId, userId);
		redPacketService.createSmallRedPackets(10000, 2000);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		System.out.println("redPacket start: "+new Date());
		GrabRedPacketThread thread = new GrabRedPacketThread(redPacketService);
		for(int i=0;i<40;i++) {
			Thread t = new Thread(thread);
			t.start();
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("redPacket end: "+new Date());
		
		//RedisShardPoolUtil.setnax("product.num", "101");
		//System.out.println("redis product.num = "+ RedisShardPoolUtil.get("product.num"));
		
		/*
		UserPO user = new UserPO();
		user.setUserId(100);
		user.setUserName("zhangsan");
		
		String key = user.getUserId().toString();
		//默认5秒过期，redis服务器自动清理过期的key
		redisService.set("user", key, user);
		
		UserPO resUser = redisService.get("user", key, UserPO.class);
		System.out.println("resUser: "+resUser.getUserName());
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		resUser = redisService.get("user", key, UserPO.class);
		System.out.println("resUser: "+resUser.getUserName());
		*/
		/*
		lock.lock();
		Long total = Long.valueOf(redisService.get("total"));
		System.out.println("num="+num+",total="+total);
		if(num<=total) {
			Long num = redisService.incr("product.num");
		}else{
			System.out.println("num over!");
		}
		lock.unlock();
		*/
//		for(int i=0;i<100;i++) {
//			CurrentTest thread = new CurrentTest(redisService,lock);
//			thread.start();
//		}
		
		//System.out.println("app3="+ApplicationContextUtil.getApplicationContext());
//		String val = RedisUtil.getInstance().get("product.num");
//		System.out.println("redis product ="+val);
		
//		String val = util.get("product.num");
//		System.out.println("redis2 product ="+val);
		
//		userService.trans();
		
		//return "user/index";
		return "index";
	}
	
	private String getCookieToken(HttpServletRequest request){
		Cookie[]cookies= request.getCookies();
		for(Cookie c:cookies){
			if(c.getName().equals("token")){
				return c.getValue();
			}
		}
		return null;		
	}
	
	private void addCookie(HttpServletResponse response,String token){
		//redisService.set("user","token",user);
		Cookie cookie=new Cookie("token",token);
		cookie.setPath("/");
		cookie.setMaxAge(60*60*24);
		response.addCookie(cookie);
	}
	
	/*
	@RequestMapping("/login")
	@ResponseBody
	public Response login(@RequestParam String userName, HttpServletRequest req)throws CustomException{
		
		ServletContext sc = req.getServletContext();
		WebApplicationContext app = WebApplicationContextUtils.getWebApplicationContext(sc);
		IDataService ds = (IDataService)app.getBean("dataService");
		
		IDataService ds2 = (IDataService)ApplicationContextUtil.getBean("dataService");
		Response r = new Response();
//		try{
			System.out.println(userName);
			UserVO user = userService.login(userName, "a");
			r.setResult(user);
//			String msg = null;
//			if("10".equals(s)){
//				msg = "用户不存在！";
//			}else if("11".equals(s)){
//				msg = "密码错误！";
//			}
//			r.setMsg(msg);
//			System.out.println(s);
//		}catch(RuntimeException e){
//			r.setStatus(false);
//			r.setMsg("登陆后台错误！");
//			System.out.println("出现异常！");
//		}
		
		req.getSession().setAttribute(Const.USER_ID, user.getUserId());
		req.getSession().setAttribute(Const.LOGIN_USER, user);
		
		//用户登陆ip
		user.setLoginIp(NetUtil.getRemoteHost(req));
		return r;
	}
*/
	@RequestMapping("/delete")
	public String delete(@RequestParam("id") String id){
			//String[] arr = ids.split(",");
//			for(int i=0;i<arr.length;i++){
//				userService.deleteUser(arr[i]);
//			}
			userService.deleteUser(id);
			
			//单例模式
//			Singleton s = Singleton.getInstance();
//			System.out.println("单例模式：" + s);
			
//			Singleton2 s2 = Singleton2.getInstance();
//			System.out.println("单例模式2：" + s2);
			
//			Singleton3 s3 = Singleton3.getInstance();
//			System.out.println("单例模式3：" + s3);
			
			return "redirect:/";
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String save(@RequestBody UserVO user){
		System.out.println(" *** controller save *** "+this);
		/*UserVO user = new UserVO();
		user.setEmail(params.get("email"));
		user.setOfficeTel(params.get("phone"));
		user.setUserName(params.get("userName"));
		user.setUserNo(params.get("userNo"));
		
		String userId = params.get("userId");
		if(!(userId == null || "".equals(userId))){
			user.setUserId(Integer.valueOf(params.get("userId")));
			userService.updateUser(user);
		}else{
			user.setPassword("123456");
			userService.addUser(user);
		}
		*/
		return "forward:/";
	}
	
	@AuthorityValid(validate=true)
	@RequestMapping("/{id}")
	public String get(@PathVariable String id, Model model, 
			HttpServletRequest request, HttpServletResponse response){
		String cookieToken=getCookieToken(request);
		System.out.println("token: "+cookieToken);
		if(cookieToken==null){
			return null;
		}else{
//			Users user=redisService.get(UserKeyPrefix.user, token, Users.class);
			/*
			 * 还需要将cookie重新发给浏览器，重新设置token到redis
			 * 以便于刷新过期时间
			 */
			addCookie(response,cookieToken);
		}
		System.out.println(" *** controller *** "+this);
		//UserVO user = userService.findUserByID(id);
		UserVO user = new UserVO();
//		Response result = new Response();
//		result.setResult(user);
//		return result;
		model.addAttribute("user", user);
		//"forward:index";
		//return "redirect:index";
		return "index";
	}
	/*
	@RequestMapping("/export")
	public void export(HttpServletResponse response){
		OutputStream os = null;
		String fileName = "导出用户.xls";
	    try {
			fileName = new String(fileName.getBytes("UTF-8"), "iso8859-1");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} 
		try {
			os = response.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}// 取得输出流   
        response.reset();// 清空输出流   
        response.setHeader("Content-disposition", "attachment; filename=" + fileName);// 设定输出文件头   
        response.setContentType("application/msexcel");// 定义输出类型 
        
        PageInfo<UserPO> page = userService.findUsers(new UserVO(), 1, 10);
        List<UserPO> list = page.getList();
        
        String[] colsHead = new String[]{"用户编号", "用户名称", "邮箱", "电话", "状态"};
        String[] propertys = new String[]{"userNo", "userName", "email", "officeTel", "status"};
        ExportExcelNew exportExcel = new ExportExcelNew("用户信息表", colsHead, propertys, os);  
        exportExcel.write(list);
        try {  
            os.close(); // 关闭流
        } catch (IOException e) {  
            e.printStackTrace();  
            System.out.println("Output   is   closed ");  
        }
        
	}
	
	
	@RequestMapping("/getUserWs/{id}")
	@ResponseBody
	public UserPO getUserWs(@PathVariable(value="id") String id) {
		String url = "http://localhost:8080/rest-cxf/services/ws/user/20";
		RestTemplate restTemplate = new RestTemplate();
		//Map<String, Object> map = new HashMap<String, Object>();
		//map.put("id", id);
		UserPO user = restTemplate.getForObject(url, UserPO.class);
		//UserPO user = restTemplate.getForEntity(url, UserPO.class, map).getBody();
		return user;
	}
	
	@RequestMapping(value="/queryUsersWs", method = RequestMethod.POST)
	@ResponseBody
	public List<UserPO> queryUsersWs(){
		String url = "http://localhost:8080/rest-cxf/services/ws/user/";
		RestTemplate restTemplate = new RestTemplate(); 
		HttpHeaders headers = new HttpHeaders();
		//MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
		MediaType type = MediaType.parseMediaType("application/x-www-form-urlencoded");
		headers.setContentType(type);
//		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
		
		UserPO params = new UserPO();
		params.setUserName("a");
		//JSONObject jsonObj = JSONObject.fromObject(params);
		ObjectMapper mapper = new ObjectMapper();            
		//User类转JSON      
		//输出结果：{"name":"小民","age":20,"birthday":844099200000,"email":"xiaomin@sina.com"}      
		String json = null;
		try {
			json = mapper.writeValueAsString(params);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		HttpEntity<String> formEntity = new HttpEntity<String>(json, headers);
		Page result = restTemplate.postForObject(url, formEntity, Page.class);
		return result.getList();
	}
	*/
}

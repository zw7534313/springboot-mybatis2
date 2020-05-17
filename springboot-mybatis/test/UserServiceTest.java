import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.Boot;
import cn.web.service.IUserService;
import junit.framework.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=Boot.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceTest {

	@Autowired
	private IUserService userService;
	
	@Test
	public void findUsers() {
		List users = userService.findUsers();
		Assert.assertNotNull(users);
		Assert.assertTrue(users.size()>0);
	}
}

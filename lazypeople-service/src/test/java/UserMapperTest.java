

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lazypeople.dao.UserMapper;
import com.lazypeople.entity.User;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class UserMapperTest {

	@Resource
	@Qualifier("userMapper")
	private UserMapper userMapper;

	@Test
	public void testSaveUser() {
//		User u = new User();
//		u.setId("2");
//		u.setName("test2");
//		userMapper.insert(u);
	}

}

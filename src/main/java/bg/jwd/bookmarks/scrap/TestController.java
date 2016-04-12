package bg.jwd.bookmarks.scrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import bg.jwd.bookmarks.constants.ViewsConstants;

@Controller
public class TestController {

	@Autowired 
	SeedDao_Old seedDao;
	
	@RequestMapping(value = "/seed")
	public String seed(){
		seedDao.seedData();
		
		return ViewsConstants.USER_HOME;
	}
}

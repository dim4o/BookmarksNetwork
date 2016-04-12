package bg.jwd.bookmarks.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.annotation.AfterReturning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import bg.jwd.bookmarks.constants.Constants;
import bg.jwd.bookmarks.constants.ViewsConstants;
import bg.jwd.bookmarks.entities.Bookmark;
import bg.jwd.bookmarks.servises.BookmarkService;
import bg.jwd.bookmarks.servises.SearchService;
import bg.jwd.bookmarks.util.UserUtils;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired	
	private BookmarkService bookmarkService;
	
	@Autowired
	private SearchService searchService;
	
	
	/*@Autowired
	private SeedDao_Old seedDao;*/
	
	/*@Secured("ROLE_USER")*/
	/*@PreAuthorize("hasRole('ROLE_USER')")*/
	//@RequestMapping(value = "/myBookmarks", method = RequestMethod.POST)
	/*public String userBookmarks(Model model) {
		
		return "userBookmarks";
	}*/

	/*@ModelAttribute("currentUserUsername")
	public String addCommonObjects(Model model){
		String currentUserUsername = null;
		if(UserUtils.getCurrentUser() != null){
			currentUserUsername = UserUtils.getCurrentUser().getUser().getUsername();
		}
		
		return currentUserUsername;
	}*/
	
	// TODO: extend this
	public String getCurrentUserUsername(){
		String currentUserUsername = null;
		if(UserUtils.getCurrentUser() != null){
			currentUserUsername = UserUtils.getCurrentUser().getUser().getUsername();
		}
		
		return currentUserUsername;
	}
	
	@AfterReturning(pointcut = Constants.AOP_EXECUTION_PATH, returning = "retVal")
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		
		/*if(true) {
            throw new IOException("this is io exception");
        }*/
		//seedDao.seedData();
		
		/*List<User> users = userDao.getUeers();
		
		for (User user : users) {
			List<User> followers = user.getFollowersCollection();
			List<User> folloing = user.getFollowingCollection();
			System.out.println("User: " + user.getUserId());
			System.out.println("Followers:");
			for (User u : followers) {
				System.out.println(u.getUserId());
			}
			System.out.println();
			System.out.println("Follwing:");
			for (User u : folloing) {
				System.out.println(u.getUserId());
			}
		}
		
		User usr = userDao.getUserByUsername("maria");
		
		System.out.println("******** " + usr.getUsername() + " ***********");*/
		
		/*User admin = userDao.getUeers().get(0);
		admin.setAddress("Mars");
		userDao.updateUser(admin);
		
		User userToDelete = userDao.getUeers().get(0);
		userDao.deleteUser(userToDelete);
		*/
		
/*		List<User> users = userDao.getUeers();
		
		User user = users.get(0);
		List<Bookmark> userBookmarks = user.getBookmarks();
		List<Role> userRoles = user.getRoles();
		
		Role role = userRoles.get(1);
		
		List<User> usersWithRoles= role.getUsers();
		
		System.out.println(role.getRoleName() + ":");
		for (User u : usersWithRoles) {
			System.out.println(u.getUsername());
		}
		System.out.println("------------");
		//System.out.println(userBookmarks.get(0).getUrl());
		Keyword key = userBookmarks.get(0).getKeywords().get(2);
		System.out.println(key.getKeyword());
		for (Bookmark b : key.getBookmarks()) {
			System.out.println(b.getUrl());
		}
		System.out.println("------------");
		
		System.out.println("|||||||||||||");
		System.out.println(user.getUsername());
		
		for (User f : user.getFollowersCollection()) {
			System.out.println(f.getUsername());
		}
		System.out.println("************");
		for (User f : user.getFollowingCollection()) {
			System.out.println(f.getUsername());
		}
		
		System.out.println("|||||||||||||");
		
			
		//System.out.println("SIZE: " + userBookmarks.size());
		
		//Bookmark first = userBookmarks.get(0);
		//System.out.println(userBookmarks.get(0).getUrl());
		
		model.addAttribute("userRoles", userRoles);
		model.addAttribute("userBookmarks", userBookmarks);*/
		//model.addAttribute("bookmarks", bookmarkDao.getBookmarks());
		
		//seedData();
		
		return ViewsConstants.USER_HOME;
	}
	
	@AfterReturning(pointcut = Constants.AOP_EXECUTION_PATH, returning = "retVal")
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String search(Model model, @RequestParam("searchTerm") String term) {
		
		model.addAttribute("term", term);
		
		List<Bookmark> bookmarks = searchService.searchBookmarks(term);
		model.addAttribute("bookmarks", bookmarks);
		model.addAttribute("currentUserUsername", this.getCurrentUserUsername());
		
//		if(bookmarks != null){
//			for (Bookmark bookmark : bookmarks) {
//				System.out.println(bookmark.getTitle());
//			}
//		}
		
		return ViewsConstants.SEARCH_RESULTS;
		//return "redirect:/user/bookmarks";
	}
}

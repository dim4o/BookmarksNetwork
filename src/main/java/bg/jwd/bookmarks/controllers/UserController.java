package bg.jwd.bookmarks.controllers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import bg.jwd.bookmarks.constants.UrlContants;
import bg.jwd.bookmarks.constants.ViewsConstants;
import bg.jwd.bookmarks.dto.BookmarkSearchDto;
import bg.jwd.bookmarks.dto.RegisterFormDto;
import bg.jwd.bookmarks.entities.Bookmark;
import bg.jwd.bookmarks.entities.Url;
import bg.jwd.bookmarks.entities.User;
import bg.jwd.bookmarks.enums.VisibilityType;
import bg.jwd.bookmarks.servises.BookmarkService;
import bg.jwd.bookmarks.servises.SearchService;
import bg.jwd.bookmarks.servises.UrlService;
import bg.jwd.bookmarks.servises.UserService;
import bg.jwd.bookmarks.util.UserUtils;

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping(value=UrlContants.USER_CONTROLLER_URL)
public class UserController {
		
	private final Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	private BookmarkService bookmarkService;	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SearchService searchService;
	
	@Autowired
	private UrlService urlService;
	
	/*@ModelAttribute("currentUserUsername")
	public String addCommonObjects(Model model){
		String currentUserUsername = null;
		if(UserUtils.getCurrentUser() != null){
			currentUserUsername = UserUtils.getCurrentUser().getUser().getUsername();
		}
		
		return currentUserUsername;
	}*/
	
	@ModelAttribute
	public void addCommonObjects(Model model){
		
		String currentUserUsername = UserUtils.getCurrentUser().getUser().getUsername();
		model.addAttribute("currentUserUsername", currentUserUsername);
	}
	
	// TODO: extend this
	public String getCurrentUserUsername(){
		String currentUserUsername = null;
		if(UserUtils.getCurrentUser() != null){
			currentUserUsername = UserUtils.getCurrentUser().getUser().getUsername();
		}
		
		return currentUserUsername;
	}
	
	@RequestMapping(value = UrlContants.EDIT_USER_PROFILE_ACTION, method = RequestMethod.GET)
	public String userEditProfile(Model model) {
		
		RegisterFormDto editProfile = new RegisterFormDto();
		model.addAttribute("editProfile", editProfile);	
		
		User currentUser = UserUtils.getCurrentUser().getUser();
		model.addAttribute("user", currentUser);
		model.addAttribute("profileActive", "active");
		
		return ViewsConstants.EDIT_USER_PROFILE;
	}

	@RequestMapping(value = UrlContants.EDIT_USER_PROFILE_ACTION, method = RequestMethod.POST)
	public String userEditProfileAction(
			Model model,
			@Valid @ModelAttribute("editProfile") RegisterFormDto editProfile, 
			BindingResult result,
			WebRequest request,
			Errors errors) {

		if(result.hasErrors()){
			log.debug(result);
			return ViewsConstants.EDIT_USER_PROFILE;
		}
		
		User editedUser = userService.editExistingAccount(editProfile);
		if(editedUser == null){
			model.addAttribute("error", "Username or email already exists.");
			return ViewsConstants.EDIT_USER_PROFILE;
		}
		
		return "redirect:/user/bookmarks";
	}
	
	/*@RequestMapping(value = "/search/{keyword}", method = RequestMethod.GET)
	public String search(@PathVariable(value="keyword") String keyword) {
		List<Bookmark> bookmarks = searchService.experimentalBookmarksSearch(keyword);
		
		return ViewsConstants.USER_HOME;
	}*/
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String search1(
			Model model, 
			@ModelAttribute(value = "searchForm") BookmarkSearchDto searchForm,
			HttpServletRequest request) {
		
		log.info("User bookmarks search started");
		
		List<Bookmark> bookmarks = this.searchService.searchBookmarks(searchForm);
		if(bookmarks != null){
			model.addAttribute("bookmarks", bookmarks);
		} else {
			String referer = request.getHeader("Referer");
			return "redirect:" + referer;
		}
		
		model.addAttribute("bookmarksActive", "active");
		
		return "userBookmarks";

	}

	@RequestMapping(value = UrlContants.USER_PROFILE, method = RequestMethod.GET)
	public String previewUserProfile(
			Model model,
			@PathVariable("username") String username) {

		//String currentUserUsername = UserUtils.getCurrentUser().getUsername();
		User user = userService.getByProperty("username", username);
		Set<User> followings = UserUtils.getCurrentUser().getUser().getFollowingCollection();
		model.addAttribute("profileActive", "active");
		
		if(followings.contains(user)){
			user.setIsFollow(true);
		} else {
			user.setIsFollow(false);
		}

		List<Bookmark> bookmarks = this.bookmarkService.getUserBookmarksWithPagination(1, 10, username, true);
		int totalPageCount = (int) Math.ceil((double)bookmarkService.getCount(username) / 10.0d);
		
		this.chaeckIsSaved(bookmarks);
		
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("bookmarks", bookmarks);
		
		model.addAttribute("user", user);
		model.addAttribute("bookmarks", bookmarks);
		model.addAttribute("currentUserUsername", this.getCurrentUserUsername());

		return ViewsConstants.USER_PROFILE;
	}
	
	
	// "/profile/{username}/page/{pageNum}/from/{totalNum}"
	@RequestMapping(value = UrlContants.USER_PROFILE_PAGINATION_URL, method = RequestMethod.GET)
	public String pagination(
			Model model,
			@PathVariable(value="pageNum") int pageNum,
			@PathVariable(value="totalNum") int totalNum,
			@PathVariable(value="username") String username) {
		
		User user = userService.getByProperty("username", username);
		Set<User> followings = UserUtils.getCurrentUser().getUser().getFollowingCollection();
		if(followings.contains(user)){
			user.setIsFollow(true);
		} else {
			user.setIsFollow(false);
		}
		
		List<Bookmark> bookmarks = this.bookmarkService.getUserBookmarksWithPagination(pageNum, 10, username, true);
		int totalPageCount = (int) Math.ceil((double)bookmarkService.getCount(username) / 10.0d);
		
		this.chaeckIsSaved(bookmarks);
		
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("bookmarks", bookmarks);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("user", user);
	
		return ViewsConstants.USER_PROFILE;
	}
	
	private void chaeckIsSaved(List<Bookmark> bookmarks){
		List<Bookmark> currentUserBookmarks = this.bookmarkService.getAllUserBookmarks(this.getCurrentUserUsername());
		List<Url> currenUserUrls = currentUserBookmarks.stream().map(b -> b.getUrl()).collect(Collectors.toList());
		
		for (Bookmark bookmark : bookmarks) {
			Url url = bookmark.getUrl();
			if(currenUserUrls.contains(url)){
				bookmark.setSaved(true);
			} else {
				bookmark.setSaved(false);
			}
		}
	}
	
	@RequestMapping(value = UrlContants.USER_FOLLOWERS_URL, method = RequestMethod.GET)
	public String followers(Model model) {

		User currentUser = UserUtils.getCurrentUser().getUser();
		Set<User> followers = currentUser.getFollowersCollection();
		Set<User> followings = currentUser.getFollowingCollection();
		
		for (User user : followers) {
			if(followings.contains(user)){
				user.setIsFollow(true);
			} else {
				user.setIsFollow(false);
			}
		}
		
		model.addAttribute("followers", followers);
		model.addAttribute("followersActive", "active");
		
		return ViewsConstants.USER_FOLLOWERS;
	}
	
	@RequestMapping(value = UrlContants.USER_FOLLOWINGS_URL, method = RequestMethod.GET)
	public String followings(Model model) {

		User currentUser = UserUtils.getCurrentUser().getUser();
		//Set<User> followers = currentUser.getFollowersCollection();
		Set<User> followings = currentUser.getFollowingCollection();
		
		log.debug(followings);
		
		for (User user : followings) {
			user.setIsFollow(true);
			/*if(followers.contains(user)){
				user.setIsFollow(true);
			} else {
				user.setIsFollow(false);
			}*/
		}

		model.addAttribute("followings", followings);
		model.addAttribute("followingsActive", "active");
		
		return ViewsConstants.USER_FOLLWINGS;
	}
	
	@RequestMapping(value = UrlContants.USER_FOLLOW_ACTION_URL, method = RequestMethod.GET)
	public String follow(
			HttpServletRequest request, 
			@PathVariable("userId") long userId) {

		User currentUser = UserUtils.getCurrentUser().getUser();
		User userToFollow = this.userService.getByProperty("userId", userId);
		Set<User> followings = currentUser.getFollowingCollection();
		
		if(!followings.contains(userToFollow) && !userToFollow.equals(currentUser)){
			
			followings.add(userToFollow);
			
			for (User user : followings) {
				user.setIsFollow(true);
			}
			//userToFollow.setIsFollow(true); 
			currentUser.setFollowingCollection(followings);
			this.userService.update(currentUser);
		}
		
		String referer = request.getHeader("Referer");

		return "redirect:" + referer;
	}
	
	@RequestMapping(value = UrlContants.USER_UNFOLLOW_ACTION_URL, method = RequestMethod.GET)
	public String unfollow(
			HttpServletRequest request, 
			@PathVariable("userId") long userId) {

		User currentUser = UserUtils.getCurrentUser().getUser();
		User userToUnfollow = this.userService.getByProperty("userId", userId);
		Set<User> followings = currentUser.getFollowingCollection();
		followings.remove(userToUnfollow);
		currentUser.setFollowingCollection(followings);
		this.userService.update(currentUser);

		String referer = request.getHeader("Referer");

		return "redirect:" + referer;
	}
	
	@RequestMapping(value = "/voteUp/{bookmarkId}", method = RequestMethod.GET)
	public String voteUpBookmark(
			HttpServletRequest request, 
			@PathVariable("bookmarkId") long bookmarkId) {
		
		Bookmark bookmark = bookmarkService.getByProperty("bookmarkId", bookmarkId);
		String authorName = bookmark.getAuthor().getUsername();
		
		if(!UserUtils.getCurrentUser().getUsername().equals(authorName)){
			int raiting = bookmark.getRaiting() + 1;
			bookmark.setRaiting(raiting);
			bookmarkService.update(bookmark);
		}

		String referer = request.getHeader("Referer");

		return "redirect:" + referer;
	}
	
	@RequestMapping(value = "/voteDown/{bookmarkId}", method = RequestMethod.GET)
	public String voteDownBookmark(
			HttpServletRequest request, 
			@PathVariable("bookmarkId") long bookmarkId) {
		
		Bookmark bookmark = bookmarkService.getByProperty("bookmarkId", bookmarkId);
		String authorName = bookmark.getAuthor().getUsername();
		
		if(!UserUtils.getCurrentUser().getUsername().equals(authorName)){
			int raiting = bookmark.getRaiting() - 1;
			bookmark.setRaiting(raiting);
			bookmarkService.update(bookmark);
		}
		
		String referer = request.getHeader("Referer");

		return "redirect:" + referer;
	}
	
	// TODO: move to BookmarkController and change
	@RequestMapping(value = "/save/{bookmarkId}", method = RequestMethod.GET)
	public String saveBookmark(
			HttpServletRequest request, 
			@PathVariable("bookmarkId") long bookmarkId,
			RedirectAttributes attributes) {
		
		Bookmark bookmark = bookmarkService.getByProperty("bookmarkId", bookmarkId);
		bookmark.setVisibility(VisibilityType.Private);
		bookmark.setAuthor(UserUtils.getCurrentUser().getUser());
		bookmarkService.add(bookmark);
		
		attributes.addFlashAttribute("successMessage", "Bookmark added successfully!");
		
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}
}
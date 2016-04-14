package bg.jwd.bookmarks.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.AfterReturning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import bg.jwd.bookmarks.constants.Constants;
import bg.jwd.bookmarks.constants.UrlContants;
import bg.jwd.bookmarks.constants.ViewsConstants;
import bg.jwd.bookmarks.dao.BookmarkDao;
import bg.jwd.bookmarks.dto.AddBookmarkFormDto;
import bg.jwd.bookmarks.dto.UserTagDto;
import bg.jwd.bookmarks.entities.Bookmark;
import bg.jwd.bookmarks.entities.Tag;
import bg.jwd.bookmarks.entities.Url;
import bg.jwd.bookmarks.entities.User;
import bg.jwd.bookmarks.enums.VisibilityType;
import bg.jwd.bookmarks.exceptions.UnauthorizeException;
import bg.jwd.bookmarks.security.CurrentUser;
import bg.jwd.bookmarks.servises.BookmarkService;
import bg.jwd.bookmarks.servises.UrlService;
import bg.jwd.bookmarks.util.UserUtils;


@Controller
@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping(value=UrlContants.BOOKMARKS_CONTROLLER_URL)
public class BookmarkController {
	
	private final Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	BookmarkService bookmarkService;
	
	@Autowired
	private UrlService urlService;
	
	@ModelAttribute("currentUserUsername")
	public void addCommonObjects(Model model){
		
		String currentUserUsername = UserUtils.getCurrentUser().getUser().getUsername();
		model.addAttribute("currentUserUsername", currentUserUsername);
		model.addAttribute("bookmarksActive", "active");
		model.addAttribute("bookmarkController", UrlContants.BOOKMARKS_CONTROLLER_URL);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String userBookmarks(Model model) {

		User currentUser = UserUtils.getCurrentUser().getUser();
		String currentUserUsername = currentUser.getUsername();

		List<Bookmark> bookmarks = bookmarkService.getUserBookmarksWithPagination(1, 10, currentUserUsername, false);
		int totalPageCount = (int) Math.ceil((double)bookmarkService.getCount(currentUserUsername) / 10.0d);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("bookmarks", bookmarks);
		model.addAttribute("userTags", currentUser.getTags());
		
		log.debug(totalPageCount);
		
		return ViewsConstants.USER_BOOKMARKS;
	}
	
	@RequestMapping(value = "/page/{pageNum}/from/{totalNum}", method = RequestMethod.GET)
	public String pagination(
			Model model,
			@PathVariable(value="pageNum") int pageNum,
			@PathVariable(value="totalNum") int totalNum) {
		
		String currentUserUsername = UserUtils.getCurrentUser().getUser().getUsername();
		
		List<Bookmark> bookmarks = this.bookmarkService.getUserBookmarksWithPagination(pageNum, 10, currentUserUsername, false);
		long totalUserBookmarksCount = bookmarkService.getCount(currentUserUsername);
		int totalPageCount = (int) Math.ceil((double)totalUserBookmarksCount / 10.0d);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("bookmarks", bookmarks);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("currentPage", pageNum);
		
		log.info("totalUserBookmarksCount: " + totalUserBookmarksCount);
		
		return ViewsConstants.USER_BOOKMARKS;
	}

	@RequestMapping(value = UrlContants.ADD_ACTION, method = RequestMethod.GET)
	public String addBookmark(Model model) {
		AddBookmarkFormDto addBookmarkForm = new AddBookmarkFormDto();
		model.addAttribute("addBookmarkForm", addBookmarkForm);
		model.addAttribute("addAction", UrlContants.ADD_ACTION);
		
		return ViewsConstants.ADD_BOOKMARK;
	}

	@RequestMapping(value = UrlContants.ADD_ACTION, method = RequestMethod.POST)
	@Transactional
	public String doAddBookmark(
			@Valid @ModelAttribute("addBookmarkForm") AddBookmarkFormDto addBookmarkForm,
			BindingResult result,
			WebRequest request,
			Errors errors) {

		try {
			Bookmark bookmarkToadd = null;
			bookmarkToadd = this.bookmarkService.createBookmark(bookmarkToadd, addBookmarkForm);
			String bookmarkUrlLink = bookmarkToadd.getUrl().getLink();
			
			Url urlFromDb = this.urlService.getByProperty("link", bookmarkUrlLink);
			
			/*System.out.println("LINK:");
			System.out.println(url.getLink());*/
			
			if(urlFromDb != null){
				bookmarkToadd.setVisibility(VisibilityType.Private);
				bookmarkToadd.setUrl(urlFromDb);
			} 
			
			this.bookmarkService.add(bookmarkToadd);
			
		} catch (Exception e) {
			log.error("The bookmark cannot be created", e);
		}
		/*if(result.hasErrors()){
			return "AddBookmark";
		}*/
		
		return "redirect:" + UrlContants.BOOKMARKS_CONTROLLER_URL;
	}
	
	@RequestMapping(value = UrlContants.DELETE_BOOKMARK_URL, method = RequestMethod.GET)
	public String userDeleteBookmark(
			Model model,
			@PathVariable(value="bookmarkId") long bookmarkId,
			HttpServletRequest request) {
		
		Bookmark bookmarkToDelete = bookmarkService.getByProperty("bookmarkId", bookmarkId);
		User currentUser = UserUtils.getCurrentUser().getUser();	
		try {
			if(isAuthorizedToEditOrDelete(currentUser, bookmarkToDelete)){
				bookmarkService.safeDeleteBookmark(bookmarkToDelete);
			}
		} catch (UnauthorizeException e) {
			log.error("The user is unauthorized to perform this action", e);
		}
		model.addAttribute("deleteAction", UrlContants.DELETE_BOOKMARK_URL);
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}
	
	@RequestMapping(value = UrlContants.EDIT_BOOKMARK_URL, method = RequestMethod.GET)
	public String editBookmark(
			Model model, 
			@PathVariable(value="bookmarkId") long bookmarkId) {
		
		Bookmark bookmarkToUpdate = bookmarkService.getByProperty("bookmarkId", bookmarkId);
		model.addAttribute("bookmark", bookmarkToUpdate);
		model.addAttribute("editAction", UrlContants.EDIT_BOOKMARK_URL);
		
		return ViewsConstants.EDIT_BOOKMARK;
	}
	
	@RequestMapping(value = UrlContants.EDIT_BOOKMARK_URL, method = RequestMethod.POST)
	public String userEditBookmark(
			@PathVariable(value="bookmarkId") long bookmarkId,
			@ModelAttribute("addBookmarkForm") AddBookmarkFormDto addBookmarkForm) {
		// TODO: bug fix - Encoding on edit
		Bookmark bookmarkToEdit = bookmarkService.getByProperty("bookmarkId", bookmarkId);
		User currentUser = UserUtils.getCurrentUser().getUser();
		
		try {
			if(isAuthorizedToEditOrDelete(currentUser, bookmarkToEdit)){
				this.bookmarkService.editBookmark(bookmarkToEdit, addBookmarkForm);
				this.bookmarkService.update(bookmarkToEdit);
			}
		} catch (UnauthorizeException e) {
			log.error("UnauthorizeException", e);
		} catch (Exception e) {
			log.error(e);
		}

		return "redirect:" + UrlContants.BOOKMARKS_CONTROLLER_URL;
	}
	
	@RequestMapping(value = UrlContants.IMPORT_ACTION_URL)
    public String uploadOneFileHandler(Model model) throws Exception{
        // Forward to "/WEB-INF/pages/uploadOneFile.jsp".
		model.addAttribute("importActive", "active");
		model.addAttribute("importAction", UrlContants.IMPORT_ACTION_URL);
		
        return ViewsConstants.IMPORT_BOOKMARKS;
    }
    
	@AfterReturning(pointcut = Constants.AOP_EXECUTION_PATH, returning = "retVal")
    @RequestMapping(value = UrlContants.IMPORT_ACTION_URL, method = RequestMethod.POST)
    public String uploadFileHandler(
    		HttpServletRequest request, 
    		Model model,
            @RequestParam("file") MultipartFile file,
            @RequestParam("visibility") String visibility) throws Throwable {
 
    	this.bookmarkService.importBookmarks(request, file, visibility);
            
        return "redirect:" + UrlContants.BOOKMARKS_CONTROLLER_URL;
    }
	
    @RequestMapping(value = "/tag/{tagName}", method = RequestMethod.GET)
    public String getUserBookmarksByTag(
    		@PathVariable(value="tagName") String tagName,
    		Model model) {
 
    	User currUser = UserUtils.getCurrentUser().getUser();
    	String useername = currUser.getUsername();
    	List<Bookmark> bookmarks = this.bookmarkService.getUserBookmarksByTag(useername, tagName);
    	
    	/*List<UserTagDto> objects = new ArrayList<>();
		for (Tag tag : currUser.getTags()) {
			long count = bookmarks.stream().filter(b -> b.getTags().contains(tag.getTagName())).count();
			UserTagDto curr = new UserTagDto();
			curr.setTagName(tag.getTagName());
			curr.setBookmarksCount(count);
			objects.add(curr);
		}*/
		
        model.addAttribute("bookmarks", bookmarks);
        model.addAttribute("userTags", currUser.getTags());
        
        return "userBookmarks";
    }
    
    private boolean isAuthorizedToEditOrDelete(User user, Bookmark bookmark) throws UnauthorizeException{
    	String username = user.getUsername();
    	String bookmarkAuthor = bookmark.getAuthor().getUsername();
    	boolean authorized =  username.equals(bookmarkAuthor) || 
				(user.isAdmin() && bookmark.getVisibility().equals(VisibilityType.Public));
    	if(!authorized){
    		throw new UnauthorizeException();
    	}
    	return authorized;
    }
}
package bg.jwd.bookmarks.controllers;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;

import bg.jwd.bookmarks.constants.UrlContants;
import bg.jwd.bookmarks.constants.ViewsConstants;
import bg.jwd.bookmarks.dto.AddUserDto;
import bg.jwd.bookmarks.dto.RegisterFormDto;
import bg.jwd.bookmarks.dto.UserSearchDto;
import bg.jwd.bookmarks.entities.Role;
import bg.jwd.bookmarks.entities.User;
import bg.jwd.bookmarks.servises.RoleService;
import bg.jwd.bookmarks.servises.SearchService;
import bg.jwd.bookmarks.servises.UserService;
import bg.jwd.bookmarks.util.UserUtils;

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping(value=UrlContants.ADMIN_CONTROLLER_URL)
public class AdminController {
	
	private final Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private SearchService searchService;
	
	@ModelAttribute
	public void addCommonObjects(Model model){
		/*model.asMap().clear();*/
		String currentUserUsername = UserUtils.getCurrentUser().getUser().getUsername();
		model.addAttribute("currentUserUsername", currentUserUsername);
		model.addAttribute("adminActive", "active");
	}
	
	@RequestMapping(value = UrlContants.USERS_URL, method = RequestMethod.GET)
	public String AllUsers(Model model) {
		
		List<User> users = userService.getAll();
		model.addAttribute("users", users);
		
		return ViewsConstants.ALL_USERS;
	}
	
	@RequestMapping(value = UrlContants.EDIT_USER_ACTION_URL, method = RequestMethod.GET)
	public String editUserDetails(Model model, @PathVariable(value="userId") long userId) {
		User user = userService.getByProperty("userId", userId);
		List<Role> roles = roleService.getAll();
		List<Role> userRoles = user.getRoles();
		roles.removeAll(userRoles);
		
		model.addAttribute("roles", roles);
		model.addAttribute("user", user);

		return ViewsConstants.EDIT_USER;
	}
	
	@RequestMapping(value = UrlContants.EDIT_USER_ADD_ROLE_ACTION_URL, method = RequestMethod.POST)
	public String addUserRole(
			Model model,
			@RequestParam("role") String role, 
			@PathVariable(value="userId") long userId) 
	{
		User user = userService.getByProperty("userId", userId);
		Role roleToAdd = roleService.getByProperty("roleName", role);
		user.getRoles().add(roleToAdd);
		userService.update(user);

		return "redirect:" + UrlContants.EDIT_USER_ACTION_REDIRECT_URL;
	}
	
	@RequestMapping(value = UrlContants.DELETE_ROLE_ACTION_URL)
	public String deleteUserRole(
			@PathVariable(value="roleName") String roleName, 
			@PathVariable(value="userId") long userId) 
	{
		User user = userService.getByProperty("userId", userId);
		
		if(!roleName.equals("user")){
			user.getRoles().remove(new Role(roleName));
			userService.update(user);
		}

		return "redirect:" + UrlContants.EDIT_USER_ACTION_REDIRECT_URL;
	}
	
	@RequestMapping(value = UrlContants.EDIT_USER_ACTION_URL, method = RequestMethod.POST)
	public String editUserDetailsAction(Model model) {
		
		return "redirect:/admin/users";
	}
	
	@RequestMapping(value = UrlContants.ENABLE_USER_ACTION_URL, method = RequestMethod.GET)
	public String enableUser(@PathVariable("userId") long userId) {
		
		User user = userService.getByProperty("userId", userId);
		user.setEnabled(true);
		userService.update(user);
		
		return "redirect:" + UrlContants.EDIT_USER_ACTION_REDIRECT_URL;
	}
	
	@RequestMapping(value = UrlContants.DISABLE_USER_ACTION_URL, method = RequestMethod.GET)
	public String disableUser(@PathVariable("userId") long userId) {

		User user = userService.getByProperty("userId", userId);
		user.setEnabled(false);
		userService.update(user);
		
		return "redirect:" + UrlContants.EDIT_USER_ACTION_REDIRECT_URL;
	}
	
	@RequestMapping(value = "/users/search", method = RequestMethod.POST)
	public String searchUser(
			Model model, 
			@ModelAttribute("userSearch") UserSearchDto userSearch) {
		//List<User> users = userService.getAllByProperty("username", searchTerm);
		List<User> users = searchService.userSearch(userSearch);
		model.addAttribute("users", users);

		return ViewsConstants.ALL_USERS;
	}
	
	@RequestMapping(value = UrlContants.ADD_USER_ACTION_URL, method = RequestMethod.GET)
	public String addUser(Model model) {

		/*List<String> roles = this.roleService.getAll().stream()
				.map(r -> r.getRoleName()).collect(Collectors.toList());;*/
		AddUserDto addUserForm = new AddUserDto();
		model.addAttribute("addUser", addUserForm);
		
		List<Role> roles = this.roleService.getAll();
		model.addAttribute("roles", roles);
		
		return ViewsConstants.ADD_USER;
	}
	
	@RequestMapping(value = UrlContants.ADD_USER_ACTION_URL, method = RequestMethod.POST)
	public String addUserAction(
			Model model,
			@Valid @ModelAttribute(value="addUser") AddUserDto addUserForm,
			BindingResult result,
			Errors errors) {
		
		List<Role> existingRoles = this.roleService.getAll();
		model.addAttribute("roles", existingRoles);
		
		User user = new User(); 
		if(!result.hasErrors()){
			user = createAccount(addUserForm, result);
			user.setEnabled(addUserForm.getStatus());
			List<Role> roles = user.getRoles();
			
			// TODO: Optimize process
			if(addUserForm.getRoles() != null){
				for (String role : addUserForm.getRoles()) {
					if(!role.equals("user")){
						Role roleToAdd = roleService.getByProperty("roleName", role);
						roles.add(roleToAdd);
					}
				}
			}
			
			user.setRoles(roles);
			userService.update(user);
			
			return "redirect:" + UrlContants.ALL_USERS_URL;
		} else {
			return ViewsConstants.ADD_USER;
		}
	}
	
	private User createAccount(RegisterFormDto userDto, BindingResult result) {
		User registred = null;
		try {
			registred = userService.registerNewUserAccount(userDto);
		} catch(Exception e){
			log.error("Your description here", e);
			e.printStackTrace();
			return null;
		}
		
		return registred;
	}
}

/*System.out.println(addUser.getUsername());
System.out.println(addUser.getStatus());
System.out.println(Arrays.toString(addUser.getRoles().toArray()));*/

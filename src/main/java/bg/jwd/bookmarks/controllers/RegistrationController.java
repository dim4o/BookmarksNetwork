package bg.jwd.bookmarks.controllers;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import bg.jwd.bookmarks.constants.UrlContants;
import bg.jwd.bookmarks.constants.ViewsConstants;
import bg.jwd.bookmarks.dto.LoginFormDto;
import bg.jwd.bookmarks.dto.RegisterFormDto;
import bg.jwd.bookmarks.entities.User;
import bg.jwd.bookmarks.servises.UserService;

@Controller
@RequestMapping(value=UrlContants.REGISTER_CONTROLLER_URL)
@PreAuthorize("hasRole('ROLE_ANONYMOUS')")
public class RegistrationController {
	
	private final Log log = LogFactory.getLog(this.getClass());
	
	@RequestMapping
	public void addCommonObjects(Model model){
		model.addAttribute("registerController", UrlContants.REGISTER_CONTROLLER_URL);
	}
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String register(Model model) {
		
		RegisterFormDto registerForm = new RegisterFormDto();
		model.addAttribute("registerForm", registerForm);	
		model.addAttribute("registerActive", "active");
		
		return ViewsConstants.REGISTER;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String registerUserAccount(
			Model model,
			@Valid @ModelAttribute("registerForm") RegisterFormDto registerForm, 
			BindingResult result,
			WebRequest request,
			Errors errors,
			RedirectAttributes attributes) {
		
		log.info("Register process started");
		@SuppressWarnings("unused")
		User registred = new User();
		if(!result.hasErrors()){
			registred = createAccount(registerForm, result);
			
			attributes.addFlashAttribute("successMessage", "Successfully registered. Please Login.");
			return "redirect:" + UrlContants.LOGIN_CONTROLLER_URL;
		} else {
			return ViewsConstants.REGISTER;
		}
		/*if(registred == null){
			result.rejectValue("email", "message.regError");
		}*/

		//return ViewsConstants.USER_HOME;
	}

	private User createAccount(RegisterFormDto userDto, BindingResult result) {
		User registred = null;
		try {
			registred = userService.registerNewUserAccount(userDto);
		} catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
		return registred;
	}
}	

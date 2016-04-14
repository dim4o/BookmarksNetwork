package bg.jwd.bookmarks.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.annotation.AfterReturning;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import bg.jwd.bookmarks.constants.Constants;
import bg.jwd.bookmarks.constants.UrlContants;
import bg.jwd.bookmarks.constants.ViewsConstants;
import bg.jwd.bookmarks.dto.LoginFormDto;

@Controller
@RequestMapping(value=UrlContants.LOGIN_CONTROLLER_URL)
@PreAuthorize("hasRole('ROLE_ANONYMOUS')") // TODO: not working ???
public class LoginController {
	
	private final Log log = LogFactory.getLog(this.getClass());
	
	@ModelAttribute("loginController")
	public void addCommonObjects(Model model){
		model.addAttribute("loginController", UrlContants.LOGIN_CONTROLLER_URL);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String login(Model model) {
		LoginFormDto loginForm = new LoginFormDto();
		model.addAttribute("loginForm", loginForm);
		model.addAttribute("loginActive", "active");

        return ViewsConstants.LOGIN;
	}
	
	@AfterReturning(pointcut = Constants.AOP_EXECUTION_PATH, returning = "retVal")
	@RequestMapping(method = RequestMethod.POST)
	public String doLogin(
			@Valid @ModelAttribute("loginForm") LoginFormDto loginForm,
            BindingResult result, 
            Map<String, Object> model) {

		log.info("Login process started");
		if (result.hasErrors()) {	
            return ViewsConstants.LOGIN;
        }
 
        return "redirect:/user/bookmarks";
	}
}

/*@RequestMapping(value = "/login", method = RequestMethod.GET)
public ModelAndView loginPost(
		@RequestParam(value = "error", required = false) String error,
		@RequestParam(value = "logout", required = false) String logout) {
	
	ModelAndView model = new ModelAndView();
	if (error != null) {
		model.addObject("error", "Invalid username and password!");
	}

	if (logout != null) {
		model.addObject("msg", "You've been logged out successfully.");
	}
	model.setViewName("login");

	return model;
}*/
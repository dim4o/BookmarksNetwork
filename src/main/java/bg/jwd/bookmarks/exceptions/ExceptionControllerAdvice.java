package bg.jwd.bookmarks.exceptions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
 
@ControllerAdvice(basePackages = {"bg.jwd.bookmarks.controllers"})
public class ExceptionControllerAdvice {
 
	private final Log log = LogFactory.getLog(this.getClass());

    @ExceptionHandler(Exception.class)
    public ModelAndView exception(Exception e) {
         
    	//log.error(e);
        e.printStackTrace();
        
        ModelAndView mav = new ModelAndView("exception");
        mav.addObject("name", e.getClass().getSimpleName());
        mav.addObject("message", e.getMessage());
 
        return mav;
    }
}
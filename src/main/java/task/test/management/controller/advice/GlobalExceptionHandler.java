package task.test.management.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ModelAndView runtimeExceptionHandler(RuntimeException ex){
        ModelAndView mav = new ModelAndView("errorPage.html");
        mav.setStatus(HttpStatus.BAD_REQUEST);
        mav.addObject("error", ex.getClass().getSimpleName());
        mav.addObject("message", ex.getMessage());

        return mav;
    }
}

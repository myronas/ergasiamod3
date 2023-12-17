package gr.hua.dit.ds.ergasia.handlers;

import gr.hua.dit.ds.ergasia.exception.DuplicateItemException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        logger.error("An error occurred: ", e);
        model.addAttribute("error", "An unexpected error occurred: " + e.getMessage());
        return "errorPage"; // Replace with your error page template
    }
    @ExceptionHandler(DuplicateItemException.class)
    public String handleDuplicateItemException(DuplicateItemException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        return "redirect:/items/new";
    }
}

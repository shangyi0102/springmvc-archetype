#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import beans.Person;
import utils.IDGenerator;
import validators.PersonValidator;
/**
 * 
 * @author Administrator
 * 请求地址需要加上 /{分发servlet名称}/
 */
@Controller
public class HelloWorldController {
	private static final Logger log = Logger.getLogger(HelloWorldController.class);
	
	@Autowired
	private Environment env;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private IDGenerator ids;
	
	
	@InitBinder({"select"})
	public void initSelect(WebDataBinder binder) {
		binder.addValidators(new PersonValidator());
	}
	
	private String getMsg(ObjectError e, Locale locale) {
    	String msg;
    	try {
    		msg = messageSource.getMessage(e.getCode(), e.getArguments(), locale);
    	} catch (NoSuchMessageException error) {
    		msg = e.getDefaultMessage();
    	}
    	return msg;
    }
	
	// 
    @RequestMapping("/helloWorld")
    public String helloWorld(@Validated @ModelAttribute("person") Person person, BindingResult res, Model model) {
    	if (res.hasErrors()) {
    		if (!res.getAllErrors().isEmpty()) {
    			for (ObjectError e : res.getAllErrors()) {
    				log.debug(getMsg(e, null));
    			}
    		}
    	} else {
    		
    	}
    	
        model.addAttribute("message", "Hello World!");
        return "index";
    }
    
    @RequestMapping("/helloJSON")
    public @ResponseBody Person helloJSON(@Validated @ModelAttribute("person") Person person, BindingResult res, Model model) {
    	if (res.hasErrors()) {
    		if (!res.getAllErrors().isEmpty()) {
    			for (ObjectError e : res.getAllErrors()) {
    				log.debug(getMsg(e, null));
    			}
    		}
    	} else {
    		
    	}
    	
        model.addAttribute("message", "Hello World!");
        return person;
    }
    
    @RequestMapping(value="/helloFile")
    public @ResponseBody String helloWorld(@RequestParam("file") MultipartFile file) throws IOException {
    	BufferedImage read = ImageIO.read(file.getInputStream());
    	String name = ids.getIDStr() + ".png";
    	File path = new File("/imgs");
    	if (!path.exists()) {
    		path.mkdir();
    	}
    	File file2 = new File(path, name);
    	if (file2.createNewFile()) {
    		ImageIO.write(read, "png", file2);
    	} else {
    		log.error("upload Failed");
    	}
//        model.addAttribute("message", "Hello World!");
        return name;
    }
}

package cn.springmvc.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author johsnon
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/user")
public class UserController {
	
}

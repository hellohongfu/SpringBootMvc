package hello.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import hello.model.*;

@RestController
public class RestServicesController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

  

    @RequestMapping("/getuser")
    public SysUser greeting(@RequestParam(value="name", defaultValue="World") String name) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 
        if (principal instanceof SysUser) {
            String username = ((SysUser)principal).getUsername();
            return (SysUser)principal;
        } else {
            String username = principal.toString();
            return null;
        }
        
    }
}
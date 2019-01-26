package com.demo.spring.controller;

import com.demo.spring.domain.User;
import com.demo.spring.domain.UserSearchForm;
import com.demo.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    UserService userService;
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerView(Model model)
    {
        User user = new User();
        model.addAttribute("user",user);
        return "user/register";
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
   // @ResponseBody
    public String registerView(Model model, @Valid @ModelAttribute("user") User user, BindingResult bindingResult)

    {
        if (bindingResult.hasErrors())
        {

            model.addAttribute("user",user);
            model.addAttribute("message", "please provide information in each field.");
            return"user/register";
        }
        userService.save(user);
        return "redirect:/";
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginView(Model model)
    {
        LoginForm user = new LoginForm();
        model.addAttribute("user",user);
        return "user/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    // @ResponseBody
    public String login(Model model, @Valid @ModelAttribute("user") LoginForm user, BindingResult bindingResult,
                        HttpSession session)

    {
        if (bindingResult.hasErrors())
        {

            model.addAttribute("user",user);
            model.addAttribute("message", "please provide information in each field.");
            return"user/login";
        }

        if (userService.validateLogin(user)==false)
        {

            model.addAttribute("user", user);
            model.addAttribute("message", "Your account name and password are invalid.");
            return "user/login";


        }
        session.setAttribute("login",true);
        return "redirect:/";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(Model model,HttpSession session)
    {
        session.removeAttribute("login");

        return "redirect:/user/login";

    }

    @RequestMapping(value = "/search" , method = RequestMethod.GET)
    public String searchView(Model model)

    {
        UserSearchForm searchForm = new UserSearchForm();
        model.addAttribute("searchCriteria", searchForm);
        return "user/search";

    }
    @RequestMapping(value = "/search" , method = RequestMethod.POST)
    public String searchView(Model model, @ModelAttribute("searchCriteria") UserSearchForm searchForm)
    {
        List<User> users = userService.searchUsers((searchForm));
        model.addAttribute("searchCriteria", searchForm);
        model.addAttribute("users",users);
        return "user/search";
    }

    @RequestMapping(value = "/update/{u}",method = RequestMethod.GET)
    public String updateView(Model model, @PathVariable User u)
    {
        model.addAttribute("user", u);
        return "user/update:/";
    }


    @RequestMapping(value = "/update/{u}",method = RequestMethod.POST)
    public String update(@ModelAttribute("user") User u)
            {
        return "redirect:/";
    }

    @RequestMapping(value ="/delete/{user}", method = RequestMethod.GET)
  //  @ResponseBody
    public String delete(@PathVariable User user)
    {
    String name = user.getFirstname()+" "+user.getLastname();

    userService.delete(user);

    return "redirect:/";
    }
}

package org.asion.account.web;

import org.asion.account.domain.model.Account;
import org.asion.account.domain.application.AccountManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * user controller.
 * @author Asion.
 * @since 16/5/29.
 */
@Controller
@RequestMapping("/user/")
public class AccountController {

    @Autowired
    private AccountManager accountManager;

    @RequestMapping("list")
    public ModelAndView list() {
        Iterable<Account> accounts = accountManager.findAll();
        return new ModelAndView("user/list", "users", accounts);
    }

    @RequestMapping("{id}")
    public ModelAndView view(@PathVariable("id") Long id) {
        Account account = accountManager.findOne(id);
        return new ModelAndView("user/view", "user", account);
    }

    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(@ModelAttribute Account account) {
        return "user/form";
    }

    @RequestMapping(value = "create",method = RequestMethod.POST)
    public ModelAndView create(@Valid Account account, BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return new ModelAndView("user/form", "formErrors", result.getAllErrors());
        }
        account = accountManager.save(account);
        redirect.addFlashAttribute("globaluser", "Successfully save a new user");
        return new ModelAndView("redirect:/user/{user.id}", "user.id", account.getId());
    }

    @RequestMapping("foo")
    public String foo() {
        throw new RuntimeException("Expected exception in controller");
    }

    @RequestMapping(value = "delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        accountManager.delete(id);
        Iterable<Account> accounts = accountManager.findAll();
        return new ModelAndView("user/list", "users", accounts);
    }

    @RequestMapping(value = "modify/{id}", method = RequestMethod.GET)
    public ModelAndView modifyForm(@PathVariable("id") Long id) {
        Account account = accountManager.findOne(id);
        return new ModelAndView("user/form", "user", account);
    }

}

package com.example.demo.controller;


import com.example.demo.model.login.Userr;
import com.example.demo.model.login.password.PasswordDTO;
import com.example.demo.model.login.token.PasswResetToken;
import com.example.demo.model.login.token.VerifToken;
import com.example.demo.service.EmailSender;
import com.example.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

@Controller
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
//    @Qualifier("emailSender")
    private EmailSender emailSender;
    @Autowired
    private UserService userService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping(value = "/login")
    public String showPageLogin(Model model) {
        Userr user = new Userr();
        model.addAttribute("user", user);
        return "login";
    }

    @GetMapping(value = "/successRegister")
    public String showPageSuccessRegister() {
        return "successRegister";
    }

    @GetMapping(value = "/registration")
    public String showPageRegistration(Model model, Userr userr) {
        model.addAttribute("user", userr);
        return "registration";
    }

    @GetMapping(value = "/forgotPassword")
    public String showPageForgotPassword(Model model, Userr userr) {
        return "forgotPassword";
    }

    @PostMapping(value = "/forgotPassword")
    public String sendEmailForgotPassword(@ModelAttribute("email") String userEmail, HttpServletRequest request
            , Model model) {
        logger.warn("in forgot password method " + userEmail);
        Userr user = userService.findUserByEmail(userEmail);
        if (user == null) {
            model.addAttribute("noEmail", messageSource.getMessage("message.noUser",
                    null, request.getLocale()));
            logger.warn("user not exitst, in forgotPassword method");
            return "forgotPassword";
        } else logger.warn("email exist");
        String resetToken = UUID.randomUUID().toString();
        userService.createPasswResetToken(user, resetToken);
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() +
                request.getContextPath();
        String text = ("To confirm change password click link below: \n" + url +
                "/resetPassword?token=" + resetToken);
        emailSender.sendEmail("change password email", text);
//        model.addAttribute("sendOk", messageSource.getMessage("message.ok", null, request.getLocale()));
        return "redirect:/successRegister";
    }

    @GetMapping(value = "/resetPassword")
    public String emailConfirmChangePassword(@RequestParam("token") String resetToken,
                                             HttpServletRequest request, Model model) {
        PasswResetToken token = userService.getPasswResetToken(resetToken);
        logger.warn("in method email confirmation of reseting password");
        Locale locale = request.getLocale();
        if (token == null) {
            String message = messageSource.getMessage("token.invalid", null, locale);
            model.addAttribute("invalidToken", message);
            return "login";
        }
        Calendar calendar = Calendar.getInstance();
        if (token.getEndOfTime().getTime() - calendar.getTime().getTime() <= 0) {
            model.addAttribute("invalidToken", messageSource.getMessage("token.expired", null, locale));
            return "login";
        }
        Userr user = token.getUser();
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null,
                userDetailsService.loadUserByUsername(user.getEmail()).getAuthorities());
//        final Authentication auth = new UsernamePasswordAuthenticationToken(user, null,
//                Arrays.asList(new SimpleGrantedAuthority("USER")));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return "redirect:/updatePassword";
//        return "updatePassword";
    }

    @GetMapping(value = "/updatePassword")
//    @PreAuthorize("hasRole('ADMIN')")
    public String showUpdatePasswordPage(Model model) {
        logger.warn("in method update showUpdatePasswordPage GET");
        model.addAttribute("passwordDTO", new PasswordDTO());
        return "updatePassword";
    }

    @PostMapping(value = "/saveResetPassword")
    public String changePassword(@Valid PasswordDTO passwordDTO, BindingResult result, HttpServletRequest request) {
        logger.warn("in method saveResetPassword POST");
        if (result.hasErrors()) {
            return "updatePassword";
        }
        Userr user = (Userr) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.changeUserPassword(user, passwordDTO);
        return "redirect:/login";
    }

    @PostMapping(value = "/registration")
    public String createUserAcountAndSendConfirmEmail(@ModelAttribute("user") @Valid Userr user, BindingResult result,
                                                      HttpServletRequest request) {
        if (result.hasErrors()) { return "registration";}
       /* Zxcvbn zxcvbn = new Zxcvbn();
        Strength strength = zxcvbn.measure(user.getPassword());
        if (strength.getScore() < 1) {
            String message = messageSource.getMessage("message.strengthPassw", null, request.getLocale());
            result.rejectValue("password", "err", message);
//            result.rejectValue("password", "err", "password too weak from controller");
            return "registration";
        }*/
        Userr existUser = userService.findUserByEmail(user.getEmail());
        if (existUser != null) {
            String message = messageSource.getMessage("message.userExist", null, request.getLocale());
            result.rejectValue("email", "err", message);
            return "registration";
        }
        userService.createUserAcount(user);
        String token = UUID.randomUUID().toString();
        userService.createToken(user, token);
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() +
                request.getContextPath();
        String text = ("To confirm registration click link below: \n" + url + "/confirm?token=" + token);
        emailSender.sendEmail("confirmation mail", text);
        return "redirect:/successRegister";
    }

    @GetMapping(value = "/user/changePassword")
    public String showChangePasswordPage(Model model) {

//        final Authentication auth = new UsernamePasswordAuthenticationToken(user, null,
//                Arrays.asList(new SimpleGrantedAuthority("USER")));
//        SecurityContextHolder.getContext().setAuthentication(auth);
        model.addAttribute("passwordDTO", new PasswordDTO());
        return "changePassword";
    }

    @GetMapping(value = "/confirm")
    public String confirmRegistration(@RequestParam("token") String verifToken, Model model, HttpServletRequest request) {
        VerifToken token = userService.getVerifToken(verifToken);
        Locale locale = request.getLocale();
        if (token == null) {
            String message = messageSource.getMessage("token.invalid", null, locale);
            model.addAttribute("invalidToken", message);
            return "login";
        }
        Userr user = token.getUser();
        Calendar calendar = Calendar.getInstance();
        if (token.getEndOfTime().getTime() - calendar.getTime().getTime() <= 0) {
            String message = messageSource.getMessage("token.expired", null, locale);
            model.addAttribute("invalidToken", message);
            model.addAttribute("token", token.getToken());
            model.addAttribute("expired", true);
            return "login";
        }
        user.setActive(true);
        userService.saveRegisteredUser(user);
        String message = messageSource.getMessage("message.allowEnter", null, request.getLocale());
        model.addAttribute("enter", message);
//        return "login";
        return "redirect:/successRegister";
    }

    @GetMapping(value = "/resendRegistrationToken")
    public String resendToken(@RequestParam("token") String oldToken, HttpServletRequest request) {
        VerifToken newToken = userService.createNewToken(oldToken);
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() +
                request.getContextPath();
        String text = ("To confirm REregistration click link below: \n" + url + "/confirm?token=" + newToken.getToken());
        emailSender.sendEmail("confirmation mail", text);
        return "redirect:/successRegister";
    }

    @PostMapping(value = "/user/saveChangedPassword")
    public String saveChangedPassword(@RequestParam("oldPassword") String oldPassword, @Valid PasswordDTO passwordDTO,
                                      BindingResult result, HttpServletRequest request) {
        logger.warn("in method saveChangedPassword POST");
        if (result.hasErrors()) {
            return "changePassword";
        }
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Userr user = userService.findUserByEmail(email);
        if (!userService.checkIfPasswordMatches(user, oldPassword)) {
            return "changePassword";
        }
        userService.changeUserPassword(user, passwordDTO);
        logger.warn("password has changed");
        return "redirect:/login";
    }

    @GetMapping(value = "/user/deleteAcount")
    public String deleteAcount() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Userr user = userService.findUserByEmail(email);
        userService.deleteAcount(user);
        SecurityContextHolder.getContext().getAuthentication().getAuthorities().clear();
        logger.warn("delete complete successfully");
        return "redirect:/login";
    }
}

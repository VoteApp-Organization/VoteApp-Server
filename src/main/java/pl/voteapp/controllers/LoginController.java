package pl.voteapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginController {
//    @RequestMapping(path = {"/login", "/login?{error}"}, method = RequestMethod.GET)
//    public String loginPanel(Model model, @RequestParam("error") Optional<String> error){
//        model.addAttribute("user", new User());
//        if(error != null){
//            if(!error.isEmpty())
//                model.addAttribute("error", "Zly nick lub haslo");
//        }
//        return "login";
//    }
}

package io.github.hengyunabc.sample;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author hengyunabc 2017-07-29
 *
 */
@Controller
public class MainController {
    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("date", new Date().toString());
        return "home";
    }

	@RequestMapping("/hello")
	public String helloWorld(Model model) {
		model.addAttribute("name", "中文");
		return "hello";
	}

	@RequestMapping("/hello-from-common")
	public String helloFromCommon(Model model) {
		model.addAttribute("name", "hello-from-common");
		return "hello-from-common";
	}
}

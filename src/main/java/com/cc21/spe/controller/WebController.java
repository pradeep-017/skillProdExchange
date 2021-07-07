package com.cc21.spe.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

import com.cc21.spe.entity.ProdSkill;
import com.cc21.spe.exception.ImagesUploadException;
import com.cc21.spe.service.ImageService;

@Controller
public class WebController {

	private static final Logger logger = LoggerFactory.getLogger(WebTierRestController.class);

	@Autowired
	private ImageService imageService;

    @GetMapping("/")
    @ResponseBody
	public String home1() {
		return "home";
	}

	@GetMapping("/home")
	public String home() {
		return "home";
	}

	@GetMapping("/admin")
	public String admin() {
		return "admin";
	}

	@GetMapping("/user")
	public String user() {
		return "user";
	}

	@GetMapping("/about")
	public String about() {
		return "about";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/skillProdList")
	public String speList() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = ((Principal)auth.getPrincipal()).getName();
        if(userName.equals("user1"))
            return "prodSkillListForUser1";
        else if(userName.equals("user2"))
            return "prodSkillListForUser2";
        
        return "prodSkillList";
	}

	@GetMapping("/addProdSkill")
	public String addProdSkillForm() {

		return "addProdSkill";
	}

	@PostMapping("/addProdSkill")
	public String addProdSkill(Model model, @RequestPart(value = "image") MultipartFile multipartFile,
			@ModelAttribute ProdSkill prodSkill, RedirectAttributes redirectAttrs) throws ImagesUploadException {
		logger.info("Received the Images from the user, multipartFiles: ");
		logger.info("Single multipartFile: " + multipartFile);
		String imageName;
		try {
			imageName = imageService.uploadFiles(multipartFile);
		} catch (Exception e) {
			throw new ImagesUploadException();
		}
		logger.info("Sending to InputQueue, imageName: " + imageName + ", multipartFile.getName(): "
				+ multipartFile.getName());

        prodSkill.setImageName(imageName);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        prodSkill.setUserName(((Principal)auth.getPrincipal()).getName());

        

//				imageService.sendImageToQueue(imageName, multipartFile.getName());
//				res =  getImageRecogResults1(imageName);
//				redirectAttrs.addAttribute("message", "Unkown error. Space could not be created.");

		model.addAttribute("message", "Added item successfully!");
		return "addProdSkill";
    }
    


    @GetMapping("/myNotification")
	public String myNotificcation(Model model) {


        // model.addAttribute("message", "Request sent successfully!");
		return "myNotification";
    }

    @GetMapping("/prodSkillListUser")
	public String getProdSkillListUser(Model model) {


        // model.addAttribute("message", "Request sent successfully!");
		return "prodSkillListUser";
    }

    @PostMapping("/acceptRequest")
	public String acceptRequest(Model model) {


        // model.addAttribute("message", "Request sent successfully!");
		return "prodSkillListUser";
    }

    @PostMapping("/rejectRequest")
	public String rejectRequest(Model model) {


        // model.addAttribute("message", "Request sent successfully!");
		return "prodSkillListUser";
    }

	@GetMapping("/403")
	public String error403() {
		return "error/403";
	}
}

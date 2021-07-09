package com.cc21.spe.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

import com.cc21.spe.entity.PSRequest;
import com.cc21.spe.entity.ProdSkill;
import com.cc21.spe.exception.ImagesUploadException;
import com.cc21.spe.service.ImageService;
import com.cc21.spe.service.PSRequestService;

@Controller
public class WebController {

	private static final Logger logger = LoggerFactory.getLogger(WebController.class);

	@Autowired
    private ImageService imageService;
    
    @Autowired
    private PSRequestService psRequestService;

    @GetMapping("/")
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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        if(userName.equals("user1"))
            return "user1";
        else if(userName.equals("user2"))
            return "user2";
        
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
    
    @PostMapping("/register")
    public String register(){
        return "registerPageSuccess";
    }

    @GetMapping("/register")
    public String registerGet(){
        return "registerPage";
    }

	@GetMapping("/skillProdList")
	public String speList() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        // Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // if (principal instanceof UserDetails) {
        // String username = ((UserDetails)principal).getUsername();
        // } else {
        // String username = principal.toString();
        // }
        String userName = auth.getName();
        if(userName.equals("user1"))
            return "prodSkillList1";
        else if(userName.equals("user2"))
            return "prodSkillList2";
        
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
		String imageName = null;
		// try {
		// 	imageName = imageService.uploadFiles(multipartFile);
		// } catch (Exception e) {
		// 	throw new ImagesUploadException();
		// }
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
    
    @PostMapping("/sendRequest")
	public ResponseEntity<String> sendRequest(Model model,  @ModelAttribute PSRequest psRequest) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userOne = auth.getName();
        psRequest.setUserOne(userOne);
        psRequest.setStatus("new");

        psRequestService.createPSRequest(psRequest);

        String msg = String.format( " You have successfully sent request to '%s' ",
            psRequest.getUserTwo());
        // model.addAttribute("message", "Request sent successfully!");
        // ObjectMapper mapper = new ObjectMapper();
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }


    @GetMapping("/myNotification")
	public String myNotificcation(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String user = auth.getName();

        List<PSRequest> reqList = psRequestService.getRequests(user, "new");
        List<PSRequest> acptList = psRequestService.getRequests(user, "accepted");

        model.addAttribute("reqList", reqList);
        model.addAttribute("acptList", acptList);
		return "myNotification";
    }

    @GetMapping("/prodSkillListOfUser")
	public String getProdSkillListUser(Model model, @RequestParam("user") String userName) {

        // model.addAttribute("message", "Request sent successfully!");
		return "prodSkillListUser1";
    }

    @PostMapping("/acceptRequest")
	public String acceptRequest(Model model, @ModelAttribute PSRequest psRequest) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userTwo = auth.getName();
        String userOne = psRequest.getUserOne();
        PSRequest psRequestDb = psRequestService.getRequest(userOne, userTwo, "new");
        if(psRequestDb != null) {
            psRequestDb.setStatus("accepted");
        }

        psRequestService.updatePSRequest(psRequestDb);

        // model.addAttribute("message", "Request sent successfully!");
        // return "prodSkillListUser";
        return myNotificcation(model);
    }

    @PostMapping("/rejectRequest")
	public String rejectRequest(Model model, @ModelAttribute PSRequest psRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userTwo = auth.getName();
        psRequest.setUserTwo(userTwo);
        psRequest.setStatus("rejected");

        psRequestService.updatePSRequest(psRequest);

        return myNotificcation(model);

        // model.addAttribute("message", "Request sent successfully!");
		// return "prodSkillListUser";
    }

	@GetMapping("/403")
	public String error403() {
		return "error/403";
	}
}

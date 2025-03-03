package com.shopme.admin.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shopme.admin.FileUploadUtil;
import com.shopme.admin.export.UserCsvExporter;
import com.shopme.admin.export.UserExcelExporter;
import com.shopme.admin.export.UserPdfExporter;
import com.shopme.admin.user.UserNotFoundException;
import com.shopme.admin.user.UserService;
import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UserController {

	@Autowired
	private UserService service;
	
	@GetMapping("/users")
	public String listAll(Model model) {
		return listByPage(1, model, "id", "asc",null );
	}
	
	@GetMapping("/users/page/{pageNum}")
	public String listByPage(@PathVariable(name="pageNum") int pageNum, Model model,
			@Param("sortField") String sortField, @Param("sortDir") String sortDir,
			@Param("keyword") String keyword){
		Page<User> page = service.listByPage(pageNum,sortField, sortDir, keyword);
		List<User> listUsers = page.getContent();
		
		long startCount = (pageNum-1) * UserService.USER_PER_PAGE + 1;
		long endCount = startCount + UserService.USER_PER_PAGE - 1;
		if(endCount > page.getTotalElements()) {
			endCount = page.getTotalElements();
		}
		
		String reverseSortDir = sortDir.equalsIgnoreCase("asc") ? "desc" : "asc";
		model.addAttribute("currentPage", pageNum);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("listUsers", listUsers);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", reverseSortDir);
		model.addAttribute("keyword", keyword);
		return "users";
		
	}
	
	@GetMapping("/users/new")
	public String newUser( Model model) {
		List<Role> listRoles=service.listRoles();
		User user=new User();
		user.setEnabled(true);
		List<User> listUsers = service.listAll();
		model.addAttribute("listUsers", listUsers);
		model.addAttribute("user", user);
		model.addAttribute("listRoles", listRoles);
		model.addAttribute("pageTitle", "create new user");
		return "user_form";
	}
	
	@PostMapping("/users/save")
	public String saveUser(User user, RedirectAttributes redirectAttributes, @RequestParam("image") MultipartFile multipartFile) throws IOException {
       if(!multipartFile.isEmpty()) {
		String fileName=StringUtils.cleanPath(multipartFile.getOriginalFilename());
		user.setPhotos(fileName);
		User savedUser = service.save(user);
		String uploadDir = "user-photos/"+savedUser.getId();
		FileUploadUtil.cleanDir(uploadDir);
		FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		
       }else {
    	   if(user.getPhotos().isEmpty())user.setPhotos(null);
    	   service.save(user);
       }
      		redirectAttributes.addFlashAttribute("message" , "The user has been saved successfully." );
	
      		
      	return getRedirectURLtoAffectedUser(user);
	}

	private String getRedirectURLtoAffectedUser(User user) {
		String firstPartOfEmail=user.getEmail().split("@")[0];	
       return "redirect:/users/page/1?sortField=id&sortDir=asc&keyword="+firstPartOfEmail;
	}
	
	@GetMapping("/users/edit/{id}")
	public String editUser(@PathVariable(name="id") Integer id,
			Model model, RedirectAttributes redirectAttributes) {
		
		try {
			List<Role> listRoles=service.listRoles();
			
			User user=service.get(id);
			model.addAttribute("user",user);
			model.addAttribute("listRoles", listRoles);
			model.addAttribute("pageTitle", "Edit User(ID: "+id +")");
			return "user_form";
			
		} catch (UserNotFoundException ex) { 
			redirectAttributes.addFlashAttribute("message", ex.getMessage());
		return "redirect:/users";
		}
	}
		@GetMapping("/users/delete/{id}")
		public String deleteUser(@PathVariable(name="id") Integer id,
				Model model, RedirectAttributes redirectAttributes) {
			try {
				service.delete(id);
				redirectAttributes.addFlashAttribute("message", "This user ID"+ 
				id+ "has been deleted successfully");
			}
			catch(UserNotFoundException ex) {
				redirectAttributes.addFlashAttribute("message", ex.getMessage());
			}
			return "redirect:/users";
	}
		
		@GetMapping("/users/{id}/enabled/{status}")
		public String updateUserEanbledStatus(@PathVariable("id") Integer id,
				@PathVariable("status") boolean enabled, RedirectAttributes redirectAttributes){
			service.updateUserEnabledStatus(id, enabled);
			String status=enabled ? "enabled":"disabled";
			String message="The user id "+id+" has been "+status;
			redirectAttributes.addFlashAttribute("message", message);
			return "redirect:/users";
		}
		
		@GetMapping("/users/export/csv")
		public void exportToCSV(HttpServletResponse response) throws IOException {
			List<User> listUser=service.listAll(); 
			UserCsvExporter exporter = new UserCsvExporter();
			exporter.export(listUser, response);
		}
		
		@GetMapping("/users/export/excel")
		public void exportToEXCEL(HttpServletResponse response) throws IOException {
			List<User> listUser=service.listAll(); 
			UserExcelExporter exporter = new UserExcelExporter();
			exporter.export(listUser, response);
		}
		
		@GetMapping("/users/export/pdf")
		public void exportToPDF(HttpServletResponse response) throws IOException {
			List<User> listUser=service.listAll(); 
			UserPdfExporter exporter = new UserPdfExporter();
			exporter.export(listUser, response);
		}
}

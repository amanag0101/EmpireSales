package com.es.empiresales.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.es.empiresales.entity.Category;
import com.es.empiresales.entity.Product;
import com.es.empiresales.entity.User;
import com.es.empiresales.helper.Message;
import com.es.empiresales.repository.CategoryRepo;
import com.es.empiresales.repository.ProductRepo;
import com.es.empiresales.repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    @Autowired UserRepo userRepo;
    @Autowired CategoryRepo categoryRepo;
    @Autowired ProductRepo productRepo;

    @ModelAttribute
    public void commonAttributes(Model m) {
        List<Category> allCategoriesList = categoryRepo.findAll();
        if(allCategoriesList != null)
            m.addAttribute("allCategoriesList", allCategoriesList);
    }

    // default page
    @RequestMapping("/")
    public String index() {
        return "redirect:/all/0/";
    }

    // show all the products
    @RequestMapping("/{category}/{page}/")
    public String showAllProducts(@PathVariable("category") String categoryName, @PathVariable("page") Integer page, Model m) {
        Pageable pageable = PageRequest.of(page, 3);
        Page<Product> allProductsList = null;

        if(categoryName.equals("all"))
            allProductsList = productRepo.findAll(pageable);
        else 
            allProductsList = productRepo.findAllByCategoryName(categoryName, pageable);

        if(allProductsList != null)
            m.addAttribute("allProductsList", allProductsList);
        
        m.addAttribute("currentPage", page);
        m.addAttribute("totalPages", allProductsList.getTotalPages());

        return "index";
    }
    
    // show specific product details
    @RequestMapping("/product/{productId}")
    public String showProductDetails(@PathVariable("productId") Long productId, Model m) {
        Product product = productRepo.getById(productId);
        
        if(product != null)
            m.addAttribute("product", product);
            
        return "productDetails";
    }
    
    // regiser page
    @RequestMapping("/register")
    public String register(Model m) {
        m.addAttribute("user", new User());
        return "register";
    }

    // process registration form
    @PostMapping("/processing-registration-form")
    public String processRegistrationForm(@Valid @ModelAttribute("user") User user, BindingResult bindingResult,
    @RequestParam(value = "password-repeat") String passwordRepeat, 
    @RequestParam(value = "agreement", defaultValue = "false") Boolean agreement, HttpSession session) {
        // if agreement not accepted
        if(!agreement) {
            session.setAttribute("message", new Message("error", "Please accept the Terms & Conditions."));
			return "/register";
        }

        // if password does not match password-repeat
        if(!user.getPassword().equals(passwordRepeat)) {
            session.setAttribute("message", new Message("error", "Password does not match."));
			return "/register";
        }

        // errors handled by bean validator
        if(bindingResult.hasErrors()) 
            return "/register";

        // check for unique email    
        User userByEmail = userRepo.findByEmail(user.getEmail());
        if(userByEmail != null) {
            session.setAttribute("message", new Message("error", "Email already taken!"));
			return "/register";
        }

        // check for unique phone number
        User userByPhone = userRepo.findByPhone(user.getPhone());
        if(userByPhone != null) {
            session.setAttribute("message", new Message("error", "Phone Number already taken!."));
			return "/register";
        }

        // add user in the database
        try {
            userRepo.save(user);
            session.setAttribute("message", new Message("success", "Registration Complete!"));
			return "redirect:/register";
        }
        catch(Exception e) {
            e.printStackTrace();
            session.setAttribute("message", new Message("error", "Some error occurred."));
			return "/register";
        }
    }
}

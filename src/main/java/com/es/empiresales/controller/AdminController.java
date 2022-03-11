package com.es.empiresales.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.es.empiresales.entity.Category;
import com.es.empiresales.entity.Product;
import com.es.empiresales.helper.Message;
import com.es.empiresales.repository.CategoryRepo;
import com.es.empiresales.repository.ProductRepo;
import com.es.empiresales.repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AdminController {
    @Autowired UserRepo userRepo;
    @Autowired CategoryRepo categoryRepo;
    @Autowired ProductRepo productRepo;

    @RequestMapping("/admin")
    public String dashboard(Model m) {
        Integer userCount = userRepo.getUserCount();
        Integer categoryCount = categoryRepo.getCategoryCount();
        Integer productCount = productRepo.getProductCount();
        
        // all user count
        if(userCount == null)
            userCount = 0;

        // all categories count
        if(categoryCount == null)
            categoryCount = 0;

        // all product count
        if(productCount == null)
            productCount = 0;

        // list of all the categories
        List<Category> allCategoriesList = categoryRepo.findAll();
        if(allCategoriesList != null)
            m.addAttribute("allCategoriesList", allCategoriesList);

        m.addAttribute("userCount", userCount);
        m.addAttribute("categoryCount", categoryCount);
        m.addAttribute("productCount", productCount);
        m.addAttribute("category", new Category());
        m.addAttribute("product", new Product());

        return "/admin/dashboard";
    }

    // check for empty strings
    public boolean isEmpty(final String attribute) {
        if(attribute == null || attribute.length() == 0)
            return true;
        else
            return false;
    }

    // process add-category form
    @PostMapping("/processing-add-category")
    public String processAddCategoryForm(@Valid @ModelAttribute("category") Category category, BindingResult bindingResult, HttpSession session) {
        if(bindingResult.hasErrors()) {
            session.setAttribute("message", new Message("error", "Please fill all the fields!"));
            return "redirect:/admin";
        }

        // check if the category is already present in the database
        Category categoryFromDb = categoryRepo.findByName(category.getName());
        if(categoryFromDb != null) {
            session.setAttribute("message", new Message("error", "Category already exists."));
            return "redirect:/admin";
        }

        // add category in database
        try {
            categoryRepo.save(category);
            session.setAttribute("message", new Message("success", "Category addedd!"));
            return "redirect:/admin";
        }
        catch(Exception e) {
            e.printStackTrace();
            session.setAttribute("message", new Message("error", "Some error occurred!"));
            return "redirect:/admin";
        }
    }

    // process add-product form
    @PostMapping("/processing-add-product")
    public String processAddProductForm(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult, 
    @RequestParam("productCategoryId") Long productCategoryId, @RequestParam("productImage") MultipartFile productImage, 
    HttpSession session) {
        if(bindingResult.hasErrors()) {
            System.out.println("Errors: " + bindingResult.getErrorCount());
            session.setAttribute("message", new Message("error", "Please fill all the fields!"));
            return "redirect:/admin";
        }

        try {
            // set product category
            Category productCategory = categoryRepo.getById(productCategoryId);
            product.setCategory(productCategory);

            // add product in the database
            Product savedProduct = productRepo.save(product);

            // set product imageUrl same as product id
            savedProduct.setImageUrl(savedProduct.getId() + "");
            savedProduct = productRepo.save(savedProduct);

            // save image in local directory
            if(!productImage.isEmpty()) {
                // image is saved with name same as product id
                File file = new ClassPathResource("/static/img/products/").getFile();
                Path path = Paths.get(file.getAbsolutePath() + File.separator + savedProduct.getId());
                Files.copy(productImage.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            }

            session.setAttribute("message", new Message("success", "Added new product"));
            return "redirect:/admin";
        }
        catch(Exception e) {
            e.printStackTrace();
            session.setAttribute("message", new Message("error", "Some error occurred."));
            return "redirect:/admin";
        }
    }
}

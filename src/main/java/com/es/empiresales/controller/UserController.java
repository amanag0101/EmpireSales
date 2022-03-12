package com.es.empiresales.controller;

import java.security.Principal;
import java.util.List;

import com.es.empiresales.entity.Cart;
import com.es.empiresales.entity.Category;
import com.es.empiresales.entity.Product;
import com.es.empiresales.entity.User;
import com.es.empiresales.repository.CartRepo;
import com.es.empiresales.repository.CategoryRepo;
import com.es.empiresales.repository.ProductRepo;
import com.es.empiresales.repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
    @Autowired UserRepo userRepo;
    @Autowired CategoryRepo categoryRepo;
    @Autowired ProductRepo productRepo;
    @Autowired CartRepo cartRepo;

    @ModelAttribute
    public void commonAttributes(Principal principal, Model m) {
        String username = principal.getName();
        User user = userRepo.findByEmail(username);

        List<Category> allCategoriesList = categoryRepo.findAll();
        if(allCategoriesList != null)
            m.addAttribute("allCategoriesList", allCategoriesList);

        m.addAttribute("username", user.getName());
    }

    // homepage
    @RequestMapping("/user")
    public String home() {
        return "redirect:/user/all/0/";
    }

    // show all the products
    @RequestMapping("/user/{category}/{page}/")
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

        return "/user/home";
    }

    // show specific product details
    @RequestMapping("/user/product/{productId}")
    public String showProductDetails(@PathVariable("productId") Long productId, Model m) {
        Product product = productRepo.getById(productId);
        
        if(product != null)
            m.addAttribute("product", product);
            
        return "/user/productDetails";
    }

    // cart
    @RequestMapping("/user/cart")
    public String cart(Principal principal, Model m) {
        String username = principal.getName();
        User user = userRepo.findByEmail(username);
        List<Cart> cartList = cartRepo.findByUser(user);

        // list of items present in the cart
        if(cartList != null) {
            // calculating total price
            Long totalPrice = 0L;
            for(Cart item : cartList) 
                totalPrice += (item.getQuantity() * item.getProduct().getPrice()); 

            m.addAttribute("cartList", cartList);
            m.addAttribute("totalCost", totalPrice);
        }

        return "/user/cart";
    }
}

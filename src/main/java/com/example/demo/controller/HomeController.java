package com.example.demo.controller;

import com.example.demo.models.User;
import com.example.demo.models2.Item;
import com.example.demo.repositories.UserRepository;
import com.example.demo.repositories2.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/")
    public String home(){
        System.out.println(userRepository.findAll().stream().map(User::getName).toList());
        System.out.println(itemRepository.findAll().stream().map(Item::getName).toList());
        return null;
    }
}

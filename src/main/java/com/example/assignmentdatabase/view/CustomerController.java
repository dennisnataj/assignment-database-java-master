package com.example.assignmentdatabase.view;

import com.example.assignmentdatabase.data_access.customer.CustomerRepositoryImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerController {
    private final CustomerRepositoryImpl customerRepository;

    public CustomerController(CustomerRepositoryImpl customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/")
    private String view(Model model) {
        model.addAttribute("artist", customerRepository.getFiveRandomArtist());
        model.addAttribute("songs", customerRepository.getFiveRandomSongs());
        model.addAttribute("genres", customerRepository.getFiveRandomGenres());


        return "home";
    }
}

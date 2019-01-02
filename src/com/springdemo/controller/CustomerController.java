package com.springdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springdemo.dao.CustomerDAO;
import com.springdemo.entity.Customer;
import com.springdemo.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	//inject the dao
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/list")
	public String listCustomers(Model model) {
		List<Customer> customers = customerService.getCustomers();
		//customers.sort((Customer c1, Customer c2) -> c1.getLastName().compareTo(c2.getLastName()));
		model.addAttribute("customers", customers);
		return "list-customers";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model model) {
		//create model attribute to bind form data 
		
		Customer customer = new Customer();
		model.addAttribute("customer", customer);
	
		return "customer-form";
	}
	
	@PostMapping("saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer customer) {
		customerService.saveCustomer(customer);
		
		return "redirect:/customer/list";
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int id, Model model) {
		
		//get the customer from database
		Customer customer = customerService.getCustomer(id);
		
		//set customer as a model attribute to populate the form
		model.addAttribute("customer", customer);
		
		//send over to our form
		return "customer-form";
	}
	
	@GetMapping("/remove")
	public String removeUser(@RequestParam("customerId") int id, Model model) {
		customerService.removeCustomer(id);
		return listCustomers(model);
	}
	
	
}

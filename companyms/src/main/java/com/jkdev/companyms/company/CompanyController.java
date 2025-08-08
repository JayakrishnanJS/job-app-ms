package com.jkdev.companyms.company;

import  org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies() {
        return ResponseEntity.ok(companyService.getAllCompanies());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCompany(@PathVariable long id, @RequestBody Company company) {
        boolean updated = companyService.updateCompany(company, id);
        if(updated)
            return new ResponseEntity<>("Company updated successfully", HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<String> addCompany(@RequestBody Company company) {
        companyService.addCompany(company);
        return new ResponseEntity<>("Company added successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCompanyById(@PathVariable long id) {
        boolean deleted = companyService. deleteCompany(id);
        if(deleted)
            return new ResponseEntity<>("Company deleted successfully", HttpStatus.OK);
        return new ResponseEntity<>("Company not found",HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable long id) {
        Company company = companyService.getCompanyById(id);
        if(company != null) {
            return new ResponseEntity<>(company, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

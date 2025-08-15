package com.jkdev.companyms.company.impl;

import com.jkdev.companyms.company.Company;
import com.jkdev.companyms.company.CompanyRepository;
import com.jkdev.companyms.company.CompanyService;
import com.jkdev.companyms.company.clients.ReviewClient;
import com.jkdev.companyms.company.dto.ReviewMessage;
import jakarta.ws.rs.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private ReviewClient reviewClient;

    public CompanyServiceImpl(CompanyRepository companyRepository, ReviewClient reviewClient) {
        this.companyRepository = companyRepository;
        this.reviewClient = reviewClient;
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public boolean updateCompany(Company company, long id) {
        Optional<Company> companyOptional = companyRepository.findById(id);
        if (companyOptional.isPresent()) {
            Company companyToUpdate = companyOptional.get();
            companyToUpdate.setDescription(company.getDescription());
            companyToUpdate.setName(company.getName());
            companyRepository.save(companyToUpdate);
            return true;
        }
        return false;
    }

    @Override
    public void addCompany(Company company) {
        companyRepository.save(company);
    }

    @Override
    public boolean deleteCompany(Long id) {
        if (companyRepository.existsById(id)) {
            companyRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }

    @Override
    public void updateCompanyRating(ReviewMessage reviewMessage) {
        System.out.println(reviewMessage.getDescription());
        // Fetch the Company entity from the repository by its ID, which is provided in the ReviewMessage object.
        // If the company is not found, throw a NotFoundException with a descriptive error message.
        Company company = companyRepository.findById(reviewMessage.getCompanyId()).orElseThrow(
                () -> new NotFoundException("Company with id " + reviewMessage.getCompanyId() + " not found"));

        // Call the ReviewClient to get the average rating for the company by its ID.
        // This might involve communication with an external system or service to fetch the updated rating details.
        double averageRating = reviewClient.getAverageRatingForCompany(reviewMessage.getCompanyId());

        // Update the rating field of the Company entity with the fetched average rating.
        company.setRating(averageRating);

        // Save the updated Company entity back to the repository to persist changes in the database.
        companyRepository.save(company);
    }
}
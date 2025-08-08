package com.jkdev.companyms.company;

import java.util.List;

public interface CompanyService {

    List<Company> getAllCompanies();
    boolean updateCompany(Company company, long id);
    void addCompany(Company company);
    boolean deleteCompany(Long id);
    Company getCompanyById(Long id);
}

package com.sample.springboot_boilerplate.service.impl;

import com.sample.springboot_boilerplate.Db.OrgHandler;
import com.sample.springboot_boilerplate.dto.OrganizationDTO;
import com.sample.springboot_boilerplate.dto.ProductDTO;
import com.sample.springboot_boilerplate.dto.EmailDTO;
import com.sample.springboot_boilerplate.dto.EmployeeDTO;

import com.sample.springboot_boilerplate.entity.Organization;
import com.sample.springboot_boilerplate.exception.ResourceNotFoundException;
import com.sample.springboot_boilerplate.mapper.OrganizationMapper;
import com.sample.springboot_boilerplate.repository.OrganizationRepository;
import com.sample.springboot_boilerplate.service.OrganizationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final OrganizationMapper organizationMapper;
    private final OrgHandler orgHandler;

    public OrganizationServiceImpl(OrganizationRepository organizationRepository, OrganizationMapper organizationMapper, OrgHandler orgHandler) {
        this.organizationRepository = organizationRepository;
        this.organizationMapper = organizationMapper;
        this.orgHandler = orgHandler;
    }

    @Override
    public List<OrganizationDTO> getAllOrganizations() {
        List<Organization> organizations = organizationRepository.findAll();
        return organizations.stream()
                .map(organizationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getProductList(Integer id) {
        List<Object[]> orgs = orgHandler.getProductList(id);
        List<ProductDTO> products = new ArrayList<>();

        for  (Object[] org : orgs) {
             ProductDTO dto = new ProductDTO();
             dto.setId(Integer.parseInt(Objects.toString(org[0])));
             dto.setName((String) org[1]);

             products.add(dto);
        }

        return products;
    }
    @Override
    public List<EmployeeDTO> getEmployeeList(Integer id) {
        List<Object[]> orgs = orgHandler.getEmployeeList(id);
        List<EmployeeDTO> product = new ArrayList<>();

        for  (Object[] org : orgs) {
             EmployeeDTO dto = new EmployeeDTO();
             dto.setId(Integer.parseInt(Objects.toString(org[0])));
             dto.setOrg_id(Integer.parseInt(Objects.toString(org[1])));
             dto.setEmp_name((String) org[2]);


             product.add(dto);
        }

        return product;
    }
    @Override
    public List<EmailDTO> getEmailList(String email) {
        List<Object[]> orgs = orgHandler.getEmailList(email);
        List<EmailDTO> product = new ArrayList<>();

        for  (Object[] org : orgs) {
             EmailDTO dto = new EmailDTO();
             dto.setId(Integer.parseInt(Objects.toString(org[0])));
             dto.setOrg_id(Integer.parseInt(Objects.toString(org[1])));
             dto.setEmp_name((String) org[2]);
             dto.setEmployee_email((String) org[3]);
             product.add(dto);
        }

        return product;
    }
    @Override
    public OrganizationDTO getOrganizationById(Integer id) {
        // Throw exception if the organization is not found
        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found with ID: " + id));

        return organizationMapper.toDTO(organization);
    }
}

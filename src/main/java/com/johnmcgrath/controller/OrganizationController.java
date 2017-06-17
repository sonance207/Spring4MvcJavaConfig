package com.johnmcgrath.controller;

import com.johnmcgrath.domain.Organization;
import com.johnmcgrath.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by root on 6/17/17.
 */

@Controller
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;


    @RequestMapping("/")
    public String listOrganizationUsingSQLTag() {
        return "listOrganization1";
    }

    @RequestMapping("/service")
    public String listOrganizationUSingService(Model model) {
        List<Organization> orgs = organizationService.getOrgList();
        model.addAttribute("orgList",orgs);
        return "listOrganization2";

    }


}

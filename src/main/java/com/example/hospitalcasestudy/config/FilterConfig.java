package com.example.hospitalcasestudy.config;

import com.example.hospitalcasestudy.filter.AuthHeaderUuidFilter;
import com.example.hospitalcasestudy.service.StaffService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<AuthHeaderUuidFilter> authFilter(
            StaffService staffService,
            AuthExclude authExclude,
            @Value("${app.auth.header.name}") String authHeaderName){
        FilterRegistrationBean<AuthHeaderUuidFilter> registrationBean
                = new FilterRegistrationBean<>();

        registrationBean.setFilter(new AuthHeaderUuidFilter(staffService, authHeaderName, authExclude));
        registrationBean.addUrlPatterns("/patient","/staffs");
        registrationBean.setOrder(1);

        return registrationBean;
    }
}

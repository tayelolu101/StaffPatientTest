package com.example.hospitalcasestudy.filter;

import com.example.hospitalcasestudy.config.AuthExclude;
import com.example.hospitalcasestudy.service.StaffService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class AuthHeaderUuidFilter  implements Filter {

    String authHeaderName;

    StaffService staffService;

    AuthExclude authExclude;

    public AuthHeaderUuidFilter(StaffService staffService, String authHeaderName, AuthExclude authExclude) {
        this.staffService = staffService;
        this.authHeaderName = authHeaderName;
        this.authExclude = authExclude;
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

       final String reqPath  = httpRequest.getServletPath();
       final String reqMethod  = httpRequest.getMethod();

        final String authHeaderUuid  = httpRequest.getHeader(authHeaderName);

        final boolean isExcludedAuthPath  = authExclude.getExclude().stream().anyMatch(path -> reqPath.toLowerCase().startsWith(path.getPath()) && path.getMethod().equalsIgnoreCase(reqMethod));


        if( !isExcludedAuthPath && !(StringUtils.hasLength(authHeaderUuid) &&
                authHeaderUuid.toLowerCase().startsWith("bearer") &&
                staffService.staffExistByUUid(authHeaderUuid.split(" ")[1]))){
            httpResponse.sendError(HttpStatus.UNAUTHORIZED.value(),HttpStatus.UNAUTHORIZED.getReasonPhrase());
            return;
        }



        filterChain.doFilter(request, response);
    }
    
}

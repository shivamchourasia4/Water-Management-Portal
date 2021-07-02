package com.example.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.JWT.constants;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@CrossOrigin
public class AuthFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpResponse.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, DELETE");
        httpResponse.setHeader("Access-Control-Allow-Headers", "*");

        if (servletRequest instanceof HttpServletRequest) {
            if (httpRequest.getHeader(HttpHeaders.ORIGIN) != null
                    && httpRequest.getMethod().equals(HttpMethod.OPTIONS.name())
                    && httpRequest.getHeader(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD) != null) {
                // log.debug("Received an OPTIONS pre-flight request.");
                return;
            }
        }

        String authHeader = httpRequest.getHeader("Authorization");

        if (authHeader != null) {
            String[] authHeaderArr = authHeader.split("Bearer");

            if (authHeaderArr.length > 1 && authHeaderArr[1] != null) {
                String token = authHeaderArr[1];

                try {
                    Claims claims = Jwts.parser().setSigningKey(constants.API_SECRET_KEY).parseClaimsJws(token)
                            .getBody();

                    httpRequest.setAttribute("email", claims.get("email").toString());
                } catch (Exception e) {
                    httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "invalid/expired token");
                    return;
                }
            } else {
                httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "Authorization token must be Bearer[token]");
                return;
            }
        } else {
            httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "Authorization Token must be provided");
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}

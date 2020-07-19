package com.ayuda.referral.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomFilter implements Filter {
 @Override
 public void init(FilterConfig config) {}

 @Override
 public void destroy() {}

 @Override
 public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
  HttpServletRequest req = (HttpServletRequest) request;
  HttpServletResponse res = (HttpServletResponse) response;

  res.setHeader("Access-Control-Allow-Origin", "*");
  res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, PATCH, DELETE");
  res.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
  if (req.getMethod().equalsIgnoreCase("OPTIONS")) {
   res.setStatus(HttpServletResponse.SC_OK);
  } else {
   chain.doFilter(request, response);
  }
 }
}
<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>

<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.beans.factory.FactoryBean"%>
<%@page import="org.springframework.security.intercept.web.FilterSecurityInterceptor"%>
<%@page import="org.springframework.security.intercept.web.FilterInvocationDefinitionSource"%>
<%
    ApplicationContext ctx =  WebApplicationContextUtils.getWebApplicationContext(application);
    FactoryBean factoryBean = (FactoryBean) ctx.getBean("&filterInvocationDefinitionSource");
    FilterInvocationDefinitionSource fids = (FilterInvocationDefinitionSource) factoryBean.getObject();
    FilterSecurityInterceptor filter = (FilterSecurityInterceptor) ctx.getBean("filterSecurityInterceptor");
    filter.setObjectDefinitionSource(fids);
%>
重新加载成功！

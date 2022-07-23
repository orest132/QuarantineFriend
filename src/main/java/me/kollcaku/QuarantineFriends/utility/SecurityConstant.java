package me.kollcaku.QuarantineFriends.utility;



public class SecurityConstant {
    public static final long EXPIRATION_TIME = 20000000;
    public static final String JWT_SECRET = "klasjdajsldkhalblabsflbalsda";
    public static final String TOKEN_PREFIX = "Bearer";
    public static final String JWT_TOKEN_HEADER = "Jwt-Token";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";
    public static final String LUFTHANSA_INDUSTRY_SOLUTION = "Lufthansa Industry Solution";
    public static final String PLAN_SPACE_APP = "PlanSpace App";
    public static final String AUTHORITIES = "Authorities";
    public static final String FORBIDDEN_MESSAGE = "You need to log in to access this page";
    public static final String ACCESS_DENIED_MESSAGE = "You do not have permission to access this page";
    public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
    public static final String REQUEST_MAPPING = "/api";
    public static final String ANGULAR_CROSS_ORIGIN = "http://localhost:4200";
    public static final String[] PUBLIC_URLS = {"/api/register","/api/login","/api/subscribe", "/swagger*/*", "/swagger-ui.html", "/v3/api-docs","/v3/api-docs/*"};
    public static final String[] ONLY_ADMIN_URLS = {"/api/delete/**","/api/teams"};
}
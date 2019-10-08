package com.example.hubProject.Config;

import com.example.hubProject.Model.User;
import com.example.hubProject.Repository.UserDao;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

import static com.example.hubProject.Commons.Constants.HEADER_STRING;


public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Resource(name = "userService")
    private UserDetailsService userDetailsService;
    
    @Autowired
    private UserDao userRepo;
    
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
    		String header = req.getHeader(HEADER_STRING);
        System.out.println(req.getRequestURL().toString());

        String username = null;
        String authToken = null;
        if (header != null) {
            authToken = header;
            try {
                username = jwtTokenUtil.getUsernameFromToken(authToken);
            } catch (IllegalArgumentException e) {
                logger.error("An error occurred while getting the token username", e);
            } catch (ExpiredJwtException e) {
                logger.warn("Token has been expired", e);
            } catch(SignatureException e){
                logger.error("Username or Password invalid");
            }
        } else {
        	
            logger.warn( "The header is ignored");
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtTokenUtil.validateToken(authToken, userDetails)) {
            	User user = userRepo.findByEmail(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, Arrays.asList(new SimpleGrantedAuthority(user.getUserType())));
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                logger.info("Authenticated User " + username + ", Safely\n");
                SecurityContextHolder.getContext().setAuthentication(authentication);
                req.setAttribute("loggedinUser",user);
            }
        }

        chain.doFilter(req, res);
    }
}
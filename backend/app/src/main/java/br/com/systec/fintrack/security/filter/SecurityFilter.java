package br.com.systec.fintrack.security.filter;

import br.com.systec.fintrack.commons.TenantContext;
import br.com.systec.fintrack.commons.exception.BaseException;
import br.com.systec.fintrack.commons.exception.TenantNotFoundException;
import br.com.systec.fintrack.security.service.AuthenticationService;
import br.com.systec.fintrack.security.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws BaseException {
        try {
            String token = recoverToken(request);

            if(token != null){
                String subject = tokenService.getSubject(token);
                UserDetails userDetails = authenticationService.loadUserByUsername(subject);

                var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);

                Long tenantId = tokenService.getTenant(token);

                if (tenantId == null) {
                    throw new TenantNotFoundException();
                }

                TenantContext.add(tenantId);
            }

            filterChain.doFilter(request,response);
        } catch (TenantNotFoundException e) {
            throw e;
        }catch (RuntimeException | IOException | ServletException e){
            throw new BaseException(e.getMessage(), e);
        }finally {
            TenantContext.clear();
        }
    }

    private String recoverToken(HttpServletRequest request){
        String token = request.getHeader("Authorization");

        if(token == null || token.isEmpty() || !token.startsWith("Bearer ")){
          return null;
        }

        return token.replace("Bearer ", "");
    }
}
package com.concafras.gestao.security;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.concafras.gestao.model.security.AlcadaUsuario;
import com.concafras.gestao.model.security.Usuario;
import com.concafras.gestao.service.UsuarioService;
 
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
 
    @Autowired
    private UsuarioService usuarioService;
 
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
 
        Usuario userProfile = usuarioService.findByUsername(username);
 
        if (userProfile == null) {
            throw new BadCredentialsException("Username not found.");
        }
 
        if (!password.equals(userProfile.getPassword())) {
            throw new BadCredentialsException("Wrong password.");
        }
        
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        
			Set<AlcadaUsuario> userRoles = userProfile.getUserRoles();

			if (null != userRoles && userRoles.size() > 0) {
				for (AlcadaUsuario userRole : userRoles) {
					authorities.add(new SimpleGrantedAuthority("ROLE_" + userRole
							.getRoleName()));
				}
			}
			
			UsuarioAutenticado portalUser = new UsuarioAutenticado(userProfile.getPessoa(), 
			    userProfile.getId(),
					username,
					userProfile.getPassword(), 
					userProfile.isPasswordExpired(),
					true, true, true, true,
					authorities );
			
			return new UsernamePasswordAuthenticationToken(userProfile, password, authorities);

    }
 
    @Override
    public boolean supports(Class<?> arg0) {
        return true;
    }
}
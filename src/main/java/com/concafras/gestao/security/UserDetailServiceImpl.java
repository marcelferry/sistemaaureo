package com.concafras.gestao.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.concafras.gestao.model.Rodizio;
import com.concafras.gestao.model.security.AlcadaUsuario;
import com.concafras.gestao.model.security.Usuario;
import com.concafras.gestao.service.RodizioService;
import com.concafras.gestao.service.UsuarioService;

@Component
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private RodizioService rodizioService;
  
    @Autowired
    private UsuarioService usuarioService;
    
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		UsuarioAutenticado portalUser = null;
		List<GrantedAuthority>  authorities = new ArrayList<GrantedAuthority>();

		if (!"".equals(username)) {
			Usuario userProfile = usuarioService.findByUsername(username);

			if (null != userProfile) {
				Set<AlcadaUsuario> userRoles = userProfile.getUserRoles();

				if (null != userRoles && userRoles.size() > 0) {
					for (AlcadaUsuario userRole : userRoles) {
						authorities.add(new SimpleGrantedAuthority("ROLE_" + userRole.getRoleName()));
					}
				}
				
				if(userProfile.getPessoa() != null){
				
					Rodizio ciclo =  rodizioService.findByAtivoTrue();
					
          if (usuarioService.isPresidente(userProfile.getPessoa())) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + "METAS_PRESIDENTE"));
          }
          
          if (usuarioService.isDirigente(userProfile.getPessoa())) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + "METAS_DIRIGENTE"));
          }
          
          if (usuarioService.isFacilitador(userProfile.getPessoa(), ciclo)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + "METAS_FACILITADOR"));
          }
          
				}

				if(authorities.size() > 0){
					portalUser = new UsuarioAutenticado(userProfile.getPessoa(), userProfile.getId(), username,
							userProfile.getPassword(), userProfile.isPasswordExpired(), 
							true, true, true, true,
							authorities
							);

				} else {
					portalUser = new UsuarioAutenticado(null, null, username, "NA", 
							true, true, true, true,
							true, authorities );
				}
			} else {
				portalUser = new UsuarioAutenticado(null, null, username, "NA", 
						true, true, true, true,
						true, authorities);
			}
		}

		return portalUser;
	}
}
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

import com.concafras.gestao.model.Dirigente;
import com.concafras.gestao.model.DirigenteNacional;
import com.concafras.gestao.model.Facilitador;
import com.concafras.gestao.model.Presidente;
import com.concafras.gestao.model.security.AlcadaUsuario;
import com.concafras.gestao.model.security.Usuario;
import com.concafras.gestao.service.DirigenteNacionalService;
import com.concafras.gestao.service.FacilitadorService;
import com.concafras.gestao.service.PresidenteService;
import com.concafras.gestao.service.UsuarioService;

@Component
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private PresidenteService presidenteService;
    
    @Autowired
    private FacilitadorService facilitadorService;
    
    @Autowired
    private DirigenteNacionalService dirigenteNacionalService;

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
						authorities.add(new SimpleGrantedAuthority("ROLE_" + userRole
								.getRoleName()));
					}
				}
				
				if(userProfile.getPessoa() != null){
				
					// PRESIDENTE?
					List<Presidente> listaPresidentes = presidenteService.getPresidente(userProfile.getPessoa());
					
					if(listaPresidentes != null && listaPresidentes.size() > 0) {
					
						boolean presidenteAtivo = false;
						List<Presidente> presidenciasAtuais = new ArrayList<Presidente>();
						
						for (Presidente presidente : listaPresidentes) {
							if(presidente.isAtivo()){
								presidenteAtivo = true;
								presidenciasAtuais.add(presidente);
							}
						}
						
						if(presidenteAtivo) {
							authorities.add(new SimpleGrantedAuthority("ROLE_" + "METAS_PRESIDENTE"));
						}
					}
					
					//FACILITADOR?
					List<Facilitador> listaFacilitadores = facilitadorService.getFacilitador(userProfile.getPessoa());
					
					if(listaFacilitadores != null && listaFacilitadores.size() > 0) {
						authorities.add(new SimpleGrantedAuthority("ROLE_" + "METAS_FACILITADOR"));
					}
					
				  //DIRIGENTE?
          List<DirigenteNacional> listaDirigentes = dirigenteNacionalService.findByDirigente(userProfile.getPessoa());
          
          if(listaDirigentes != null && listaDirigentes.size() > 0) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + "METAS_DIRIGENTE"));
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
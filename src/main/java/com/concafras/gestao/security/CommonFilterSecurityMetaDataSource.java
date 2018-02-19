package com.concafras.gestao.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

public class CommonFilterSecurityMetaDataSource implements
		FilterInvocationSecurityMetadataSource {
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		FilterInvocation fI = (FilterInvocation) object;
		Object principal = null;
		if (SecurityContextHolder.getContext().getAuthentication() == null
				|| SecurityContextHolder.getContext().getAuthentication()
						.getPrincipal() == null) {
			throw new AuthenticationCredentialsNotFoundException(
					"An Authentication object was not found in the SecurityContext");
		} else {
			principal = SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
		}

		// to strip of query string from url.
		String url = fI.getRequestUrl();
		if (url.indexOf('?') != -1)
			url = url.substring(0, url.indexOf('?'));
		url = url.trim();

		List<ConfigAttribute> attributes = new ArrayList<ConfigAttribute>();
		User user = (User) principal;
		
		//This is sample code for role based url access.
        //Here the user bean contains a set of tasks with urls. We are comparing the current url with the set of urls
        // allowed for this user .
        //Please provide your own code as per your requirement.
        /*  for(Task roleBasedUrls:user.getTasks())
            {
                 if(StringUtils.isNotEmpty(roleBasedUrls.getUrl()) && (roleBasedUrls.getUrl().toLowerCase()).contains(url.toLowerCase()))
                 {
                         attributes.add(new SecurityConfig("IS_AUTHENTICATED_FULLY"));
                         break;
                 }
            }
        */

		if (attributes.isEmpty()) {
			attributes.add(new SecurityConfig("IS_NOT_AUTHENTICATED_FULLY"));
		}
		return attributes;
	}

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}
}
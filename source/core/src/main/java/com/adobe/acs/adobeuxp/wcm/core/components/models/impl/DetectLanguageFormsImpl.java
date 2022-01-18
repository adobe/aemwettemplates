/*
Copyright 2020 Adobe. All rights reserved.
This file is licensed to you under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License. You may obtain a copy
of the License at http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under
the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR REPRESENTATIONS
OF ANY KIND, either express or implied. See the License for the specific language
governing permissions and limitations under the License.
*/
package com.adobe.acs.adobeuxp.wcm.core.components.models.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.acs.adobeuxp.wcm.core.components.models.DetectLanguageForms;
import com.day.cq.wcm.api.Page;


@Model(
	    adaptables = SlingHttpServletRequest.class,
	    adapters = {DetectLanguageForms.class},
	    resourceType = DetectLanguageFormsImpl.RESOURCE_TYPE
	)
public class DetectLanguageFormsImpl implements DetectLanguageForms {
	public static final String RESOURCE_TYPE = "adobeuxp/components/content/languagenavigationForms";
	private static final Logger LOGGER = LoggerFactory.getLogger(DetectLanguageFormsImpl.class);
	public static final String AF_ACCEPT_LANG_PARAM = "afAcceptLang";
	public static final String AF_FORMS_LOCATION = "/content/forms/af";
	public static final String AF_ACCEPT_LANG_REGEX = "(afAcceptLang=(([\\w]{2})-([\\w]{2})))|(afAcceptLang=([\\w]{2}))";
	public static final String SELECTOR_LANG_REGEX = "(.([\\w]{2})-([\\w]{2}).html)|(.([\\w]{2}).html)";
	
	
	@ScriptVariable
	protected Page currentPage;
	
	@SlingObject
	private SlingHttpServletRequest request;
	
    
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    @Nullable
    private String label;
    
    @Override
    @Nullable
    public String getActionURL(){
    	String lng = getLang();
    	if(lng.equals("fr") && !isSelector()) {
    		String query = StringUtils.replaceFirst(request.getQueryString(), AF_ACCEPT_LANG_REGEX, "afAcceptLang=en");
    		return request.getRequestURI() +   "?" + query;
    	}else if(lng.equals("fr") && isSelector()) {
    		String uri = StringUtils.replaceFirst(request.getRequestURI(), SELECTOR_LANG_REGEX, "en.html");
    		return uri;
    	}else if(lng.equals("en") && !isSelector()) {
    		String query = StringUtils.replaceFirst(request.getQueryString(), AF_ACCEPT_LANG_REGEX, "afAcceptLang=fr");
    		return request.getRequestURI() +   "?" + query;
    	}else if(lng.equals("en") && isSelector()) {
    		String uri = StringUtils.replaceFirst(request.getRequestURI(), SELECTOR_LANG_REGEX, "fr.html");
    		return uri;
    	}
    	
    	return request.getRequestURI() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
    }
    
    @Override
    @Nullable
    public String getLabel(){
    	return label;
    }
    
    @Override
    @Nullable 
    public boolean isSelector(){
		 if(getLanguageSelector()!=null) {
			 return true;
		 }
		 return false;
	}
 
    @Override
    public String getLang(){
    	if(StringUtils.startsWith(currentPage.getPath(), AF_FORMS_LOCATION) ) {
    		
    		return getLanguageParameter();
    	}
    	return currentPage == null ? Locale.getDefault().toLanguageTag()
                : currentPage.getLanguage(false).toLanguageTag();
    }
    
    
	@NotNull
	private String getLanguageParameter() {
		 
		return java.util.Optional.ofNullable(
        		java.util.Optional.ofNullable(request.getParameter(AF_ACCEPT_LANG_PARAM))
                .orElseGet(()->java.util.Optional.ofNullable(getLanguageSelector())
                		.orElse(null)))
            .orElse(null);
	}
	
	private String getLanguageSelector() {
		
		if(request.getRequestPathInfo().getSelectors().length > 0) {
			List<String> selectors = Arrays.asList(request.getRequestPathInfo().getSelectors()); 
			
			if(isValid(parseLocale(selectors.get(0)))) {
				return selectors.get(0);
			}
		}
		return null;
	}
	
	private Locale parseLocale(String locale) {
	  String[] parts = locale.split("_");
	  switch (parts.length) {
	    case 3: return new Locale(parts[0], parts[1], parts[2]);
	    case 2: return new Locale(parts[0], parts[1]);
	    case 1: return new Locale(parts[0]);
	    default: throw new IllegalArgumentException("Invalid locale: " + locale);
	  }
	}

	private boolean isValid(Locale locale) {
	  try {
	    return locale.getISO3Language() != null && locale.getISO3Country() != null;
	  } catch (MissingResourceException e) {
	    return false;
	  }
	}
}

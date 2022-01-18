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
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.via.ResourceSuperType;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.export.json.ExporterConstants;
import com.adobe.cq.wcm.core.components.models.ExperienceFragment;
import com.day.cq.wcm.api.Page;

import lombok.experimental.Delegate;


@Model(adaptables = { Resource.class,
		SlingHttpServletRequest.class }, adapters = ExperienceFragment.class, resourceType = CustomExperienceFragmentImpl.RESOURCE_TYPE, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)

@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)

public class CustomExperienceFragmentImpl implements ExperienceFragment {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomExperienceFragmentImpl.class);
	
	public static final String RESOURCE_TYPE = "adobeuxp/components/content/experiencefragment";
	public static final String AF_ACCEPT_LANG_PARAM = "afAcceptLang";
	public static final String AF_FORMS_LOCATION = "/content/forms/af";
	public static final String LANG_REGEX = "(\\/([\\w]{2})-([\\w]{2})\\/)|(\\/([\\w]{2})\\/)";
	
	@Self 
	@Via(type = ResourceSuperType.class) 
	@Delegate(excludes = DelegationExclusion.class) 
	private ExperienceFragment delegate;

	@Self
	private SlingHttpServletRequest request;

	@SlingObject
	private ResourceResolver resourceResolver;

	@ScriptVariable(injectionStrategy = InjectionStrategy.OPTIONAL)
	private Page currentPage;
	
	@Override
	public String getLocalizedFragmentVariationPath() {
		
		try { 
			if(StringUtils.startsWith(currentPage.getPath(), AF_FORMS_LOCATION) ) {
				String fragmentVariationPath = request.getResource().getValueMap().get(ExperienceFragment.PN_FRAGMENT_VARIATION_PATH, String.class);
				String lng = getLanguageParameter();
				LOGGER.debug("Language {}", lng);
				if(StringUtils.isNoneEmpty(fragmentVariationPath) && lng!=null) {
					LOGGER.debug("Current Page: {} FragmentVariationPath: {} Language: {}",currentPage.getPath(),fragmentVariationPath,lng);
					fragmentVariationPath = StringUtils.replaceFirst(fragmentVariationPath, LANG_REGEX, "/"+lng+"/");
					if(resourceExists(fragmentVariationPath)) {
						return fragmentVariationPath;
					}
					
				}
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return delegate.getLocalizedFragmentVariationPath();
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
		
	private boolean resourceExists(final String path) {
		return (StringUtils.isNotEmpty(path) && this.request.getResourceResolver().getResource(path) != null);
	} 
	
	private interface DelegationExclusion { 
		String getLocalizedFragmentVariationPath();
	}
}

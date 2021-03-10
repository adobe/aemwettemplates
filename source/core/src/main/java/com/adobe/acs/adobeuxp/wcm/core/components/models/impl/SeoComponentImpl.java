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

import com.adobe.acs.adobeuxp.wcm.core.components.models.SeoComponent;
import com.adobe.cq.export.json.ExporterConstants;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.api.NameConstants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;

@Model(
        adaptables = {SlingHttpServletRequest.class},
        adapters = {SeoComponent.class},
        resourceType = {SeoComponentImpl.RESOURCE_TYPE},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)


public class SeoComponentImpl implements SeoComponent {

	protected static final String RESOURCE_TYPE = "adobeuxp/components/structure/seo";
	private static final Logger LOGGER = LoggerFactory.getLogger(SeoComponentImpl.class);
	
	private static final String DEFAULTCOUNTRY = "Canada";
	private static final String DEFAULTINSTITUTION = "Goverment of Canada - Gouvernement du Canada";
	private static final String DEFAULTCREATOR = "Goverment of Canada - Gouvernement du Canada";
	private static final String DEFAULTSOURCE = "aem";

    
	@ScriptVariable
	protected com.day.cq.wcm.api.Page currentPage;
	
    @ScriptVariable
    private ValueMap properties;
	
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL, name = JcrConstants.JCR_TITLE)
    private String title;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL, name = JcrConstants.JCR_DESCRIPTION)
    private String description;
    
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL, name = JcrConstants.JCR_LASTMODIFIED)
    protected Calendar dateModified;
    
    
	@Override
	public String getDescription() {
		if (StringUtils.isBlank(description)) {
			return(currentPage.getDescription());			
        }
		return "";		
	}

	@Override
	public String getTitle() {
		if (StringUtils.isBlank(title)) {
            title = StringUtils.defaultIfEmpty(currentPage.getPageTitle(), currentPage.getTitle());
        }
		return title;
	}

	@Override
	public String getLanguage() {
        return currentPage == null ? Locale.getDefault().toLanguageTag()
                : currentPage.getLanguage(false).toLanguageTag();
	}

	@Override
	public String getDatemodified() {
        if (dateModified == null) {
        	dateModified = currentPage.getLastModified();
        }
        // Format return value per requirements
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        String strTemp = sdf.format(dateModified.getTime());                
        return (strTemp);
	}

	@Override
	public String getDateissued() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCountry() {
		// Return hard-coded value, implement logic per requirements  
		return DEFAULTCOUNTRY;
	}

	@Override
	public String getInstitution() {
		// Return hard-coded value, implement logic per requirements
		return DEFAULTINSTITUTION;
	}

	@Override
	public String getSource() {
		// Return hard-coded value, implement logic per requirements
		return DEFAULTSOURCE;
	}

	@Override
	public String getCreator() {
		// Return hard-coded value, implement logic per requirements
		return DEFAULTCREATOR;
	}
	
}

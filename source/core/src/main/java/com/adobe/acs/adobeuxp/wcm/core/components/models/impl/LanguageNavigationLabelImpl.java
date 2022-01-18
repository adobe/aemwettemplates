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

import javax.annotation.PostConstruct;


import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.acs.adobeuxp.wcm.core.components.models.LanguageNavigationLabel;
import com.adobe.cq.export.json.ExporterConstants;
import com.adobe.cq.wcm.core.components.util.AbstractComponentImpl;
import com.day.cq.wcm.api.Page;

@Model(
        adaptables = {SlingHttpServletRequest.class},
        adapters = {LanguageNavigationLabel.class},
        resourceType = {LanguageNavigationLabelImpl.RESOURCE_TYPE},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)


public class LanguageNavigationLabelImpl extends AbstractComponentImpl implements LanguageNavigationLabel {

	protected static final String RESOURCE_TYPE = "adobeuxp/components/content/languagenavigation";
	private static final Logger LOGGER = LoggerFactory.getLogger(LanguageNavigationLabelImpl.class);
	
	@ScriptVariable
	protected com.day.cq.wcm.api.Page currentPage;
	
	@ScriptVariable
    private ValueMap properties;
	
    private static final String SUBTITLE = "subtitle";
    private String subtitle;
  
    
    @PostConstruct
    protected void init() {
    	Page rootInstitutionPage = currentPage.getAbsoluteParent(2);
    	subtitle = rootInstitutionPage != null ? rootInstitutionPage.getProperties().get(SUBTITLE, String.class) : " ";
    }
    
    @Override
	public String getSubtitle() {
       	return (subtitle);  	
	}

}

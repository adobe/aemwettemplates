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
package com.adobe.acs.adobeuxp.wcm.core.components.models;

import static org.apache.sling.api.resource.ResourceResolver.PROPERTY_RESOURCE_TYPE;

import java.util.Locale;

import javax.annotation.PostConstruct;

import org.apache.http.client.fluent.Request;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.settings.SlingSettingsService;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

//@Model(adaptables = Resource.class)
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class})
public class HeaderModel {

    @OSGiService
    private SlingSettingsService settings;
    @SlingObject
    private Resource currentResource;
    @SlingObject
    private ResourceResolver resourceResolver;
    @SlingObject
    private SlingHttpServletRequest req;
    
    private String languageOtherPath;
    private String otherLanguage;
    
    //private String aa;
    private String otherLang;

    @PostConstruct
    protected void init() {
    	
    	//aa = req.getRequestPathInfo().getResourcePath();
    	//aa = req.getRequestURI();
    	languageOtherPath = req.getRequestURI();
       /* PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
        Page currentPage = pageManager.getContainingPage(currentResource);

        if (currentPage.getLanguage().equals(Locale.CANADA_FRENCH)) {
        	otherLanguage = Locale.ENGLISH.getLanguage();
        	languageOtherPath = currentPage.getPath().replace("/fr_ca", "/en");
        }
        else {
        	otherLanguage = Locale.CANADA_FRENCH.getLanguage();
        	languageOtherPath = currentPage.getPath().replace("/en", "/fr_ca");
        }*/
    	//if(aa.contains("/en/")||aa.endsWith("en.html")) {
    	if(languageOtherPath.contains("/en/")||languageOtherPath.endsWith("en.html")) {
    		//aa = aa.replaceFirst("/en", "/fr_ca");
    		languageOtherPath = languageOtherPath.replaceFirst("/en", "/fr_ca");
    		otherLanguage = "Francais";
    		otherLang = "fr_ca";
    	}else {
    		//aa = aa.replaceFirst("/fr_ca", "/en");
    		languageOtherPath = languageOtherPath.replaceFirst("/fr_ca", "/en");
    		otherLanguage = "English";
    		otherLang = "en";
    	}
    	
    }

    public String getLanguageOtherPath() {
        return languageOtherPath;
        //return aa;
    }
    
    public String getOtherLanguage() {
    	return otherLanguage;
    	//return aa;
    }
    
    public String getOtherLang() {
    	return otherLang;
    	//return aa;
    }
}

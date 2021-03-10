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

import java.util.Locale;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.jetbrains.annotations.Nullable;

import com.adobe.acs.adobeuxp.wcm.core.components.models.QuickSearchModel;
import com.day.cq.wcm.api.Page; 

@Model(
	    adaptables = SlingHttpServletRequest.class,
	    adapters = {QuickSearchModel.class},
	    resourceType = QuickSearchModelImpl.RESOURCE_TYPE
	)
public class QuickSearchModelImpl implements QuickSearchModel{
	public static final String RESOURCE_TYPE = "adobeuxp/components/content/quickSearch";
 
	@ScriptVariable
	protected Page currentPage;
	
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    @Nullable
    private String actionURL;
    
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    @Nullable
    private String label;
    
    @Override
    @Nullable
    public String getActionURL(){
    	return actionURL;
    }
    
    @Override
    @Nullable
    public String getLabel(){
    	return label;
    }
    
    @Override
    public String getLang(){
    	return currentPage == null ? Locale.getDefault().toLanguageTag()
                : currentPage.getLanguage(false).toLanguageTag();
    }
}
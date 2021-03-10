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

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;

import com.adobe.acs.adobeuxp.wcm.core.components.models.impl.LayoutContainerUXPImpl;
import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.export.json.ContainerExporter;
import com.adobe.cq.wcm.core.components.models.LayoutContainer;
import com.day.cq.wcm.foundation.model.responsivegrid.ResponsiveGrid;

@Model(
        adaptables = {SlingHttpServletRequest.class},
        adapters = {ContainerExporter.class, ComponentExporter.class},  
        resourceType = WetResponsiveGrid.RESOURCE_TYPE,  
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL 
)

public class WetResponsiveGrid extends ResponsiveGrid {
	protected static final String RESOURCE_TYPE = "adobeuxp/components/content/responsivegrid";
	
	@ScriptVariable
    private Resource resource;
	
    /**
     * Class names of the WET responsive grid
     */
    private String wetClassNames;
    
    
    
	@PostConstruct
    protected void initModel() {
		super.initModel();
		
	}
	
	public String getWetGridClassName(){
		return wetClassNames +  "columns";
	}
}

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

import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.via.ResourceSuperType;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.jetbrains.annotations.NotNull;
import lombok.experimental.Delegate;
import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.wcm.core.components.models.LayoutContainer;

/**
 * Layout container model implementation.
 */

@Model(
        adaptables = {Resource.class, SlingHttpServletRequest.class},
        adapters = LayoutContainer.class,  
        resourceType = LayoutContainerUXPImpl.RESOURCE_TYPE,  
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL 
)

public class LayoutContainerUXPImpl implements LayoutContainer{
	/**
     * Standard logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LayoutContainerUXPImpl.class);
    
	protected static final String RESOURCE_TYPE = "adobeuxp/components/content/wcontainer";
	
	//private static final String ID_PREFIX = "wContainer";
	
	String PN_CONTAINER = "container";
	String CONTAINER_ID = "id";
	
    enum ContainerType {
        DEFAULT("default"),
        SECTION("section"),
        CONTAINER("container"),
        MAIN("main"),
        MAIN_HOME("mainhome"),
        ROW("row"),
        ROWEQH("roweqh"),
        HEADER("header"),
        FOOTER("footer"),
        NAV("navigation")
        ;

        private String container;

        ContainerType(String container) {
            this.container = container;
        }
        
        public String getContainerType() {
            return container;
        }

        public static ContainerType getContainerType(String container) {
            for (ContainerType containerType : values()) {
                if (containerType.container.equals(container)) {
                    return containerType;
                }
            }
            return null;
        }
    }

    /**
     * Component properties.
     */
    @ScriptVariable
    private ValueMap properties;
    
	@Self 
	@Via(type = ResourceSuperType.class)  
	 
	@Delegate(excludes = DelegationExclusion.class)
    private LayoutContainer delegate;
	
	private ContainerType containerType;
	
	private String id;
	 
    @Override
    public @NotNull LayoutType getLayout() {
        return delegate.getLayout();
    }
	
    public @NotNull ContainerType getContainerType() {
    	// LOGGER.debug("INTO getContainerType"+containerType.getContainerType() );
        return containerType;
    }
    
    @Override
    public String getExportedType() {
        return LayoutContainerUXPImpl.RESOURCE_TYPE;
    }
    
    @Override
    public String getId() {
        if (id == null) {
            this.id = properties.get(CONTAINER_ID, String.class);
        }
        return id;
    }
    
    @PostConstruct
    protected void initModel() {
    	this.containerType =ContainerType.getContainerType(properties.get(PN_CONTAINER, String.class));
        	
    }
    
    private interface DelegationExclusion { 
    	LayoutType getLayout();  
    }


}

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

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.via.ResourceSuperType;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.adobe.acs.adobeuxp.wcm.core.components.models.CustomListItemModel;
import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.export.json.ExporterConstants;
import com.adobe.cq.wcm.core.components.models.List;
import com.adobe.cq.wcm.core.components.models.ListItem;
import com.day.cq.wcm.api.designer.Style;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.experimental.Delegate;

@Model(
        adaptables = {Resource.class, SlingHttpServletRequest.class},
        adapters = {List.class, ComponentExporter.class},  
        resourceType = GCListImpl.RESOURCE_TYPE,  
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL 
)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)

public class GCListImpl implements List {
	/**
     * Standard logger.
     */
	private static final Logger LOGGER = LoggerFactory.getLogger(GCListImpl.class);
	
	private static final String ID_PREFIX = "GCList";
	
	protected static final String RESOURCE_TYPE = "adobeuxp/components/content/gcList";
	
	private static final String LIST_CUSTOM_TYPE="custom";
	
	@Self 
	@Via(type = ResourceSuperType.class)  
	@Delegate(excludes = DelegationExclusion.class)
    private List delegate;

    /**
     * The current style.
     */
    @ScriptVariable
    private Style currentStyle;
    
    /**
     * Component properties.
     */
    @ScriptVariable
    private ValueMap properties;
    

    @Inject
    @Named("customListItems")
	@Via("resource")
    private java.util.List<CustomListItemModel> customListItems;
    
	/**
     * Result list.
     */
    private Collection<ListItem> listItems;
    
    
	@NotNull
	private String getListType() {
        return java.util.Optional.ofNullable(
        		java.util.Optional.ofNullable(properties.get(PN_SOURCE, String.class))
                .orElseGet(() -> java.util.Optional.ofNullable(currentStyle.get(PN_SOURCE, String.class))
                    .orElse(null)))
            .orElse(null);
    }
	
	@NotNull
	public String getItemTemplate(){
		String type;
		switch (getListType()) {
			case LIST_CUSTOM_TYPE: 
				type = "_"+getListType();
				break;
			default:
				type="";
				break;
		}
		return type;
	}
	
	
	@Override
    @NotNull
    @JsonProperty("items")
    public Collection<ListItem> getListItems() {
		if(delegate.getListItems().isEmpty()){
			if(getListType()!=null && getListType().equals(LIST_CUSTOM_TYPE)){
				if(customListItems!=null){
					return customListItems.stream().filter(Objects::nonNull).collect(Collectors.toList());
				}
				return listItems;
			}
		}
		return delegate.getListItems();
	}

    private interface DelegationExclusion {  
    	List getListItems();
    	
    }

}

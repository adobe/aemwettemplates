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

import java.io.Serializable;

import javax.inject.Inject;
import javax.jcr.Node;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.wcm.core.components.models.ListItem;

 
@Model(adaptables = Resource.class)
public class CustomListItemModel implements ListItem, Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomListItemModel.class);
	
	@Self
    private Node node;
	
	@Inject
	@Optional
    private String title;
	
	@Inject
	@Optional
    private String description;
	
	@Inject
	@Optional
    private String url;
	
	
    public CustomListItemModel(){
    	//nothing
    }
	@Override
    public String getTitle() {
		return title;
	}
	
	@Override
    public String getDescription() {
        return description;
    }
	
	 @Override
	 public String getURL() {
		 if( !url.startsWith("http") && !url.endsWith(".html")) {
			 return url +".html";
         }
	     return url;
	 }
	 
	 @Override
	 public String getPath() {
	        return url;
	 }

	 @Override
	 public String getName() {
	        return title;
	 }
}

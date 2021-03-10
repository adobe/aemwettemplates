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
package com.adobe.acs.adobeuxp.wcm.core.rewriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.osgi.service.component.annotations.Component;

import org.apache.sling.rewriter.Transformer;
import org.apache.sling.rewriter.TransformerFactory;


@Component(property = { "pipeline.type=wetgridmapping" }, service = { TransformerFactory.class })
public class WetGridMappingTransformerFactory implements TransformerFactory {
	
	private static final Logger LOG = LoggerFactory.getLogger(WetGridMappingTransformerFactory.class);
	
	@Override
	public Transformer createTransformer() {
		LOG.trace("create WetGridMappingTransformerFactory");
		return new WetGridMappingTransformer();
	}
 

}

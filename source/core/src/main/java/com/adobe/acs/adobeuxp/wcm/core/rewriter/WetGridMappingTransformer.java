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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.swing.text.html.HTML;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.rewriter.DefaultTransformer;
import org.apache.sling.rewriter.ProcessingComponentConfiguration;
import org.apache.sling.rewriter.ProcessingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import com.google.gson.Gson;

public class WetGridMappingTransformer extends DefaultTransformer {

	private static final Logger LOG = LoggerFactory.getLogger(WetGridMappingTransformerFactory.class);
	
	//private ContentHandler contentHandler;
	protected static final String NO_COL = "noCol";
	
	private HashMap<String, Object> includeMappingArray;
	private HashMap<String, Object> replaceClassesArray;
    protected List<String> validComponentClassesArray = new ArrayList<String>();
    protected List<String> removedClassesArray = new ArrayList<String>();

	@Override
	public void startElement(String uri, String localName, String raw, Attributes atts) throws SAXException {
		try{
			Attributes updatedAttributes = updateAttributes(uri, localName, atts);
			super.startElement(uri, localName, raw, updatedAttributes);
		} catch (Exception e) {
            LOG.error("startElement() : ", e);
            throw e;
        }
        	
        	


    
           
	}

    private Attributes updateAttributes(final String uri, final String localName, final Attributes attributes) throws SAXException {
    	AttributesImpl updatedAttributes = new AttributesImpl(attributes);
    	
    	
        for (int i = 0; i < updatedAttributes.getLength(); i++) {
        	if(StringUtils.equalsAnyIgnoreCase(updatedAttributes.getLocalName(i), HTML.Attribute.CLASS.toString())) {
        		String originalClass = updatedAttributes.getValue(i);
        		String[] classArr = StringUtils.split(originalClass," ");
        		
        		if(validComponentClassesArray.contains(classArr[0])){
	        		
	        		String newClass ="";
	        		
	        		for(int j =0; j < classArr.length;j++){
	        			if(includeMappingArray.containsKey(classArr[j]) && !ArrayUtils.contains(classArr, NO_COL)){
	        				 
	        				String cssClass = includeMappingArray.get(classArr[j]).toString();
	        				newClass += classArr[j] + " " + cssClass + " ";
	        				
	        			}else{
	        				if(replaceClassesArray.containsKey(classArr[j])){
		        				String cssClass = replaceClassesArray.get(classArr[j]).toString();
		        				newClass += cssClass.trim() + " ";
		        			}else{
		        				newClass += classArr[j] + " ";
		        			}
	        			}
	        		} 
	        		updatedAttributes.setValue(i,newClass.trim());
        		}else{
        			updatedAttributes.setValue(i,originalClass.trim());
        		}
                 
            }
        }
        
        
    	return updatedAttributes;
    }
    
    
    
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		 super.endElement(uri, localName, qName);

	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);

	}
 

	@SuppressWarnings("unchecked")
	@Override
	public void init(ProcessingContext context, ProcessingComponentConfiguration config) throws IOException {
		LOG.info("INIT PROCESSING");
		
		ValueMap configuration = config.getConfiguration();
		String includeMapping = configuration.get("includeClassesMapping", String.class); 
		String replaceClasses = configuration.get("replaceClasses", String.class); 
		
		String[] validClasses = configuration.get("validComponentClasses", String[].class);
		String[] removedClasses = configuration.get("removedClasses", String[].class);
		
		if (includeMapping != null) {
			includeMappingArray = new Gson().fromJson(includeMapping, HashMap.class);
		}
		if (replaceClasses != null) {
			replaceClassesArray = new Gson().fromJson(replaceClasses, HashMap.class);
		}
		if (removedClasses != null) {
			removedClassesArray.addAll(Arrays.asList(removedClasses));
		}
		if (validClasses != null) {
			validComponentClassesArray.addAll(Arrays.asList(validClasses));
		}
		
		super.init(context, config);
		 
	}

 

}

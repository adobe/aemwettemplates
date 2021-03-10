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

public interface SeoComponent {

	/***
    * @return a string to display page description
	*/
	String getDescription();
	
	/***
    * @return a string to display page title
	*/
	String getTitle();
	
	/***
    * @return a string to display page language
	*/
	String getLanguage();
	
	/***
	* @return a string to display last modified date
	*/
	String getDatemodified();		
	
	/***
	* @return a string to display date issues 
	*/
	String getDateissued();		
	
	/***
	* @return a string to display country 
	*/
	String getCountry();		
	
	/***
	* @return a string to display institution  
	*/
	String getInstitution();		
	    
	/***
	* @return a string to display source  
	*/
	String getSource();		
    
	/***
	* @return a string to display creator  
	*/
	String getCreator();		
    
}

/*
 * Copyright 2020 Adobe. All rights reserved.
 * This file is licensed to you under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License. You may obtain a copy
 * of the License at http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under
 * the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR REPRESENTATIONS
 * OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
"use strict";

use(function() {
    var CONST = {
        PROP_DATE_CREATED: 'jcr:created',
        PROP_DATE_MODIFIED: 'cq:lastModified',
        PROP_DATE_PUBLISHED: "gcLastPublished",
        PROP_DATE_ISSUED: "gcIssued",
        PROP_DATE_MODIFIED_IS_OVERRIDDEN: 'gcModifiedIsOverridden',
        PROP_MODIFIED_OVERRIDE_DATE: 'gcModifiedOverride'
    };

    var data = {};

    function getLastModified() {
        var modified = extractDate(CONST.PROP_DATE_MODIFIED, "");
    	var published = extractDate(CONST.PROP_DATE_PUBLISHED, "");
    	var issued = extractDate(CONST.PROP_DATE_ISSUED, "");
    	var created = extractDate(CONST.PROP_DATE_CREATED, "");
    	var modifedOverridden = pageProperties.get(CONST.PROP_DATE_MODIFIED_IS_OVERRIDDEN, "");

        if (modifedOverridden == "true") {
            /* # Use Overriden Last Modified Date. */
            var newModified = extractDate(CONST.PROP_MODIFIED_OVERRIDE_DATE, "");
            if (newModified != undefined && newModified != "") {
                modified = newModified;
            }
        } else {
            if (published != "" && published != undefined) {
                /* # Use New Last Published Property Date. */
                modified = published;
            } else {
                if (modified == "" || modified == undefined) {
                    /* # Use Created Date.*/
                    if (created !="" && created != undefined) {
                        modified = created;
                    }
                }
            }
        }

		return modified;
    }

    function extractDate(dateProp){

        var dateStr = "";
        try {
            if(pageProperties.get(dateProp, "") == "" || pageProperties.get(dateProp, "") == "null"){
                dateStr = "";
            }
            else{
                var dateObj;
                dateStr = pageProperties.get(dateProp, Date.class);
                dateStr = dateStr.substring(0,10);
            }
        } catch (err) {
			dateStr = "";
        }

        return dateStr;
    }

    function formatDate(date) {
        var yyyy = date.getFullYear();
        var mm = ("0" + (date.getMonth() + 1)).slice(-2);
        var dd = ("0" + (date.getDate())).slice(-2);

        return yyyy + "-" + mm + "-" + dd;
    }

    data.lastModified = getLastModified();
    data.lang = currentPage.getLanguage(false).getLanguage();
    data.CONST = CONST;

    return data;
});
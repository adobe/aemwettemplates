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
   var data = {};
    var CONST = {
        PROP_HIDEREPORTPROBLEM: "hideReportProblem",
        PROP_HIDESHAREPAGE: "hideSharePage"
    };

    /*  There is also a class change in page.js checking for homepage  */
    function getDateModClass() {
        var template = pageProperties.get("cq:template", "");
        if (template == "/conf/uxp/settings/wcm/templates/home-page-3") {
            return "container";
        }

        return null;
    }

    data.dateModClass = getDateModClass();
    data.lang = currentPage.getLanguage(false).getLanguage();
    data.hideReportProblem = currentPage.properties[CONST.PROP_HIDEREPORTPROBLEM]=="true" || currentPage.properties[CONST.PROP_HIDEREPORTPROBLEM] == true;
    data.hideSharePage = currentPage.properties[CONST.PROP_HIDESHAREPAGE] == "true" || currentPage.properties[CONST.PROP_HIDESHAREPAGE] == true;    
    return data;
});
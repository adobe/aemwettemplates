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

    var page =  currentPage.path+".html" || "None";
    var lang = pageProperties.get("jcr:language", pageProperties.get("gcLanguage", "None"));
    var title = pageProperties.get("jcr:title", "None");
    var owner = pageProperties.get("gcCreator", "None/None");

    var rapFormPage = "/content/canadasite/reportaproblem/en/reportaproblem/jcr:content/par/mwsgeneric_base_html"; //eng
    if (lang == "fra" || lang == "fr") {
        rapFormPage = "/content/canadasite/reportaproblem/fr/reportaproblem/jcr:content/par/mwsgeneric_base_html"; //fra
    }

    var rapTextNode = currentSession.getNode(rapFormPage);
    if (rapTextNode.hasProperty("text")) {
        data.formHtml = rapTextNode.getProperty("text").getString();
        // Escape special characters in title MWS-980
        data.formHtml = data.formHtml.replace("${PAGE_TITLE}", org.apache.commons.lang3.StringEscapeUtils.escapeHtml4(title));
        data.formHtml = data.formHtml.replace("${SUBMISSION_PAGE}", page);
        data.formHtml = data.formHtml.replace("${LANG}", lang);
        data.formHtml = data.formHtml.replace("${PAGE_OWNER}", owner);
    }

    return data;

});
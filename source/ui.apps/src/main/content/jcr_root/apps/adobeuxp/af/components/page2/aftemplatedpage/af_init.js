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

/**
 * Initializes Adaptive form metadata information
 */
"use strict";
var global = this;
(function () {
    if (global.Packages) {
        var guideContainerPath = global.Packages.com.adobe.aemds.guide.taglibs.GuideELUtils.getGuideContainerPath(request, resource);
        if (guideContainerPath) {
            var formDAMAssetMetadataPath = global.Packages.com.adobe.aemds.guide.utils.GuideUtils.convertGuideContainerPathToFMAssetMetadataPath(guideContainerPath);
            var formMetadataResource = request.getResourceResolver().getResource(formDAMAssetMetadataPath);
            if (formMetadataResource) {
                var metadata = formMetadataResource.adaptTo(global.Packages.org.apache.sling.api.resource.ValueMap);
                var creatorTool = metadata.get(global.Packages.com.adobe.aemds.guide.utils.GuideConstants.FM_DAM_FD_GENERATOR, global.Packages.com.adobe.aemds.guide.utils.GuideConstants.FM_DAM_DEFAULT_GENERATOR);
            }
        }
    }
    return {
        creatorTool : creatorTool
    };
})();

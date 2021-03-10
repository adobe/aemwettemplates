<%@taglib prefix="cq" uri="http://www.day.com/taglibs/cq/1.0" %>
<%@taglib prefix="sling" uri="http://sling.apache.org/taglibs/sling/1.0" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%--
  ~
  ~ Copyright 2020 Adobe. All rights reserved.
  ~
  ~ This file is licensed to you under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License. You may obtain a copy
  ~ of the License at http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software distributed under
  ~ the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR REPRESENTATIONS
  ~ OF ANY KIND, either express or implied. See the License for the specific language
  ~ governing permissions and limitations under the License.
  --%>
<%@include file="/libs/fd/af/components/guidesglobal.jsp" %>
<%@ page import="com.adobe.aemds.guide.taglibs.GuideELUtils,
                com.adobe.aemds.guide.utils.GuideUtils,
                com.adobe.aemds.guide.utils.GuideConstants,
                com.day.cq.wcm.api.Page,
                org.apache.sling.api.resource.Resource,
                com.day.cq.wcm.api.Template" %>
<cq:defineObjects/>
<%@page session="false" %>
<%
    String guideContainerPath = GuideELUtils.getGuideContainerPath(slingRequest, resource);
    Resource guideContainerResource = resourceResolver.resolve(guideContainerPath);
    String lang = GuideELUtils.getLocale(slingRequest, guideContainerResource);
    // in case resource is within template node :
    Resource templateResource = resource.getParent().getParent();
    Template templateNode = templateResource.adaptTo(Template.class);
    boolean isTemplate = templateNode!=null ? templateNode.hasStructureSupport() : false;
    boolean isWebDocument = !isTemplate && guideContainerResource.isResourceType(GuideConstants.RT_WEB_DOCUMENT_CONTAINER);
    String documentName = "";
    if(isWebDocument){
        int rhsIndex = guideContainerPath.lastIndexOf("/channels/");
        if(rhsIndex>-1){
            String prefixPath = guideContainerPath.substring(0, rhsIndex);
            Page pageResource = resourceResolver.resolve(prefixPath).adaptTo(Page.class);
            if(pageResource!=null){
                documentName = pageResource.getTitle();
            }
        }
    }
%>
<!DOCTYPE html>
<html lang="<%= lang %>">
<% if(isWebDocument){ %>
    <title> <%= documentName + " - " + (currentPage.getTitle() == null ?
    xssAPI.encodeForHTML(currentPage.getName()) : xssAPI.encodeForHTML(currentPage.getTitle())) %> </title>
<% }  %>
<cq:include script="fallbackLibrary.jsp"/>
<%-- Adobe Target relevant clientlibs: --%>
<c:if test="<%=GuideUtils.isAdobeTargetConfigured(resource)%>">
    <sling:include path="contexthub" resourceType="granite/contexthub/components/contexthub"/>
    <%-- @TODO Remove cq.shared as this is a heavy library. Tracked via CQ-4219972 --%>
    <ui:includeClientLib categories="cq.shared"/>
    <cq:include script="/libs/cq/cloudserviceconfigs/components/servicelibs/servicelibs.jsp"/>
</c:if>
<cq:include script="afpagecontent.html"/>
</html>
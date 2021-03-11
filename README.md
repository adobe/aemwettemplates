# Adobe AEM - WET Compatible Website Templates and Components 

## Introduction

This project leverages Adobe AEM Sites and AEM Forms to create a citizen facing web interface to provide online services.

The web interface is built using Adobe recommended best practices and leverages Government of Canada approved technology and UX standards.

The web interface includes the following:

* A set of AEM Sites WET compliant page templates and components that are using the latest [Adobe Core Components](https://docs.adobe.com/content/help/en/experience-manager-core-components/using/introduction.html). These WET compliant templates and components are used to build the informational part of the web interface.

* The templates are based on [editable templates](https://helpx.adobe.com/ca/experience-manager/6-4/sites/developing/using/page-templates-editable.html)
    
* A set of AEM Forms WET compliant templates and components. These components are used to create the data capture/wizard interfaces to capture end-user data.

* A sample bilingual website structure that leverages AEM to maintain dependency between languages.

* A User service section that provides a list of available forms as well as forms that were submitted or saved as draft

Web Experience Toolkit (WET) includes reusable components for building and maintaining innovative websites that are accessible, usable, and interoperable (more details around WET can be found at https://github.com/wet-boew/wet-boew). Any web sites hosted for Government of Canada must create WET compatible HTML to meet WET accessibility requirements. 

Purpose of this project is to allow AEM Authors to use “basic” set of Editable Templates / Core Components to create WET compatible pages.  


**Sample page create with the package:**

![GCHome](https://user-images.githubusercontent.com/39708218/110717535-4164aa00-81d7-11eb-8df1-8e75188ac94a.JPG)
<br>
<br>
**Sample form created with the package:**

![AF](https://user-images.githubusercontent.com/39708218/110816097-0905b000-8259-11eb-9bd1-ae3a354c6943.JPG)



## Usage

### Prerequisites/ Initial Setup

Following is list of dependencies:
  *	Application is developed/compiled for AEM 6.5 SP7
  * Corresponding AEM Forms add-on package should be deployed
  * AEM Core Components package is included as part of ui.apps (current version is specified in “main” pom.xml)
  * Required WET CSS/JS files are referenced from www.canada.ca

As part of initial setup, following packages should be deployed to AEM instance:
  * ui.apps 
  * ui.content
  
  
    
## Modules

The main parts of the application are:

* core: Java bundle containing all core functionality like OSGi services, Sling rewriter and component-related Java code.
* ui.apps: contains the /apps parts of the project, ie JS&CSS clientlibs, components, runmode specific configurations 
    * AEM Core Components are embedded and deployed as part of ui.apps 
    * Required WET CSS/JS files are referenced from www.canada.ca
* ui.content: contains editable template configuration and sample content using the components from the ui.apps
    * Pages 		     /content/uxp		
	* Assets 		     /content/dam/uxp
	* Forms 		     /content/forms/af/uxp
	* Ex. Fragments 	 /content/experience-fragments/uxp
    * Editable templates /conf/uxp    


## How to build

Note: Set JAVA_HOME to Java 1.8
To build all the modules run in the project root directory the following command with Maven 3:

    mvn clean install

If you have a running AEM instance you can build and package the whole project and deploy into AEM with

    mvn clean install -PautoInstallPackage

Or to deploy it to a publish instance, run

    mvn clean install -PautoInstallPackagePublish

Or alternatively

    mvn clean install -PautoInstallPackage -Daem.port=4503

Or to deploy only the bundle to the author, run

    mvn clean install -PautoInstallBundle

## Maven settings

The project comes with the auto-public repository configured. To setup the repository in your Maven settings, refer to:

    http://helpx.adobe.com/experience-manager/kb/SetUpTheAdobeMavenRepository.html

### Contributing

Contributions are welcomed! Read the [Contributing Guide](./.github/CONTRIBUTING.md) for more information.

### Licensing

This project is licensed under the Apache V2 License. See [LICENSE](LICENSE) for more information.

Project Description
===================
*(last update: Oct 30th, 2013)*

*VotesManager* project is an open-source mobile (tablet-targeted) application to aggregate votes.

VotesManager is a mobile application that helps humans in vote aggregation, a process typical in jury-based competitions. The complete application will provide an easy-to-use interface, providing real-time classification, simple (wizard-based) configuration, and aggregation through sum, arithmetic average, weighted average and more complex formulas.

*In the current version, the application only can aggregate votes using sum and arithmetic average. All configuration is to be done by modifying the XML file the application produces upon votes submit.*

Features and Requirements
-------------------------
*Features*  
* Sum-based vote aggregation
* Average-based vote aggregation
* (planned, partly done) Competitor number and name configuration
* (planned, partly done) Voter number and name configuration
* (planned) Weighted sum and average to aggregate votes
* (planned) Wizard-based initial configuration

3rd and 4th points are marked as "partly done" as Competitors and Voters are only configurable through XML file.  

*Requirements*
* Android 3.0 (Honeycomb) or higher

Usage Scenarios
---------------

Three figures appear during the lifetime of the application: the *aggregator*, i.e., the application user; the *voter*, the one who submits a vote; and the *competitor*.

The application user will receive the evaluations from the voters offline - in the basic version scenario; She must then aggregate the votes in order to create a competitor classification. In this way, without using other and more complex software (such as LibreOffice) and with just an easy-to-use, lightweighted tablet, she can easily take her task to the end in few minutes.  
The list-and-detail layout, to be provided for landscape orientation, will let the user take the whole situation under control, by monitoring how many competitors have been voted, and by looking at the provisional classification in every moment.

The application can also be used by jurors to take their votes, instead of the vote aggregator; In this case, the voter can aggregate various parameters (formerly the voters' fields) and thus obtain an overall evaluation of the single competitor, eventually having her own and personal classification.

In further releases, other useful functionalities will be developed, such as, a configuration wizard, the creation of reports about the voting process, inter-device communication, and the like.

*Any other suggested use will be inserted in future releases of this document.*

Development State
-----------------
The application is *to be released*.  
Just a very basic version will be released, mostly in order to make the project available to the people - and some code readable for potential developers.

Functional analysis documents were released, reporting both basic and advanced features, to let the (hopefully big) Community to choose what to work on - including the documentation itself.

Documentation
-------------
A more-or-less formal [analysis](https://github.com/dturrina/votesmanager/blob/master/REQUIREMENTS.md) in form of requirement list has been written, showing the potentiality the project may have.  

Developers can access [Developers Guidelines](https://github.com/dturrina/votesmanager/blob/master/DEV_GUIDELINES.md) and learn the planned way to contribute to the project.  

Discussion
----------
In this very early phase of the project, just some mailing lists are provided (thanks to FreeLists for their service):  

Development Mailing List  
You can subscribe to the Development Mailing List by sending an email to dev-votesmanager-request at freelists dot org with "subscribe" in the Subject field, OR by visiting the list page on [freelists](http://www.freelists.org/list/dev-votesmanager).  
You can post on the mailing list sending emails to dev-votesmanager at freelists dot org - The mailing list is moderated, in order to prevent spam, but all legitimate messages will get through it.

Bugs Mailing List  
You can subscribe to the Bugs Mailing List by sending an email to bugs-votesmanager-request at freelists dot org with "subscribe" in the Subject field, OR by visiting the list page on [freelists](http://www.freelists.org/list/bugs-votesmanager).  
You can post on the mailing list sending emails to bugs-votesmanager at freelists dot org - The mailing list is moderated, in order to prevent spam, but all legitimate messages will get through it.

Announcements Mailing List  
You can subscribe to the Development Mailing List by sending an email to announce-votesmanager-request at freelists dot org with "subscribe" in the Subject field, OR by visiting the list page on [freelists](http://www.freelists.org/list/announce-votesmanager).  
This mailing list is for announcements only.

These are the only and best places where to discuss about the future of VotesManager.

Other Resources
---------------
[GitHub project page](http://dturrina.github.io/votesmanager)  
[GitHub author page](https://github.com/dturrina)  

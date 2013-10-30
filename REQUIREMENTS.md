Requirements
============
*(last update: Oct 30th, 2013)*

A formal requirements specification lets all users and developers to be aware of the features the project has - and will have in future releases, and also to make a quick review of what the development state is.  
Moreover, it is possible for volunteers to plan what to concentrate on in the early stages of development process.

Requirements are differentiated in four groups: the functional ones represent what the application will do; interface requirements tell how the app will appear to the final user; design requirements are a basic guideline for developers; inverse requirements state what the application must not do.  
For clarity purpose, each requirement has a unique number.

In the following statements, this glossary is to be considered:

* *voter* is the person submitting votes;
* *competitor* is the person the vote is given to - it may also be a parameter;
* *user* is the one using the application, i.e., the person who aggregates votes.

When a requirement number is followed by parenthesis with another requirement number, it means the further depends from the latter's implementation.

Also, three priority levels are given:

* MUST indicates the requirement to be absolutely implemented - someday
* SHOULD stands for strongly suggested, but not necessary, requirements
* MAY is for optional stuff

As development state goes on, MUST requirements will be implemented, this meaning that SHOULD ones gain priority, and so on.  
By the way, this list is all but exhaustive: anyone can improve it - or suggest some points to be canceled. Software is an evolving entity.

Functional Requirements
-----------------------

FR01 (partly done) The number of voters MUST be changeable and configurable  
FR02 (partly done) The name of voters MUST be changeable and configurable  
FR03 Each voter SHOULD have a variable and configurable weight  
FR04 (partly done) The number of competitors MUST be changeable and configurable  
FR05 (partly done) The name of competitors MUST be changeable and configurable  
FR06 Each competitor MAY have an equal extra fields number  
FR07(FR06) The content of extra fields SHOULD be variable and configurable  
FR08 (done) The application MUST provide sum and arithmetic average for vote aggregation  
FR09(FR03) The application SHOULD provide weighted average for vote aggregation  
FR10 (done) The application MUST show the complete classification at the end of voting process  
FR11 (done) The application SHOULD be able to show the complete classification in any moment  
FR12 The application MAY show a partial (top *n*) classification  
FR13(FR12) The application MAY show a partial (top *n*) classification in any moment  
FR14 The classification SHOULD be modifiable by the user, at the end of the voting process  
FR15 Each competitor MAY have a variable and configurable weight  
FR16 Votes MAY be given within a configurable range  
FR17(FR16) The application MUST notify out-of-range votes  
FR18 The application MAY provide special values for not classifiable and absent voter  
FR19 The application MAY consider an overall coefficient, to be applied on all given votes  
FR20 The application MAY aggregate votes without considering best and/or worst *m* votes  
FR21 The votes MAY express a yes/no judgment  
FR22 The votes MAY express a voter-related "top *n*" classification  
FR23 The application MAY provide options for classification normalization  
FR24 (partly done) The application MAY provide import/export functionalities for names, votes, classification, and reports  
FR25 The application MAY provide sharing functionalities  
FR26 Configuration MAY be password-protected  

Interface Requirements
----------------------

IF01 The application SHOULD show a logo on each screen  
IF02(IF01) The logo MUST be configurable  
IF03 The classification SHOULD highlight top *n* elements  
IF04(IF03) The number of highlighted elements MUST be configurable  
IF05 The application MAY use a 3-or-more colors theme  
IF06(IF05) The color theme MUST be configurable  
IF07 The application SHOULD open on configuration screen, should the configuration be the default one  
IF08 (done) The application layout MUST support tablets  
IF09 The application layout MAY support smartphones  
IF10 The tablet layout SHOULD differentiate between portrait and landscape orientation  
IF11 A configuration wizard MAY be provided  
IF12(FR16) Fixed-range votes MAY be expressed with graphical elements instead of numbers  
IF13 All application text (i.e. labels, titles, and the like) SHOULD be translated at least in Italian language  

Design Requirements
-------------------

DR01 Configuration screen or wizard MUST be reachable from any point when the application is running  
DR02 (done) Aggregation formulas MUST lie in a separate class  
DR03 The number of duplicate lines MUST be reduced to the minimum, encouraging utility classes  

Inverse Requirements
--------------------

IR01 The application MUST NOT fail without giving informations about what happened.
IR02(IR01) The cause of failures SHOULD NOT be the error stacktrace.

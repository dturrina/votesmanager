Project Management
==================

As an Open Source project, it is important to immediately point out the role the different components of the Community will have.  
Besides the usual roles, this project tends to spread the concept of collaboration: not only the Community has a fundamental role, but also the Management Team will be composed by many people and will tightly work together for the project to grow up in size and functionalities.

Management Team
---------------
Decisions should never be taken by individuals. Thus, the creation of a Management Team is to be planned.

The Management Team must be formed by:

* One person who represents the potential user;
* At least one developer for each platform the project will be developed for.

The role of the Management Team is to evaluate and accept (or eventually reject) project-wide or multi-platform-related Community submissions.  
Communications between the Managemet Team members are to be published, in the worst case just after the decision took place.

The developers being part of the Management Team will automatically act as Subproject Manager, the Subproject being the branch of project regarding a particular platform.  
Subproject Managers are the only people who could eventually reject a Community submission (see below), as long as it does not affect other Subprojects.  
Subproject Managers will assign tasks to people and control their development and realization. For no reason but a developer's request or reiterate unanswered solicitations, the developer can be removed from a task after its assignment.

Other Managers
--------------

Should the project grow up enough, some other specialized figures are to contribute to the project management.

* A *Documentation Manager* will care about the consistence between development and documentation status
* A *Patch Manager* will probably become necessary when many patches will be provided, in order to both submit them to committers and track possible duplicates
* A *Translation Manager* will coordinate different documentation and application translation, and will cooperate with both Documentation Manager and Management Team
* An *Issue Manager* will check issue tracker and talk to the Management Team to plan solution releases
* A *FAQ Manager* will provide a point of contact for FAQ requests and adding

All these figures are to be considered when the project will have a widespread community and tons of contributors. In this first stage, all roles will be provided within the Management Team.  
The group of managers may eventually take the place of the Management Team, should everyone agree about it.

Community
---------

As usual in Open Source projects, the Community may contribute in many forms: by suggesting new functionalities, by submitting bugs (and possibly their fixing), by implementing open tasks, by checking other people's code. Also non-development work is needed, e.g. to create the user manual or to check code documentation. In other words, any help will be appreciated.

The Management Team must encourage Community contributions by accepting the highest number of new functionality requests and by assigning open tasks to Community members, instead of Management Team members.  
Any decision against the work of a Community member must be clearly and privately explained; should the decision affect a large number of Community members, it should be explained in a public page.

Design requirements are to be followed by all developers, and this is one reason a submission could be rejected.  
Community members must act according to software licenses and common law.

Any piece of code submitted to the project should have been previously tested within the application following the checklist that will be published on the project website and/or the repository.

Only the Community member a task was assigned to can ask to be removed from it.

Contributors Needed
-------------------

This section will hold roles the Management Team or other managers feel lack of.  
It must be kept up-to-date as much as possible.

Anyway, contributors are not to be limited to these roles - it wouldn't be an open source project.

* Developers - Android developers, Java developers (utilities and test classes can be just POJOs)
* Translators - Italian first, for both documentation and string resources
* Testers (please provide the device you will get the application on)
* Reviewers for both docs and code

Resources Needed
----------------

Do you wish to contribute with something different? The project is still in early stage, so there is not only need for contributors, but also for resources.

What we have:

* Git repository
* Mailing lists

What we don't have:

* Website
* Web Domain
* Forum
* IRC channel (and people to follow it intensively)

What we could change:

* Mailing lists - in order to have lists with the same domain of the website

If you want to offer your help and resources with one of these points, please contact the project author.

Release Guidelines
------------------

The first public stable release, called Release 1.0, will have all *must* functional [requirements](https://github.com/dturrina/votesmanager/blob/master/REQUIREMENTS.md) satisfied and tested.  
The Management Team could define Alpha and Beta releases in order to let the Community test newly-introduced functionality, without any warranty about the application to be fully running and free of bugs.

Micro (3rd-level) releases will bring *only* security updates or bug fixes; Minor releases should implement a single or few features and be backwards and forwards compatible, that is, switching a modified class between a minor and its previous/next version will not change the user experience (besides of utility classes, every minor-released class must be modular). Major releases hold all previous minor releases' features and/or big interface changes.  
Some widely-impacting or particularly complex features (whose corresponding requirement is indicated by a star) are to be destined for major releases.

Generally speaking, no deadline is to be given to a Community member, although the Community member itself is encouraged to give a *probable release date*.  
This does not apply when the Management Team plans a new release.

It is suggested for each implemented requirement to take trace of all people who contributed in its realizing, and for each bug-fixing release to cite the bug reporters and solvers.

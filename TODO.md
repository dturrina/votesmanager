TODOs, i.e., What Is Needed Now
===============================

*last update: Nov 4th, 2013*

This page highlights what is mostly needed at the moment, as a guideline for potential contributors to have an idea of what to do. It is usually a short, highest-priority list taken from the [Issue page](https://github.com/dturrina/votesmanager/issues).  
Remember all [requirements](http://dturrina.github.io/votesmanager/requirements.html) are to be done, someday.  

At the moment, the application can load and save data from a file named *data.xml* stored on the device SD card.  
Should this file not be found, the application will load data from a default *data.xml* file stored between application assets. Data will then be stored on the SD card upon every tap on "Submit votes" button.

What We Mostly Need Now
-----------------------

* Complete the database part by performing incremental saving informations *first* on database, *then* on file (e.g., votes saved on database when exiting the "Competitor" detail, and saved on file when exiting the app). (Part of Issue #5.)
* Issue #1: Configurability. Create a Settings activity and/or an initial configuration wizard.
* Issue #9 (bug): Classification update. Load *all* aggregate votes when opening classification view.
* Issue #4: Test Plan. A guide to those who want to participate in testing the app behavior.

Volounteers for the cited tasks are pleased to contact the author.

Developers Guidelines
=====================

Get Involved
------------

Everyone is welcome when offering some help. We need many tasks to be performed:  
* *testing* of alpha and beta releases
* *bug* reporting, fixing and testing
* writing *documentation* (both code documentation and user manual)
* writing *translations* of the application
* *reviewing* for code and documentation

All these tasks are equally necessary, so feel free to contribute in the way you think you can do better!  
As the project is still small, we only have a few mailing lists: [development](link here), [bugs](link here) and [announcements](link here). Both documentation and translation developers will join development mailing list, as a first step.

Android
-------

Writing Code  
Some simple advice and rules:

* Write your code *well*. People will read it, so make it readable and self-explained as much as you can. Don't use short one-letter variable names (except from array indexes and similar stuff), prefer camelCase meaningful names. On the other hand, don't use names that seem to be infinite - some developers could prefer notepad editing.
* Use comments. It's a little effort for those who write code to explain what the code does, it may be a big effort for others to read it.
* Use Doxygen for classes and methods comments, so to automatically create code documentation and let others use your own class - one used, once maintained.
* Write libraries and utilities in a general-purpose way. Try to imagine the various functionalities your methods may have, and write the method signature *as it would provide all functionalities* - no matter if you don't implement all of them at once.
* Use TODOs if you don't have time to implement all functionalities a method may have. Concentrate on your main task, others will make your method evolve.
* Preserve *compilable* and *runnable* attributes for the whole application. Once committed, your patches should never, ever make the compilation fail and/or make the application crash.
* Keep in mind side effects when changing others' code. There's nothing worse then creating bugs because a method signature changed.
* (Try to) Always catch exceptions. The application should degrade in a graceful way. (Better not to degrade at all, but developers may not always be perfect.)

Naming Conventions

* Activities are to be named <SimpleDescriptionOfWhatItIs>Activity, e.g., StartActivity, VotingActivity, ClassificationActivity. Layouts are to be named activity_<simple_description_of_what_it_is> or fragment_<simple_description_of_what_it_is>.  
* Utilities are to be named just like POJOs.  
* Common Java naming conventions are applied.  

Compiling  
As a typical Android application, the most-used compiling will probably happen inside a preconfigured environment, such as Eclipse with Android SDK or Android Studio. In both cases, the IDE provides one-click buttons to both compile and install the application on a plugged Android device.
Should such an environment not be available (or, for command-line addicted), it is possible to create and compile Android applications without Eclipse or Android Studio. Please check [Android official documentation](http://developer.android.com/training/basics/firstapp/creating-project.html).  

Installing  
Again, without a preconfigured environment, it is enough for the user to upload the APK file (the main result of Android application building) on the selected device, and double-click... better, tap the file icon. Auto-install should begin, and at the end of the process you will be able to open and run the application.  
*Remember to provide permissions to install application coming from "unknown sources" - i.e., not coming from Play Store. We're not ready for a full publishing yet, and we will only provide stable versions. Testers will have to manually download the APK from [download page](link here).*  

Running  
In the very first release, the application is only capable of summing some marks for some competitors.  
Data will be loaded from a default XML file, when no *data.xml* file is found on SD card. Tap on the competitor name to show her votes - or to give her votes, and tap the text box to enter a value.  

Testing  
In this very basic scenario, the tester will just have to check the votes are correctly summed, and the classification is correct. These points will be found in every checklist written for testing purposes, just to be sure basic features are always implemented.

iOS
------

Nothing here yet - Let us develop it before, please!

Windows Phone
-------------

Nothing here yet - Let us develop it before, please!

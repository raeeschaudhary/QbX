##Project QBX


###Technologies required:
1. Java Development Kit (preferably oracle jdk 1.7 and latest update, current 51, openjdk can cause problems)
2. IDE: _IntelliJ, STS, NetBeans_
3. Database: _MySql_ (preferably the latest version, e.g. 5.6)
4. _Grails 2.2.4_
5. _Git_
    
###Code Base
1. Download and install Git.
2. Right the folder which you want to be the home for the codebase, and choose Git Bash.(For integration of IntelliJ with Git, refer to  the section below _Setting Up the Environment->IntelliJ_ point 4 onward)
    
###Valid Project
- Working code directory is `qbx\qbx-web`.
    
###Setting up the Environment:
- JAVA
    1. Download Oracle Java Development Kit
    2. Create a System Environment variable named `JAVA_HOME`
    3. Create another System Variable `PATH` or update if it already exists, and add `%JAVA_HOME%\bin` for Windows. Use [gvmtool](http://gvmtool.net/) for linux.
- GRAILS
    1. Download Grails 2.2.4 from their [website](http://grails.org/download). QBX currently runs n Grails 2.2.4 so that is a requirement otherwise the project won't be able to run.
    2. Download the zip file and place it in a folder of your choice.
    3. Create a System environment variable for Grails by the name of `GRAILS_HOME`.
    4. Modify System environment variable `PATH` by adding `%GRAILS_HOME%\bin`. So now the `PATH` variable looks like this: `%JAVA_HOME%\bin;%GRAILS_HOME%\bin`
- IntelliJ
    1. Install an IDE. Here we will assume the usage of IntelliJ. Download [IntelliJ IDEA](http://www.jetbrains.com/idea/download/index.html). Ultimate version needs to be bought. Install IntelliJ.
    2. Configure Groovy in IntelliJ.
        - At startup after IntelliJ installation, add support for the plugin of Grails.
        - Otherwise, you can later change it from _File -> Settings_. Then choose plugins, and check _Grails_.
    3. Enable Git in IntelliJ, as mentioned in the step above.
    4. After that, go to _Project->Settings_, under _Version Control_, click Git. On the right hand panel, you need to give the address to the git executable, named _git.exe_, present in `.../Git/bin/`.
    5. For the ssh drop down right below the executable field, its preferable to choose "Built In".
    6. For more information on this issue, refer to [jetbrains website](http://www.jetbrains.com/idea/webhelp/using-git-integration.html).
    7. For projects being developed on cross-platform operatins systems, windows uses CRLF line endings(a format) and Linux, OS X use LF line ending format. If not taken care of these line endings will be changed from one format to the other, causing in merge conflicts. There are 2 possible solutions:
        - If using from Git, you need to change the 'core.autocrlf' property in the Git config to 'true'(for Windows) or to 'input' in case of Linux.
        - From IntelliJ itself wth Git integrated, you need to place an xml file here: `.IntelliJIdea12\config\codestyles\Default _1_.xml`, which contains policies for the commiting.
More info on this topic can be found [here](http://stackoverflow.com/questions/3206843/how-line-ending-conversions-work-with-git-core-autocrlf-between-different-operat)
- Git
    1. Download latest version of git
        - [msgit for windows](https://code.google.com/p/msysgit/downloads/list?q=full+installer+official+git)
        - `sudo apt-get install git-core` - for ubuntu/debian
    2. Select point 3 _Run git and included unix tools from the windows command prompt_, when needed. _Path_ will be updated during installation.
    3. Open console and check `git --version`. The result should be like `git version 1.9.0.msysgit.0`.
    4. If git installation successful, generate ssh keys and add it to Bitbucket account, follow to the [official guide](https://confluence.atlassian.com/display/BITBUCKET/Set+up+SSH+for+Git).
- MySql
    1. Download latest version of [MySql community server](http://dev.mysql.com/downloads/mysql/)
    2. Run `.exe` or `.msi` file and follow the instructions.
    3. Select _Developer default_.
    4. Specify password for _root_ user.

    Follow [this guide](http://www.mysqltutorial.org/install-mysql/), if there is any questions.

    - Ubuntu/Debian users `sudo apt-get install mysql-server mysql-client`

###Database Setup
  1. Create a new database named `qbx`.
  2. MySql Settings:
      - username = `root`,
      - password = `root`,
      - host = `localhost`,
      - port = `3306`.

###Using Codebase
  1. Compile the package qbx-core using Grails from console. `grails compile`
  2. Compile the package qbx-web using Grails from console. `grails compile`
  3. In the qbx-web codebase, run the application using the script `grails run-app`.
    
###QBX from Command Prompt
As QBX is a grails application, you can run it from the command prompt.

  1. Go to the QBX's project's root directory, then to qbx/qbx-core, which is the domain plugin, i.e., contains only functionality, no view
  2. Then run commands
     - `grails clean` (clean is optional but a good practice)
     - `grails compile`
  3. Troubleshooting: There may be en error loading dependencies and resources, if so, enter
  `grails refresh-dependencies` and then `clean` and `compile` again
  4. Navigate back to `qbx/`, then `qbx-web`. Repeat the same procedure. This is the main interface, so you can then run `grails`, this will make the command prompt enter the _grails interactive mode_, which is useful when running the app as ou can interact with the application more robustly. After a while, you will see the grails environemnt set up, then enter `run-app`. 
  5. To run in a production environment, enter `run-app prod`.
After loading resources, it will provide a url, which will be `http://localhost:8080/qbx-web`, because grails applications by default run on port **8080**. To make it run on another port, run `grails -DServer.port=8090 run-app`,  [more info](http://stackoverflow.com/questions/10955899/how-to-change-grails-localhost-port)
    
###QBX on IntelliJ
  1. Go to `qbx\qbx-web` in console
  2. Run `grails integrate-with --intellij`
  3. In intellij idea _File -> Open_, go to qbx-web and open _qbx-web.ipr_
  4. Press _Convert_ when IDE will ask.
  5. Run project always from console, not from IDE.

###Debug on Intellij
   1. Open _Run -> Edit configurations_
   2. Click green `+` and choose _remote_
   3. Set name e.g. _debug_, 

       **Use following settings**:

       - _Transport_: `Socket`
       - _Debugger mode_: `Attach`
       - _Host_: `localhost`
       - _Port_: `5005` - it's default grails debug port

   4. In console run `grails-debug`
   5. Select from dropdown in the top of IDE "debug" and click debug button
   6. In console: `grails run-app`

    
###Website Credentials
  1. The live website address: [http://test.qbcheck.com](http://test.qbcheck.com)
  2. The website has a system admin account:

      - _login_: `systemadmin@gmail.com`
      - _password_: `sAdmin1`

  3. To go through splash page use

    In right column: **Without voucher code**

    - _Patient Id_: `34JDED55`
    - _Access code_: `4922`

###Logs
- When running `qbx-web`, logs can be found in this location `...\qbx\qbx-web\logs`.
- Logs also available online by the path `qbx-web/logs/qbcheck.log`
    
###TroubleShooting
  - There may be en error loading dependencies and resources, if so, enter `grails refresh-dependencies` and then clean and compile again.

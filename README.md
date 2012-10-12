jpos-workflow-cli
=================

jPos cli command to convert a jPos transaction manager config to a directed graph (GraphViz DOT format)
Note that the transaction manager configuration requires to be completed with transition informations. Please take a look here for more infos : https://github.com/dgrandemange/jPosWorkflowEclipsePlugin/wiki

Recipee :
--------- 
First, 'mvn -Pdemo install'
Then, under runtime directory 'java -jar q2.jar -cli'

Then under CLI prompt :
> txmgr2dot -p"deploy/20_txmgr.xml" -d"./html" -s -r"jPosWorkflowCliDemo"

About the txmgr2dot command options used above :
* -p : specifiy the transaction manager XML config file you want to convert,
* -d : specify the DOT file(s) output directory,
* -s : is optional and indicates you want to benefit the subflow functionnality (should be used only if the tx mgr config is subflow compliant, which is the case here)
* -r : is optional and set the root flow title (default to 'root')

Here we specify './html' as output dir because there are already some resources (html, css, images) in this directory that will nicely enhance the browsing of the graph(s).

BUT FIRST, you will need to convert each generated DOT file(s) into SVG files.
Install Graphviz 2.28 on your system, 
Then, under the 'runtime/help/' directory, for all DOT files present :
> $GRAPHVIZHOME\bin\dot.exe -Gcharset=latin1 -Tsvg -O xxxxxxxx.dot

Some '.dot.svg' file(s) should now be available in the 'runtime/help/' directory.

Eventually, open the 'index.html' with a W3C compliant browser (I use Firefox 15.0.1 during my devs) and enjoy graph(s) navigation :
* nodes and edge tooltips on mouse over; context management relative informations are now also available,
* subflows inter-navigation by simple double-click on identified subflow nodes,

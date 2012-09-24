jpos-workflow-cli
=================

jPos cli command to convert a jPos transaction manager config to a directed graph (GraphViz DOT format)

Recipee :
--------- 
First, 'mvn -Pdemo install'
Then, under runtime directory 'java -jar q2.jar -cli'

Then under CLI prompt :
> txmgr2dot -p"deploy/20_txmgr.xml" -d"./html" -s

About the txmgr2dot command options used above :
* -p : specifiy the transaction manager XML config file you want to convert,
* -d : specify the DOT file(s) output directory,
* -s : is optional and indicates you want to benefit the subflow functionnality (should be used only if the tx mgr config is subflow compliant, which is the case here)

Here we specifiy './html' as output dir because there are already some special already prepared resources (html, css, images) in this directory that will nicely enhance the browsing of the graphs.

BUT FIRST, you will need to convert each generated DOT file(s) into SVG files.
Install Graphviz 2.28 on your system, 
Then, under the 'runtime/help/' directory, for all DOT files present :
> $GRAPHVIZHOME\bin\dot.exe -Gcharset=latin1 -Tsvg -O _____.dot
(This will result in a '_____.dot.svg' file creation in the directory)

Once you are done with SVG conversion, open the 'index.html' with a W3C compliant browser (I use Firefox 15.0.1 for my tests) and enjoy !

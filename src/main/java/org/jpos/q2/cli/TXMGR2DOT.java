package org.jpos.q2.cli;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.jpos.jposext.jposworkflow.cli.ContextMgmtInfoPopulatorCLIImpl;
import org.jpos.jposext.jposworkflow.cli.TxMgr2DotOptWrapper;
import org.jpos.jposext.jposworkflow.cli.UsageException;
import org.jpos.jposext.jposworkflow.model.Graph;
import org.jpos.jposext.jposworkflow.service.IDOTLabelFactory;
import org.jpos.jposext.jposworkflow.service.support.FacadeImpl;
import org.jpos.jposext.jposworkflow.service.support.GraphConverterServiceImpl;
import org.jpos.jposext.jposworkflow.service.support.LabelFactoryVelocityImpl;
import org.jpos.jposext.jposworkflow.service.support.TooltipFactoryVelocityImpl;
import org.jpos.q2.CLICommand;
import org.jpos.q2.CLIContext;

/**
 * CLI command to convert a transaction manager config to GraphViz DOT file(s)
 * 
 * @author dgrandemange
 * 
 */
public class TXMGR2DOT implements CLICommand {

	public static final String DEFAULT_ROOT_GRAPHNAME = "root";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jpos.q2.CLICommand#exec(org.jpos.q2.CLIContext,
	 * java.lang.String[])
	 */
	public void exec(CLIContext cli, String[] args) throws Exception {
		Options options = getDefinedOptions();
		try {
			TxMgr2DotOptWrapper optWrapper = new TxMgr2DotOptWrapper();
			manageCmdArgs(args, optWrapper);

			// First, convert configuration to graph(s)
			Map<String, Graph> graphs = new HashMap<String, Graph>();
			genGraphsFromConfigFile(optWrapper, graphs);
			if (0 == graphs.size()) {
				cli.println("Unable to convert config to graph(s). Reason unknown.");
			} else {
				// Then, export the graph(s) to DOT format
				String outputDirPath = optWrapper.getOutputDirOpt();
				File outputDir = new File(outputDirPath);
				if (!outputDir.exists()) {
					cli.println(String.format("'%s' is not a valid path",
							outputDirPath));
				} else if (!outputDir.isDirectory()) {
					cli.println(String.format("'%s' is not a directory",
							outputDirPath));
				} else if (!outputDir.canWrite()) {
					cli.println(String.format(
							"'%s' is not a writeable directory", outputDirPath));
				} else {
					int dotCreatedCount = 0;
					for (Entry<String, Graph> entry : graphs.entrySet()) {
						String key = entry.getKey();

						String fileName;
						String graphName;
						if (FacadeImpl.ROOT_KEY.equals(key)) {
							if (null == optWrapper.getRootGraphName()) {
								graphName = DEFAULT_ROOT_GRAPHNAME;
							} else {
								graphName = optWrapper.getRootGraphName();
							}
							fileName = "root.dot";
						} else {
							graphName = key;
							fileName = graphName + ".dot";
						}

						String createdDotFilePath = createDOTFile(entry.getValue(), fileName, graphName,
								outputDirPath);
						dotCreatedCount++;
						cli.println(String.format(
								"DOT file '%s' created", createdDotFilePath));						
					}
					cli.println(String.format("%d DOT file(s) created", dotCreatedCount));						
				}
			}

		} catch (UsageException e) {
			showUsage(cli, options, e);
		}
	}

	/**
	 * @param graph
	 *            The graph to export as DOT
	 * @param graphName
	 *            Graph name used as DOT file name
	 * @param outputDir
	 *            DOT file output directory
	 */
	protected String createDOTFile(Graph graph, String fileName, String graphName,
			String outputDir) {
		IDOTLabelFactory labelFactory = new LabelFactoryVelocityImpl();
		IDOTLabelFactory toolTipFactory = new TooltipFactoryVelocityImpl();
		GraphConverterServiceImpl graphConverterService = new GraphConverterServiceImpl();
		graphConverterService.setLabelFactory(labelFactory);
		graphConverterService.setToolTipFactory(toolTipFactory);

		FileOutputStream result = null;
		PrintWriter pw = null;
		try {
			String saveFilePath = String.format("%s%s%s", outputDir,
					System.getProperty("file.separator"), fileName);
			result = new FileOutputStream(saveFilePath);
			pw = new PrintWriter(result);
			graphConverterService.convertGraphToDOT(graphName, graph, pw);
			pw.flush();
			pw.close();
			return saveFilePath;
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} finally {
			if (result != null) {
				try {
					result.flush();
					result.close();
				} catch (Exception e) {
				}
			}
		}
	}

	/**
	 * @param optWrapper
	 *            Command options
	 * @param graphs
	 *            Map of graphs
	 * @throws MalformedURLException
	 */
	protected void genGraphsFromConfigFile(TxMgr2DotOptWrapper optWrapper,
			Map<String, Graph> graphs) throws MalformedURLException {
		File txMgrConfigFile = null;

		String txMgrConfigFilePath = optWrapper.getTxMgrConfigFilePath();

		txMgrConfigFile = new File(txMgrConfigFilePath);
		URL url = txMgrConfigFile.toURI().toURL();

		FacadeImpl jPosWorkflowFacade = new FacadeImpl();
		ContextMgmtInfoPopulatorCLIImpl ctxMgmtInfoPopulator = new ContextMgmtInfoPopulatorCLIImpl();

		if (optWrapper.isSubflowMode()) {
			jPosWorkflowFacade.getGraphSubFlowMode(url, ctxMgmtInfoPopulator,
					graphs);
		} else {
			Graph graph = jPosWorkflowFacade
					.getGraph(url, ctxMgmtInfoPopulator);
			graphs.put(FacadeImpl.ROOT_KEY, graph);
		}
	}

	/**
	 * Get defined command line options
	 * 
	 * @return command line options
	 */
	@SuppressWarnings("static-access")
	private Options getDefinedOptions() {
		Option txMgrConfigFilePathOpt = OptionBuilder
				.withDescription(
						"transaction manager XML configuration file path")
				.withLongOpt("configpath").hasArgs(1).isRequired(true)
				.create("p");

		Option outputDirOpt = OptionBuilder
				.withDescription("generated DOT files output directory")
				.withLongOpt("outputdir").hasArgs(1).isRequired(true)
				.create("d");

		Option subflowModeOpt = OptionBuilder
				.withDescription("activate subflows discovery mode")
				.withLongOpt("subflowmode").isRequired(false).create("s");

		Option rootGraphNameOpt = OptionBuilder
				.withDescription(
						String.format("root graph name (default=%s)",
								DEFAULT_ROOT_GRAPHNAME))
				.withLongOpt("rootgraphname").hasArgs(1).isRequired(false)
				.create("r");

		Options options = new Options();
		options.addOption(txMgrConfigFilePathOpt);
		options.addOption(outputDirOpt);
		options.addOption(subflowModeOpt);
		options.addOption(rootGraphNameOpt);

		return options;
	}

	/**
	 * @param argc
	 */
	protected void manageCmdArgs(String argc[], TxMgr2DotOptWrapper optWrapper) {

		Options options = getDefinedOptions();

		CommandLineParser parser = new PosixParser();

		try {
			CommandLine line = parser.parse(options, argc);

			if (line != null) {
				if (line.hasOption("p")) {
					optWrapper.setTxMgrConfigFilePath(line.getOptionValue("p"));
				}

				if (line.hasOption("d")) {
					optWrapper.setOutputDirOpt(line.getOptionValue("d"));
				}

				if (line.hasOption("s")) {
					optWrapper.setSubflowMode(true);
				}

				if (line.hasOption("r")) {
					optWrapper.setRootGraphName(line.getOptionValue("r"));
				}

			} else {
				throw new UsageException("Unknown options : see usage");
			}
		}

		catch (ParseException e) {
			throw new UsageException(
					"Command line arguments parsing failed. Reason : "
							+ e.getMessage());
		}

	}

	/**
	 * Show command usage
	 * 
	 * @param options
	 */
	private void showUsage(CLIContext cli, Options options, UsageException e) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		PrintWriter pw = new PrintWriter(bos);
		pw.println(e.getMessage());

		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp(pw, 80, TXMGR2DOT.class.getSimpleName(), "",
				options, 2, 5, "");
		pw.flush();
		cli.println(new String(bos.toByteArray()));
	}

}

package org.jpos.jposext.jposworkflow.cli;

/**
 * Some wrapper for TXMGR2DOT command options
 * 
 * @author dgrandemange
 *
 */
public class TxMgr2DotOptWrapper {

	private String txMgrConfigFilePath;

	private String outputDirOpt;

	private String rootGraphName;

	private boolean subflowMode;

	/**
	 * @return the txMgrConfigFilePath
	 */
	public String getTxMgrConfigFilePath() {
		return txMgrConfigFilePath;
	}

	/**
	 * @param txMgrConfigFilePath
	 *            the txMgrConfigFilePath to set
	 */
	public void setTxMgrConfigFilePath(String txMgrConfigFilePath) {
		this.txMgrConfigFilePath = txMgrConfigFilePath;
	}

	/**
	 * @return the outputDirOpt
	 */
	public String getOutputDirOpt() {
		return outputDirOpt;
	}

	/**
	 * @param outputDirOpt
	 *            the outputDirOpt to set
	 */
	public void setOutputDirOpt(String outputDirOpt) {
		this.outputDirOpt = outputDirOpt;
	}

	/**
	 * @return the subflowMode
	 */
	public boolean isSubflowMode() {
		return subflowMode;
	}

	/**
	 * @param subflowMode
	 *            the subflowMode to set
	 */
	public void setSubflowMode(boolean subflowMode) {
		this.subflowMode = subflowMode;
	}

	/**
	 * @return the rootGraphName
	 */
	public String getRootGraphName() {
		return rootGraphName;
	}

	/**
	 * @param rootGraphName
	 *            the rootGraphName to set
	 */
	public void setRootGraphName(String rootGraphName) {
		this.rootGraphName = rootGraphName;
	}

}

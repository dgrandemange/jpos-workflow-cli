package org.jpos.q2.cli;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.fail;

import org.jpos.q2.cli.TXMGR2DOT;
import org.jpos.q2.cli.TXMGR2DOT.UsageException;
import org.junit.Before;
import org.junit.Test;

/**
 * @author dgrandemange
 *
 */
public class TXMGR2DOTTest {

	private TXMGR2DOT cmd;
	private TXMGR2DOT.TxMgr2DotOptWrapper optWrapper;
	
	@Before
	public void setUp() throws Exception {
		cmd = new TXMGR2DOT();
		optWrapper = cmd.new TxMgr2DotOptWrapper();
	}

	@Test
	public void testManageCmdArgs_Nominal_ConfigFilePath() {		
		String[] argc = new String[] {"-p\"deploy/20_txmgr.xml\"", "-d\"/tmp/\""};
		cmd.manageCmdArgs(argc, optWrapper);
		
		assertThat(optWrapper.getOutputDirOpt()).isEqualTo("/tmp/");
		assertThat(optWrapper.getTxMgrConfigFilePath()).isEqualTo("deploy/20_txmgr.xml");
		assertThat(optWrapper.isSubflowMode()).isFalse();
		assertThat(optWrapper.getRootGraphName()).isNull();
	}

	@Test
	public void testManageCmdArgs_Nominal_ConfigFilePath_SubFlowDiscoveryActivation() {		
		String[] argc = new String[] {"-p\"deploy/20_txmgr.xml\"", "-d\"/tmp/\"", "-s"};
		cmd.manageCmdArgs(argc, optWrapper);
		
		assertThat(optWrapper.getOutputDirOpt()).isEqualTo("/tmp/");
		assertThat(optWrapper.getTxMgrConfigFilePath()).isEqualTo("deploy/20_txmgr.xml");
		assertThat(optWrapper.isSubflowMode()).isTrue();
		assertThat(optWrapper.getRootGraphName()).isNull();
	}
	
	@Test
	public void testManageCmdArgs_Nominal_ConfigFilePath_SpecifyRootGraphName() {
		String[] argc = new String[] {"-p\"deploy/20_txmgr.xml\"", "-d\"/tmp/\"", "-r\"mainTxnMgrConfig\""};
		cmd.manageCmdArgs(argc, optWrapper);
		
		assertThat(optWrapper.getOutputDirOpt()).isEqualTo("/tmp/");
		assertThat(optWrapper.getTxMgrConfigFilePath()).isEqualTo("deploy/20_txmgr.xml");
		assertThat(optWrapper.isSubflowMode()).isFalse();
		assertThat(optWrapper.getRootGraphName()).isEqualTo("mainTxnMgrConfig");		
	}
	
	@Test
	public void testManageCmdArgs_Error_OutputDirIsMissing() {		
		String[] argc = new String[] {"-p\"deploy/20_txmgr.xml\""};
		
		try {
			cmd.manageCmdArgs(argc, optWrapper);
			fail("UsageException expected");
		}
		catch(UsageException e) {
			assertThat(e.getMessage()).contains("Missing required option: d");
		}
	}	

	@Test
	public void testManageCmdArgs_Error_ConfigAccessIsMissing() {		
		String[] argc = new String[] {"-d\"/tmp/\""};
		
		try {
			cmd.manageCmdArgs(argc, optWrapper);
			fail("UsageException expected");
		}
		catch(UsageException e) {
			assertThat(e.getMessage()).contains("Missing required option: p");
		}
	}
	
}

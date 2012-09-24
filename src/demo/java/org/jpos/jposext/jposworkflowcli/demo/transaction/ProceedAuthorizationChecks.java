package org.jpos.jposext.jposworkflowcli.demo.transaction;

import java.io.Serializable;

import org.jpos.transaction.GroupSelector;

public class ProceedAuthorizationChecks implements GroupSelector {

	public int prepare(long id, Serializable context) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void commit(long id, Serializable context) {
		// TODO Auto-generated method stub

	}

	public void abort(long id, Serializable context) {
		// TODO Auto-generated method stub

	}

	public String select(long id, Serializable context) {
		// TODO Auto-generated method stub
		return null;
	}

}

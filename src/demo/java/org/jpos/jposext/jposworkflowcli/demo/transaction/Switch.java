package org.jpos.jposext.jposworkflowcli.demo.transaction;

import java.io.Serializable;

import org.jpos.jposext.ctxmgmt.annotation.UpdateContextRule;
import org.jpos.jposext.ctxmgmt.annotation.UpdateContextRules;
import org.jpos.jposext.jposworkflowcli.demo.ICtxAttr;
import org.jpos.transaction.GroupSelector;

@UpdateContextRules({
		@UpdateContextRule(attrNames = {ICtxAttr.INCOMING_REQUEST}),
		@UpdateContextRule(id = "0100", attrNames = {}),
		@UpdateContextRule(id = "unhandled", attrNames = {}) })
public class Switch implements GroupSelector {

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

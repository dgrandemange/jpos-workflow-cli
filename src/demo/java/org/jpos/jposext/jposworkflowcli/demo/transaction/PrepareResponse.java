package org.jpos.jposext.jposworkflowcli.demo.transaction;

import java.io.Serializable;

import org.jpos.jposext.ctxmgmt.annotation.UpdateContextRule;
import org.jpos.jposext.ctxmgmt.annotation.UpdateContextRules;
import org.jpos.jposext.jposworkflowcli.demo.ICtxAttr;
import org.jpos.transaction.TransactionParticipant;

@UpdateContextRules({ @UpdateContextRule(attrNames = { ICtxAttr.OUTGOING_RESPONSE })})
public class PrepareResponse implements TransactionParticipant {

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

}

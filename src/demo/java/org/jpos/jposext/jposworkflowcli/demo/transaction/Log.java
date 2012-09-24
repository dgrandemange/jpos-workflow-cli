package org.jpos.jposext.jposworkflowcli.demo.transaction;

import java.io.Serializable;

import org.jpos.transaction.TransactionParticipant;

public class Log implements TransactionParticipant {

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

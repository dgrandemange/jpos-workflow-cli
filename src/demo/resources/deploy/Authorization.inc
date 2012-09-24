<group name="Authorization">
	<participant class="org.jpos.jposext.jposworkflowcli.demo.transaction.ProceedAuthorizationChecks">
		<property name="ok" value="Accept" selectCriterion="checks are ok : accept the transaction" />
		<property name="ko" value="Reject" selectCriterion="checks are ko : reject the transaction" />
	</participant>
</group>

<group name="Reject">
	<participant class="org.jpos.jposext.jposworkflowcli.demo.transaction.PrepareResponse">
		<property name="field39" value="12" />
	</participant>
</group>

<group name="Accept">
	<participant class="org.jpos.jposext.jposworkflowcli.demo.transaction.PrepareResponse">
		<property name="field39" value="00" />
	</participant>	
</group>
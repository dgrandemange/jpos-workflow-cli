package org.jpos.jposext.jposworkflow.cli;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jpos.jposext.ctxmgmt.annotation.UpdateContextRule;
import org.jpos.jposext.ctxmgmt.annotation.UpdateContextRules;
import org.jpos.jposext.jposworkflow.model.ParticipantInfo;
import org.jpos.jposext.jposworkflow.model.SubFlowInfo;
import org.jpos.jposext.jposworkflow.service.support.ContextMgmtInfoPopulatorAbstractImpl;

/**
 * A context management info populator using runtime introspection to discover
 * annotations on participant classes
 * 
 * @author dgrandemange
 * 
 */
public class ContextMgmtInfoPopulatorCLIImpl extends
		ContextMgmtInfoPopulatorAbstractImpl {

	@Override
	public void processParticipantAnnotations(
			Map<String, List<ParticipantInfo>> jPosTxnMgrGroups) {
		for (Entry<String, List<ParticipantInfo>> entry : jPosTxnMgrGroups
				.entrySet()) {

			for (ParticipantInfo participantInfo : entry.getValue()) {
				if (participantInfo instanceof SubFlowInfo) {
					continue;
				}

				Map<String, String[]> updCtxAttrByTransId = new HashMap<String, String[]>();
				participantInfo.setUpdCtxAttrByTransId(updCtxAttrByTransId);

				try {
					@SuppressWarnings("rawtypes")
					Class pClazz = Class.forName(participantInfo.getClazz());
					Annotation[] annotations = pClazz.getAnnotations();
					for (Annotation annotation : annotations) {
						if (UpdateContextRules.class.equals(annotation
								.annotationType())) {
							UpdateContextRules rulesAnnotation = (UpdateContextRules) annotation;
							UpdateContextRule[] ruleAnnotationTab = rulesAnnotation
									.value();
							for (UpdateContextRule ruleAnnotation : ruleAnnotationTab) {
								String id = null;
								String[] attrNames = null;
								id = ruleAnnotation.id();
								attrNames = ruleAnnotation.attrNames();
								if (null != attrNames) {
									if (null == id) {
										updCtxAttrByTransId.put(
												UpdateContextRule.DEFAULT_ID,
												attrNames);
									} else {
										updCtxAttrByTransId.put(id, attrNames);
									}
								}
							}
						}
					}
				} catch (ClassNotFoundException e) {
					// Safe to ignore : class has not been found in the
					// classpath but we don't bother
				}
			}
		}
	}

}

package com.cedar.cp.utc.struts.admin.reset;

import com.cedar.cp.utc.common.CPAction;
import com.cedar.cp.utc.common.CPSystemProperties;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

public class ChallengeWordVerification extends CPAction {
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		ChallengeForm myForm = (ChallengeForm) form;
		CPSystemProperties sysProps = getCPSystemProperties(request);

		ResetAccessor accessor = null;
		ResetAccessor.UserResetDTO resetDTO = null;
		List challenges = null;
		boolean answersValid = false;
		try {
			accessor = new ResetAccessor();
			accessor.runUserDetsQueryName(myForm.getUserID());
			resetDTO = accessor.getReset();
			if (!resetDTO.isValid()) {
				accessor.runUserDetsQueryEMail(myForm.getEmail());
				resetDTO = accessor.getReset();
			}
			challenges = accessor.getUserChallengeWord(resetDTO.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (accessor != null) {
				accessor.close();
			}
		}

		if ((resetDTO != null) && (resetDTO.isValid()) && (challenges != null)) {
			List answeredQs = (List) myForm.getChallenges();

			Iterator i$;
			if (answeredQs.size() == 1) {
				for (i$ = answeredQs.iterator(); i$.hasNext();) {
					Object answeredQ = i$.next();

					ChallengeDTO answer = (ChallengeDTO) answeredQ;

					Iterator it1 = challenges.iterator();
					boolean bForBreak = false;
					while (true) {
						if (!it1.hasNext()) {
							bForBreak = true;
							break;
						}
						Object challenge = it1.next();

						ChallengeDTO validatedAnswer = (ChallengeDTO) challenge;

						if (validatedAnswer.getAnswer().trim().equalsIgnoreCase(answer.getAnswer().trim())) {
							answersValid = true;
							break;
						}

						answersValid = false;
						bForBreak = true;
						break;
					}
					if (bForBreak)
						break;
				}

			}

			if (answersValid) {
				return mapping.findForward("success");
			}

			String message = "Mother's maiden name is incorrect";
			try {
				accessor = new ResetAccessor();
				int strikes = resetDTO.getStrikes() + 1;
				accessor.updateStrike(strikes, resetDTO.getUserId());
				if (strikes >= sysProps.getResetStrikesAllowed()) {
					accessor.diableUSER(resetDTO.getUserId());
					message = "Mother's maiden name is incorrect, so user has been disabled";
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				accessor.close();
			}

			ActionErrors errors = new ActionErrors();
			errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("cp.changePassword.error", message));
			addErrors(request, errors);
			return mapping.findForward("fail");
		}

		ActionErrors errors = new ActionErrors();
		errors.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage("cp.changePassword.error", "unable to load user"));
		addErrors(request, errors);
		return mapping.findForward("fail");
	}
}
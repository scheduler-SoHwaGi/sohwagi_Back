package org.project.sohwagi.user.application.port.in.usecase;

import org.project.sohwagi.user.application.port.in.command.SaveFcmTokenCommand;

public interface SaveFcmTokenUseCase {

	void saveFcmToken(SaveFcmTokenCommand command);

}

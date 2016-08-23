package utils;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import play.data.validation.ValidationError;

public class CommonUtil {

	/**
	 * validationErrorsからメッセージを交換するためのメソッド
	 * 
	 * @param validationErrors
	 * @return メッセージのフォーマット： フィルード1：フィルード1のエラー1, フィルード1のエラー2, ...；
	 *         フィールド2:フィールド2のエラー1,：フィルード2のエラー2, ...; ...
	 */
	public static String transferValidationErrorToMessage(Map<String, List<ValidationError>> validationErrors) {
		if (validationErrors.size() == 0) {
			return null;
		}
		StringBuilder message = new StringBuilder("");

		boolean firstField = false;
		for (Entry<String, List<ValidationError>> entry : validationErrors.entrySet()) {
			if (entry.getKey() != null && entry.getValue() != null && entry.getValue().size() > 0) {
				StringBuilder errorMessageBuilder = new StringBuilder("");
				boolean firstError = true;
				for (ValidationError validationError : entry.getValue()) {
					if (validationError != null) {
						for (String detailMessage : validationError.messages()) {
							if (firstError) {
								errorMessageBuilder.append(detailMessage);
								firstError = false;
							} else {
								errorMessageBuilder.append(", " + detailMessage);
							}
						}
					}
				}
				
				String errorMessage = errorMessageBuilder.toString();
				if (errorMessage.trim().length() > 0) {
					if (!firstField) {
						message.append(entry.getKey() + ": " + errorMessage);
						firstField = true;
					} else {
						message.append("; " + entry.getKey() + ": " + errorMessage);
					}
				}
			}
		}

		return message.toString();
	}
}

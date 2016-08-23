package controllers;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import com.google.gson.Gson;
import static play.libs.Json.toJson;

import play.Logger;
import play.data.FormFactory;
import play.i18n.MessagesApi;
import play.mvc.Controller;
import play.mvc.Result;
import utils.ConstantUtil.ERROR_CODE;

/**
 * レスポンスを作成するための共有するController
 */
public abstract class BaseController extends Controller {
	public final static int SUCCESS_CODE = 0;

	public final static String CODE = "errorCode";
	public final static String USER_MESSAGE = "userMessage";
	public final static String DEVELOPER_MESSAGE = "developerMessage";

	@Inject
	MessagesApi messagesApi;

	@Inject
	FormFactory formFactory;

	public Result handleFailed(ERROR_CODE errorCode, String message) {
		Map<String, Object> responseFields = new HashMap<String, Object>();
		responseFields.put(CODE, errorCode.getCode());
		responseFields.put(USER_MESSAGE, messagesApi.preferred(request()).at(errorCode.getMessageCode()));
		responseFields.put(DEVELOPER_MESSAGE, message);
		Logger.debug(message);
		return ok(new Gson().toJson(responseFields));
	}

	public Result handleSuccess(Object data) {
		if (data == null) {
			Map<String, Object> responseFields = new HashMap<String, Object>();
			responseFields.put(CODE, ERROR_CODE.OK.getCode());
			return ok(new Gson().toJson(responseFields));
		} else
			return ok(toJson(new ResponseObject(ERROR_CODE.OK.getCode(), data)));
	}

	public class ResponseObject {
		private int errorCode;
		private Object data;

		public ResponseObject(int errorCode, Object data) {
			this.errorCode = errorCode;
			this.data = data;
		}

		public int getErrorCode() {
			return errorCode;
		}

		public void setStatusCode(int errorCode) {
			this.errorCode = errorCode;
		}

		public Object getData() {
			return data;
		}

		public void setData(Object data) {
			this.data = data;
		}
	}
}

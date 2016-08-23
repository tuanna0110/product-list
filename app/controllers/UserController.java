package controllers;

import play.data.Form;
import play.mvc.Result;

import play.mvc.Security;
import utils.CommonUtil;
import utils.ConstantUtil.ERROR_CODE;
import auth.SecuredToken;
import forms.LoginForm;
import models.User;

public class UserController extends BaseController {
    public Result login() {
        Form<LoginForm> loginForm = formFactory.form(LoginForm.class).bindFromRequest();

        if (loginForm.hasErrors()) {
            return this.handleFailed(ERROR_CODE.INVALID_INPUT, CommonUtil.transferValidationErrorToMessage(loginForm.errors()));
        }

        String username = loginForm.get().getUsername();
        String password = loginForm.get().getPassword();
        User user = User.findByLoginInfo(username, password);
        if (user == null) {
        	return this.handleFailed(ERROR_CODE.LOGIN_FAILED, "ログインできません。username=" + username +" password=" + password);
        } else {
            String authToken = user.createToken();
            return this.handleSuccess(new AuthTokenResponse(authToken));
        } 
    }
    
    @Security.Authenticated(SecuredToken.class)
    public Result logout() {
    	User user = (User) ctx().args.get("user");
    	user.deleteAuthToken();
    	return this.handleSuccess(null);
    }

    public class AuthTokenResponse {
    	private String authToken;
    	public AuthTokenResponse (String authToken) {
    		this.authToken = authToken;
    	}
		public String getAuthToken() {
			return authToken;
		}
		public void setAuthToken(String authToken) {
			this.authToken = authToken;
		}
    	
    }
}

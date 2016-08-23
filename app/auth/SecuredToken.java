package auth;

import models.User;
import utils.ConstantUtil;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;

/**
 * 認証のトークンをチェックするクラス。
 * https://github.com/jamesward/play-rest-securityプロジェクトを参考しました。
 */
public class SecuredToken extends Security.Authenticator {

	@Override
	public String getUsername(Context ctx) {
		String[] authTokenHeaderValues = ctx.request().headers().get(ConstantUtil.AUTH_TOKEN_HEADER);
		if ((authTokenHeaderValues != null) && (authTokenHeaderValues.length == 1)
				&& (authTokenHeaderValues[0] != null)) {
			User user = User.findByAuthToken(authTokenHeaderValues[0]);
			if (user != null) {
				ctx.args.put("user", user);
				return user.getUsername();
			}
		}

		return null;
	}

	@Override
	public Result onUnauthorized(Context ctx) {
		return unauthorized();
	}

}

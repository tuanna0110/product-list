package controllers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

import javax.inject.Inject;

import com.avaje.ebean.annotation.Transactional;

import auth.SecuredToken;
import models.Product;
import play.Environment;
import play.Logger;
import play.mvc.Result;
import play.mvc.Security;
import services.StoredImage;
import utils.ConstantUtil;
import utils.ConstantUtil.ERROR_CODE;

@Security.Authenticated(SecuredToken.class)
public class ProductImageController extends BaseController {
	@Inject
	StoredImage storedImage;

	@Inject
	private Environment environment;

	public Result read(Long id) {
		try {
			Product product = Product.find.byId(id);
			if (product == null) {
				return notFound(messagesApi.preferred(request()).at(ERROR_CODE.PRODUCT_NOT_FOUND.getMessageCode()));
			}

			InputStream is = null;
			if (product.getImageID() == null || (is = storedImage.getImage(product.getImageID())) == null) {
				// 商品のイメージがない場合、デフォルトのイメージを返事する
				is = new BufferedInputStream(new FileInputStream(environment.getFile("public/images/favicon.png")));
			}

			String mimeType = URLConnection.guessContentTypeFromStream(is);
			return ok(is).as(mimeType);
		} catch (IOException exception) {
			Logger.debug(exception.getMessage());
			return notFound(exception.getMessage());
		}
	}

	@Transactional
	public Result create(Long id) throws IOException {
		try {
			Product product = Product.find.byId(id);
			if (product == null) {
				return this.handleFailed(ERROR_CODE.PRODUCT_NOT_FOUND, "商品がない　id = " + id);
			}

			//ファイルのサイズをチェック
			if (request().body().asRaw() == null || request().body().asRaw().size() == 0) {
				return this.handleFailed(ERROR_CODE.INVALID_INPUT, "null body");
			}

			Long filesize = request().body().asRaw().size();
			if (filesize > ConstantUtil.FILE_MAX_SIZE) {
				return this.handleFailed(ERROR_CODE.FILE_TOO_LARGE, "filesize = " + filesize);
			}

			//ファイルのタイプをチェック
			File file = request().body().asRaw().asFile();
			InputStream is = new BufferedInputStream(new FileInputStream(file));
			String mimeType = URLConnection.guessContentTypeFromStream(is);
			if (mimeType == null) {
				return this.handleFailed(ERROR_CODE.FILE_INVALID_FORMAT, "ファイルタイプを検知をできません");
			}

			boolean validFileType = false;
			for (ConstantUtil.IMAGE_TYPE_WHITELIST validMimeType : ConstantUtil.IMAGE_TYPE_WHITELIST.values()) {
				if (validMimeType.getValue() == mimeType) {
					validFileType = true;
					break;
				}
			};
			if (!validFileType) {
				return this.handleFailed(ERROR_CODE.FILE_INVALID_FORMAT, "ファイルタイプを検知をできません");
			}

			String imageID = storedImage.saveImage(is);
			if (product.getImageID() != null) {
				storedImage.deleteImage(imageID);
			}
			product.setImageID(imageID.toString());
			product.save();
			return this.handleSuccess(new ProductImageResponseData(id, imageID));
		} catch (IOException exception) {
			Logger.debug(exception.getMessage());
			throw exception;
		}
	}
	
	public Result delete(Long id) {
			Product product = Product.find.byId(id);
			if (product == null) {
				return this.handleFailed(ERROR_CODE.PRODUCT_NOT_FOUND, "商品がない　id = " + id);
			}
			
			if (product.getImageID()!= null && !storedImage.deleteImage(product.getImageID())) {
				return this.handleFailed(ERROR_CODE.FILE_CANNOT_DELETE, "ファイルを削除できません　id=" + product.getImageID());				
			}
			product.setImageID(null);
			product.save();
			return this.handleSuccess(new ProductImageResponseData(id, null));
	}

	public class ProductImageResponseData {
		private Long id;
		private String imageID;

		public ProductImageResponseData(Long id, String imageID) {
			this.id = id;
			this.imageID = imageID;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getImageID() {
			return imageID;
		}

		public void setImageID(String imageID) {
			this.imageID = imageID;
		}
	}
}

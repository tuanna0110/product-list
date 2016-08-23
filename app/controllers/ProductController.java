package controllers;

import play.data.Form;
import play.mvc.*;
import services.StoredImage;
import utils.CommonUtil;
import utils.ConstantUtil.ERROR_CODE;
import models.Product;

import javax.inject.Inject;

import com.avaje.ebean.annotation.Transactional;

import auth.SecuredToken;

@Security.Authenticated(SecuredToken.class)
public class ProductController extends BaseController {

	@Inject
	StoredImage storedImage;
	public Result create() {
		Form<Product> form = formFactory.form(Product.class).bindFromRequest();
        if (form.hasErrors()) {
            return this.handleFailed(ERROR_CODE.INVALID_INPUT, CommonUtil.transferValidationErrorToMessage(form.errors()));
        }

		Product productToSave = form.get();
		productToSave.save();
		return this.handleSuccess(productToSave);
	}

	public Result read(Long id) {
		Product product = Product.find.byId(id);
		if (product == null) {
			return this.handleFailed(ERROR_CODE.PRODUCT_NOT_FOUND, "商品がない　id = " + id);
		}
		return this.handleSuccess(product);
	}

	public Result update(Long id) {
		Product product = Product.find.byId(id);
		if (product == null) {
			return this.handleFailed(ERROR_CODE.PRODUCT_NOT_FOUND, "商品がない　id = " + id);
		}

		Form<Product> form = formFactory.form(Product.class).bindFromRequest();
        if (form.hasErrors()) {
            return this.handleFailed(ERROR_CODE.INVALID_INPUT, CommonUtil.transferValidationErrorToMessage(form.errors()));
        }

		Product productToUpdate = form.get();
		productToUpdate.setId(product.getId());
		productToUpdate.setImageID(product.getImageID());
		productToUpdate.update();
		return this.handleSuccess(productToUpdate);
	}

	@Transactional
	public Result delete(Long id) {
		Product product = Product.find.byId(id);
		if (product != null) {
			product.delete();
			if (product.getImageID() != null) {
				storedImage.deleteImage(product.getImageID());
			}
		}

		// 商品がない場合は商品削除したこと同じだとお思います。
		return this.handleSuccess(null);
	}
}

package controllers;

import java.util.List;

import auth.SecuredToken;
import forms.SearchForm;
import models.Product;
import play.data.Form;
import play.mvc.Result;
import play.mvc.Security;
import utils.CommonUtil;
import utils.ConstantUtil.ERROR_CODE;

@Security.Authenticated(SecuredToken.class)
public class ProductListController extends BaseController {

	public Result get() {
		Form<SearchForm> form = formFactory.form(SearchForm.class).bindFromRequest();
		if (form.hasErrors()) {
			return this.handleFailed(ERROR_CODE.INVALID_INPUT,
					CommonUtil.transferValidationErrorToMessage(form.errors()));
		}

		SearchForm searchForm = form.get();
		searchForm.fillDefault();
		List<Product> listProduct = Product.search(searchForm.getSearchText(), searchForm.getMinPrice(),
				searchForm.getMaxPrice(), searchForm.getOrderBy(), searchForm.getLimit(), searchForm.getOffset());
		int total = Product.count(searchForm.getSearchText(), searchForm.getMinPrice(), searchForm.getMaxPrice());
		return this.handleSuccess(new ProductListData(total, listProduct));
	}

	public class ProductListData {
		private int total;
		private List<Product> list;

		public ProductListData(int total, List<Product> list) {
			this.total = total;
			this.list = list;
		}

		public int getTotal() {
			return total;
		}

		public void setTotal(int total) {
			this.total = total;
		}

		public List<Product> getList() {
			return list;
		}

		public void setList(List<Product> list) {
			this.list = list;
		}
	}
}

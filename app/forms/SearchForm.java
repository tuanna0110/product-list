package forms;

import java.util.ArrayList;
import java.util.List;

import play.data.validation.Constraints;
import play.data.validation.ValidationError;
import utils.ConstantUtil;
import utils.ConstantUtil.ORDER_BY;

public class SearchForm {

	public final static int MIN_PRICE_DEFAULT = -1;
	public final static int MAX_PRICE_DEFAULT = -1;
	public final static int ORDER_BY_DEFAULT = -1;

	public final static int LIMIT = 10;
	public final static int OFFSET = 0;

	@Constraints.MaxLength(100)
	private String searchText;

	@Constraints.Max(2147483647)
	@Constraints.Min(0)
	private Integer minPrice;

	@Constraints.Max(2147483647)
	@Constraints.Min(0)
	private Integer maxPrice;

	private Integer orderBy;

	@Constraints.Max(2147483647)
	@Constraints.Min(0)
	private Integer limit;

	@Constraints.Max(2147483647)
	@Constraints.Min(0)
	private Integer offset;

	public List<ValidationError> validate() {
		List<ValidationError> errors = new ArrayList<ValidationError>();
		if (this.orderBy == null) return null;
		for (ConstantUtil.ORDER_BY utilOrderBy : ConstantUtil.ORDER_BY.values()) {
			if (utilOrderBy.getValue() == orderBy) {
				return null;
			}
		};

		errors.add(new ValidationError("orderBy", "error.invalid"));
		return errors;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public int getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(int minPrice) {
		this.minPrice = minPrice;
	}

	public int getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(int maxPrice) {
		this.maxPrice = maxPrice;
	}

	public int getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(int orderBy) {
		this.orderBy = orderBy;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public void fillDefault() {
		if (this.minPrice == null)
			this.minPrice = -1;
		if (this.maxPrice == null)
			this.maxPrice = -1;
		if (this.orderBy == null)
			this.orderBy = ORDER_BY.ID_DESC.getValue();
		if (this.limit == null)
			this.limit = 10;
		if (this.offset == null)
			this.offset = 0;
	}
}

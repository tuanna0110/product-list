package models;

import play.data.validation.Constraints;
import javax.persistence.*;
import java.util.List;

import utils.ConstantUtil;

@Entity
public class Product extends BaseModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

	@Constraints.MaxLength(100)
	@Constraints.Required
	public String title;

	@Constraints.MaxLength(500)
	@Constraints.Required
	public String description;

	@Constraints.Max(2147483647)
	@Constraints.Min(0)
	@Constraints.Required
	public int price;

	public static Finder<Long, Product> find = new Finder<Long, Product>(Product.class);

	public Product(String title, String description, int price) {
		this.title = title;
		this.description = description;
		this.price = price;
	}

	/**
	 * 検索条件に該当する商品リストを習得する
	 * 
	 * @param textSearch
	 *            FULLTEXT検索するための文字例
	 * @param minPrice
	 *            検索条件の最小価値
	 * @param maxPrice
	 *            検索条件の最大価値
	 * @param orderBy
	 *            検索結果の標準の順序
	 * @param limit
	 *            検索結果の行数
	 * @param offset
	 *            検索結果の最初位置
	 * @return List<Product> 検索条件に該当する商品リスト
	 */
	public static List<Product> search(String textSearch, int minPrice, int maxPrice, int orderBy, int limit,
			int offset) {
		StringBuilder stringQuery = new StringBuilder("WHERE 1=1");
		if (textSearch != null) {
			stringQuery.append(" AND MATCH(title, description) AGAINST ('" + textSearch + "' IN BOOLEAN MODE");
		}
		if (minPrice >= 0) {
			stringQuery.append(" AND price >= " + minPrice);
		}
		if (maxPrice >= 0) {
			stringQuery.append(" AND price <= " + maxPrice);
		}

		for (ConstantUtil.ORDER_BY utilOrderBy : ConstantUtil.ORDER_BY.values()) {
			if (utilOrderBy.getValue() == orderBy) {
				stringQuery.append(" ORDER BY " + utilOrderBy);
				break;
			}
		};

		stringQuery.append(" LIMIT " + limit);
		stringQuery.append(" OFFSET " + offset);

		return find.setQuery(stringQuery.toString()).findList();
	}
}
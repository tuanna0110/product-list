package models;

import play.data.validation.Constraints;
import javax.persistence.*;
import java.util.List;
import utils.ConstantUtil;

@Entity
public class Product extends BaseModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Constraints.MaxLength(100)
	@Constraints.Required
	private String title;

	@Constraints.MaxLength(500)
	@Constraints.Required
	private String description;

	@Constraints.Max(2147483647)
	@Constraints.Min(1)
	@Constraints.Required
	private Integer price;	

	@Column
	@Constraints.MaxLength(256)
	private String imageID;

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}	

	public String getImageID() {
		return imageID;
	}

	public void setImageID(String imageID) {
		this.imageID = imageID;
	}

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
		StringBuilder stringQuery = buildConditonalQuery(textSearch, minPrice, maxPrice);

		for (ConstantUtil.ORDER_BY utilOrderBy : ConstantUtil.ORDER_BY.values()) {
			if (utilOrderBy.getValue() == orderBy) {
				stringQuery.append(" ORDER BY " + utilOrderBy.toString());
				break;
			}
		};

		stringQuery.append(" LIMIT " + limit);
		stringQuery.append(" OFFSET " + offset);
		return find.setQuery(stringQuery.toString()).findList();
	}
	
	public static int count(String textSearch, int minPrice, int maxPrice) {
		StringBuilder stringQuery = buildConditonalQuery(textSearch, minPrice, maxPrice);
		return find.setQuery(stringQuery.toString()).findRowCount();
	}
	
	private static StringBuilder buildConditonalQuery(String textSearch, int minPrice, int maxPrice) {
		StringBuilder stringQuery = new StringBuilder("WHERE 1=1");
		
		/* TODO: MARIADBのFULLTEXT機能を使ってみたかったですが、
		 * 日本語で使えるための設定は時間がちょっとかかる。。。
		if (textSearch != null) {
			stringQuery.append(" AND MATCH(description) AGAINST ('" + textSearch + "' IN BOOLEAN MODE)");
		}
		*/
		
		if (textSearch != null) {
			stringQuery.append(" AND (title LIKE '%" + textSearch + "%' OR description LIKE '%" + textSearch + "%')");
		}
		
		if (minPrice >= 0) {
			stringQuery.append(" AND price >= " + minPrice);
		}
		if (maxPrice >= 0) {
			stringQuery.append(" AND price <= " + maxPrice);
		}
		
		return stringQuery;
	}
}
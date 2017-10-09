package models.helpers;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.util.List;

/**
 * The type Pagination adapter.
 *
 * @param <T> the type parameter
 */
public class PaginationAdapter<T> {

	private Integer pageNumber;
	private Integer pageSize;
	private List<T> model;
	private Long numberOfPages;

	private PaginationAdapter() {}

	/**
	 * Create output pagination adapter.
	 *
	 * @return the pagination adapter
	 */
	public static PaginationAdapter createOutput() {
		return new PaginationAdapter<>();
	}

	/**
	 * Gets page number.
	 *
	 * @return the page number
	 */
	public Integer getPageNumber() { return this.pageNumber; }

	/**
	 * Sets page number.
	 *
	 * @param pageNumber the page number
	 * @return the page number
	 */
	public PaginationAdapter setPageNumber(@Nonnull @Nonnegative final Integer pageNumber) {
		this.pageNumber = isPositive(pageNumber) ? pageNumber : null;
		return this;
	}

	/**
	 * Gets page size.
	 *
	 * @return the page size
	 */
	public Integer getPageSize() { return this.pageSize; }

	/**
	 * Sets page size.
	 *
	 * @param pageSize the page size
	 * @return the page size
	 */
	public PaginationAdapter setPageSize(@Nonnull @Nonnegative final Integer pageSize) {
		this.pageSize = isPositive(pageSize) ? pageSize : null;
		return this;
	}

	/**
	 * Gets model.
	 *
	 * @return the model
	 */
	public List<T> getModel() { return this.model; }

	/**
	 * Sets model.
	 *
	 * @param model the model
	 * @return the model
	 */
	public PaginationAdapter setModel(List<T> model) {
		this.model = model;
		return this;
	}

	/**
	 * Gets number of pages.
	 *
	 * @return the number of pages
	 */
	public Long getNumberOfPages() { return this.numberOfPages; }

	/**
	 * Sets number of pages.
	 *
	 * @param numberOfPages the number of pages
	 * @return the number of pages
	 */
	public PaginationAdapter setNumberOfPages(@Nonnull @Nonnegative final Long numberOfPages) {
		this.numberOfPages = isPositive(numberOfPages) ? numberOfPages : null;
		return this;
	}

	private Boolean isPositive(final Integer number) {
		return isPositive(number.longValue());
	}

	private Boolean isPositive(final Long number) {
		return number != null && number >= 0;
	}
}

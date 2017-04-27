package za.co.yellowfire.threesixty.ui.component;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.vaadin.viritin.LazyList.CountProvider;
import org.vaadin.viritin.v7.SortableLazyList.SortablePagingProvider;

public class SpringEntityProvider<T, I extends Serializable> implements SortablePagingProvider<T>, CountProvider {
	private static final long serialVersionUID = 1L;
	
	private transient PagingAndSortingRepository<T, I> repository;
	
	public SpringEntityProvider(PagingAndSortingRepository<T, I> repository) {
		this.repository = repository;
	}
	
	/**
     * Fetches one "page" of entities from the back-end. The amount
     * "maxResults" should match with the value configured for the LazyList
     *
     * @param firstRow the index of first row that should be fetched
     * @param sortAscending the direction to be used for sorting, true if ascending
     * @param property the property based on the sorting should be done, null for natural order
     * @return a sub list from given first index
     */
	//@Override
    public List<T> findEntities(
    		int firstRow, 
    		boolean sortAscending,
            String property) {
		
		return (List<T>) getResults();
	}

	/**
     * @return the count of entities listed in the LazyList
     */
	//@Override
	public int size() {
		return ((List<T>) getResults()).size();
	}
	
	protected PagingAndSortingRepository<T, I> getRepository() { 
		return this.repository;
	}
	
	protected Iterable<T> getResults() {
		return repository.findAll();
	}
}

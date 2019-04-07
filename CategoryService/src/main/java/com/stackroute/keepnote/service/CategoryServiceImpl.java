package com.stackroute.keepnote.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.keepnote.exception.CategoryDoesNoteExistsException;
import com.stackroute.keepnote.exception.CategoryNotCreatedException;
import com.stackroute.keepnote.exception.CategoryNotFoundException;
import com.stackroute.keepnote.model.Category;
import com.stackroute.keepnote.repository.CategoryRepository;

/*
* Service classes are used here to implement additional business logic/validation 
* This class has to be annotated with @Service annotation.
* @Service - It is a specialization of the component annotation. It doesn't currently 
* provide any additional behavior over the @Component annotation, but it's a good idea 
* to use @Service over @Component in service-layer classes because it specifies intent 
* better. Additionally, tool support and additional behavior might rely on it in the 
* future.
* */

@Service
public class CategoryServiceImpl implements CategoryService {

	/*
	 * Autowiring should be implemented for the CategoryRepository. (Use
	 * Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */
	@Autowired
	private CategoryRepository categoryRepository;
	/*
	 * This method should be used to save a new category.Call the corresponding
	 * method of Respository interface.
	 */
	public Category createCategory(Category category) throws CategoryNotCreatedException {

		Category currentcategory = this.categoryRepository.save(category);
		if (currentcategory != null) {
			return currentcategory;
		} else {
			throw new CategoryNotCreatedException("category was not created");
		}
	}

	/*
	 * This method should be used to delete an existing category.Call the
	 * corresponding method of Respository interface.
	 */
	public boolean deleteCategory(String categoryId) throws CategoryDoesNoteExistsException {
		 Category currentCategory = categoryRepository.findById(categoryId).get();
		
		if (currentCategory != null) {
			try{
			this.categoryRepository.delete(currentCategory);
			return true;
			}catch(Exception e){
				throw e;
			}
		} else {
		throw new CategoryDoesNoteExistsException("User was not found");
	}
	}

	/*
	 * This method should be used to update a existing category.Call the
	 * corresponding method of Respository interface.
	 */
	public Category updateCategory(Category category, String categoryId) {
		try{
		Category currentCategory = categoryRepository.findById(categoryId).get();
		if (currentCategory != null) {
			return this.categoryRepository.save(category);
		}else{
			return null;
		}
		//return currentUser;
		}catch(Exception e){
			throw e;
		}
			
	}

	/*
	 * This method should be used to get a category by categoryId.Call the
	 * corresponding method of Respository interface.
	 */
	public Category getCategoryById(String categoryId) throws CategoryNotFoundException {

		Optional<Category> result = this.categoryRepository.findById(categoryId);
		 return result.orElseThrow(() -> new CategoryNotFoundException(categoryId));
	}

	/*
	 * This method should be used to get a category by userId.Call the corresponding
	 * method of Respository interface.
	 */
	public List<Category> getAllCategory() {

		return this.categoryRepository.findAll();
	}

}

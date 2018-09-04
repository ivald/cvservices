package edu.ilyav.api.service;

import edu.ilyav.api.models.Language;
import edu.ilyav.api.service.exceptions.ResourceNotFoundException;

import java.util.List;

public interface LanguageService {
	List<Language> findAll();

	Language findById(Long id);

	void delete(Long id);

	Language saveOrUpdate(Language language) throws ResourceNotFoundException;

}

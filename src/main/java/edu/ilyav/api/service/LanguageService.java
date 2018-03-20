package edu.ilyav.api.service;

import edu.ilyav.api.models.Language;

import java.util.List;

public interface LanguageService {
	List<Language> findAll();

	Language findById(Long id);

	void delete(Long id);

	Language saveOrUpdate(Language language);

}

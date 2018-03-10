package edu.ilyav.api.service;

import edu.ilyav.api.models.Education;

import java.util.List;

public interface EducationService {
	List<Education> findAll();

	Education findById(Long id);

	void delete(Long id);

	Education saveOrUpdate(Education experience);

}

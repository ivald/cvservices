package edu.ilyav.api.service;

import edu.ilyav.api.models.Summary;
import edu.ilyav.api.service.exceptions.ResourceNotFoundException;

import java.util.List;

public interface SummaryService {
	List<Summary> findAll();

	Summary findById(Long id);

	Summary saveOrUpdate(Summary summary) throws ResourceNotFoundException;

}

package edu.ilyav.api.service;

import edu.ilyav.api.models.Summary;

import java.util.List;

public interface SummaryService {
	List<Summary> findAll();

	Summary findById(Long id);

	Summary saveOrUpdate(Summary summary);

}

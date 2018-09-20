package edu.ilyav.api.service.impl;

import edu.ilyav.api.dao.SummaryRepository;
import edu.ilyav.api.models.ProfileContent;
import edu.ilyav.api.models.Summary;
import edu.ilyav.api.service.SummaryService;
import edu.ilyav.api.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SummaryServiceImpl extends BaseServiceImpl implements SummaryService {

	@Autowired
	private SummaryRepository summaryRepository;
	
	@Override
	public List<Summary> findAll() {
		return summaryRepository.findAll();
	}

	@Override
	public Summary findById(Long id){
		return summaryRepository.findById(id).get();
	}
	
	@Override
	public Summary saveOrUpdate(Summary summary) throws ResourceNotFoundException {
		ProfileContent profileContent = getProfileContent(summary.getProfileContentId()).get();
		updateHomeProfileObjects();
		summary.setProfileContent(profileContent);
		return summaryRepository.save(summary);
	}

}

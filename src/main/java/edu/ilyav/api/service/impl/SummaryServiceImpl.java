package edu.ilyav.api.service.impl;

import edu.ilyav.api.cotrollers.HomeController;
import edu.ilyav.api.cotrollers.ProfileController;
import edu.ilyav.api.dao.SummaryRepository;
import edu.ilyav.api.models.ProfileContent;
import edu.ilyav.api.models.Summary;
import edu.ilyav.api.service.ProfileContentService;
import edu.ilyav.api.service.SummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SummaryServiceImpl implements SummaryService {

	@Autowired
	private ProfileContentService profileContentService;

	@Autowired
	private SummaryRepository summaryRepository;
	
	@Override
	public List<Summary> findAll() {
		return summaryRepository.findAll();
	}

	@Override
	public Summary findById(Long id){
		return summaryRepository.findById(id);
	}
	
	@Override
	public Summary saveOrUpdate(Summary summary) {
		ProfileContent profileContent = profileContentService.findById(summary.getProfileContentId());
		summary.setProfileContent(profileContent);
		HomeController.isChanged = Boolean.TRUE;
		ProfileController.isChanged = Boolean.TRUE;
		return summaryRepository.save(summary);
	}

}

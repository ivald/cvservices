package edu.ilyav.api.service.impl;

import edu.ilyav.api.dao.EmailMeRepository;
import edu.ilyav.api.dao.ProfileRepository;
import edu.ilyav.api.models.EmailMe;
import edu.ilyav.api.models.Profile;
import edu.ilyav.api.models.Role;
import edu.ilyav.api.service.EmailMeService;
import edu.ilyav.api.service.ProfileService;
import edu.ilyav.api.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmailMeServiceImpl extends BaseServiceImpl implements EmailMeService {

    @Autowired
    private EmailMeRepository emailMeRepository;

    @Autowired
    private ProfileService profileService;

    @Override
    public List<EmailMe> findAll() {
        return emailMeRepository.findAll();
    }

    @Override
    public EmailMe findById(Long id) {
        return emailMeRepository.findById(id).get();
    }

    @Override
    public void delete(Long id) {
        emailMeRepository.deleteById(id);
    }

    @Override
    public EmailMe saveOrUpdate(EmailMe emailMe) throws ResourceNotFoundException {
        Profile profile = profileService.findById(emailMe.getProfileId());
        emailMe.setProfile(profile);
        emailMe.setUnread(Boolean.TRUE);
        return emailMeRepository.save(emailMe);
    }

}

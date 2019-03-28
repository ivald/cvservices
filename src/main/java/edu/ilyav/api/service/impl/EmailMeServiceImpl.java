package edu.ilyav.api.service.impl;

import edu.ilyav.api.dao.EmailMeRepository;
import edu.ilyav.api.models.EmailMe;
import edu.ilyav.api.models.Role;
import edu.ilyav.api.service.EmailMeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailMeServiceImpl extends BaseServiceImpl implements EmailMeService {

    @Autowired
    private EmailMeRepository emailMeRepository;

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
    public EmailMe saveOrUpdate(EmailMe role) {
        return emailMeRepository.save(role);
    }

}

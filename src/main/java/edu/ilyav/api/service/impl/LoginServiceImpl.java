package edu.ilyav.api.service.impl;

import edu.ilyav.api.dao.LoginRepository;
import edu.ilyav.api.models.Login;
import edu.ilyav.api.service.LoginService;
import edu.ilyav.api.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginServiceImpl extends BaseServiceImpl implements LoginService {

	@Autowired
	private LoginRepository loginRepository;
	
	@Override
	public List<Login> findAll() {
		return loginRepository.findAll();
	}

	@Override
	public Login findById(Long id){
		return loginRepository.findById(id);
	}

	@Override
	public void delete(Long id) {
		loginRepository.delete(id);
	}
	
	@Override
	public Login saveOrUpdate(Login login) throws ResourceNotFoundException {
		return loginRepository.save(login);
	}
	
}

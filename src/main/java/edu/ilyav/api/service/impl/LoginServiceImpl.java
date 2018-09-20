package edu.ilyav.api.service.impl;

import edu.ilyav.api.dao.LoginRepository;
import edu.ilyav.api.models.Login;
import edu.ilyav.api.service.LoginService;
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
		return loginRepository.findById(id).get();
	}

	@Override
	public void delete(Long id) {
		loginRepository.deleteById(id);
	}
	
	@Override
	public Login saveOrUpdate(Login login) {
		return loginRepository.save(login);
	}
	
}

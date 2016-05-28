package com.maliavin.vcp.component;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.maliavin.vcp.domain.Company;
import com.maliavin.vcp.domain.User;
import com.maliavin.vcp.exception.SavingException;
import com.maliavin.vcp.repository.storage.CompanyRepository;
import com.maliavin.vcp.repository.storage.UserRepository;

/**
 * Aspect for validation users and companies when admin try to save it in db
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
@Aspect
@Component
public class SaveValidator {
    
    @Autowired
    private CompanyRepository companyRepository;
    
    @Autowired 
    private UserRepository userRepository;
    
    @Before("execution(* com.maliavin.vcp.service.impl.AdminServiceImpl.addCompany(..))")
    public void validateAddCompany(JoinPoint jp){
        Company company = (Company) jp.getArgs()[0];
        checkCompany(company);
    }
    
    @Before("execution(* com.maliavin.vcp.service.impl.AdminServiceImpl.saveCompany(..))")
    public void validateSaveCompany(JoinPoint jp){
        Company company = (Company) jp.getArgs()[1];
        checkCompany(company);
    }
    
    private void checkCompany(Company company) {
        String id = company.getId();
        String name = company.getName();
        String email = company.getContactEmail();
        String phone = company.getPhone();
        List<Company> companies = companyRepository.findByNameOrContactEmailOrPhone(name, email, phone);

        for (Company c : companies){
            String msgTmpl = "Company with same %s is already exists";
            List<String> equals = new ArrayList<>();
            if (c.getName().equals(name)){
                equals.add("name");
            }
            if (c.getContactEmail().equals(email)){
                equals.add("email");
            }
            if (c.getPhone().equals(phone)){
                equals.add("phone");
            }
            String msg = String.format(msgTmpl, equals.toString());
            if (id == null || !c.getId().equals(id)){
                 throw new SavingException(msg);
            }
        }
    }
    
    @Before("execution(* com.maliavin.vcp.service.impl.AdminServiceImpl.addUser(..))")
    public void validateAddUser(JoinPoint jp){
        User user = (User) jp.getArgs()[0];
        checkUser(user);
    }
    
    @Before("execution(* com.maliavin.vcp.service.impl.AdminServiceImpl.saveUser(..))")
    public void validateSaveUser(JoinPoint jp){
        User user = (User) jp.getArgs()[1];
        checkUser(user);
    }

    private void checkUser(User user) {
        String id = user.getId();
        String login = user.getLogin();
        String email = user.getEmail();
        
        List<User> users = userRepository.findByLoginOrEmail(login, email);
        
        for (User u : users){
            String msgTmpl = "User with same %s is already exists";
            List<String> equals = new ArrayList<>();
            if (u.getLogin().equals(login)){
                equals.add("login");
            }
            if (u.getEmail().equals(email)){
                equals.add("email");
            }
            String msg = String.format(msgTmpl, equals.toString());
            if (id == null || !u.getId().equals(id)){
                 throw new SavingException(msg);
            }
        }
        
    }

}

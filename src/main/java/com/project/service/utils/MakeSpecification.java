package com.project.service.utils;

import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Component;

@Component
public class MakeSpecification {

    public Specifications makeClient(String lookingFor){

       return Specifications.where(new ClientSpecification(new SearchCriteria("firstName", ":", lookingFor)))
               .or(new ClientSpecification(new SearchCriteria("lastName", ":", lookingFor)))
               .or(new ClientSpecification(new SearchCriteria("lastName", ":", lookingFor)))
               .or(new ClientSpecification(new SearchCriteria("municipality", ":", lookingFor)))
               .or(new ClientSpecification(new SearchCriteria("email", ":", lookingFor)))
               .or(new ClientSpecification(new SearchCriteria("location", ":", lookingFor)))
               .or(new ClientSpecification(new SearchCriteria("province", ":", lookingFor)))
               .or(new ClientSpecification(new SearchCriteria("address", ":", lookingFor)))
               .or(new ClientSpecification(new SearchCriteria("note", ":", lookingFor)));
    }
    public Specifications makeUser(String lookingFor){

        return Specifications.where(new UserSpecification(new SearchCriteria("email", ":", lookingFor)))
                .or(new UserSpecification(new SearchCriteria("firstName", ":", lookingFor)))
                .or(new UserSpecification(new SearchCriteria("lastName", ":", lookingFor)))
                .or(new UserSpecification(new SearchCriteria("username", ":", lookingFor)));
    }
}

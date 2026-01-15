package vision.services;

import vision.exceptions.ServiceValidationException;

public class ValidateAndParseIDService {
    public static Long ValidateAndParseLongID(String idStr) {
        Long id =  Long.parseLong(idStr);
        if(id < 1){
            throw new ServiceValidationException();
        }
        return id;
    }
}

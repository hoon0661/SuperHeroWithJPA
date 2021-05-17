/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hoon.hero.superherowithjpa.locationcontroller;

import hoon.hero.superherowithjpa.dao.OrganizationDao;
import hoon.hero.superherowithjpa.models.Organization;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hoon0
 */
@CrossOrigin
@RestController
@RequestMapping("/api/organization")
public class OrganizationController {
    @Autowired
    OrganizationDao organizationDao;
    
    @GetMapping
    public List<Organization> getAllOrganizations(){
        return organizationDao.findAll();
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Organization addOrganization(@Valid @RequestBody Organization organization){
        return organizationDao.save(organization);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Organization> getOrganizationById(@PathVariable int id){
        Organization result = organizationDao.findById(id).orElse(null);
        if(result == null){
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity updateOrganization(@PathVariable int id, @Valid @RequestBody Organization organization){
        ResponseEntity response = new ResponseEntity(HttpStatus.NOT_FOUND);
        if(id != organization.getId()){
            response = new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
        } else {
            organizationDao.save(organization);
            response = new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return response;
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity deleteOrganization(@PathVariable int id){
        ResponseEntity response = new ResponseEntity(HttpStatus.NOT_FOUND);
        if(organizationDao.findById(id) != null){
            organizationDao.deleteById(id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return response;
        
    }
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}

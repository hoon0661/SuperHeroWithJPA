/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hoon.hero.superherowithjpa.locationcontroller;

import hoon.hero.superherowithjpa.dao.HeroDao;
import hoon.hero.superherowithjpa.dao.LocationDao;
import hoon.hero.superherowithjpa.dao.OrganizationDao;
import hoon.hero.superherowithjpa.dao.SightingDao;
import hoon.hero.superherowithjpa.models.Hero;
import hoon.hero.superherowithjpa.models.Organization;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
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
@RequestMapping("/api/hero")
public class HeroController {
    
    @Autowired
    HeroDao heroDao;
    
    @Autowired
    OrganizationDao organizationDao;
    
    @GetMapping
    public List<Hero> getAllHeroes(){
        return heroDao.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Hero> getHeroById(@PathVariable int id){
        Hero result = heroDao.findById(id).orElse(null);
        if(result == null){
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Hero addHero(@Valid @RequestBody Hero hero){
        return heroDao.save(hero);
    }
    
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public Hero addHero(@RequestBody Hero hero, HttpServletRequest request){
//        String[] organizationIds = request.getParameterValues("organizationIds");
//        List<Organization> organizations = new ArrayList<>();
//        if(organizationIds != null){        
//            for(String organizationId : organizationIds){
//                organizations.add(organizationDao.getOne(Integer.parseInt(organizationId)));
//            }
//            hero.setOrganizations(organizations);
//        }
//        return heroDao.save(hero);
//    }
    
    @PutMapping("/{id}")
    public ResponseEntity updateHero(@PathVariable int id, @Valid @RequestBody Hero hero){
        ResponseEntity response = new ResponseEntity(HttpStatus.NOT_FOUND);
        if(id != hero.getId()){
            response = new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
        } else {          
            heroDao.save(hero);
            response = new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return response;
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity deleteHero(@PathVariable int id){
        ResponseEntity response = new ResponseEntity(HttpStatus.NOT_FOUND);
        if(heroDao.findById(id) != null){
            heroDao.deleteById(id);
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

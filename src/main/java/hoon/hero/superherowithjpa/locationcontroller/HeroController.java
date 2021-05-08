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
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    
    @GetMapping
    public List<Hero> getAllHeroes(){
        return heroDao.findAll();
    }
    
}

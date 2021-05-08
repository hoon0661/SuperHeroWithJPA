/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hoon.hero.superherowithjpa.dao;

import hoon.hero.superherowithjpa.models.Sighting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hoon0
 */
@Repository
public interface SightingDao extends JpaRepository<Sighting, Integer>{
    
}

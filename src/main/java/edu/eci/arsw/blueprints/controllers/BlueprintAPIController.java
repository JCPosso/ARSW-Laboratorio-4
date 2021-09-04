/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hcadavid
 */
@Service
public class BlueprintAPIController {
    @RestController
    @RequestMapping(value = "/blueprints")
    public class BlueprintController  {
        @Autowired
        BlueprintsServices blueprintservices = null;

        @RequestMapping(method = RequestMethod.GET)
        public ResponseEntity<?> getAllBlueprints() {
            try {
                //obtener datos que se enviarán a través del API
                return new ResponseEntity<>(blueprintservices.getAllBlueprints(), HttpStatus.ACCEPTED );
            } catch (BlueprintNotFoundException ex) {
                Logger.getLogger( BlueprintController.class.getName() ).log( Level.SEVERE, null, ex );
                return new ResponseEntity<>( ex.getMessage(), HttpStatus.NOT_FOUND );
            }
        }

        @RequestMapping(path="/{author}" , method = RequestMethod.GET)
        public ResponseEntity<?> getBlueprintsByAuthor(@PathVariable ("author") String author) {
            try {
                //obtener datos que se enviarán a través del API
                return new ResponseEntity<>(blueprintservices.getBlueprintsByAuthor(author), HttpStatus.ACCEPTED );
            } catch (BlueprintNotFoundException ex) {
                Logger.getLogger( BlueprintController.class.getName() ).log( Level.SEVERE, null, ex );
                return new ResponseEntity<>( ex.getMessage(), HttpStatus.NOT_FOUND );
            }
        }

        @RequestMapping(path="/{author}/{bpname}" , method = RequestMethod.GET)
        public ResponseEntity<?> getBlueprintByAuthorAndName(
                                @PathVariable ("author") String author,
                                @PathVariable ("bpname") String bpname) {
            try {
                //obtener datos que se enviarán a través del API
                return new ResponseEntity<>(blueprintservices.getBlueprint( author,bpname ), HttpStatus.ACCEPTED );
            } catch (BlueprintNotFoundException ex) {
                Logger.getLogger( BlueprintController.class.getName() ).log( Level.SEVERE, null, ex );
                return new ResponseEntity<>( ex.getMessage(), HttpStatus.NOT_FOUND );
            }
        }


    }
}


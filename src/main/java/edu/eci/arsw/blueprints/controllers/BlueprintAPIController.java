/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import java.util.logging.Level;
import java.util.logging.Logger;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

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
        @Qualifier("BlueprintsServices")
        BlueprintsServices bps;

        @RequestMapping(method = RequestMethod.GET)
        public ResponseEntity<?> getAllBlueprints() {
            try {
                //obtener datos que se enviarán a través del API
                return new ResponseEntity<>(bps.getAllBlueprints(), HttpStatus.ACCEPTED );
            } catch (BlueprintNotFoundException ex) {
                Logger.getLogger( BlueprintController.class.getName() ).log( Level.SEVERE, null, ex );
                return new ResponseEntity<>( ex.getMessage(), HttpStatus.NOT_FOUND );
            }
        }

        @RequestMapping(path="/{author}" , method = RequestMethod.GET)
        public ResponseEntity<?> getBlueprintsByAuthor(@PathVariable ("author") String author) {
            try {
                //obtener datos que se enviarán a través del API
                return new ResponseEntity<>(bps.getBlueprintsByAuthor(author), HttpStatus.ACCEPTED );
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
                return new ResponseEntity<>(bps.getBlueprint( author,bpname ), HttpStatus.ACCEPTED );
            } catch (BlueprintNotFoundException ex) {
                Logger.getLogger( BlueprintController.class.getName() ).log( Level.SEVERE, null, ex );
                return new ResponseEntity<>( ex.getMessage(), HttpStatus.NOT_FOUND );
            }
        }

        @RequestMapping(method = RequestMethod.POST)
        @ResponseBody
        public  synchronized ResponseEntity<?> addBlueprint(@RequestBody Blueprint bp){
            try {
                //registrar dato
                bps.addNewBlueprint( bp );
                return new ResponseEntity<>(HttpStatus.CREATED);
            } catch (BlueprintPersistenceException ex) {
                Logger.getLogger(BlueprintController.class.getName()).log(Level.SEVERE, null, ex);
                return new ResponseEntity<>(ex.getMessage(),HttpStatus.FORBIDDEN);
            }
        }

        @RequestMapping(value="/{author}/{bpname}" , method = RequestMethod.PUT)
        @ResponseBody
        public synchronized ResponseEntity<?> addBlueprintByAuthorAndName(
                @PathVariable ("author") String author,
                @PathVariable ("bpname") String bpname,
                @RequestBody Blueprint bp) {
            try {
                bps.setBlueprint( author,bpname,bp );
                return new ResponseEntity<>(HttpStatus.CREATED );
            } catch (BlueprintNotFoundException ex) {
                Logger.getLogger(BlueprintController.class.getName()).log(Level.SEVERE, null, ex);
                return new ResponseEntity<>(ex.getMessage(),HttpStatus.FORBIDDEN);
            }
        }
    }
}
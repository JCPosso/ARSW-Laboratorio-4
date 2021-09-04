/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.model;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Home
 */
@Component
@Qualifier("redundancyFilter")
public class redundancyFilter implements Filter{
    @Override
    public Blueprint filterPoints(Blueprint bp) {
        List<Point> origins = bp.getPoints();
        ArrayList<Point> news = new ArrayList<Point>();
        for(int i=0;i<origins.size()-1;i++){
            if(!(origins.get(i).toString().equals(origins.get(i+1).toString()))) news.add(origins.get(i));
        }
        return new Blueprint(bp.getAuthor(),bp.getName(),news);
    }
    
}

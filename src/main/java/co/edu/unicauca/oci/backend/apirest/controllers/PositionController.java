package co.edu.unicauca.oci.backend.apirest.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unicauca.oci.backend.apirest.services.IPositionService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class PositionController {

    @Autowired
    private IPositionService servicePosition;

    @GetMapping("/positions")
    public ResponseEntity<?> findAllPositions() {
        return ResponseEntity.ok(this.servicePosition.findAllPosition());
    }

    @GetMapping("/dependences")
    public ResponseEntity<?> findAllDependences() {
        return ResponseEntity.ok(this.servicePosition.findAllDependences());
    }
}
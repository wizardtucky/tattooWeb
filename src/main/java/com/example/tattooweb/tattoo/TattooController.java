//package com.example.tattooweb.tattoo;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping(path = "api2/")
//public class TattooController {
//    private final TattooSerice tattooSerice;
//
//    @Autowired
//    public TattooController(TattooSerice tattooSerice) {
//        this.tattooSerice = tattooSerice;
//    }
//    @GetMapping
//    public List<Tattoo> getTattoos(){
//        return tattooSerice.getTattoos();
//    }
//
//    @PostMapping
//    private void registerNewTattoo(@RequestBody Tattoo tattoo) {
//        tattooSerice.addNewTattoo(tattoo);
//    }
//}
//

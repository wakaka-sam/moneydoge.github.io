package com.example.demo;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
public class HistoryController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/History")
    public int CreateErrand(@RequestParam("uid") String uid, @RequestParam("type") String type, @RequestParam("price") int price) {
        return jdbcTemplate.update("insert into moneydog.history(uid,type,price)values (?,?,?)", uid,type,price);
    }

    @RequestMapping(value = "/History1", method = RequestMethod.GET)
    public String say()
    {
        return "aa";
    }
}

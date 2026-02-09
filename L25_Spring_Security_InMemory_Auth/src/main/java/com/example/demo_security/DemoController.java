package com.example.demo_security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    // 9f71639b-8e0a-4daa-9680-2a0e4c4bf096
    // af8f2571-b7d6-4c1c-a34c-48876197da29

    @GetMapping("/student")         // Only by the people who have STUDENT role
    public String getStudentDetails() {
        return "Hello Student!!";
    }

    @GetMapping("/faculty")         // Only by the people who have FACULTY role
    public String getFacultyDetails() {
        return "Hello Faculty!!";
    }

    @GetMapping("/")                // Anyone (Need not be authenticated to call this, i.e bypassing the filter chain)
    public String getUser() {
        return "Hello User!!";
    }

    /**
     * Cookie stored by FE initially - FE174C97BFE0304944DB2F6B6E070CA6
     * Cookie sent by the FE in /demo - FE174C97BFE0304944DB2F6B6E070CA6
     *
     * Cookie returned by the server in POST /login - B8F1417FBB4A9D84ED411C2182EE1CA8
     * Cookie stored by FE after logging in - B8F1417FBB4A9D84ED411C2182EE1CA8
     *
     */
}

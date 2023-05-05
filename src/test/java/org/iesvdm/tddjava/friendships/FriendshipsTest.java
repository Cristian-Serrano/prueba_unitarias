package org.iesvdm.tddjava.friendships;



import org.junit.jupiter.api.*;
//import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

public class FriendshipsTest {

    Friendships friendships;

    @BeforeAll
    public static void beforeAll() {
        // This method will be executed once on initialization time
    }

    @BeforeEach
    public void beforeEach() {
        friendships = new Friendships();
        friendships.makeFriends("Joe", "Audrey");
        friendships.makeFriends("Joe", "Peter");
        friendships.makeFriends("Joe", "Michael");
        friendships.makeFriends("Joe", "Britney");
        friendships.makeFriends("Joe", "Paul");
    }


    @AfterAll
    public static void afterClass() {
        // This method will be executed once when all test are executed
    }

    @AfterEach
    public void afterEach() {
        // This method will be executed once after each test execution
    }

    @Test
    public void alexDoesNotHaveFriends() {
        //assertTrue(friendships.getFriendsList("Alex").isEmpty(), "Alex does not have friends");
        assertThat(friendships.getFriendsList("Alex"))
                .isEmpty();
    }

    @Test
    public void joeHas5Friends() {
        //assertEquals( 5, friendships.getFriendsList("Joe").size(), "Joe has 5 friends");
        assertThat(friendships.getFriendsList("Joe"))
                .hasSize(5)
                .as("Joe has 5 friends");
    }

    @Test
    public void joeIsFriendWithEveryone() {
        List<String> friendsOfJoe = Arrays.asList("Audrey", "Peter", "Michael", "Britney", "Paul");
        /* assertTrue(
                friendships.getFriendsList("Joe").containsAll(friendsOfJoe)
        );*/
        assertThat(friendships.getFriendsList("Joe"))
                .contains("Audrey",
                        "Peter",
                        "Michael",
                        "Britney",
                        "Paul");
    }

}

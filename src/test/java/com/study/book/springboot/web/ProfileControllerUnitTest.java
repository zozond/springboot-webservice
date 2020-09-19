package com.study.book.springboot.web;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.mock.env.MockEnvironment;

public class ProfileControllerUnitTest {

    @Test
    public void real_profile(){
        String expectedProfile = "real";
        MockEnvironment env = new MockEnvironment();

        env.addActiveProfile(expectedProfile);
        env.addActiveProfile("oauth");
        env.addActiveProfile("real-db");

        ProfileController controller = new ProfileController(env);
        String profile = controller.profile();
        Assertions.assertThat(profile).isEqualTo(expectedProfile);
    }

    @Test
    public void oauth_profile(){
        String expectedProfile = "oauth";
        MockEnvironment env = new MockEnvironment();

        env.addActiveProfile(expectedProfile);
        env.addActiveProfile("real-db");

        ProfileController controller = new ProfileController(env);
        String profile = controller.profile();
        Assertions.assertThat(profile).isEqualTo(expectedProfile);
    }


    @Test
    public void default_profile(){
        String expectedProfile = "default";
        MockEnvironment env = new MockEnvironment();

        ProfileController controller = new ProfileController(env);
        String profile = controller.profile();
        Assertions.assertThat(profile).isEqualTo(expectedProfile);
    }
}

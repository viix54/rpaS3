package by.jrr.start.service;

import by.jrr.start.bean.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoginServiceTest {

    LoginService loginService;
    User user;
    User blockedUser;
    String positiveUserInput = "password";
    String negativeUserInput = "wrong";


    @Before
    public void setUp(){
        this.loginService = new LoginService();
        this.user = getUser();
        this.blockedUser = getBlockedUser();
        Assert.assertEquals(3, getUser().getLoginAttempts());
        Assert.assertFalse(user.isBlocked());

        Assert.assertEquals(0, blockedUser.getLoginAttempts());
        Assert.assertTrue(blockedUser.isBlocked());
    }

    @Test
    public void checkUserPassword_positive(){



       boolean actualResult = loginService.checkUserPassword(user, positiveUserInput);
        Assert.assertTrue(actualResult);

    }



    @Test
    public void checkUserPassword_negative(){




        boolean actualResult = loginService.checkUserPassword(user, negativeUserInput);
        Assert.assertFalse(actualResult);

    }

    @Test
    public void reduceLoginAttempts(){
        loginService.reduceLoginAttempts(user);
        Assert.assertEquals(2, user.getLoginAttempts());
    }

    @Test
    public void login_positive(){



        boolean actualResult = loginService.login(user, positiveUserInput);
        Assert.assertTrue(actualResult);
    }

    @Test
    public void login_negative(){




        boolean actualResult = loginService.login(user, negativeUserInput);
        Assert.assertFalse(actualResult);
        Assert.assertEquals(2, user.getLoginAttempts());

    }

    @Test
    public void blockUser(){
        loginService.blockUser(user);
        Assert.assertTrue(user.isBlocked());
    }

    @Test
    public void after3wrongPasswords_ShouldBlockUser(){



        loginService.login(user, negativeUserInput);
        Assert.assertEquals(2, user.getLoginAttempts());
        Assert.assertFalse(user.isBlocked());

        loginService.login(user, negativeUserInput);
        Assert.assertEquals(1, user.getLoginAttempts());
        Assert.assertFalse(user.isBlocked());

        loginService.login(user, negativeUserInput);
        Assert.assertEquals(0, user.getLoginAttempts());
        Assert.assertTrue(user.isBlocked());

    }

    @Test
    public void blockedUserLogin_ShouldReturnFalse(){
        boolean actualResult = loginService.login(blockedUser, positiveUserInput);
        Assert.assertFalse(actualResult);
    }

    private User getUser() {
        User user = new User();

        user.setPassword("password");
        return user;
    }

    private User getBlockedUser() {
        User user = new User();

        user.setPassword("password");
        user.setLoginAttempts(0);
        user.setBlocked(true);
        return user;
    }


}
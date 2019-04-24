$(document).ready(function() {
    $('form').submit(function(event) {
        event.preventDefault();
        var email = $('input[name=email]').val();
        var login = $('input[name=login]').val();
        var password = $('input[name=password]').val();
        var username = $('input[name=username]').val();
        var emailValid = false;
        var loginValid = false;
        var passwordValid = false;
        var usernameValid = false;

        if (email === "") {
            $('input[name=email]').addClass('alert-danger border-danger');
            if ($('#emailId').length) {
                $('#emailId').text('Empty email!')
            } else {
                $('.email').append("<p id='emailId' style='color: red;'>Empty email!</p>");
            }
            emailValid = false;
        } else if (!/^[\w\.-]+@[_a-zA-Z]+\.[a-z]{2,3}$/.test(email)) {
            $('input[name=email]').addClass('alert-danger border-danger');
            if ($('#emailId').length) {
                $('#emailId').text('Incorrect email!')
            } else {
                $('.email').append("<p id='emailId' style='color: red;'>Incorrect email!</p>");
            }
            emailValid = false;
        } else {
            $('input[name=email]').removeClass('alert-danger border-danger');
            $('#emailId').remove();
            emailValid = true;
        }

        if (login === "") {
            $('input[name=login]').addClass('alert-danger border-danger');
            if ($('#loginId').length)  {
                $('#loginId').text('Empty login!');
            } else {
                $('.login').append("<p id='loginId' style='color: red;'>Empty login!</p>");
            }
            loginValid = false;
        } else if (login.length < 5) {
            $('input[name=login]').addClass('alert-danger border-danger');
            if ($('#loginId').length)  {
                $('#loginId').text('Login must be > 5!');
            } else {
                $('.login').append("<p id='loginId' style='color: red;'>Login must be > 5!</p>");
            }
            loginValid = false;
        } else {
            $('input[name=login]').removeClass('alert-danger border-danger');
            $('#loginId').remove();
            loginValid = true;
        }

        if (password === "") {
            $('input[name=password]').addClass('alert-danger border-danger');
            if ($('#passwordId').length) {
                $('#passwordId').text('Empty password');
            } else {
                $('.password').append("<p id='passwordId' style='color: red;'>Empty password!</p>");
            }
            passwordValid = false;
        } else if (password.length < 10) {
            $('input[name=password]').addClass('alert-danger border-danger');
            if ($('#passwordId').length) {
                $('#passwordId').text('Password must be > 10!');
            } else {
                $('.password').append("<p id='passwordId' style='color: red;'>Password must be > 10!</p>");
            }
            passwordValid = false;
        } else {
            $('input[name=password]').removeClass('alert-danger border-danger');
            $('#passwordId').remove();
            passwordValid = true;
        }

        if (username === "") {
            $('input[name=username]').addClass('alert-danger border-danger');
            if (!$('#usernameId').length) {
                $('.username').append("<p id='usernameId' style='color: red;'>Empty username!</p>");
            }
            usernameValid = false;
        }  else {
            $('input[name=username]').removeClass('alert-danger border-danger');
            $('#usernameId').remove();
            usernameValid = true;
        }

        if (emailValid && loginValid && passwordValid && usernameValid) {
            $("form").unbind('submit').submit();
        }
    })
});
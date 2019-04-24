$(document).ready(function () {
    $('form').submit(function (event) {
        event.preventDefault();
        var login = $('input[name=login]').val();
        var password = $('input[name=password]').val();
        var loginValid = false;
        var passwordValid = false;

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

        if (loginValid && passwordValid) {
            $("form").unbind('submit').submit();
        }
    })
});
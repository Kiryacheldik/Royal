$().ready(function () {
    $("#loginForm").validate({
        rules: {
            login: {
                required: true,
                minlength: 5
            },
            password: {
                required: true,
                minlength: 5
            },
            confirmPassword: {
                required: true,
                minlength: 5,
                equalTo: "#password"
            }
        },
        messages: {
            login: {
                required: "Please enter login",
                minlength: "Login must have al least 5 symbols"
            },
            password: {
                required: "Please enter password",
                minlength: "Password must have al least 5 symbols"
            },
            confirmPassword: {
                required: "Please enter confirm password",
                minlength: "Confirm Password must have al least 5 symbols",
                equalTo: "Please enter the same password as above"
            }
        },
        highlight: function (element, errorClass, validClass) {
            if ( element.type === "radio" ) {
                this.findByName( element.name ).addClass( errorClass ).removeClass( validClass );
                $(element).addClass('alert-danger');
                $(element).css('border', '1px solid red');
            } else {
                $(element).addClass( errorClass ).removeClass( validClass );
                $(element).addClass('alert-danger');
                $(element).css('border', '1px solid red');
            }
        },
        unhighlight: function (element, errorClass, validClass) {
            if ( element.type === "radio" ) {
                this.findByName( element.name ).removeClass( errorClass ).addClass( validClass );
                $(element).removeClass('alert-danger');
                $(element).css('border', '');
            } else {
                $( element ).removeClass( errorClass ).addClass( validClass );
                $(element).removeClass('alert-danger');
                $(element).css('border', '');
            }
        }
    });
});
$().ready(function () {
    $("#registrationForm").validate({
        rules: {
            email: {
                required: true,
                minlength: 5,
                email: true
            }, username: {
                required: true,
                minlength: 5
            }, login: {
                required: true,
                minlength: 5
            }, password: {
                required: true,
                minlength: 5
            }, confirmPassword: {
                required: true,
                minlength: 5,
                equalTo: "#password"
            }
        },
        messages: {
            email: {
                required: "Please enter email",
                minlength: "Email must be at least 5 symbols",
                email: "Please enter valid email"
            },
            username: {
                required: "Please enter username",
                minlength: "User name must have at least 5 symbols"
            },
            login: {
                required: "Please enter login",
                minlength: "Login must have at least 5 symbols"
            },
            password: {
                required: "Please enter password",
                minlength: "Password must have at least 5 symbols"
            },
            confirmPassword: {
                required: "Please enter confirm password",
                minlength: "Confirm Password must have at least 5 symbols",
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
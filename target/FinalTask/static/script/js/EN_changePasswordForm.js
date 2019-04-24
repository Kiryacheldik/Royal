$().ready(function () {
    $("#changePassword").validate({
        rules: {
            oldPassword: {
                required: true,
                minlength: 5
            },
            newPassword: {
                required: true,
                minlength: 5
            },
            confirmPassword: {
                required: true,
                minlength: 5,
                equalTo: "#newPassword"
            }
        },
        messages: {
            oldPassword: {
                required: "Please enter your current password",
                minlength: "Password must have at least 5 symbols"
            },
            newPassword: {
                required: "Please enter new password",
                minlength: "Password must have at least 5 symbols"
            },
            confirmPassword: {
                required: "Please enter new password again",
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
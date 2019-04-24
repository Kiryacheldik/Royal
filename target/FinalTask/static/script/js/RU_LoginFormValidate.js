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
                required: "Пожалуйста, введите логин",
                minlength: "Логин должен состоять минимум из 5 символов"
            },
            password: {
                required: "Пожалуйста, введите пароль",
                minlength: "Пароль должен состоять минимум из 5 символов"
            },
            confirmPassword: {
                required: "Пожалуйста, введите подтверждение пароля",
                minlength: "Пароль должен состоять минимум из 5 символов",
                equalTo: "Пожалуйста, правильно введите пароль еще раз"
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
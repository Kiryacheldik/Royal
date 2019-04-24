$().ready(function () {
    $("#createTattoo").validate({
        rules: {
            name: {
                required: true,
                minlength: 5
            },
            titleImage: {
                required: true,
                accept: "image/*"
            },
            ordinaryImage: {
                accept: "image/*"
            },
            sketchImage: {
                required: true,
                accept: "image/*"
            }
        },
        messages: {
            name: {
                required: "Пожалуйста, введите название татуировки",
                minlength: "Название татуировки должно содержать минимум 5 символов"
            },
            titleImage: {
                required: "Пожалуйста, выберете главное изображение",
                accept: "Файл должен иметь .jpg, .jpeg, .png, .JPG расширение"
            },
            ordinaryImage: {
                accept: "Файл должен иметь .jpg, .jpeg, .png, .JPG расширение"
            },
            sketchImage: {
                required: "Пожалуйста, выберете эскиз изображения",
                accept: "Файл должен иметь .jpg, .jpeg, .png, .JPG расширение"
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
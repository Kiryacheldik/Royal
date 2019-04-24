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
                required: "Please, enter tattoo name",
                minlength: "Tattoo name must have at least 5 symbols"
            },
            titleImage: {
                required: "Please choose title image",
                accept: "File must have .jpg, .jpeg, .png, .JPG extension"
            },
            ordinaryImage: {
                accept: "File must have .jpg, .jpeg, .png, .JPG extension"
            },
            sketchImage: {
                required: "Please choose sketch image",
                accept: "File must have .jpg, .jpeg, .png, .JPG extension"
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
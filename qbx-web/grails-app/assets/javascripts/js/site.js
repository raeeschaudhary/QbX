$(document).ready(function () {

    var birthdate_inp = function (input) {
        $('#ui-datepicker-div').addClass('birthdate_inp_datepicker');
    };

/*    $(".birthdate_inp").datepicker({
        changeMonth: true,
        changeYear: true,
        beforeShow: birthdate_inp
    });*/

    $(".birthdate_inp").datepicker({
        format: 'dd-mm-yyyy',
        autoclose: true,
        orientation: "auto right",
        startView: 2, weekStart: 1,
        startDate: '-90y', endDate: "-3y"
    });

    //buttonset
//    $("#radio").buttonset();

    var del_message = $('#del_message');
    $(function () {
        function position() {
            $(del_message).position({
                of: $(".table"),
                //my: $( "#my_horizontal" ).val() + " " + $( "#my_vertical" ).val(),
                //at: $( "#at_horizontal" ).val() + " " + $( "#at_vertical" ).val(),
                //collision: $( "#collision_horizontal" ).val() + " " + $( "#collision_vertical" ).val()
            });
        }

        position();
    });
});
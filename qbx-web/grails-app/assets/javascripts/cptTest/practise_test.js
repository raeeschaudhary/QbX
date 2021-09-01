/*
 Practise test check
 Qbtech AB, Sweden
 */


// ----------------------------- PRACTISE TEST ---------------------------------
function check_practise_result(DataObj) {
    // function calcs commission and omission error
    // the code is taken from QbTest

    var ommision = 0;
    var commision = 0;
    var stimuli = 0;
    var nonstimuli = 0;
    var b = 0;

    for (var i = -1; i < DataObj.stimuli_length(); i++) {

        var target = false;
        var endTime = 4000000;

        if (i < DataObj.stimuli_length() - 1)
            endTime = DataObj.get_stimuli_time(i + 1);

        if (i > -1) {
            target = (DataObj.get_stimuli(i) & 0x80) > 0;
        }

        var nclicks = 0;

        // check button clicks
        while (b < DataObj.button_length() && DataObj.get_button(b) < endTime) {

            if (!target && nclicks == 0 && i > -1) commision++;

            nclicks++;
            b++;
        }

        if (target && nclicks == 0) ommision++;

        if (i > -1) {
            if (target) stimuli++;
            else nonstimuli++;
        }

    } // for

    //console.log("Befoe comminsion " + commision)
    //console.log("Before non Stimulie " + nonstimuli)

    var ommis_error = (100 * ommision / stimuli);
    var commis_error = (100 * commision / nonstimuli);

    var val;

    if (ommis_error >= 50 || commis_error >= 50){
        val = false;
    }
    else{
        val = true;
    }

    var ret_obj = {return_val: val, omision: ommis_error, comision: commis_error};

    //console.log("before comminsion " + commis_error)
    //console.log("Before omission " + ommis_error)

    return ret_obj;

}

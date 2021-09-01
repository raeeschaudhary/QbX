// Stimuli generator
// QbTech AB, Sweden
// Author: Yury Dorofeev
// all rights reserved. Remember, to copy-paste this code leads direct to the jail!

var StimuliGenerator = function () {
    var Stimulus = [];
    var stimuli_max_numb;
    var testType;
    var target_percent;
    var stim_index;

    // --------------- PUBLIC ------------------
    this.init = function (test_type, practice) {
        testType = test_type;
        var ret_val;
        stim_index = 0;
        Stimulus = [];

        if (practice)
            stimuli_max_numb = 20;

        if (testType == 1) {
            target_percent = 0.5;
            if (!practice)
                stimuli_max_numb = 450;

            ret_val = TestChild();
        }
        else {
            target_percent = 0.25;
            if (!practice)
                stimuli_max_numb = 600;

            ret_val = TestAdult();
        }

        return ret_val;
    };

    this.get_stimuli = function () {
        var ret_val;

        if (Stimulus instanceof Array) {
            ret_val = Stimulus[stim_index];

            stim_index++;
            if (stim_index == stimuli_max_numb)
                Stimulus = null;
        }

        return ret_val;
    };

    // --------------- PRIVATE ------------------
    var TestAdult = function () {

        var practice_stimuli = Math.floor(stimuli_max_numb * target_percent);
        var practice_target = Math.floor(practice_stimuli * target_percent);
        var targets = practice_stimuli;

        // the first quartile
        var forbidden_first_stimuli = 7;
        var first_arr = qbTestAdultHelper(practice_stimuli, practice_target,
            forbidden_first_stimuli);

        // other 3 quartile
        var second_arr = qbTestAdultHelper(stimuli_max_numb - practice_stimuli,
            targets - practice_target, first_arr[first_arr.length - 1]);


        // concatenate two arrays
        if (Array.isArray(second_arr) && Array.isArray(first_arr)) {

            Stimulus = first_arr.concat(second_arr);

            if (Stimulus.length < stimuli_max_numb) {
                alert("Error: the number of stimuli is less than :" + stimuli_max_numb +
                    " array_1=" + first_arr.length + ", array_2=" + second_arr.length);
                return false;
            }
            return true;
        }
        else {
            alert("FALSE!");
            return false;
        }

    };

    var TestChild = function () {

        var targets = Math.floor(stimuli_max_numb * target_percent);
        Stimulus = qbTestHelper(stimuli_max_numb - 2, targets);
        Stimulus.push(128);
        Stimulus.push(0);

        if (Stimulus.length < stimuli_max_numb) {
            alert("Error: the number of stimuli is less than : " + Stimulus.length);
            return false;
        }
        return true;
    };

    var qbTestHelper = function (stimuli_number, target_number) {

        var stimuli = [];

        // init stimuli list
        for (var i = 0; i < stimuli_number; i++)
            stimuli.push(0);

        for (var i = 0; i < target_number;) {

            var x = Math.floor(Math.random() * stimuli_number);
            if (stimuli[x] == 0) {
                stimuli[x] = 128;
                i++;
            }
        }

        return stimuli;
    };

    var qbTestAdultHelper = function (stimuli_number, target_number, forbidden_first_stimuli) {

        var bum = target_number / stimuli_number;

        while (true) {
            var stimuli = [];
            var first = -1;
            // 0x7F - 127 in hex
            while ((first = Math.floor(Math.random() * 4)) == (forbidden_first_stimuli & 0x7F));

            stimuli.push(first);

            for (var i = 1; i < stimuli_number; i++) {
                if (Math.random() < bum)
                    stimuli.push(stimuli[stimuli.length - 1]);
                else {
                    var x = 0;
                    while ((x = Math.floor(Math.random() * 4)) == stimuli[stimuli.length - 1]);

                    stimuli.push(x);
                }
            }

            var targets = 0;
            var prev = -1;

            for (var i = 0; i < stimuli.length; i++) {

                if (stimuli[i] == prev) {
                    targets++;
                    // 0x80 - 128 in hex
                    stimuli[i] = (stimuli[i] | 0x80);
                }

                prev = stimuli[i];
            }

            if (targets == target_number) break;

        } // while(true)

        return stimuli;
    }
};
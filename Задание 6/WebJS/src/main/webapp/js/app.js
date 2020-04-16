window.notify = function(message) {
    $.notify(message, {position: "bottom right"})
};

ajax = function(input, $error) {
    var settings = {
        type: "POST",
        url: "",
        dataType: "json",
        success: function (response) {
            if (response["error"]) {
                $error.text(response["error"]);
            } else {
                location.href = response["redirect"];
            }
        }
    };

    for (var key in input) {
        settings[key] = input[key];
    }

    $.ajax(settings)
};
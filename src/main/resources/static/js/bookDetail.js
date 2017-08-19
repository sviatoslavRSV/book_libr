/**
 * Created by RSV on 18.08.2017.
 */


$(document).ready(function (data) {
    $.ajax({
        // url: "/",
        type: "get", //send it through get method
        data: {
            BookID: 1,
        },
        success: function (response,data, type, row, meta) {
            console.log(response);
            console.log(data);
            console.log(type);
            console.log(row);
        },
        error: function (xhr) {
        }
    });
});


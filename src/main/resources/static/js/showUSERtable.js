/**
 * Created by RSV on 15.08.2017.
 */

var editor; // use a global for the submit and return data rendering in the examples

$(document).ready(function () {
    $('#messageTable').DataTable({
        dom: "Bfrtip",
        // ajax: "/fake-data/data.json",
        ajax: "/user/books",
        columns: [
            {data: "id"},
            {data: "image"},
            {data: "title"},
            {data: "author"},
            {data: "publishOffice"},
            {data: "description"}
        ],
        select: false,
        buttons: [
            // {extend: "create", editor: editor},
            // {extend: "edit", editor: editor},
            // {extend: "remove", editor: editor}
        ]
    });
});
/*$(document).ready(function () {
 // $('#mybtn').click(function () {
 var table = $('#messageTable').DataTable({
 "sAjaxSource": "/user/getAll",
 "sAjaxDataProp": "",
 "order": [[0, "asc"]],
 "aoColumns": [
 {"mData": "id"},
 {"mData": "title"},
 {"mData": "author"},
 {"mData": "publishOffice"},
 {"mData": "description"}
 ]
 })
 // });
 });*/


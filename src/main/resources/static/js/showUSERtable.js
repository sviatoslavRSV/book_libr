/**
 * Created by RSV on 08.08.2017.
 */

var editor; // use a global for the submit and return data rendering in the examples

$(document).ready(function () {
    editor = new $.fn.dataTable.Editor({
        fields: [
            {
                label: "Image:",
                name: "image",
            }, {
                label: "Title:",
                name: "title"
            }, {
                label: "Author:",
                name: "author"
            }, {
                label: "Publishing office:",
                name: "publishOffice"
            }, {
                label: "Short description:",
                name: "description"
            }, {
                label: "Book:",
                name: "book",
            }]
    });

    $('#messageTable').DataTable({
        dom: "Bfrtip",
        ajax: "/user/books",
        columns: [
            {data: "id"},
            {
                data: "image",
                render: function (file_id) {
                    return file_id ? '<img src="' + editor.file('files', file_id).web_path + '"/>' : null;
                },
                defaultContent: "No image",
                title: "Image"
            },
            {
                data: "title",
                render: function (data, type, row, meta) {
                    return '<a href="/user/book/' + row.id + '">' + data + '</a>';
                }
            },
            {data: "author"},
            {data: "publishOffice"},
            {data: "description"},
            {
                data: "book",
                render: function (file_id) {
                    return file_id ?
                        '<a  href="' + editor.file('files', file_id).web_path + '">'
                        + editor.file('files', file_id).filename + '</a>' :
                        null;
                },
                defaultContent: "No file",
                title: "Book"
            }
        ],
        select: false,
        buttons: []
    });
});


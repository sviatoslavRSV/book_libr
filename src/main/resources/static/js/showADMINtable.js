/**
 * Created by RSV on 08.08.2017.
 */

var editor; // use a global for the submit and return data rendering in the examples

$(document).ready(function () {
    editor = new $.fn.dataTable.Editor({
        ajax: {
            create: {
                type: 'post',
                contentType: 'application/json',
                dataType: 'json',
                data: function (d) {
                    return JSON.stringify(d.data[0]);
                },
                url: '/admin/books/add'
            },
            edit: {
                type: 'post',
                contentType: 'application/json',
                dataType: 'json',
                data: function (d) {
                    var returnedObject;
                    for (var prop in d.data) {
                        returnedObject = d.data[prop];
                        returnedObject.id = prop;
                    }
                    return JSON.stringify(returnedObject);
                },
                url: '/admin/books/add'
            },
            remove: {
                type: 'post',
                contentType: 'application/json',
                dataType: 'json',
                data: function (d) {
                    var returnedObject;
                    for (var prop in d.data) {
                        returnedObject = d.data[prop];
                        returnedObject.id = prop;
                    }
                    return JSON.stringify(returnedObject);
                },
                url: '/admin/books/delete'
            },
        },
        table: "#messageTable",
        idSrc: 'id',
        fields: [
            {
                label: "Image:",
                name: "image",
                type: "upload",
                ajax: '/admin/books/upload_image',
                display: function (file_id) {
                    return '<img src="' + editor.file('files', file_id).web_path + '"/>';
                },
                clearText: "Clear",
                noImageText: 'No image'
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
                type: "upload",
                ajax: '/admin/books/upload_book',
                display: function (file_id) {
                    return '<a  href="' + editor.file('files', file_id).web_path + '">'
                        + editor.file('files', file_id).filename + '</a>';
                },
                clearText: "Clear",
                noImageText: 'No file'
            }]
    });

    $('#messageTable').DataTable({
        dom: "Bfrtip",
        ajax: "/user/books",
        // deferRender: true,
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
        select: true,
        buttons: [
            {extend: "create", editor: editor},
            {extend: "edit", editor: editor},
            {extend: "remove", editor: editor}
        ]
    });
});


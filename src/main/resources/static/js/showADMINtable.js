/**
 * Created by RSV on 08.08.2017.
 */

var editor; // use a global for the submit and return data rendering in the examples

$(document).ready(function () {
    editor = new $.fn.dataTable.Editor({
        // ajax: "/user/books",
        // ajax: "/fake-data/data.json",
        ajax: {
            create: {
                type: 'post',
                contentType: 'application/json',
                dataType: 'json',
                data: function (d) {
                    // var imageId = d.data[0].image;
                    // var imageObj = editor.file('files', imageId);
                    // d.data[0].image = imageObj.filename;
                    console.log(d.data[0]);
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
                        console.log(prop, d.data[prop]);
                        returnedObject = d.data[prop];
                        returnedObject.id = prop;
                    }
                    return JSON.stringify(returnedObject);
                },
                // url: '/user/books/update'
                url: '/admin/books/add'
            },
            remove: {
                type: 'post',
                contentType: 'application/json',
                dataType: 'json',
                data: function (d) {
                    var returnedObject;
                    for (var prop in d.data) {
                        console.log(prop, d.data[prop]);
                        returnedObject = d.data[prop];
                        returnedObject.id = prop;
                    }
                    return JSON.stringify(returnedObject);
                },
                url: '/admin/books/delete'
            },
            upload_image: {
                type: 'post',
                url: '/admin/books/upload_image'
            },
            upload_book: {
                type: 'post',
                url: '/admin/books/upload_book'
            }
        },
        table: "#messageTable",
        idSrc: 'id',
        fields: [
            {
                label: "Image:",
                name: "image",
                type: "upload_image",
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
            },{
                label: "Book:",
                name: "book",
                type: "upload_book",
                display: function (file_id) {
                    return '<img src="' + editor.file('files', file_id).web_path + '"/>';
                },
                clearText: "Clear",
                noImageText: 'No file'
            }]
    });

    $('#messageTable').DataTable({
        dom: "Bfrtip",
        // ajax: "/fake-data/data.json",
        ajax: "/user/books",
        columns: [
            {data: "id"},
            {
                data: "image",
                // render: function (file_object) {
                //     console.log(file_object);
                //     return file_object ?
                //         '<img src="' + editor.file('files', file_object).web_path + '"/>' :
                //         null;
                // },
                render: function (file_id) {
                    return file_id ?
                        '<img src="'+editor.file( 'files', file_id ).web_path+'"/>' :
                        // '<a  href="' + editor.file('files', file_id).web_path + '">'
                        // + editor.file('files', file_id).filename + '</a>' :
                        null;
                },
                defaultContent: "No image",
                title: "Image"
            },
            {data: "title"},
            {data: "author"},
            {data: "publishOffice"},
            {data: "description"},
            {
                data: "book",
                // render: function (file_object) {
                //     console.log(file_object);
                //     return file_object ?
                //         '<img src="' + editor.file('files', file_object).web_path + '"/>' :
                //         null;
                // },
                render: function (file_id) {
                    return file_id ?
                        '<img src="'+editor.file( 'files', file_id ).web_path+'"/>' :
                        // '<a  href="' + editor.file('files', file_id).web_path + '">'
                        // + editor.file('files', file_id).filename + '</a>' :
                        null;
                },
                defaultContent: "No file",
                title: "File"
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


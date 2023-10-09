let pathURI = window.location.pathname
const regex = /\/board\/(\d+)/;
const match = pathURI.match(regex);
const number = match[1];
let boardId = number;

$(document).ready(function () {
    if (pathURI.includes("update")) {
        doUpdateBoard();
    } else {
        doGetBoardView();
    }

    $("#update-btn").click(function () {
        putUpdateBoard();
    })

    $("#boardDelete-btn").click(function () {
        deleteBoard();
    });
})

function doGetBoardView() {
    let getURL = "/fureverhomes/board/{board_id}/view".replace("{board_id}", boardId);

    $.ajax({
        url: getURL,
        type: "GET",
        success: function (data) {

            let title = data.title;
            let uploadDate = formatLocalDateTime(data.uploadDate);
            let views = data.views;
            let writer = data.writer;
            let isLogin = data.equalLogin;
            let content = data.content;
            if (data.files != null) {
                let files_list = data.files;
                let dataBody = $("#image");
                dataBody.empty();
                let links = [];
                for (let i = 0; i < files_list.length; i++) {

                    let files = files_list[i];
                    let link = files.filePath;
                    let isDelete = files.isDelete;
                    if (isDelete !== true) {
                        link = link + "/" + files.saveFileName;

                        links.push(link);
                    }
                }

                for (let i = 0; i < links.length; i++) {

                    let img = $("<img>", {
                    src: links[i],
                    alt: "board-img",
                    class: "rounded mb-1",
                    style: "width: 90%; align-content: center;"
                    });

                    dataBody.append(img);
                }
            }

            console.log(isLogin)

            if (!isLogin) {
                $("#board_menu").hide();
            }

            $("#title").text(title);
            $("#date").text(uploadDate);
            $("#views").text(views);
            $("#writer").text(writer);
            $("#content").text(content);

        },error: function () {
            alert("페이지를 불러오는데 실패했습니다.")
        }
    });
}

function formatLocalDateTime(date) {
    let yymmdd = date.substring(0, 10)
    let hour = date.substring(11, 13);
    let minute = date.substring(14, 16);

    return yymmdd + ' ' + hour + ':' + minute;
}

function formatPathDate(date) {
    return date.substring(0, 10).replace(/-/g, '');
}

function putUpdateBoard() {
    let updateLink = "/fureverhomes/board/{board_id}/update".replace("{board_id}", boardId);
    location.href = updateLink;
}
function deleteBoard() {
    let deleteLink = "/fureverhomes/board/{board_id}/delete".replace("{board_id}", boardId);

    $.ajax({
        type: "DELETE",
        url: deleteLink,
        success: function () {
            location.href = "/fureverhomes/board";
        },
        error: function () {
            alert("게시글 삭제 실패!")
        }
    });
}

function doUpdateBoard() {
    let getURL = "/fureverhomes/board/{board_id}/view".replace("{board_id}", boardId);

    $.ajax({
        url: getURL,
        type: "GET",
        success: function (data) {

            let title = data.title;
            let content = data.content;

            $("#title").val(title);
            $("#content").text(content);

            // 튜터 인증 파일 이름 바꾸기
            $(document).on("change", "#customFile", function () {
                let fileValue = $(this).val().split("\\");
                let fileName = fileValue[fileValue.length - 1]; // 파일명

                $(this).siblings(".custom-file-label").text(fileName);
            });

            $(document).on("click", "#update-btn", function () {
                let updatetitle = $("#title").val();
                let updatecontent = $("#content").val();
                if (updatetitle === "" || updatetitle === null) {
                    updatetitle = title;
                }
                if (updatecontent === "" || updatecontent === null) {
                    updatecontent = content;
                }

                let putURL = "/fureverhomes/board.update";

                const formData = new FormData($("#board_form")[0]);
                $(".file-upload-row").each(function (index, element) {
                    let filesInput = $(element).find(".custom-file-input2")[0];
                    let files = filesInput.files;

                    $.each(files, function (index, file) {
                        formData.append("customFile", file);
                    });
                });

                formData.append("board_id", boardId);
                formData.append("title", updatetitle);
                formData.append("content", updatecontent);

                $.ajax({
                    type: "POST",
                    url: putURL,
                    data: formData,
                    processData: false, // processData를 false로 설정하여 jQuery가 데이터를 처리하지 않도록 함
                    contentType: false,
                    async: false,
                    success: function (data, status) {
                        location.href="/fureverhomes/board/" + data;
                    },
                    error: function () {
                        alert("등록 실패!")
                    }
                });
            })

            $(document).on("click", "#add-img-btn", function () {
                addFileUpload();
            });

            $(document).on("click", "#del-img-btn", function () {
                $(this).closest(".file-upload-row").remove();
            })

        },error: function () {
            alert("페이지를 불러오는데 실패했습니다.")
        }
    });
}


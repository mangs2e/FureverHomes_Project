let pathURI = window.location.pathname
const regex = /\/(\d+)$/;
const match = pathURI.match(regex);
const number = match[1];
let boardId = number;

$(document).ready(function () {
    doGetBoardView();

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
            let pathDate = formatPathDate(data.uploadDate);
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
                    let link = "/board_images/";
                    link = link + pathDate + "/" + files.saveFileName;
                    console.log(link);

                    links.push(link);
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


let pathURI = window.location.pathname
const regex = /\/(\d+)$/;
const match = pathURI.match(regex);
const number = match[1];
let boardId = number;

$(document).ready(function () {
    doGetBoardView();
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
            let content = data.content;

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


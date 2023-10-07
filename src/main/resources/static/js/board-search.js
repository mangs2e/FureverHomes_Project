$(document).ready(function (){
    boardSearchGet(1,null,null);

    $("#search-form").submit(function (e){
        e.preventDefault();

        let searchType = $("#search-type").val();
        let searchKeyword = $("#search-keyword").val();

        boardSearchGet(1, searchType, searchKeyword);
    });
});

function boardSearchGet(page, searchType, searchKeyword){
    let apiUrl = "/fureverhomes/board.list?page={page}&size={size}"
    apiUrl = apiUrl.replace("{page}",page);
    apiUrl = apiUrl.replace("{size}",5);

    if(searchType !== null) {
        apiUrl += "&searchType=" + searchType;
    }
    if(searchKeyword !== null && searchKeyword !== "") {
        apiUrl += "&searchKeyword=" + searchKeyword;
    }

    console.log(apiUrl);

    $.ajax({
        url: apiUrl,
        type: "GET",
        async: true,
        success: function (data) {
            let board_list = data.boardList;
            let total = data.totalCount;

            let dataBody = $("#board-list");
            dataBody.empty();

            for (let i = 0; i < board_list.length; i++) {
                let board = board_list[i];

                let link = "/fureverhomes/board/";
                let boardId = board.board_id;

                link = link + boardId;

                let tr = $("<tr>", {style: "padding: 0;"});
                let th = $("<th>", {
                    style: "text-align:center; vertical-align:middle;",
                    scope: "row",
                    text: (page - 1) * 5 + i + 1
                });
                let ta = $("<td>", {
                    style: "text-align:center; vertical-align:middle; padding: 1;",
                });
                let atitle = $("<a>", {
                    href: link,
                    class: "text-black",
                    text: board.title,
                    id: "board-view"
                });
                let twriter = $("<td>", {
                    style: "text-align:center; vertical-align:middle; padding: 1;",
                    text: board.writer
                });
                let tdate = $("<td>", {
                    style: "text-align:center; vertical-align:middle; padding: 1;",
                    text: formatLocalDateTime(board.uploadDate)
                })
                let tviews = $("<td>", {
                    style: "text-align:center; vertical-align:middle; padding: 1;",
                    text: board.views
                })

                dataBody.append(tr);
                tr.append(th, ta, twriter, tdate, tviews);
                ta.append(atitle);
            }
            pagination(page,total);
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

// 페이지 링크에 대한 클릭 이벤트 리스너 추가
$(document).on("click", ".pagination .page-link", function (event) {
    event.preventDefault();
    let pageNumber = parseInt($(this).data("page"));
    if (!isNaN(pageNumber)) {

        let searchType = $("#search-type").val();
        let searchKeyword = $("#search-keyword").val();

        boardSearchGet(pageNumber, searchType, searchKeyword);
    }
});

function pagination(currentPage, total) { //(페이지,데이터갯수)
    let totalPages = Math.ceil(total / 5);
    let pageGroupSize = 5;
    let startPage = Math.ceil(currentPage / pageGroupSize) * pageGroupSize - pageGroupSize + 1;
    let endPage = Math.min(startPage + pageGroupSize - 1, totalPages);

    if(total === 0) {
        alert("검색 결과가 없습니다.")
        location.reload();
    }
    let pagination = $(".pagination");
    pagination.empty();

    // 이전 페이지 링크 추가
    let prevDisabled = startPage === 1 ? "disabled" : "";
    if(total > 0) {
        pagination.append(`<li class="page-item ${prevDisabled}">
                      <a class="page-link" href="#" aria-label="Previous" data-page="${startPage - 1}">Previous</a>
                    </li>`);
    }

    // 페이지 번호 링크 추가
    for (let i = startPage; i <= endPage; i++) {
        let activeClass = i === currentPage ? "active" : "";
        let listItem = `<li class="page-item ${activeClass}">
                <a class="page-link" href="#" data-page="${i}">${i}</a>
              </li>`;
        pagination.append(listItem);
    }

    // 다음 페이지 링크 추가
    let nextDisabled = endPage === totalPages ? "disabled" : "";
    if(total > 0) {
        pagination.append(`<li class="page-item ${nextDisabled}">
                      <a class="page-link" href="#" aria-label="Next" data-page="${endPage + 1}">Next</a>
                    </li>`);
    }
}
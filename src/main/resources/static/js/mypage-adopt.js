$(document).ready(function () {
    adoptHistoryGet();
});

function adoptHistoryGet() {
    let pathURL = "/fureverhomes/mypage/adopt.list"

    $.ajax({
        url: pathURL,
        type: "GET",
        async: false,
        success: function (data) {

            let dataBody = $("#adopt-history");
            dataBody.empty();

            for(let i = 0; i < data.length; i++) {
                let date = data[i].applicationDate;
                let name = data[i].name;
                let adoptStatus = data[i].adoptStatus;

                console.log(`Date: ${date}, Name: ${name}, Adopt Status: ${adoptStatus}`)

                if (adoptStatus === "PROCEEDING") {
                    adoptStatus = "진행중";
                } else if (adoptStatus === "COMPLETE") {
                    adoptStatus = "완료";
                } else adoptStatus = "취소";

                let tr = $("<tr>", {style: "padding: 0;"});

                let th = $("<th>", {
                    style: "text-align:center; vertical-align:middle;",
                    scope: "row",
                    text: i + 1,
                });

                let tdate = $("<td>", {
                    style: "text-align:center; vertical-align:middle; padding: 1;",
                    text: date
                })

                let tname = $("<td>", {
                    style: "text-align:center; vertical-align:middle; padding: 1;",
                    text: name
                })

                let tstatus = $("<td>", {
                    style: "text-align:center; vertical-align:middle; padding: 1;",
                    text: adoptStatus
                })

                let tbutton = $("<td>", {
                    style: "text-align:center; vertical-align:middle; padding: 1;"
                })

                let button = $("<button>", {
                    type: "button",
                    class: "btn btn-warning",
                    text: "신청취소",
                    style: adoptStatus === "취소" ? "display: none;" : "",
                    "data-toggle": "modal",
                    "data-target": "#modal-default-3"
                });

                dataBody.append(tr);
                tr.append(th);
                tr.append(tdate);
                tr.append(tname);
                tr.append(tstatus);
                tr.append(tbutton);
                tbutton.append(button);

                $("#adopt-cancel").on("click", function (e) {
                    e.preventDefault();
                    console.log(data[i].id)
                    adoptCancel(data[i].id);
                })
            }
            function adoptCancel(adopt_id) {
                let data = {
                    reason : $("#cancel-reason").val(),
                    adoptId : adopt_id
                }
                console.log(data)
                let putURL = "/fureverhomes/mypage/adopt.cancel";
                $.ajax({
                    type: "PUT",
                    url: putURL,
                    contentType: "application/json",
                    data: JSON.stringify(data),
                    success: function () {
                        console.log("성공")
                        $("#modal-default-3").modal('hide'); // 모달 창 닫기
                        // 실행창 초기화
                        location.reload();
                    },
                    error: function (data) {
                        alert("Error!")
                    },
                    complete: function (data, textStatus) {
                    },
                });
            }
        },error: function () {
            alert("페이지를 불러오는데 실패했습니다.")
        }
    });
}

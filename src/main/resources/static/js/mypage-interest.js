$(document).ready(function () {
    adoptInterestGet();
});

function adoptInterestGet() {
    let pathURL = "/fureverhomes/mypage/interest.list"

    $.ajax({
        url: pathURL,
        type: "GET",
        async: false,
        success: function (data) {

            let dataBody = $("#interest-list");
            dataBody.empty();

            for(let i = 0; i < data.length; i++) {
                let name = data[i].name;
                let animalId = data[i].animalId;

                let tr = $("<tr>", {style: "padding: 0;"});

                let th = $("<th>", {
                    style: "text-align:center; vertical-align:middle;",
                    scope: "row",
                    text: i + 1,
                });

                let tname = $("<td>", {
                    style: "text-align:center; vertical-align:middle; padding: 1;",
                    text: name
                })

                let ta = $("<td>", {
                    style: "text-align:center; vertical-align:middle; padding: 1;"
                })

                let a = $("<a>", {
                    href: "/fureverhomes/detail/" + animalId,
                    style: "color: gray;",
                    text: "정보 확인하고 입양하러 가기"
                })

                let tbutton = $("<td>", {
                    style: "text-align:center; vertical-align:middle; padding: 1;"
                })

                let button = $("<button>", {
                    type: "button",
                    class: "btn btn-warning",
                    text: "관심취소",
                    "data-toggle": "modal",
                    "data-target": "#modal-default-3"
                });

                dataBody.append(tr);
                tr.append(th);
                tr.append(tname);
                tr.append(ta);
                ta.append(a);
                tr.append(tbutton);
                tbutton.append(button);

                $("#interest-cancel").on("click", function (e) {
                    e.preventDefault();
                    interestCancel(data[i].interestId);
                })
            }

            function interestCancel(id) {
                let deleteURL = "/fureverhomes/mypage/interest.delete";

                $.ajax({
                    type: "DELETE",
                    url: deleteURL,
                    contentType: "application/json",
                    data: JSON.stringify({
                        interestId : id
                    }),
                    success: function () {
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

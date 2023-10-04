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
                let id = data[i].id;

                console.log(`Date: ${date}, Name: ${name}, Adopt Status: ${adoptStatus}`)

                if (adoptStatus === "PROCEEDING") {
                    adoptStatus = "진행중";
                } else if (adoptStatus === "COMPLETE") {
                    adoptStatus = "완료";
                } else adoptStatus = "취소";

                let tr = $("<tr>").css("padding", "0");
                // let tr = $("<tr>", {style: "padding: 0;"});

                // let th = $("<th>", {
                //     style: "text-align:center; vertical-align:middle;",
                //     scope: "row",
                //     text: i + 1,
                // });
                let th = $("<th scope=\"row\">")
                    .css("text-align", "center")
                    .css("vertical-align", "middle")
                    .text(i + 1);

                // let tdate = $("<td>", {
                //     style: "text-align:center; vertical-align:middle; padding: 1;",
                //     text: date
                // })
                let tdate = $("<td>")
                    .css("text-align", "center")
                    .css("vertical-align", "middle")
                    .css("padding", "1")
                    .text(date);

                // let tname = $("<td>", {
                //     style: "text-align:center; vertical-align:middle; padding: 1;",
                //     text: name
                // })
                let tname = $("<td>")
                    .css("text-align", "center")
                    .css("vertical-align", "middle")
                    .css("padding", "1")
                    .text(name);

                // let tstatus = $("<td>", {
                //     style: "text-align:center; vertical-align:middle; padding: 1;",
                //     text: adoptStatus
                // })
                let tstatus = $("<td>")
                    .css("text-align", "center")
                    .css("vertical-align", "middle")
                    .css("padding", "1")
                    .text(adoptStatus);

                // let tbutton = $("<td>", {
                //     style: "text-align:center; vertical-align:middle; padding: 1;"
                // })
                let tbutton = $("<td>")
                    .css("text-align", "center")
                    .css("padding", "1")
                    .css("vertical-align", "middle");

                // let button = $("<button>", {
                //     type: "button",
                //     class: "btn btn-warning",
                //     text: "신청취소",
                //     style: adoptStatus === "진행중" ? "" : "display: none;",
                //     "data-toggle": "modal",
                //     "data-target": "#modal-default-3",
                //     id: id
                // });
                let button = $("<button>")
                    .addClass("btn btn-warning cancelAdopt")
                    .text("신청취소")
                    .css("display", adoptStatus === "진행중" ? "" : "none")
                    .attr("data-toggle", "modal")
                    .attr("data-target", "#modal-default-3")
                    .on("click", function (event) {
                        event.preventDefault();

                        // 모달을 띄우기 위한 속성 설정
                        $("#modal-default-3").modal({
                            show: true
                        });

                        $("#adopt-cancel").on("click", function (e) {
                            e.preventDefault();
                            console.log(id)
                            adoptCancel(id);
                        });

                        function adoptCancel(adopt_id) {
                            let data = {
                                reason: $("#cancel-reason").val(),
                                adoptId: adopt_id
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


                        // 즉시 실행되는 함수
                        // (function (index) {
                        //     button.on("click", function (e) {
                        //         e.preventDefault();
                        //         $("#modal-default-3").on("show.bs.modal", function () {
                        //             // 모달이 열릴 때 실행되어야 하는 코드
                        //             console.log(data[index].id);
                        //             adoptCancel(data[index].id);
                        //         });
                        //     });
                        // })(i);

                    });
                tr.append(th, tdate, tname, tstatus, tbutton);
                // tr.append(tdate);
                // tr.append(tname);
                // tr.append(tstatus);
                // tr.append(tbutton);
                tbutton.append(button);
                dataBody.append(tr);
            }
        },error: function () {
            alert("페이지를 불러오는데 실패했습니다.")
        }
    });
}

$(document).ready(function () {
    $("#create-btn").click(function () {
        let postURL = "/fureverhomes/board.create"
        let data = {
            title: $("#title").val(),
            content: $("#content").val()
        }

        $.ajax({
            type: "POST",
            url: postURL,
            contentType: 'application/json',
            data: JSON.stringify(data),
            async: false,
            success: function (data, status) {
                location.href="/fureverhomes/board/view"
            },
            error: function (data, textStatus) {
                alert("등록 실패!")
            }
        });
    })
});

//이미지 업로드
function imageName() {
    $("#file").on("change", function(){
        let fileName = $("#file").val().split("\\");
        //확장자 추출
        let fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);
        fileExt = fileExt.toLowerCase();

        if(fileExt != "jpg" && fileExt != "jpeg" && fileExt != "png") {
            alert("이미지 파일만 등록 가능합니다.")
        }
    })
}
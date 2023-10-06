$(document).ready(function () {
    $(document).on("click", "#add-img-btn", function () {
        addFileUpload();
    })

    $("#create-btn").click(function () {

        let title = $("#title").val();
        let content = $("#content").val();

        if (title === "" || title === null || content === "" || content === null) {
            alert("내용을 적어주세요.")
            return false;
        }

        let postURL = "/fureverhomes/board.create";

        const formData = new FormData($("#board_form")[0]);
        // formData.append("customFile", $("#customFile")[0].files[0]);
        $(".file-upload-row").each(function (index, element) {
            let filesInput = $(element).find(".custom-file-input2")[0];
            let files = filesInput.files;

            $.each(files, function (index, file) {
                formData.append("customFile", file);
            });
        });
        formData.append("title", title);
        formData.append("content", content);

        $.ajax({
            type: "POST",
            url: postURL,
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

    // 튜터 인증 파일 이름 바꾸기
    $(document).on("change", "#customFile", function () {
        let fileValue = $(this).val().split("\\");
        let fileName = fileValue[fileValue.length - 1]; // 파일명

        $(this).siblings(".custom-file-label").text(fileName);
    });
});

function addFileUpload() {
    let newFileUploadRow = $("#fileForm .file-upload-row:first").clone();
    newFileUploadRow.find(".custom-file-input2").val("");
    newFileUploadRow.find("#show-files").text("Choose file");
    newFileUploadRow.appendTo("#fileForm");
}
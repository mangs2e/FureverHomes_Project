$(document).ready(function () {

    $("#sendResetPasswordLink").click(function (key, value) {

        let email = $("#email").val();

        $.ajax({
            type: "POST",
            url: "/fureverhomes/signin/sendLink.post",
            contentType: 'application/json',
            data: JSON.stringify({
                email: email
            }),
            success: function (data) {
                alert("이메일이 전송되었습니다. 메일을 확인해주세요.");
                location.href = "/fureverhomes/signin";
            },
            error: function (data, status, xhr) {
                if(xhr.status === 401) {
                    alert("존재하지 않는 회원정보입니다. 회원가입을 시도해주세요.");
                    location.href = "/fureverhomes/signup";
                } else {
                    alert("이메일 전송에 실패했습니다. 이메일을 다시 확인해주세요.");
                    location.href = "/fureverhomes/main";
                }
            },
        });
    });
});
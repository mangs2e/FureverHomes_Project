$(document).ready(function () {
    history.pushState(null, null, "/fureverhomes/change");

    let regPwd = RegExp(/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,12}$/);
    let passwordCheck = true;
    let passwordConfirmCheck = true;

// 비밀번호 유효성 검사
    $("#newPassword").change(function () {
        let password = $("#newPassword").val();
        if (!regPwd.test(password)) {
            $(".newPassword")
                .text("비밀번호는 영문, 숫자포함 6-12자여야합니다.")
                .css("color", "red");
            $("#newPassword").attr("class", "form-control is-invalid");
            passwordCheck = false;
        } else {
            $(".newPassword").text("");
            $("#newPassword").focus();
            $("#newPassword").attr("class", "form-control is-valid");
            passwordCheck = true;
        }
    });

// 비밀번호 일치 여부 검사
    $("#new-password-confirm").change(function () {
        let password = $("#newPassword").val();
        let confirmedPassword = $("#new-password-confirm").val();

        if (password !== confirmedPassword) {
            $(".new-password-confirm")
                .text("비밀번호가 일치하지 않습니다.")
                .css("color", "red");
            $("#new-password-confirm").attr("class", "form-control is-invalid");
            passwordConfirmCheck = false;
        } else {
            $(".new-password-confirm").text("");
            $("#new-password-confirm").focus();
            $("#new-password-confirm").attr("class", "form-control is-valid");
            passwordConfirmCheck = true;
        }
    });

    $("#change-password").click(function () {

        sessionStorage.removeItem("email");

        if (!passwordCheck) {
            $("#newPassword").focus();
            return false;
        } else if (!passwordConfirmCheck) {
            $("#new-password-confirm").focus();
            return false;
        }

        let password = $("#newPassword").val();

        $.ajax({
            type: "POST",
            url: "/fureverhomes/signin/change.post",
            contentType: 'application/json',
            data: JSON.stringify({
                password: password
            }),
            async: false,
            success: function () {
                alert("비밀번호가 변경됐습니다. 현재 페이지를 닫고 다시 로그인해주세요.");
            },
            error: function () {
                alert("유효하지 않은 접근입니다.");
                location.href = "/fureverhomes/main";
            }
        });
    });
});
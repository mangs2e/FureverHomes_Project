$(document).ready(function() {


    $("#signin").click(function () {

        let email = $("#email").val();
        let password = $("#password").val();

        console.log(email);
        console.log(password);

        if (!email) {
            alert("이메일을 입력해주세요.");
            email.focus();
        } else if (!password) {
            alert("비밀번호를 입력해주세요.");
            password.focus();
        }

        $.ajax({
            type: "POST",
            url: "/fureverhomes/signin.post",
            contentType: 'application/json',
            data: JSON.stringify({
                email : email,
                password : password,
            }),
            async: false,
            success: function (data, status) {
                alert("성공!")
                location.href = "/fureverhomes/animal";
            },
            error: function (data, textStatus) {
                alert("로그인 정보가 맞지 않습니다.")
                location.href = "/fureverhomes/signin";
            }
        });
    });

});

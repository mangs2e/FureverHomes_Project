$(document).ready(function() {

    // 정규식 선언
    let regName = RegExp(/^[A-Za-z가-힣 ]+$/);
    let regEmail = RegExp(/^([a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,})$/);
    let regPwd = RegExp(/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,12}$/);

    // 변수 선언
    let emailCheck = true;
    let passwordCheck = true;
    let passwordConfirmCheck = true;
    let nameCheck = true;

    //datepicker
    $("#birthDate").datepicker({
    });

//이메일 유효성 검사
    $("#email").change(function () {
        if (!regEmail.test($("#email").val())) {
            $(".email").text("유효하지 않은 이메일 양식입니다.").css("color", "red");
            $("#email").attr("class", "form-control is-invalid");
            emailCheck = false;
        } else {
            $(".email").text("");
            $("#email").attr("class", "form-control is-valid");
            emailCheck = true;
        }
    });


//이름 유효성 검사
    $("#name").change(function () {
        if (!regName.test($("#name").val())) {
            $(".name").text("유효하지 않은 이름 양식입니다.").css("color", "red");
            $("#name").attr("class", "form-control is-invalid");
            nameCheck = false;
        } else {
            $(".name").text("");
            $("#name").attr("class", "form-control is-valid");
            nameCheck = true;
        }
    });

// 비밀번호 유효성 검사
    $("#password").change(function () {
        let password = $("#password").val();
        if (!regPwd.test(password)) {
            $(".password")
                .text("비밀번호는 영문, 숫자포함 6-12자여야합니다.")
                .css("color", "red");
            $("#password").attr("class", "form-control is-invalid");
            passwordCheck = false;
        } else {
            $(".password").text("");
            $("#password-confirm").focus();
            $("#password").attr("class", "form-control is-valid");
            passwordCheck = true;
        }
    });

// 비밀번호 일치 여부 검사
    $("#password-confirm").change(function () {
        let password = $("#password").val();
        let confirmedPassword = $("#password-confirm").val();

        if (password !== confirmedPassword) {
            $(".password-confirm")
                .text("비밀번호가 일치하지 않습니다.")
                .css("color", "red");
            $("#password-confirm").attr("class", "form-control is-invalid");
            passwordConfirmCheck = false;
        } else {
            $(".password-confirm").text("");
            $("#password-confirm").focus();
            $("#password-confirm").attr("class", "form-control is-valid");
            passwordConfirmCheck = true;
        }
    });

// 파일 이름 바꾸기
// $("#customFile").change(function () {
//     fileValue = $("#customFile").val().split("\\");
//     fileName = fileValue[fileValue.length - 1]; // 파일명
//     $("#showFiles").text(fileName);
// });

    $("#createAccount").click(function () {
        if (!emailCheck) {
            $("#email").focus();
            return false;
        } else if (!passwordCheck) {
            $("#password").focus();
            return false;
        } else if (!passwordConfirmCheck) {
            $("#password-confirm").focus();
            return false;
        } else if (!nameCheck) {
            $("#name").focus();
            return false;
        }

        let birthDate = $('#birthDate').val();
        let formattedBirthDate = formatDate(birthDate);

        // 날짜 형식 변환 함수 (MM/dd/yyyy -> yyyy-MM-dd)
        function formatDate(dateString) {
            let parts = dateString.split('/');
            if (parts.length === 3) {
                let month = parts[0];
                let day = parts[1];
                let year = parts[2];
                return `${year}-${month}-${day}`;
            }
            return dateString;
        }

        let data = {
            email: $('input[name="email"]').val(),
            name: $('input[name="name"]').val(),
            password: $('input[name="password"]').val(),
            sex: $('input[name="genderRadios"]:checked').val(),
            birth: formattedBirthDate
        };

        $.ajax({
            type: "POST",
            url: "/fureverhomes/signup.post",
            contentType: 'application/json',
            data: JSON.stringify(data),
            async: false,
            success: function (data, status) {
                let email = btoa(data);
                sessionStorage.setItem("email", email);
                alert("성공! 이메일 인증해주셈");
                // history.replaceState({}, null, location.pathname);
                // window.location.href = "/html/furever_Email-Auth.html";
                location.replace("/fureverhomes/signup/emailAuth");
            },
            error: function (data, textStatus) {
                alert("이미 존재하는 회원입니다. 로그인을 진행해주세요.")
                location.href = "/fureverhomes/signin";
            }
        });
    });
});

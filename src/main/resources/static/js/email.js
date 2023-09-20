$(document).ready(function () {

    let email = atob(sessionStorage.getItem("email"));

    let dataBody = $("#email-auth");
    let form = $("<form>", {id: "emailTokenForm"});
    let div1 = $("<div>", {class: "form-group"});
    let label1 = $("<label>", {text: "인증번호"});
    let div2 = $("<div>", {class: "form-group"});
    let div3 = $("<div>", {class: "input-group mb-4"});
    let div4 = $("<div>", {class: "input-group-prepend"});
    let span1 = $("<span>", {class: "input-group-text"});
    let span2 = $("<span>", {class: "fas fa-unlock-alt"});
    let input1 = $("<input>", {
        class: "form-control",
        id: "email-code",
        placeholder: "인증번호를 입력해주세요",
        type: "email_Token",
        "aria-label": "email_Token",
        required: true
    })
    let button1 = $("<button>", {
        type: "button",
        id: "auth-btn",
        class: "btn btn-block btn-warning",
        text: "인증하기"
    })


    $("#send-btn").click(function () {

        // dataBody 재구성
        dataBody.empty();
        dataBody.append(form);
        form.append(div1);
        div1.append(label1);
        div1.append(div2);
        div2.append(div3);
        div3.append(div4);
        div4.append(span1);
        span1.append(span2);
        div3.append(input1);
        form.append(button1);

        $.ajax({
            type: "POST",
            url: "/fureverhomes/signup/emailAuth.post",
            contentType: 'application/json',
            data: JSON.stringify({
                email: email
            }),
            success: function (data, status) {
            },
            error: function (data, textStatus) {
                alert("인증코드 전송에 실패하였습니다. 다시 시도하여 주세요.");
                location.href = "/fureverhomes/signup/emailAuth";
            },
            complete: function (data, textStatus) {
            },
        });
    });

    $(document).on('click', '#auth-btn', function (event) {
        event.preventDefault();

        let emailCode = $("#email-code").val();
        sessionStorage.removeItem("email");
        console.log(emailCode);

        $.ajax({
            type: "POST",
            url: "/fureverhomes/signup/success.post",
            contentType: 'application/json',
            data: JSON.stringify({
                email : email,
                emailCode : emailCode,
            }),
            success: function (data, status) {
                alert("가입 성공했습니다. 로그인해주세요.")
                location.href = "/fureverhomes/signin"
            },error: function (data, status) {
                alert("이메일인증에 실패했습니다. 다시 시도해주세요.")
                location.href = "/fureverhomes/signup"
            }
        });
    });
});
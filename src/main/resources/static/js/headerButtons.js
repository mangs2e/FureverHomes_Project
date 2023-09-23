$(document).ready(function () {

    $('#signOut-btn').click(function() {
        $.ajax({
            type: "POST",
            url: "/fureverhomes/signout.post",
            success: function () {
                alert("메인 페이지로 이동합니다.")
                location.href = "/fureverhomes/main";
            },
            error: function () {
                alert("로그아웃 실패!")
            }
        })
    });
});

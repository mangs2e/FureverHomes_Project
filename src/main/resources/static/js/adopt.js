let pathURI = window.location.pathname
const regex = /\/(\d+)$/;
const match = pathURI.match(regex);
const number = match[1];
let animalId = number;

// 정규식 선언
let regTel = RegExp(/^[0-9]+$/);
let regContactTime = RegExp(/^[a-zA-Z0-9가-힣 ,.:~-]+$/);
let regJob = RegExp(/^[a-zA-Z가-힣 ]+$/);

// 변수 선언
let telCheck = true;
let contactTimeCheck = true;
let jobCheck = true;

$(document).ready(function (){
    datailGet();

    //연락처 유효성 검사
    $("#phonenum").change(function () {
        if (!regTel.test($("#phonenum").val())) {
            $(".tel").text("숫자만 입력해주세요").css("color", "red");
            $("#phonenum").attr("class", "form-control is-invalid");
            telCheck = false;
        } else {
            $(".tel").text("");
            $("#phonenum").attr("class", "form-control is-valid");
            telCheck = true;
        }
    });


    //연락 시간 유효성 검사
    $("#contact_time").change(function () {
        if (!regContactTime.test($("#contact_time").val())) {
            $(".time").text("특수문자는 ~ , . - : 만 사용할 수 있습니다.").css("color", "red");
            $("#contact_time").attr("class", "form-control is-invalid");
            contactTimeCheck = false;
        } else {
            $(".time").text("");
            $("#contact_time").attr("class", "form-control is-valid");
            contactTimeCheck = true;
        }
    });

    //직업 유효성 검사
    $("#job").change(function () {
        if (!regJob.test($("#job").val())) {
            $(".job")
                .text("특수문자나 숫자는 입력할 수 없습니다.")
                .css("color", "red");
            $("#job").attr("class", "form-control is-invalid");
            jobCheck = false;
        } else {
            $(".job").text("");
            $("#job").attr("class", "form-control is-valid");
            jobCheck = true;
        }
    });

    $("#interest-btn").click(function () {

        let pathUrl = "/fureverhomes/detail/{animal_id}/interest".replace("{animal_id}", animalId);
        console.log(pathUrl);

        $.ajax({
            type: "POST",
            url: pathUrl,
            contentType: 'application/json',
            data: JSON.stringify({
                animalId
            }),
            async: false,
            success: function (data, status) {
            },
            error: function (data, textStatus) {
                alert("이미 등록된 동물입니다.")
            }
        });
    });

    $("#adopt-btn").click(function () {

        if (!telCheck) {
            alert("모달창을 닫고 입력형식을 확인해주세요.");
            $("#modal-default-2").hide();
            return false;
        }
        if (!contactTimeCheck) {
            alert("모달창을 닫고 입력형식을 확인해주세요.");
            $("#modal-default-2").hide();
            return false;
        }
        if (!jobCheck) {
            alert("모달창을 닫고 입력형식을 확인해주세요.");
            $("#modal-default-2").hide();
            return false;
        }

        let pathUrl = "/fureverhomes/detail/{animal_id}/adopt".replace("{animal_id}", animalId);
        console.log(pathUrl);
        let breed = $('input[name="breeding"]:checked').val();
        breed = breed === "Y";

        let data = {
            animalId : animalId,
            phonenum: $("#phonenum").val(),
            contact_time: $("#contact_time").val(),
            residence: $("#residence").val(),
            job: $("#job").val(),
            breeding: breed,
            adopt_reason: $("#adopt_reason").val(),
            add_comment: $("#add_comment").val()
        };

        $.ajax({
            type: "POST",
            url: pathUrl,
            contentType: 'application/json',
            data: JSON.stringify(data),
            async: false,
            success: function (data, status) {
                alert("등록됐습니다. 마이페이지를 확인해주세요.")
                location.reload();
            },
            error: function (data, textStatus) {
                alert("이미 신청된 내역입니다. 마이페이지를 확인해주세요.")
            }
        });
    });
});

function datailGet(){
    let imgSrc = "/assets/img/animal/" + animalId + ".jpg";
    let img = $("<img>", {
        src: imgSrc,
        className: "card-img-top border-0",
        style: "border-radius: 70%",
        alt: "animal-picture",
    });

    let getUrl = "/fureverhomes/detail/{animal_id}/getDetail";
    getUrl = getUrl.replace("{animal_id}", animalId);
    console.log(getUrl);

    $.ajax({
        url: getUrl,
        type: "GET",
        async: false,
        success: function (data, status, xhr) {
            if (data.personality === null) {
                $("#personality").val("정보 없음");
            } else {
                $("#personality").val(data.personality);
            }
            if (data.health_condition === null) {
                $("#health-condition").val("정보 없음");
            } else {
                $("#health-condition").val(data.health_condition);
            }
            if (data.neuter === true) {
                $("#neuter").val("중성화 완료");
            }
            else if (data.neuter === false) {
                $("#neuter").val("중성화 미완료");
            } else $("#neuter").val("확인불가");
            $("#shelter-name").val(data.shelter_name);
            if (data.shelter_tel === null) {
                $("#shelter-tel").val("정보 없음");
            } else {
                $("#shelter-tel").val(data.shelter_tel);
            }
            $("#animal-name").text(data.name);
            $("#profile-img").append(img);
            if (data.sex === "F") {
                $("#animal-sex").append('<svg xmlns="http://www.w3.org/2000/svg" width="20" height="16" fill="gray" class="bi bi-gender-female" viewBox="0 0 16 16">\n' +
                    '                                    <path fill-rule="evenodd" d="M8 1a4 4 0 1 0 0 8 4 4 0 0 0 0-8zM3 5a5 5 0 1 1 5.5 4.975V12h2a.5.5 0 0 1 0 1h-2v2.5a.5.5 0 0 1-1 0V13h-2a.5.5 0 0 1 0-1h2V9.975A5 5 0 0 1 3 5z"/>\n' +
                    '                                </svg>');
            } else if (data.sex === "M") {
                $("#animal-sex").append('<svg xmlns="http://www.w3.org/2000/svg" width="20" height="16" fill="gray" class="bi bi-gender-male" viewBox="0 0 16 16">\n' +
                    '  <path fill-rule="evenodd" d="M9.5 2a.5.5 0 0 1 0-1h5a.5.5 0 0 1 .5.5v5a.5.5 0 0 1-1 0V2.707L9.871 6.836a5 5 0 1 1-.707-.707L13.293 2H9.5zM6 6a4 4 0 1 0 0 8 4 4 0 0 0 0-8z"/>\n' +
                    '</svg>');
            };
            $("#age").text(data.age);
            $("#animal-region").text(data.region);

        },error: function () {
            alert("페이지를 불러오는데 실패했습니다.")
        }
    });
}
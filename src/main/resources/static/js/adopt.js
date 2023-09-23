$(document).ready(function (){
    datailGet();
});

function datailGet(){
    let pathURI = window.location.pathname
    const regex = /\/(\d+)$/;
    const match = pathURI.match(regex);
    const number = match[1];
    let animalId = number;
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
            let jsonData = JSON.parse(data);
            console.log(jsonData)

            $("#personality").val(jsonData.personality);
            $("#health-condition").val(jsonData.health);
            if (jsonData.neuter === true) {
                $("#neuter").val("중성화 완료");
            }
            else if (jsonData.neuter === false) {
                $("#neuter").val("중성화 미완료");
            } else $("#neuter").val("확인불가");
            $("#shelter-name").val(jsonData.shelter_name);
            $("#shelter-tel").val(jsonData.shelter_tel);
            $("#animal-name").text(jsonData.name);
            $("#profile-img").append(img);
            if (jsonData.sex === "F") {
                $("#animal-sex").append('<svg xmlns="http://www.w3.org/2000/svg" width="20" height="16" fill="gray" class="bi bi-gender-female" viewBox="0 0 16 16">\n' +
                    '                                    <path fill-rule="evenodd" d="M8 1a4 4 0 1 0 0 8 4 4 0 0 0 0-8zM3 5a5 5 0 1 1 5.5 4.975V12h2a.5.5 0 0 1 0 1h-2v2.5a.5.5 0 0 1-1 0V13h-2a.5.5 0 0 1 0-1h2V9.975A5 5 0 0 1 3 5z"/>\n' +
                    '                                </svg>');
            } else if (jsonData.sex === "M") {
                $("#animal-sex").append('<svg xmlns="http://www.w3.org/2000/svg" width="20" height="16" fill="gray" class="bi bi-gender-male" viewBox="0 0 16 16">\n' +
                    '  <path fill-rule="evenodd" d="M9.5 2a.5.5 0 0 1 0-1h5a.5.5 0 0 1 .5.5v5a.5.5 0 0 1-1 0V2.707L9.871 6.836a5 5 0 1 1-.707-.707L13.293 2H9.5zM6 6a4 4 0 1 0 0 8 4 4 0 0 0 0-8z"/>\n' +
                    '</svg>');
            };
            $("#age").text(jsonData.age);
            $("#animal-region").text(jsonData.region);

        },error: function () {
            alert("페이지를 불러오는데 실패했습니다.")
        }
    });
}
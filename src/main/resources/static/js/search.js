$(document).ready(function (){
    searchGet(1,null,null,null);

    $("#search-form").submit(function (e){
        e.preventDefault();

        let species = $("#species").val();
        let sex = $("#sex").val();
        let region = $("#region").val();

        searchGet(1, species, sex, region);
    });
});

function searchGet(page, species, sex, region){
    let apiUrl = "/fureverhomes/animal.list?page={page}&size={size}"
    apiUrl = apiUrl.replace("{page}",page);
    apiUrl = apiUrl.replace("{size}",6);

    if(species) {
        apiUrl += "&species=" + species;
    }
    if(sex) {
        apiUrl += "&sex=" + sex;
    }
    if(region) {
        apiUrl += "&region=" + region;
    }

    $.ajax({
        url: apiUrl,
        type: "GET",
        async: true,
        success: function (data, status, xhr) {
            let all_list = data.animals;
            let total = data.totalCount;

            let dataBody = $("#animal-card");
            dataBody.empty();

            for (let i = 0; i < all_list.length; i++) {
                let animal = all_list[i];

                let link = "/fureverhomes/detail/";
                let imgSrc = "/assets/img/animal/"
                let imgId = animal.id;

                link = link + imgId;
                imgSrc = imgSrc + imgId + ".jpg";

                let div1 = $("<div>", {class: "col-12 col-md-6 mb-3"});
                let div2 = $("<div>", {class: "card border-light mb-4 animate-up-5"});
                let div3 = $("<div>", {class: "row no-gutters align-items-center"});
                let div4 = $("<div>", {class: "col-12 col-lg-6 col-xl-4"});
                let a1 = $("<a>", {href: link})
                let img = $("<img>", {
                    src: imgSrc,
                    alt: "animal-picture",
                    className: "card-img p-2 rounded-xl",
                    id: imgId,
                    style: "object-fit: cover; width: 100%; height: 230px;"
                });

                let div5 = $("<div>", {class: "col-12 col-lg-6 col-xl-8"});

                let div6 = $("<div>", {class: "card-body"});
                let div7 = $("<div>", {class: "mb-3"});

                let span1 = $("<span>", {
                        class: "h5 mb-3",
                        text: animal.name
                    }
                )
                let heartSpan = $("<span>", {style: "float: right"})

                let female = '<svg xmlns="http://www.w3.org/2000/svg" width="20" height="16" fill="gray" class="bi bi-gender-female" viewBox="0 0 16 16"><path fill-rule="evenodd" d="M8 1a4 4 0 1 0 0 8 4 4 0 0 0 0-8zM3 5a5 5 0 1 1 5.5 4.975V12h2a.5.5 0 0 1 0 1h-2v2.5a.5.5 0 0 1-1 0V13h-2a.5.5 0 0 1 0-1h2V9.975A5 5 0 0 1 3 5z"/></svg>';
                let male = '<svg xmlns="http://www.w3.org/2000/svg" width="20" height="16" fill="gray" class="bi bi-gender-male" viewBox="0 0 16 16">\n' +
                    '  <path fill-rule="evenodd" d="M9.5 2a.5.5 0 0 1 0-1h5a.5.5 0 0 1 .5.5v5a.5.5 0 0 1-1 0V2.707L9.871 6.836a5 5 0 1 1-.707-.707L13.293 2H9.5zM6 6a4 4 0 1 0 0 8 4 4 0 0 0 0-8z"/>\n' +
                    '</svg>';
                let $female = $(female)
                let $male = $(male)
                let region = animal.region;
                switch (region) {
                    case "SEOUL":
                        region = "서울";
                        break;
                    case "INCHEON":
                        region = "인천";
                        break;
                    case "DAEJEON":
                        region = "대전";
                        break;
                    case "DAEGU":
                        region = "대구";
                        break;
                    case "ULSAN":
                        region = "울산";
                        break;
                    case "BUSAN":
                        region = "부산";
                        break;
                    case "GWANGJU":
                        region = "광주";
                        break;
                    case "SEJONG":
                        region = "세종";
                        break;
                    case "GYEONGGI":
                        region = "경기도";
                        break;
                    case "GANGWON":
                        region = "강원도";
                        break;
                    case "CHU도GCHEONG":
                        region = "충청도";
                        break;
                    case "GYEONGSANG":
                        region = "경상도";
                        break;
                    case "JEOLLA":
                        region = "전라도";
                        break;
                    case "JEJU":
                        region = "제주도";
                        break;
                }

                let span2 = $("<span>", {
                        class: "h6 mb-3",
                        text: animal.age
                    }
                )

                let div8 = $("<div>", {class: "mb-5", style: "color:#424767;"});

                let span3 = $("<span>", {class: "fas fa-map-marker-alt mr-2"});
                let span4 = $("<span>", {text: region});

                dataBody.append(div1);
                div1.append(a1);
                a1.append(div2);
                div2.append(div3);
                div3.append(div4);
                div4.append(img);
                div3.append(div5);
                div5.append(div6);
                div6.append(div7);
                div7.append(span1);
                div7.append(heartSpan);
                if (animal.sex === "F") {
                    heartSpan.append($female);
                } else if (animal.sex === "M") {
                    heartSpan.append($male)
                }
                heartSpan.append(span2);
                div6.append(div8);
                div8.append(span3);
                div8.append(span4);
            }
            pagination(page,total);
        },error: function (xhr) {
            alert("페이지를 불러오는데 실패했습니다.")
        }
    });
}

// 페이지 링크에 대한 클릭 이벤트 리스너 추가
$(document).on("click", ".pagination .page-link", function (event) {
    event.preventDefault();
    let pageNumber = parseInt($(this).data("page"));
    if (!isNaN(pageNumber)) {

        let species = $("#species").val();
        let sex = $("#sex").val();
        let region = $("#region").val();

        searchGet(pageNumber, species, sex, region);
    }
});

function pagination(currentPage, total) { //(페이지,데이터갯수)
    let totalPages = Math.ceil(total / 6); //페이지 수 (ex. 한 페이지에 6개이고 데이터가 60개면 10페이지)
    let pageGroupSize = 5; //한번에 보여줄 페이지 갯수
    let startPage = Math.ceil(currentPage / pageGroupSize) * pageGroupSize - pageGroupSize + 1;
    let endPage = Math.min(startPage + pageGroupSize - 1, totalPages);

    if(total === 0) {
        alert("검색 결과가 없습니다.")
        location.reload();
    }
    let pagination = $(".pagination");
    pagination.empty();

    // 이전 페이지 링크 추가
    let prevDisabled = startPage === 1 ? "disabled" : "";
    if(total > 0) {
        pagination.append(`<li class="page-item ${prevDisabled}">
                      <a class="page-link" href="#" aria-label="Previous" data-page="${startPage - 1}">Previous</a>
                    </li>`);
    }

    // 페이지 번호 링크 추가
    for (let i = startPage; i <= endPage; i++) {
        let activeClass = i === currentPage ? "active" : "";
        let listItem = `<li class="page-item ${activeClass}">
                <a class="page-link" href="#" data-page="${i}">${i}</a>
              </li>`;
        pagination.append(listItem);
    }

    // 다음 페이지 링크 추가
    let nextDisabled = endPage === totalPages ? "disabled" : "";
    if(total > 0) {
        pagination.append(`<li class="page-item ${nextDisabled}">
                      <a class="page-link" href="#" aria-label="Next" data-page="${endPage + 1}">Next</a>
                    </li>`);
    }
}